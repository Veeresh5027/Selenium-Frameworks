
package testcases;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import dataproviders.LoginDataProvider;
import pages.LoginPage;
import utilities.ExcelUtils;

public class LoginTest extends BaseTest {

    private static final String EXCEL_PATH =
            "src/test/resources/testdata/OrangeHRM_TestData.xlsx";

    private static final String SHEET_NAME = "Login";

    // TC_LOGIN_001 to TC_LOGIN_006
    // Excel-driven login scenarios
    @Test(
        priority = 1,
        dataProvider = "loginData",
        dataProviderClass = LoginDataProvider.class
    )
    public void verifyLoginWithExcelData(
            String username,
            String password,
            String expectedResult) {

        logger.info("Starting login test. Expected result: " + expectedResult);

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("Valid")) {

            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.urlContains("dashboard"));

            Assert.assertTrue(
                    loginPage.getCurrentUrl().contains("dashboard"),
                    "Login failed with valid credentials"
            );

            logger.info("Valid login verified successfully");

        } else if (expectedResult.equalsIgnoreCase("Invalid")) {

            String actualMessage = loginPage.getLoginErrorMessage();

            Assert.assertEquals(
                    actualMessage,
                    "Invalid credentials",
                    "Unexpected error message for invalid login"
            );

            logger.info("Invalid credentials validation verified");

        } else if (expectedResult.equalsIgnoreCase("UsernameRequired")) {

            List<String> actualMessages =
                    loginPage.getFieldValidationMessages();

            List<String> expectedMessages =
                    Arrays.asList("Required");

            Assert.assertEquals(
                    actualMessages,
                    expectedMessages,
                    "Username required validation failed"
            );

            logger.info("Username required validation verified");

        } else if (expectedResult.equalsIgnoreCase("PasswordRequired")) {

            List<String> actualMessages =
                    loginPage.getFieldValidationMessages();

            List<String> expectedMessages =
                    Arrays.asList("Required");

            Assert.assertEquals(
                    actualMessages,
                    expectedMessages,
                    "Password required validation failed"
            );

            logger.info("Password required validation verified");

        } else if (expectedResult.equalsIgnoreCase("BothRequired")) {

            List<String> actualMessages =
                    loginPage.getFieldValidationMessages();

            List<String> expectedMessages =
                    Arrays.asList("Required", "Required");

            Assert.assertEquals(
                    actualMessages,
                    expectedMessages,
                    "Both fields required validation failed"
            );

            logger.info("Both fields required validation verified");

        } else {

            Assert.fail(
                    "Unknown ExpectedResult in Excel: " + expectedResult
            );
        }
    }

    // TC_LOGIN_007 - Verify Password Masking
    @Test(priority = 2)
    public void verifyPasswordMasking() {

        logger.info("Starting password masking test");

        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Login page is not displayed"
        );

        Assert.assertTrue(
                loginPage.isPasswordMasked(),
                "Password field is not masked"
        );

        logger.info("Password masking verified successfully");
    }

    // TC_LOGIN_008 - Verify Forgot Password Navigation
    @Test(priority = 3)
    public void verifyForgotPasswordNavigation() {

        logger.info("Starting forgot password navigation test");

        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(
                loginPage.isForgotPasswordLinkDisplayed(),
                "Forgot Password link is not displayed"
        );

        loginPage.clickForgotPassword();

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.urlContains(
                        "requestPasswordResetCode"
                ));

        Assert.assertTrue(
                loginPage.getCurrentUrl().contains(
                        "requestPasswordResetCode"
                ),
                "Forgot Password navigation failed"
        );

        logger.info("Forgot Password navigation verified successfully");
    }

    // TC_LOGIN_009 - Verify Login Using ENTER Key
    @Test(priority = 4)
    public void verifyLoginUsingEnterKey() {

        logger.info("Starting ENTER key login test");

        String username =
                ExcelUtils.getCellData(EXCEL_PATH, SHEET_NAME, 1, 0);

        String password =
                ExcelUtils.getCellData(EXCEL_PATH, SHEET_NAME, 1, 1);

        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginUsingEnterKey(username, password);

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.urlContains("dashboard"));

        Assert.assertTrue(
                loginPage.getCurrentUrl().contains("dashboard"),
                "Login using ENTER key failed"
        );

        logger.info("ENTER key login verified successfully");
    }
}
