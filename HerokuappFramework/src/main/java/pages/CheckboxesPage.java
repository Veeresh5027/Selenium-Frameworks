package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CheckboxesPage {
    WebDriver driver;
    By checkboxes = By.cssSelector("form#checkboxes input[type='checkbox']");

    public CheckboxesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCheckbox(int index) {
        List<WebElement> chkList = driver.findElements(checkboxes);
        WebElement chk = chkList.get(index);
        if (!chk.isSelected()) {
            chk.click();
        }
    }

    public void unclickCheckbox(int index) {
        List<WebElement> chkList = driver.findElements(checkboxes);
        WebElement chk = chkList.get(index);
        if (chk.isSelected()) {
            chk.click();
        }
    }

    public boolean isCheckboxSelected(int index) {
        return driver.findElements(checkboxes).get(index).isSelected();
    }
}