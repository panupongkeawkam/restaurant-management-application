
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;

import org.apache.poi.xssf.usermodel.*;

public class Main {

    public static void main(String[] args) throws Exception {
        new LogAPI();
        new TableFrame();
        new TableManagerFrame();
        new ChangePINFrame();
        new LoginFrame();
        new AdminFrame();
        new MenuAPI();
        new EditMenu();
        new MainFrame();
//        databaseInitialize();
    }

    public static boolean databaseInitialize() throws Exception { // not use in this class

        if (new File("database.xlsx").exists()) {
            System.out.println("File exists!");
            return false;
        }

        File databaseExcel = new File("database.xlsx");

        FileOutputStream fos = new FileOutputStream(databaseExcel);
        XSSFWorkbook workbook = new XSSFWorkbook(); // create file writer

        JButton b = new JButton("yes");

        // test for create sheet and value in sheet
        workbook.createSheet("S1");
        workbook.getSheet("S1").createRow(0).createCell(0).setCellValue("Hello");
        workbook.getSheet("S1").createRow(1).createCell(1).setCellValue("World");

        workbook.createSheet("S2");
        workbook.getSheet("S2").createRow(1);
        workbook.getSheet("S2").getRow(1).createCell(2).setCellValue("Hello");
        workbook.getSheet("S2").getRow(1).getCell(2).setCellValue("Hello World");

        workbook.write(fos); // write in file
        workbook.close();

        System.out.println("File create success!");

//        Desktop.getDesktop().open(new File("database.xlsx")); {
        return true;
    }
}
