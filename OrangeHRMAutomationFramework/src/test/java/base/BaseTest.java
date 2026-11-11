
package base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utilities.ReadConfig;

public class BaseTest {

    protected WebDriver driver;
    protected ReadConfig config;

    protected Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setup() {

        config = new ReadConfig();

        String browser = config.getBrowser();

        logger.info("Starting browser: " + browser);

        DriverFactory.initDriver(browser);

        driver = DriverFactory.getDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(config.getImplicitWait()));

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(config.getPageLoadTimeout()));

        logger.info("Opening OrangeHRM application");

        driver.get(config.getApplicationUrl());

        // Wait until the OrangeHRM login page is ready
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(config.getExplicitWait()));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("username")));

        logger.info("OrangeHRM login page loaded successfully");
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
