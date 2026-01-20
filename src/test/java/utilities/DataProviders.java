package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
    @DataProvider(name = "LoginData")
    public Object[][] getData() {

        ExcelUtility excel =
                new ExcelUtility(".\\testdata\\opencartLogindata.xlsx", "Sheet1");

        int rows = excel.getRowCount();
        int cols = excel.getColumnCount();

        Object[][] data = new Object[rows][cols];

        for (int i = 1; i <= rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i-1][j] = excel.getCellData(i, j);
            }
        }

        excel.close();
        return data;
    }

}
