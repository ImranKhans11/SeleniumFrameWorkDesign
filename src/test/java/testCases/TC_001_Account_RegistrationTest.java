package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.HomePage;
import pageObject.RegistrationPage;
import testBase.TestBase;

public class TC_001_Account_RegistrationTest extends TestBase {

    @Test(groups = {"Sanity", "Master"})
    public void verify_Register_Account() {

        try {
            logger.info("******* Test Case Execution Started *******");

            HomePage homePage = new HomePage(driver);
            RegistrationPage registrationPage = new RegistrationPage(driver);

            logger.info("Click on MyAccount Link");
            homePage.clickMyAccount();
            logger.info("Click on Register Link");
            homePage.clickRegister();

            logger.info("Fill the Registration Form");
            registrationPage.setTxtFirstName(randomAlphabet().toUpperCase());
            registrationPage.setTxtLastName(randomAlphabet().toUpperCase());
            registrationPage.setTxtEmail(randomAlphaNumeric() + "@gmail.com");
            registrationPage.setTxtTelephone(randomNumeric());

            String password = randomAlphaNumeric();

            registrationPage.setTxtPassword(password);
            registrationPage.setTxtCnfrmPassword(password);
            registrationPage.setBtnNewsLetter();
            registrationPage.setAgreePrivacypolicy();
            registrationPage.setBtnContinue();

            String expectedConfirmMsg = "Your Account Has Been Created!";

            Assert.assertEquals(registrationPage.confirmMsg(), expectedConfirmMsg);
            logger.info("******* Test Case Execution Ended *******");
        } catch (RuntimeException e) {
            logger.error("Error logs generated");
            logger.debug("Debug logs generated");
            Assert.fail();
        }
    }
}
