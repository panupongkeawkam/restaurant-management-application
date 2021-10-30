
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.xssf.usermodel.*;

public class LogAPI implements DataAPI {

    private static String log[][];

    public LogAPI() {
        this.readDataFromExcel();
        log = this.arrayReversed(log);
    }

    public void readDataFromExcel() {
        File logExcel = new File("log.xlsx");
        try (FileInputStream fis = new FileInputStream(logExcel)) {
            XSSFWorkbook workbook = new XSSFWorkbook(logExcel);
            int sheetCount = workbook.getNumberOfSheets();
            log = new String[sheetCount][4];
            for (int i = 0; i < sheetCount; i++) {
                XSSFSheet eachSheet = workbook.getSheetAt(i);
                log[i][0] = String.valueOf(sheetCount - i);
                log[i][1] = eachSheet.getSheetName();
                log[i][2] = String.valueOf(65465.25);
                log[i][3] = String.valueOf(eachSheet.getLastRowNum() + 1);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void writeDataInExcel(String menuName, double menuPrice) {
    }

    public String[][] arrayReversed(String[][] arr) {
        int j = 0;
        String temp[][];
        temp = new String[arr.length][arr[0].length];
        for (int i = arr.length - 1; i >= 0; i--) {
            temp[j] = arr[i];
            j++;
        }
//        arr = temp;
        return temp;
    }

    public static String[][] getLog() {
        return log;
    }

    public static void setLog(String[][] logVar) {
        log = logVar;
    }
}
