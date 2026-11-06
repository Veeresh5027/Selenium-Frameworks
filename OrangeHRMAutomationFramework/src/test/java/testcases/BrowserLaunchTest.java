package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class BrowserLaunchTest extends BaseTest {

    @Test
    public void verifyBrowserLaunch() {

        logger.info("Browser launch test started");

        Assert.assertNotNull(driver,
                "WebDriver initialization failed");

        logger.info("Browser launched successfully");

        logger.info("Current URL: " + driver.getCurrentUrl());

        logger.info("Browser launch test completed");
    }
}