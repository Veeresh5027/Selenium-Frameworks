package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckboxesPage;

public class CheckboxesTests extends BaseTest {

    @Test(priority = 1)
    public void testSelectCheckbox() {
        driver.get(readConfig.getApplicationUrl() + "checkboxes");
        CheckboxesPage checkboxesPage = new CheckboxesPage(driver);

        checkboxesPage.clickCheckbox(0);
        Assert.assertTrue(checkboxesPage.isCheckboxSelected(0), "Checkbox 1 should be selected");
    }

    @Test(priority = 2)
    public void testUnselectCheckbox() {
        driver.get(readConfig.getApplicationUrl() + "checkboxes");
        CheckboxesPage checkboxesPage = new CheckboxesPage(driver);

        checkboxesPage.unclickCheckbox(1);
        Assert.assertFalse(checkboxesPage.isCheckboxSelected(1), "Checkbox 2 should be unchecked");
    }
}