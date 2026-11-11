package testcases;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import dataproviders.PIMDataProvider;
import pages.LoginPage;
import pages.PIMPage;

public class PIMTest extends BaseTest {

    // Common navigation method: Login -> PIM -> Employee List
    private PIMPage navigateToEmployeeListPage() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                config.getValidUsername(),
                config.getValidPassword());

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20));

        wait.until(ExpectedConditions.urlContains("dashboard"));

        PIMPage pimPage = new PIMPage(driver);

        pimPage.clickPIMMenu();

        Assert.assertTrue(
                pimPage.isEmployeeListPageDisplayed(),
                "Employee List page was not displayed");

        return pimPage;
    }

    // Common navigation method: Login -> PIM -> Add Employee
    private PIMPage navigateToAddEmployeePage() {

        PIMPage pimPage = navigateToEmployeeListPage();

        pimPage.clickAddButton();

        Assert.assertTrue(
                pimPage.isAddEmployeePageDisplayed(),
                "Add Employee page was not displayed");

        return pimPage;
    }

    // =========================================================
    // TC_PIM_001: Add employee with required fields only
    // =========================================================
    @Test(
            dataProvider = "addEmployeeData",
            dataProviderClass = PIMDataProvider.class
    )
    public void verifyAddEmployeeWithRequiredFields(
            String firstName,
            String lastName) {

        logger.info("Starting TC_PIM_001: Add employee with required fields only");

        PIMPage pimPage = navigateToAddEmployeePage();

        pimPage.enterFirstName(firstName);
        pimPage.enterLastName(lastName);

        pimPage.clickSave();

        Assert.assertTrue(
                pimPage.isEmployeeProfileDisplayed(),
                "Employee was not created successfully");

        logger.info("TC_PIM_001 completed successfully");
    }

    // =========================================================
    // TC_PIM_002: Add employee with login credentials enabled
    // =========================================================
    @Test(
            dataProvider = "addEmployeeWithLoginData",
            dataProviderClass = PIMDataProvider.class
    )
    public void verifyAddEmployeeWithLoginCredentials(
            String firstName,
            String lastName,
            String username,
            String password) {

        logger.info("Starting TC_PIM_002: Add employee with login credentials");

        PIMPage pimPage = navigateToAddEmployeePage();

        pimPage.enterFirstName(firstName);
        pimPage.enterLastName(lastName);

        pimPage.enableCreateLoginDetails();

        pimPage.enterUsername(username);
        pimPage.enterPassword(password);
        pimPage.enterConfirmPassword(password);

        pimPage.clickSave();

        Assert.assertTrue(
                pimPage.isEmployeeProfileDisplayed(),
                "Employee with login credentials was not created successfully");

        logger.info("TC_PIM_002 completed successfully");
    }

    // =========================================================
    // TC_PIM_003: Verify required-field validation
    // =========================================================
    @Test
    public void verifyRequiredFieldValidationOnAddEmployee() {

        logger.info("Starting TC_PIM_003: Required field validation");

        PIMPage pimPage = navigateToAddEmployeePage();

        pimPage.clickSave();

        String firstNameError =
                pimPage.getFieldValidationMessage("First Name");

        String lastNameError =
                pimPage.getFieldValidationMessage("Last Name");

        Assert.assertEquals(
                firstNameError,
                "Required",
                "First Name required validation message is incorrect");

        Assert.assertEquals(
                lastNameError,
                "Required",
                "Last Name required validation message is incorrect");

        logger.info("TC_PIM_003 completed successfully");
    }

    // =========================================================
    // TC_PIM_004: Search employee by Employee ID
    // =========================================================
    @Test
    public void verifySearchEmployeeByEmployeeId() {

        logger.info("Starting TC_PIM_004: Search employee by Employee ID");

        // Step 1: Navigate to Add Employee page
        PIMPage pimPage = navigateToAddEmployeePage();

        // Step 2: Enter unique employee details
        String firstName = "Auto";
        String lastName = "Search" + System.currentTimeMillis();

        pimPage.enterFirstName(firstName);
        pimPage.enterLastName(lastName);

        // Step 3: Retrieve actual Employee ID BEFORE saving
        String employeeId =
                pimPage.getEmployeeIdFromAddEmployeePage();

        logger.info("Actual Employee ID from Add Employee page: " + employeeId);

        Assert.assertFalse(
                employeeId.isEmpty(),
                "Employee ID is empty on the Add Employee page");

        // Step 4: Save employee
        pimPage.clickSave();

        Assert.assertTrue(
                pimPage.isEmployeeProfileDisplayed(),
                "Test employee was not created successfully");

        // Step 5: Navigate to Employee List
        pimPage.clickPIMMenu();

        Assert.assertTrue(
                pimPage.isEmployeeListPageDisplayed(),
                "Employee List page was not displayed");

        // Step 6: Enter generated Employee ID
        pimPage.enterEmployeeIdForSearch(employeeId);

        // Step 7: Search employee
        pimPage.clickSearchForEmployeeId(employeeId);

        // Step 8: Verify search result
        Assert.assertEquals(
                pimPage.getEmployeeSearchResultCount(),
                1,
                "Expected exactly one employee for Employee ID: " + employeeId);

        Assert.assertTrue(
                pimPage.isEmployeePresentInSearchResults(employeeId),
                "Search result does not contain Employee ID: " + employeeId);

        logger.info("TC_PIM_004 completed successfully");
    }

    // =========================================================
    // TC_PIM_005: Search employee by Employee Name
    // =========================================================
    @Test(
            dataProvider = "searchByEmployeeNameData",
            dataProviderClass = PIMDataProvider.class
    )
    public void verifySearchEmployeeByEmployeeName(String employeeName) {

        logger.info("Employee name received from Excel: [" + employeeName + "]");

        Assert.assertNotNull(
                employeeName,
                "Employee name from Excel is null");

        Assert.assertFalse(
                employeeName.trim().isEmpty(),
                "Employee name from Excel is blank");

        logger.info("Starting TC_PIM_005: Search employee by Employee Name");

        PIMPage pimPage = navigateToEmployeeListPage();

        pimPage.enterEmployeeNameForSearch(employeeName);

        pimPage.clickSearch();

        Assert.assertTrue(
                pimPage.getEmployeeSearchResultCount() > 0,
                "No employee search results found for: " + employeeName);

        Assert.assertTrue(
                pimPage.isEmployeePresentInSearchResults(employeeName),
                "Search results do not contain employee: " + employeeName);

        logger.info("TC_PIM_005 completed successfully");
    }

    // =========================================================
    // TC_PIM_006: Search with non-existing Employee ID
    // =========================================================
    @Test(
            dataProvider = "searchNonExistingEmployeeIdData",
            dataProviderClass = PIMDataProvider.class
    )
    public void verifySearchWithNonExistingEmployeeId(String employeeId) {

        logger.info("Starting TC_PIM_006: Search with non-existing Employee ID");

        // Validate test data from Excel
        Assert.assertNotNull(
                employeeId,
                "Employee ID from Excel is null");

        Assert.assertFalse(
                employeeId.trim().isEmpty(),
                "Employee ID from Excel is blank");

        logger.info("Employee ID received from Excel: [" + employeeId + "]");

        PIMPage pimPage = navigateToEmployeeListPage();

        pimPage.enterEmployeeIdForSearch(employeeId);

        pimPage.clickSearch();

        Assert.assertTrue(
                pimPage.isNoRecordsFoundDisplayed(),
                "'No Records Found' message was not displayed for Employee ID: "
                        + employeeId);

        Assert.assertEquals(
                pimPage.getEmployeeSearchResultCount(),
                0,
                "Unexpected employee records found for Employee ID: " + employeeId);

        logger.info("TC_PIM_006 completed successfully");
    }
}