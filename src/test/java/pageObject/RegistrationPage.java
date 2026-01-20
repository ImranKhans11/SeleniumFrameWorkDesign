package pageObject;

import baseClass.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BaseClass {

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "input-firstname")
    WebElement txtFirstName;

    @FindBy(id = "input-lastname")
    WebElement txtLastName;

    @FindBy(id= "input-email")
    WebElement txtEmail;

    @FindBy(id = "input-telephone")
    WebElement txtTelephone;

    @FindBy(id="input-password")
    WebElement txtPassword;

    @FindBy(id="input-confirm")
    WebElement txtCnfrmPassword;

    @FindBy(xpath = "//input[@name='newsletter' and @value='0']")
    WebElement btnNewsLetter;

    @FindBy(xpath = "//input[@name = 'agree']")
    WebElement agreePrivacypolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//h1[text()='Your Account Has Been Created!']")
    WebElement txtConfirmationMsg;

    public void setTxtFirstName(String firstName) {
        txtFirstName.sendKeys(firstName);
    }

    public void setTxtLastName(String lastName) {
        txtLastName.sendKeys(lastName);
    }

    public void setTxtEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setTxtTelephone(String phone) {
        txtTelephone.sendKeys(phone);
    }

    public void setTxtPassword(String password) {
        txtPassword.sendKeys(password);
    }

    public void setTxtCnfrmPassword(String password) {
        txtCnfrmPassword.sendKeys(password);
    }

    public void setBtnNewsLetter() {
        boolean newsletterNoBtn = btnNewsLetter.isSelected();
        if(!newsletterNoBtn) {
            btnNewsLetter.click();
        }
    }

    public void setAgreePrivacypolicy() {
        boolean agreePvcyPlcy = agreePrivacypolicy.isSelected();
        if(!agreePvcyPlcy) {
            agreePrivacypolicy.click();
        }
    }

    public void setBtnContinue() {
        btnContinue.click();
    }

    public String confirmMsg() {
        String actualConfirmMsg = txtConfirmationMsg.getText();
        return actualConfirmMsg;
    }
}

