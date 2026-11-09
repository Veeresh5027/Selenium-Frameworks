package testcases;

import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class ExcelReadTest {

    @Test
    public void verifyExcelReading() {

        String filePath = "src/test/resources/testdata/OrangeHRM_TestData.xlsx";
        String sheetName = "Login";

        int rowCount = ExcelUtils.getRowCount(filePath, sheetName);

        System.out.println("Total Rows: " + rowCount);

        for (int i = 1; i < rowCount; i++) {

            String username = ExcelUtils.getCellData(filePath, sheetName, i, 0);
            String password = ExcelUtils.getCellData(filePath, sheetName, i, 1);
            String expectedResult = ExcelUtils.getCellData(filePath, sheetName, i, 2);

            System.out.println("--------------------------------");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("Expected Result: " + expectedResult);
        }
    }
}