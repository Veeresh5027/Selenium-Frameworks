package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PIMPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // PIM menu
    @FindBy(xpath = "//span[normalize-space()='PIM']")
    private WebElement pimMenu;

    // Employee List page
    @FindBy(xpath = "//h5[normalize-space()='Employee Information']")
    private WebElement employeeInformationHeading;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[normalize-space()='Reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    private WebElement addButton;

    // Employee ID search field
    @FindBy(xpath = "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeIdSearchField;

    // Employee Name search field
    @FindBy(xpath = "//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeNameSearchField;

    // Add Employee page
    @FindBy(xpath = "//h6[normalize-space()='Add Employee']")
    private WebElement addEmployeeHeading;

    @FindBy(name = "firstName")
    private WebElement firstNameField;

    @FindBy(name = "lastName")
    private WebElement lastNameField;

    // Actual Employee ID field on Add Employee page
    @FindBy(xpath = "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeIdField;

    // Create Login Details toggle
    @FindBy(xpath = "//p[normalize-space()='Create Login Details']/following::span[contains(@class,'oxd-switch-input')][1]")
    private WebElement createLoginDetailsToggle;

    // Login details fields
    @FindBy(xpath = "//label[contains(normalize-space(), 'Username')]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement usernameField;

    @FindBy(xpath = "//label[starts-with(normalize-space(), 'Password') and not(contains(normalize-space(), 'Confirm'))]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement passwordField;

    @FindBy(xpath = "//label[contains(normalize-space(), 'Confirm Password')]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement confirmPasswordField;

    // Save and Cancel buttons
    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    private WebElement cancelButton;

    // Search results
    private final By employeeRows =
            By.cssSelector(".oxd-table-body .oxd-table-card");

    private final By noRecordsMessage =
            By.xpath("//*[normalize-space()='No Records Found']");

    public PIMPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20));

        PageFactory.initElements(driver, this);
    }

    // Wait until OrangeHRM form loader disappears
    private void waitForLoaderToDisappear() {

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.className("oxd-form-loader")));
    }

    // Navigate to PIM
    public void clickPIMMenu() {

        wait.until(ExpectedConditions.elementToBeClickable(
                pimMenu)).click();

        wait.until(ExpectedConditions.visibilityOf(
                employeeInformationHeading));
    }

    // Verify Employee List page
    public boolean isEmployeeListPageDisplayed() {

        try {
            return wait.until(ExpectedConditions.visibilityOf(
                    employeeInformationHeading)).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    // Employee List elements
    public boolean isSearchButtonDisplayed() {
        return searchButton.isDisplayed();
    }

    public boolean isResetButtonDisplayed() {
        return resetButton.isDisplayed();
    }

    public boolean isAddButtonDisplayed() {
        return addButton.isDisplayed();
    }

    // Click Add button
    public void clickAddButton() {

        waitForLoaderToDisappear();

        wait.until(ExpectedConditions.elementToBeClickable(
                addButton)).click();

        wait.until(ExpectedConditions.visibilityOf(
                addEmployeeHeading));
    }

    // Verify Add Employee page
    public boolean isAddEmployeePageDisplayed() {

        try {
            return wait.until(ExpectedConditions.visibilityOf(
                    addEmployeeHeading)).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    // Enter First Name
    public void enterFirstName(String firstName) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(firstNameField));

        element.clear();
        element.sendKeys(firstName);
    }

    // Enter Last Name
    public void enterLastName(String lastName) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(lastNameField));

        element.clear();
        element.sendKeys(lastName);
    }

    // Retrieve actual Employee ID from Add Employee page
    public String getEmployeeIdFromAddEmployeePage() {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(employeeIdField));

        return element.getDomProperty("value").trim();
    }

    // Enable Create Login Details
    public void enableCreateLoginDetails() {

        waitForLoaderToDisappear();

        wait.until(ExpectedConditions.elementToBeClickable(
                createLoginDetailsToggle)).click();

        wait.until(ExpectedConditions.visibilityOf(
                usernameField));
    }

    // Enter Username
    public void enterUsername(String username) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(usernameField));

        element.clear();
        element.sendKeys(username);
    }

    // Enter Password
    public void enterPassword(String password) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(passwordField));

        element.clear();
        element.sendKeys(password);
    }

    // Enter Confirm Password
    public void enterConfirmPassword(String confirmPassword) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(confirmPasswordField));

        element.clear();
        element.sendKeys(confirmPassword);
    }

    // Click Save
    public void clickSave() {

        waitForLoaderToDisappear();

        wait.until(ExpectedConditions.elementToBeClickable(
                saveButton)).click();
    }

    // Click Cancel
    public void clickCancel() {

        waitForLoaderToDisappear();

        wait.until(ExpectedConditions.elementToBeClickable(
                cancelButton)).click();
    }

    // Get required-field validation message
    public String getFieldValidationMessage(String fieldName) {

        String inputName;

        if (fieldName.equalsIgnoreCase("First Name")) {
            inputName = "firstName";

        } else if (fieldName.equalsIgnoreCase("Last Name")) {
            inputName = "lastName";

        } else {
            throw new IllegalArgumentException(
                    "Unsupported field name: " + fieldName);
        }

        By errorLocator = By.xpath(
                "//input[@name='" + inputName + "']"
                        + "/ancestor::div[contains(@class,'oxd-input-group')]"
                        + "//span[contains(@class,'oxd-input-field-error-message')]"
        );

        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                errorLocator)).getText();
    }

    // Verify employee profile page
    public boolean isEmployeeProfileDisplayed() {

        try {
            wait.until(ExpectedConditions.urlContains(
                    "viewPersonalDetails"));

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // =========================================================
    // EMPLOYEE SEARCH METHODS - TC_PIM_004, TC_PIM_005, TC_PIM_006
    // =========================================================

    // Enter Employee ID in search field
    public void enterEmployeeIdForSearch(String employeeId) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(employeeIdSearchField));

        element.clear();
        element.sendKeys(employeeId);
    }

    // Enter Employee Name in search field
    public void enterEmployeeNameForSearch(String employeeName) {

        employeeName = employeeName.trim();

        // Locate and wait for Employee Name field
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(
                        employeeNameSearchField));

        // Clear existing value and enter employee name
        element.click();
        element.sendKeys(org.openqa.selenium.Keys.CONTROL, "a");
        element.sendKeys(employeeName);

        // Wait for autocomplete suggestions to load
        By suggestionLocator =
                By.cssSelector(".oxd-autocomplete-option");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    suggestionLocator));

            List<WebElement> suggestions =
                    driver.findElements(suggestionLocator);

            if (!suggestions.isEmpty()) {
                // Select the first available suggestion from the autocomplete dropdown
                suggestions.get(0).click();
                return;
            }
        } catch (Exception e) {
            // Fallback: If dropdown suggestion doesn't appear, press TAB to dismiss
            element.sendKeys(org.openqa.selenium.Keys.TAB);
        }
    }

    // Click Search
    public void clickSearch() {

        waitForLoaderToDisappear();

        wait.until(ExpectedConditions.elementToBeClickable(
                searchButton)).click();

        // Wait until results or No Records Found is displayed
        wait.until(driver ->
                !driver.findElements(employeeRows).isEmpty()
                        || !driver.findElements(noRecordsMessage).isEmpty());
    }

    // Search employee and wait for the matching result
    public void clickSearchForEmployeeId(String employeeId) {

        waitForLoaderToDisappear();

        wait.until(ExpectedConditions.elementToBeClickable(
                searchButton)).click();

        By matchingEmployee = By.xpath(
                "//div[contains(@class,'oxd-table-body')]"
                        + "//div[contains(@class,'oxd-table-card')]"
                        + "[contains(.,'" + employeeId + "')]");

        wait.until(driver ->
                !driver.findElements(matchingEmployee).isEmpty()
                        || !driver.findElements(noRecordsMessage).isEmpty());
    }

    // Get employee search result rows
    public List<WebElement> getEmployeeSearchResultRows() {

        return driver.findElements(employeeRows);
    }

    // Get employee search result row count
    public int getEmployeeSearchResultCount() {

        return getEmployeeSearchResultRows().size();
    }

    // Check whether a result row contains expected text
    // Check whether a result row contains expected text (handles First/Last name formatting)
    public boolean isEmployeePresentInSearchResults(String expectedText) {

        List<WebElement> rows = getEmployeeSearchResultRows();

        // Split expected text into name parts (e.g., "John" and "Doe")
        String[] parts = expectedText.trim().split("\\s+");

        for (WebElement row : rows) {
            String rowText = row.getText();

            // Check if all parts of the name exist in the row text (ignoring order/formatting like "Doe, John")
            boolean allPartsFound = true;
            for (String part : parts) {
                if (!rowText.toLowerCase().contains(part.toLowerCase())) {
                    allPartsFound = false;
                    break;
                }
            }

            if (allPartsFound) {
                return true;
            }
        }

        return false;
    }

    // Verify No Records Found message
    public boolean isNoRecordsFoundDisplayed() {

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    noRecordsMessage)).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }
}