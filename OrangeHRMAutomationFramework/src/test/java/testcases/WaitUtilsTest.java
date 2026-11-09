package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.WaitUtils;

public class WaitUtilsTest extends BaseTest {

    @Test
    public void verifyExplicitWait() {

        WebElement username = WaitUtils.waitForVisibility(
                driver,
                By.name("username"),
                20
        );

        Assert.assertTrue(username.isDisplayed());

        logger.info("Explicit wait verified successfully");
    }
}