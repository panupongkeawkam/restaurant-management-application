
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.xssf.usermodel.*;

public class MenuAPI implements DataAPI {

    private static String menu[][];

    public MenuAPI() {
        this.readDataFromExcel();
    }

    public void readDataFromExcel() {
        File menuExcel = new File("menu.xlsx");
        try (FileInputStream fis = new FileInputStream(menuExcel)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("menu");
            int rowCount = sheet.getLastRowNum() + 1;
            menu = new String[rowCount][3];
            for (int i = 0; i < rowCount; i++) {
                menu[i][0] = String.valueOf(i + 1);
                menu[i][1] = sheet.getRow(i).getCell(0).getStringCellValue();
                menu[i][2] = String.valueOf(sheet.getRow(i).getCell(1).getNumericCellValue());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void writeDataInExcel(String menuName, double menuPrice) {
        File menuExcel = new File("menu.xlsx");
        try (FileInputStream fis = new FileInputStream(menuExcel)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("menu");
            int lastRow = sheet.getLastRowNum();
            XSSFRow newRow = sheet.createRow(lastRow + 1);
            newRow.createCell(0).setCellValue(menuName);
            newRow.createCell(1).setCellValue(menuPrice);
            FileOutputStream fos = new FileOutputStream(menuExcel);
            workbook.write(fos);
            workbook.close();
            this.readDataFromExcel();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String[][] getMenu() {
        return menu;
    }

}
