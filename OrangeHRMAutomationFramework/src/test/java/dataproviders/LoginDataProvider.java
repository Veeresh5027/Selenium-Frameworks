
package dataproviders;

import org.testng.annotations.DataProvider;

import utilities.ExcelUtils;

public class LoginDataProvider {

    private static final String EXCEL_PATH =
            "src/test/resources/testdata/OrangeHRM_TestData.xlsx";

    private static final String SHEET_NAME = "Login";

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {

        int rowCount = ExcelUtils.getRowCount(EXCEL_PATH, SHEET_NAME);

        // Subtract 1 to exclude the header row
        Object[][] data = new Object[rowCount - 1][3];

        for (int i = 1; i < rowCount; i++) {

            data[i - 1][0] = ExcelUtils.getCellData(EXCEL_PATH, SHEET_NAME, i, 0);

            data[i - 1][1] = ExcelUtils.getCellData(EXCEL_PATH, SHEET_NAME, i, 1);

            data[i - 1][2] = ExcelUtils.getCellData(EXCEL_PATH, SHEET_NAME, i, 2);
        }

        return data;
    }
}
