
package dataproviders;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class PIMDataProvider {

    private static final String EXCEL_PATH =
            "src/test/resources/testdata/OrangeHRM_TestData.xlsx";

    private static final String SHEET_NAME = "PIM";

    private static List<String> readRow(int excelRowNumber) {

        List<String> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (FileInputStream file = new FileInputStream(EXCEL_PATH);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);

            if (sheet == null) {
                throw new RuntimeException(
                        "PIM sheet not found in test data workbook");
            }

            Row row = sheet.getRow(excelRowNumber);

            if (row == null) {
                throw new RuntimeException(
                        "Excel row not found: " + excelRowNumber);
            }

            for (int column = 0; column < row.getLastCellNum(); column++) {
                data.add(formatter.formatCellValue(row.getCell(column)));
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read PIM test data Excel file", e);
        }

        return data;
    }

    // TC_PIM_001 - Add employee
    @DataProvider(name = "addEmployeeData")
    public static Object[][] addEmployeeData() {

        List<String> data = readRow(1);

        return new Object[][] {
                { data.get(0), data.get(1) }
        };
    }

    // TC_PIM_002 - Add employee with login credentials
    @DataProvider(name = "addEmployeeWithLoginData")
    public static Object[][] addEmployeeWithLoginData() {

        List<String> data = readRow(2);

        return new Object[][] {
                { data.get(0), data.get(1), data.get(2), data.get(3) }
        };
    }

    // TC_PIM_004 - Search employee by Employee ID
    @DataProvider(name = "searchByEmployeeIdData")
    public static Object[][] searchByEmployeeIdData() {

        List<String> data = readRow(3);

        return new Object[][] {
                { data.get(4) }
        };
    }

    // TC_PIM_005 - Search employee by Employee Name
    // TC_PIM_005 - Search employee by Employee Name
    @DataProvider(name = "searchByEmployeeNameData")
    public static Object[][] searchByEmployeeNameData() {

        List<String> data = readRow(5);

        String employeeName = data.get(5);

        System.out.println(
                "Employee Name read from Excel: [" + employeeName + "]");

        return new Object[][] {
                { employeeName }
        };
    }

    // TC_PIM_006 - Search with non-existing Employee ID
    @DataProvider(name = "searchNonExistingEmployeeIdData")
    public static Object[][] searchNonExistingEmployeeIdData() {

        List<String> data = readRow(6);

        String employeeId = data.get(4);

        System.out.println(
                "Non-existing Employee ID read from Excel: [" + employeeId + "]");

        return new Object[][] {
                { employeeId }
        };
    }
}
