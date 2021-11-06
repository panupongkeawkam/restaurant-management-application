
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.HashMap;
import java.lang.Math;
import java.io.*;
import org.apache.poi.xssf.usermodel.*;

public class TableInformation {

    private static String[][] menu;
    private static Object[][] addedMenu;

    public TableInformation() {
        menu = MenuAPI.getMenu();
        addedMenu = new Object[menu.length][3];
    }

    public static void tableInitial() {
        menu = MenuAPI.getMenu();
        try (FileOutputStream fos = new FileOutputStream("table-info.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook();
            for (int i = 0; i < 24; i++) {
                XSSFSheet sh = wb.createSheet("table-" + i);
                for (int j = 0; j < menu.length; j++) {
                    sh.createRow(j).createCell(0).setCellValue(menu[j][0]);
                    sh.getRow(j).createCell(1).setCellValue(0);
                    sh.getRow(j).createCell(2).setCellValue(0);
                }
            }
            wb.write(fos);
            wb.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Object[][] getAddedMenu(String sheetName) {
        try (FileInputStream fis = new FileInputStream("table-info.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(sheetName);
            int rowCount = sh.getLastRowNum() + 1;
            addedMenu = new Object[rowCount][3];
            for (int i = 0; i < rowCount; i++) {
                addedMenu[i][0] = sh.getRow(i).getCell(0).getStringCellValue();
                addedMenu[i][1] = sh.getRow(i).getCell(1).getNumericCellValue();
                addedMenu[i][2] = (int) sh.getRow(i).getCell(2).getNumericCellValue();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return addedMenu;
    }

    public static void addMenu(String table, int menuID) {
        try (FileInputStream fis = new FileInputStream("table-info.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(table);
            int currentCount = (int) sh.getRow(menuID).getCell(2).getNumericCellValue();
            double menuPrice = Double.parseDouble(menu[menuID][1]);
            sh.getRow(menuID).getCell(2).setCellValue(currentCount + 1);
            currentCount++;
            sh.getRow(menuID).getCell(1).setCellValue(menuPrice * currentCount);
            FileOutputStream fos = new FileOutputStream("table-info.xlsx");
            wb.write(fos);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void removeMenu(String table, int menuID) {
        try (FileInputStream fis = new FileInputStream("table-info.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(table);
            int currentCount = (int) sh.getRow(menuID).getCell(2).getNumericCellValue();
            double menuPrice = Double.parseDouble(menu[menuID][1]);
            if (currentCount > 0) {
                sh.getRow(menuID).getCell(2).setCellValue(currentCount - 1);
                currentCount--;
            }
            sh.getRow(menuID).getCell(1).setCellValue(menuPrice * currentCount);
            FileOutputStream fos = new FileOutputStream("table-info.xlsx");
            wb.write(fos);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void resetMenu(String table) {
        try (FileInputStream fis = new FileInputStream("table-info.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(table);
            int rowCount = sh.getLastRowNum() + 1;
            for (int i = 0; i < rowCount; i++) {
                sh.getRow(i).getCell(2).setCellValue(0);
                sh.getRow(i).getCell(1).setCellValue(0);
            }
            FileOutputStream fos = new FileOutputStream("table-info.xlsx");
            wb.write(fos);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
