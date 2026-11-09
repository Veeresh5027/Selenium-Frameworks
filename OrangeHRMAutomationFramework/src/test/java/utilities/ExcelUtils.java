package utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

    private ExcelUtils() {
        // Prevent object creation
    }

    public static int getRowCount(String filePath, String sheetName) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet not found: " + sheetName);
            }

            return sheet.getLastRowNum() + 1;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read Excel file: " + filePath, e);
        }
    }

    public static String getCellData(
            String filePath, String sheetName,
            int rowNum, int colNum) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet not found: " + sheetName);
            }

            Row row = sheet.getRow(rowNum);

            if (row == null) {
                return "";
            }

            Cell cell = row.getCell(
                    colNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            if (cell == null) {
                return "";
            }

            DataFormatter formatter = new DataFormatter();

            FormulaEvaluator evaluator = workbook
                    .getCreationHelper()
                    .createFormulaEvaluator();

            return formatter.formatCellValue(cell, evaluator);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read Excel cell data", e);
        }
    }
}