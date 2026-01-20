package pageObject;

import baseClass.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BaseClass {
    WebDriver driver;
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "email")
    WebElement txtEmail;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(xpath = "//input[@class='btn btn-primary']")
    WebElement btnLogin;

    public void setTxtEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setTxtPassword(String password) {
        txtPassword.sendKeys(password);
    }

    public void setBtnLogin() {
        btnLogin.click();
    }

}
