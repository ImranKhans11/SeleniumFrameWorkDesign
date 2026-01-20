package utilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.testng.*;
import testBase.TestBase;

import java.awt.Desktop;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/* ===================== EXTENT REPORT LISTENER ===================== */
public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static String reportPath;
    private static Properties prop = new Properties();

    /* ===================== LOAD PROPERTIES ===================== */
    static {
        try (FileInputStream fis =
                     new FileInputStream("src/test/resources/config.properties")) {
            prop.load(fis);
        } catch (Exception e) {
            System.err.println("Property load failed: " + e.getMessage());
        }
    }

    /* ===================== SUITE START ===================== */
    @Override
    public void onStart(ITestContext context) {
        try {
            String timestamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            reportPath = System.getProperty("user.dir")
                    + "/reports/Automation_Report_" + timestamp + ".html";

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("Execution Summary");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("User name",
                    System.getProperty("user.name"));

            String os = context.getCurrentXmlTest().getParameter("os");
            extent.setSystemInfo("Operating System", os);
            String browser = context.getCurrentXmlTest().getParameter("browser");
            extent.setSystemInfo("Browser", browser);

            for (String group : context.getIncludedGroups()) {
                extent.setSystemInfo("Group", group);
            }

        } catch (Exception e) {
            System.err.println("Extent init failed: " + e.getMessage());
        }
    }

    /* ===================== TEST START ===================== */
    @Override
    public void onTestStart(ITestResult result) {
        try {
            // Test name = Class name
            String className = result.getTestClass().getName();
            test = extent.createTest(className);

            test.assignCategory(result.getMethod().getGroups());

        } catch (Exception e) {
            System.err.println("Test creation failed: " + e.getMessage());
        }
    }

    /* ===================== SUCCESS ===================== */
    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    /* ===================== FAILURE ===================== */
    @Override
    public void onTestFailure(ITestResult result) {
        try {
            test.log(Status.FAIL, result.getThrowable());

            // Get BaseTest instance
            TestBase baseTest =
                    (TestBase) result.getInstance();

            String screenshotPath =
                    baseTest.captureScreenshot(
                            result.getTestClass().getName());

            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
            }

        } catch (Exception e) {
            System.err.println("Failure handling error: " + e.getMessage());
        }
    }

    /* ===================== SKIPPED ===================== */
    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, result.getThrowable());
    }

    /* ===================== SUITE FINISH ===================== */
    @Override
    public void onFinish(ITestContext context) {
        try {
            if (extent != null) {
                extent.flush();
            }
            Desktop.getDesktop()
                    .browse(new File(reportPath).toURI());
        } catch (Exception e) {
            System.err.println("Report open failed: " + e.getMessage());
        }
    }
}
