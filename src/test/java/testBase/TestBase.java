package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class TestBase {

    public WebDriver driver;
    public Logger logger;
    public Properties prop;

    @BeforeClass(groups = {"Sanity", "Regression", "Master", "Datadriven"})
    @Parameters({"os", "browser"})
    public void setUp(String os, String br) throws IOException {

        FileReader file = new FileReader(".\\src\\test\\resources\\config.properties");
        prop = new Properties();
        prop.load(file);

        logger = LogManager.getLogger(this.getClass());

        if(prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
//            DesiredCapabilities dc = new DesiredCapabilities();
//
//            switch (os.toLowerCase()) {
//                case "windows" : dc.setPlatform(Platform.WINDOWS); break;
//                case "mac" : dc.setPlatform(Platform.MAC); break;
//                case "linux": dc.setPlatform(Platform.LINUX); break;
//                default:
//                    System.out.println("Invalid Platform");
//                    return;
//            }
//
//            switch (br.toLowerCase()) {
//                case("chrome") : dc.setBrowserName("chrome"); break;
//                case("edge") : dc.setBrowserName("edge"); break;
//                case("firefox") : dc.setBrowserName("firefox"); break;
//                default:
//                    System.out.println("Invalid browser"); return;
//            }
//
//            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
//
//        }
//
//        if(prop.getProperty("execution_env").equalsIgnoreCase("local")) {
//            switch (br.toLowerCase()) {
//                case("chrome") : driver = new ChromeDriver(); break;
//                case("edge") : driver = new EdgeDriver(); break;
//                case("firefox") : driver = new FirefoxDriver(); break;
//                default:
//                    System.out.println("Invalid browser"); return;
//            }
//        }
            switch (br.toLowerCase()) {

                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments(
                            "--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--disable-gpu",
                            "--window-size=1920,1080"
                    );
                    chromeOptions.setPlatformName(os);
                    driver = new RemoteWebDriver(
                            new URL("http://localhost:4444"),
                            chromeOptions
                    );
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--width=1920", "--height=1080");
                    firefoxOptions.setPlatformName(os);
                    driver = new RemoteWebDriver(
                            new URL("http://localhost:4444"),
                            firefoxOptions
                    );
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.setPlatformName(os);
                    driver = new RemoteWebDriver(
                            new URL("http://localhost:4444"),
                            edgeOptions
                    );
                    break;

                default:
                    throw new RuntimeException("Invalid browser value in config file: " + br);
            }

        } else if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {

                case "chrome":
                    driver = new ChromeDriver();
                    break;

                case "firefox":
                    driver = new FirefoxDriver();
                    break;

                case "edge":
                    driver = new EdgeDriver();
                    break;

                default:
                    throw new RuntimeException("Invalid browser value in config file: " + br);
            }

        } else {
            throw new RuntimeException("Invalid execution_env value in config file");
        }

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("baseURL"));
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master", "Datadriven"})
    public void teardown() {
        driver.quit();
    }

    public String randomAlphabet() {
        String randAlphabet = RandomStringUtils.randomAlphabetic(5);

        return randAlphabet;
    }

    public String randomNumeric() {
        String randNumeric = RandomStringUtils.randomNumeric(10);

        return randNumeric;
    }

    public String randomAlphaNumeric() {
        String randAlphaNumeric = RandomStringUtils.randomAlphanumeric(8);

        return randAlphaNumeric;
    }

    public String captureScreenshot(String testName) {
        try {
            String timestamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            String path = System.getProperty("user.dir")
                    + "/screenshots/" + testName + "_" + timestamp + ".png";

            File src =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            Files.copy(src.toPath(), new File(path).toPath());

            return path;

        } catch (Exception e) {
            System.err.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
}
