
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.xssf.usermodel.*;

public class DatabaseAPI {

    private MainFrame mf;
    private FileInputStream fis;
    private FileOutputStream fos;
    private File log, menu;
    private XSSFWorkbook workbook;
    private String menuList[];

    public DatabaseAPI(MainFrame mf) {
        this.mf = mf;
        excelLogInitialize();
    }

    public boolean excelLogInitialize() {
        try {
            log = new File("log.xlsx");
            fos = new FileOutputStream(log);
            workbook = new XSSFWorkbook();
//            workbook.createSheet("test");
            workbook.write(fos);
            System.out.println("File create success!");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] getMemuListFromExcel() {
        try {
            menu = new File("food-menu.xlsx");
            fis = new FileInputStream(menu);
            workbook = new XSSFWorkbook(fis);

            XSSFSheet sheet = workbook.getSheet("menu");
            int length = sheet.getLastRowNum() + 1;
            menuList = new String[length];

            for (int i = 0; i < length; i++) {
                menuList[i] = sheet.getRow(i).getCell(0).getStringCellValue();
            }

            return menuList;
        } catch (Exception e) {
            return null;
        }
    }
}
