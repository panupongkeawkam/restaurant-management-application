
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            int rowCount = sheet.getLastRowNum();
            menu = new String[rowCount][3];
            for (int i = 0; i < rowCount; i++) {
                menu[i][0] = String.valueOf(i + 1);
                menu[i][1] = sheet.getRow(i).getCell(0).getStringCellValue();
                menu[i][2] = String.valueOf(sheet.getRow(i).getCell(1).getNumericCellValue());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void writeDataInExcel(String menuName, double menuPrice) {
        File menuExcel = new File("menu.xlsx");
        try (FileInputStream fis = new FileInputStream(menuExcel)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("menu");
            int lastRow = sheet.getLastRowNum();
            System.out.println(lastRow);
            sheet.createRow(lastRow).createCell(0).setCellValue(menuName);
            sheet.getRow(lastRow).createCell(1).setCellValue(menuPrice);
            FileOutputStream fos = new FileOutputStream(menuExcel);
            workbook.write(fos);
            this.readDataFromExcel();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static String[][] getMenu() {
        return menu;
    }

}
