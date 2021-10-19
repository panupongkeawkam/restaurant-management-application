import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.usermodel.*;

public class Main {

    public static void main(String[] args) throws Exception {
        File file = new File("test-database2.xls");
        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(fis);

        int lastRow = wb.getSheet("Sheet0").getLastRowNum();
        int lastCell = wb.getSheet("Sheet0").getRow(lastRow).getLastCellNum();
        String x = wb.getSheet("Sheet0").getRow(lastRow).getCell(0).getStringCellValue();
        System.out.println(x);
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println(day.format(LocalDateTime.now()));
        
//        wb.createSheet(day.format(LocalDateTime.now()));
//        wb.write(file);
//        wb.getSheetAt(1).createRow(0);
//        wb.getSheetAt(1).getRow(0).createCell(0);
//        wb.getSheetAt(1).getRow(0).getCell(0).setCellValue(time.format(LocalDateTime.now()));

//        wb.getSheet("18-10-2021").createRow(10).createCell(10).setCellValue("Yes");
        
        
        wb.write(file);

//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sh = wb.createSheet();
//        sh.createRow(0);
//        sh.getRow(0).createCell(0).setCellValue("Hello1");
//        sh.getRow(0).createCell(1).setCellValue("World1");
//        
//        sh.createRow(1);
//        sh.getRow(1).createCell(0).setCellValue("Hello2");
//        sh.getRow(1).createCell(1).setCellValue("World2");
//        
//        File file = new File("database2.xls");
//        FileOutputStream fos = new FileOutputStream(file);
//        wb.write(fos);
//        wb.close();
//        
//        String x = wb.getSheet("Sheet0").getRow(0).getCell(0).getStringCellValue();
//        System.out.println(x);
    }
}
