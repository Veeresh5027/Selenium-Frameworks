
package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {

    Properties prop;

    public ReadConfig() {

        try {
            FileInputStream fis = new FileInputStream(
                "src/test/resources/config.properties");

            prop = new Properties();
            prop.load(fis);

            fis.close();

        } catch (IOException e) {
            throw new RuntimeException(
                "Unable to load config.properties file", e);
        }
    }

    public String getApplicationUrl() {
        return prop.getProperty("baseUrl");
    }

    public String getBrowser() {
        return prop.getProperty("browser");
    }

    public int getImplicitWait() {
        return Integer.parseInt(
            prop.getProperty("implicitWait"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(
            prop.getProperty("explicitWait"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(
            prop.getProperty("pageLoadTimeout"));
    }

    public boolean getScreenshotOnFailure() {
        return Boolean.parseBoolean(
            prop.getProperty("screenshotOnFailure"));
    }
}
