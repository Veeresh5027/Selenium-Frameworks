
package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.WaitUtils;

public class LoginPage {

    private WebDriver driver;

    private static final int TIMEOUT = 20;

    // Username field
    @FindBy(name = "username")
    private WebElement usernameField;

    // Password field
    @FindBy(name = "password")
    private WebElement passwordField;

    // Login button
    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    // Forgot password link
    @FindBy(css = "p.orangehrm-login-forgot-header")
    private WebElement forgotPasswordLink;

    // Login error message
    @FindBy(css = ".oxd-alert-content-text")
    private WebElement loginErrorMessage;

    // Constructor
    public LoginPage(WebDriver driver) {

        this.driver = driver;

        // Initialize all @FindBy WebElements
        PageFactory.initElements(driver, this);
    }

    // Verify login page is displayed
    public boolean isLoginPageDisplayed() {

        return WaitUtils.waitForVisibility(
                driver, By.name("username"), TIMEOUT).isDisplayed()
                && WaitUtils.waitForVisibility(
                driver, By.name("password"), TIMEOUT).isDisplayed()
                && WaitUtils.waitForVisibility(
                driver, By.cssSelector("button[type='submit']"),
                TIMEOUT).isDisplayed();
    }

    // Enter username
    public void enterUsername(String username) {

        WaitUtils.waitForVisibility(
                driver, By.name("username"), TIMEOUT);

        usernameField.clear();
        usernameField.sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {

        WaitUtils.waitForVisibility(
                driver, By.name("password"), TIMEOUT);

        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Click login button
    public void clickLogin() {

        WaitUtils.waitForClickable(
                driver, By.cssSelector("button[type='submit']"),
                TIMEOUT);

        loginButton.click();
    }

    // Reusable login method
    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Verify password masking
    public boolean isPasswordMasked() {

        return "password".equalsIgnoreCase(
                passwordField.getAttribute("type"));
    }

    // Verify forgot password link is displayed
    public boolean isForgotPasswordLinkDisplayed() {

        return WaitUtils.waitForVisibility(
                driver,
                By.cssSelector("p.orangehrm-login-forgot-header"),
                TIMEOUT).isDisplayed();
    }

    // Click forgot password link
    public void clickForgotPassword() {

        WaitUtils.waitForClickable(
                driver,
                By.cssSelector("p.orangehrm-login-forgot-header"),
                TIMEOUT);

        forgotPasswordLink.click();
    }

    // Get login error message
    public String getLoginErrorMessage() {

        return WaitUtils.waitForVisibility(
                driver,
                By.cssSelector(".oxd-alert-content-text"),
                TIMEOUT).getText();
    }

    // Get field validation messages
    public List<String> getFieldValidationMessages() {

        List<WebElement> elements = driver.findElements(
                By.cssSelector(".oxd-input-field-error-message"));

        List<String> messages = new ArrayList<String>();

        for (WebElement element : elements) {
            messages.add(element.getText());
        }

        return messages;
    }

    // Login using ENTER key
    public void loginUsingEnterKey(String username, String password) {

        enterUsername(username);
        enterPassword(password);

        passwordField.sendKeys(Keys.ENTER);
    }

    // Get current URL
    public String getCurrentUrl() {

        return driver.getCurrentUrl();
    }
}
