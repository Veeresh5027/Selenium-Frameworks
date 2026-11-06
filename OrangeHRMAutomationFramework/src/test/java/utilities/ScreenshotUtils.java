package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

    public static byte[] captureScreenshot(
            WebDriver driver, String testName) {

        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        try {
            Path directory = Paths.get("Screenshots");
            Files.createDirectories(directory);

            String timestamp = new SimpleDateFormat(
                    "yyyyMMdd_HHmmss").format(new Date());

            String safeTestName = testName.replaceAll(
                    "[^a-zA-Z0-9._-]", "_");

            Path filePath = directory.resolve(
                    safeTestName + "_" + timestamp + ".png");

            Files.write(filePath, screenshot);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to save screenshot", e);
        }

        return screenshot;
    }
}