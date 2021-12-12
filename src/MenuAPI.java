
import java.io.*;

import org.apache.poi.xssf.usermodel.*;

public class MenuAPI implements DataAPI {

    private static Object menuList[][];
    private static XSSFWorkbook wb;

    public MenuAPI() {
    }

    public static void readDataFromExcel() { // อ่านเมนูเพื่อมากำหนดใน attrbute Object[][]
        File menuExcel = new File("menu.xlsx");
        try (FileInputStream fis = new FileInputStream(menuExcel)) {
            wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("menu");

            int rowCount = sheet.getLastRowNum() + 1; // จำนวนแถวใน sheet
            menuList = new Object[rowCount][2];
            for (int i = 0; i < rowCount; i++) { // loop เพื่อกำหนดทุกค่าใน sheet ลงใน array
                menuList[i][0] = sheet.getRow(i).getCell(0).getStringCellValue();
                menuList[i][1] = sheet.getRow(i).getCell(1).getNumericCellValue();
            }
            if (rowCount == 0) { // เมื่อไม่มีข้อมูลใดๆ ใน sheet
                menuList = new Object[1][2]; // กำหนดขนาด array ที่ตายตัว
                menuList[0][0] = ""; // กำหนดค่าเป็น String เปล่าเพื่อป้องกันการเกิด error
                menuList[0][1] = "";
            }
        } catch (FileNotFoundException fnfex) { // กรณืเมื่อไม่พบไฟล์ให้สร้างขึ้นใหม่
            try (FileOutputStream fos = new FileOutputStream(menuExcel)) {
                wb = new XSSFWorkbook();
                wb.createSheet("menu");
                wb.write(fos);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeDataInExcel(String menuName, double menuPrice) {
        File menuExcel = new File("menu.xlsx");
        try (FileInputStream fis = new FileInputStream(menuExcel)) {
            wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("menu");

            int lastRow = sheet.getLastRowNum();
            XSSFRow newRow = sheet.createRow(lastRow + 1);

            newRow.createCell(0).setCellValue(menuName);
            newRow.createCell(1).setCellValue(menuPrice);
            FileOutputStream fos = new FileOutputStream(menuExcel);

            wb.write(fos);
            wb.close();
            this.readDataFromExcel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteMenuInExcel(int row) { // ลบเมนูในฐานข้อมูล
        File menuExcel = new File("menu.xlsx");
        try (FileInputStream fis = new FileInputStream(menuExcel)) {
            wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("menu");
            XSSFRow rowSelect = sheet.getRow(row); // สร้าง Object row และ get row ที่จะลบ

            int lastRow = sheet.getLastRowNum(); // get เลข index ของ row สุดท้าย
            sheet.removeRow(rowSelect); // ทำการลบ row ที่กำหนด
            if (lastRow != row) { // กรณี row ที่ลบ ไม่ใช้ row สุดท้าย
                sheet.shiftRows(row + 1, lastRow, -1); // ทำการ shift row ขึ้น
            }

            FileOutputStream fos = new FileOutputStream(menuExcel);
            wb.write(fos);
            wb.close();
            this.readDataFromExcel(); // เรียก method เพื่ออ่านและกำหนดข้อมูลใหม่
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object[][] getMenuList() {
        readDataFromExcel();
        return menuList;
    }
}
