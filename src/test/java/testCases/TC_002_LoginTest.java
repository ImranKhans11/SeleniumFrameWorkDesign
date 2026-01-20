package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.TestBase;

public class TC_002_LoginTest extends TestBase {

    @Test(groups = {"Regression", "Master"})
    public void verifyLoginTest() {

        logger.info("***** Test case Started *****");
        HomePage hp = new HomePage(driver);
        LoginPage lp = new LoginPage(driver);
        MyAccountPage map = new MyAccountPage(driver);

        logger.info("***** Click on My Account link *****");
        hp.clickMyAccount();
        logger.info("***** Click on Login link *****");
        hp.clickLogin();

        logger.info("***** Fill details *****");
        lp.setTxtEmail(prop.getProperty("email"));
        lp.setTxtPassword(prop.getProperty("password"));

        logger.info("***** Click on Login button *****");
        lp.setBtnLogin();

        boolean MyAccountPageText = map.isMyAccountPageExists();

        logger.info("***** verify My Account page is displayed *****");
        Assert.assertTrue(MyAccountPageText);
    }
}
