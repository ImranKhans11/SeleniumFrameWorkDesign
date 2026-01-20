package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtility {

    private Workbook workbook;
    private Sheet sheet;

    // Constructor
    public ExcelUtility(String excelPath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Excel file");
        }
    }

    // Get total row count (excluding header logic)
    public int getRowCount() {
        return sheet.getLastRowNum();   // index-based
    }

    // Get total column count
    public int getColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }

    // Read cell data by row & column index
    public String getCellData(int rowNum, int colNum) {
        try {
            DataFormatter formatter = new DataFormatter();
            Cell cell = sheet.getRow(rowNum).getCell(colNum);
            return formatter.formatCellValue(cell);
        } catch (Exception e) {
            return "";
        }
    }

    // Close workbook
    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
