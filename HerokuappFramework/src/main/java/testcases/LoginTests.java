package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    @Test(priority = 1)
    public void testValidLogin() {
        driver.get(readConfig.getApplicationUrl() + "login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.enterUsername(readConfig.getUsername());
        loginPage.enterPassword(readConfig.getPassword());
        loginPage.clickLogin();

        String message = loginPage.getFlashMessage();
        Assert.assertTrue(message.contains("You logged into a secure area!"), "Login failed!");
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        driver.get(readConfig.getApplicationUrl() + "login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.enterUsername("wrongUser");
        loginPage.enterPassword("wrongPassword");
        loginPage.clickLogin();

        String message = loginPage.getFlashMessage();
        Assert.assertTrue(message.contains("Your username is invalid!"), "Error message mismatch!");
    }
}