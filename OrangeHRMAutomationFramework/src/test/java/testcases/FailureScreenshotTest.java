package testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class FailureScreenshotTest extends BaseTest {

    @Test
    public void verifyFailureScreenshot() {

        logger.info("Starting intentional failure test");

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(20));

        // Wait until OrangeHRM username field is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("username")));

        logger.info("OrangeHRM login page is fully displayed");

        // Intentionally fail after page is displayed
        Assert.fail("Intentional failure to verify screenshot capture");
    }
}