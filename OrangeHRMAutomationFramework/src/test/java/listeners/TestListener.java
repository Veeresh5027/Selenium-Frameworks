package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.DriverFactory;
import reports.AllureManager;
import utilities.ScreenshotUtils;

public class TestListener implements ITestListener {

    private static final Logger logger =
            LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {

        logger.info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        logger.info("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        logger.error("Test failed: " + result.getName(),
                result.getThrowable());

        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {

            try {
                byte[] screenshot =
                        ScreenshotUtils.captureScreenshot(
                                driver, result.getName());

                AllureManager.attachScreenshot(
                        "Failure Screenshot - " + result.getName(),
                        screenshot);

                logger.info("Failure screenshot captured successfully");

            } catch (Exception e) {

                logger.error(
                        "Unable to capture or attach failure screenshot",
                        e);
            }

        } else {

            logger.warn("WebDriver is null. Screenshot cannot be captured.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        logger.warn("Test skipped: " + result.getName());
    }
}