package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.TestBase;
import utilities.DataProviders;

public class TC_003_LoginDDTTest extends TestBase {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven")
    public void loginDDT(String email, String password, String expStatus) {

        try {

            logger.info("***** Test Case execution started *****");
            HomePage hp = new HomePage(driver);

            logger.info("***** Click on My Account link *****");
            hp.clickMyAccount();

            logger.info("***** Click on Login link *****");
            hp.clickLogin();

            LoginPage lp = new LoginPage(driver);

            logger.info("***** Filling the details *****");
            lp.setTxtEmail(email);
            lp.setTxtPassword(password);
            lp.setBtnLogin();

            MyAccountPage map = new MyAccountPage(driver);

            boolean myAccountPageDisplayed = map.isMyAccountPageExists();

            if (expStatus.equalsIgnoreCase("valid")) {
                if (myAccountPageDisplayed) {
                    map.Logout();
                    Assert.assertTrue(true);
                } else
                    Assert.fail();
            }

            if (expStatus.equalsIgnoreCase("Invalid")) {
                if (myAccountPageDisplayed) {
                    map.Logout();
                    Assert.fail();
                } else
                    Assert.assertTrue(true);
            }
        }
        catch (Exception e) {
            Assert.fail();
        }
        finally {
            logger.info("***** Test Case execution ended ***** ");
        }
    }
}
