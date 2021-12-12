
import java.io.*;

import org.apache.poi.xssf.usermodel.*;

public class TableInformationAPI {

    private static Object[][] menuList;
    private static Object[][] menuInOrders;
    private static XSSFWorkbook wb;

    public TableInformationAPI() {
        menuList = MenuAPI.getMenuList();
        menuInOrders = new Object[menuList.length][3];
    }

    public static void tableInitial() { // กำหนดค่าเริ่มต้นของเมนูในออเดอร์ของทุกโต๊ะลงในไฟล์
        menuList = MenuAPI.getMenuList();
        try (FileOutputStream fos = new FileOutputStream("table-info.xlsx")) {
            wb = new XSSFWorkbook();
            for (int i = 0; i < 24; i++) {
                XSSFSheet sh = wb.createSheet("table-" + (i + 1));
                for (int j = 0; j < menuList.length; j++) {
                    sh.createRow(j).createCell(0).setCellValue((String) menuList[j][0]);
                    sh.getRow(j).createCell(1).setCellValue((int) 0);
                    sh.getRow(j).createCell(2).setCellValue((double) 0.0);
                }
            }
            wb.write(fos);
            wb.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object[][] getMenuInOrders(int tableNumber) { // get ค่าข้อมูลเมนูในออเดอร์ของแต่ละโต๊ะที่ buffer ไว้
        try (FileInputStream fis = new FileInputStream("table-info.xlsx")) {
            wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet("table-" + tableNumber);

            int rowCount = sh.getLastRowNum() + 1;
            menuInOrders = new Object[rowCount][3];

            for (int i = 0; i < rowCount; i++) {
                menuInOrders[i][0] = sh.getRow(i).getCell(0).getStringCellValue();
                menuInOrders[i][1] = (int) sh.getRow(i).getCell(1).getNumericCellValue();
                menuInOrders[i][2] = sh.getRow(i).getCell(2).getNumericCellValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return menuInOrders;
    }

    public static void tableBuffering() { // buffer ค่าเมนูในออเดอร์ของแต่ละโต๊ะ
        try (FileInputStream fis = new FileInputStream("table-info.xlsx")) {
            TableInformation tif[] = TableFrame.getTableInfo();
            wb = new XSSFWorkbook(fis);
            for (int i = 0; i < 24; i++) {
                XSSFSheet sh = wb.getSheet("table-" + (i + 1));

                int rowCount = sh.getLastRowNum() + 1;
                for (int j = 0; j < rowCount; j++) {
                    XSSFRow row = sh.getRow(j);
                    Object[][] data = tif[i].getMenuInOrders();
                    row.getCell(1).setCellValue((int) data[j][1]);
                    row.getCell(2).setCellValue((double) data[j][2]);
                }

                FileOutputStream fos = new FileOutputStream("table-info.xlsx");
                wb.write(fos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
