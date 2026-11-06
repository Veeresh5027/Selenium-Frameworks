package base;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utilities.ReadConfig;

public class BaseTest {

    protected WebDriver driver;
    protected ReadConfig config;

    protected Logger logger = LogManager.getLogger(BaseTest.class);

    @SuppressWarnings("deprecation")
	@BeforeMethod
    public void setup() {

        config = new ReadConfig();

        String browser = config.getBrowser();

        logger.info("Starting browser: " + browser);

        DriverFactory.initDriver(browser);

        driver = DriverFactory.getDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(
                config.getImplicitWait(), TimeUnit.SECONDS);

        driver.manage().timeouts().pageLoadTimeout(
                config.getPageLoadTimeout(), TimeUnit.SECONDS);

        driver.get(config.getApplicationUrl());

        logger.info("Navigated to application: "
                + config.getApplicationUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        logger.info("Test execution completed. Observing browser for 4 seconds...");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Browser observation delay interrupted", e);
        } finally {
            logger.info("Closing browser session");
            DriverFactory.quitDriver();
            logger.info("Browser session closed");
        }
    }
}