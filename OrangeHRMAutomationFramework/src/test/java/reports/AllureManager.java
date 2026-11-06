package reports;

import java.io.ByteArrayInputStream;

import io.qameta.allure.Allure;

public class AllureManager {

    public static void attachScreenshot(
            String attachmentName, byte[] screenshot) {

        if (screenshot != null) {

            Allure.addAttachment(
                    attachmentName,
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png");
        }
    }
}