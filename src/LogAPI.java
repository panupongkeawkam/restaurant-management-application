
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class LogAPI implements DataAPI {

    private static String allSheet[][];
    private static XSSFWorkbook wb;

    public LogAPI() {
        checkRecentSheet();
        readDataFromExcel();
    }

    public static void readDataFromExcel() { // อ่านข้อมูล overview ของทุก sheet
        try (FileInputStream fis = new FileInputStream("log.xlsx")) { // นำเข้าไฟล์
            wb = new XSSFWorkbook(fis); // สร้าง Object excel workbook
            int sheetCount = wb.getNumberOfSheets(); // get จำนวน sheet ทั้งหมด
            allSheet = new String[sheetCount][4]; // กำขนาด array ตาม จำนวน sheet
            for (int i = 0; i < sheetCount; i++) {
                XSSFSheet eachSheet = wb.getSheetAt(i); // get sheet ณ index i
                XSSFCellStyle cellColor; // ประกาศ Object cell style

                allSheet[i][0] = String.valueOf(sheetCount - i);
                allSheet[i][1] = eachSheet.getSheetName();
                cellColor = eachSheet.getRow(eachSheet.getLastRowNum()).getCell(0).getCellStyle(); // get cell style ณ row สุดท้าย cell 0
                short colorIndex = cellColor.getFillForegroundColor(); // get index ของสีพื้นหลัง
                if (colorIndex != IndexedColors.YELLOW1.index) { // ถ้า cell ไม่ใช่สีเหลือง (สีเหลืองแปลว่าสรุปยอดวันนั้นแล้ว)
                    allSheet[i][2] = "อยู่ระหว่างการดำเนินการ"; // กำหนดข้อความตรงยอดสรุปให้ sheet ณ วันนั้น ยังดำเนินการไม่เสร็จ
                    allSheet[i][3] = "อยู่ระหว่างการดำเนินการ";
                    break;
                }
                double total = eachSheet.getRow(eachSheet.getLastRowNum()).getCell(0).getNumericCellValue(); // get ยอดสรุป
                allSheet[i][2] = String.format("%.2f", total);
                allSheet[i][3] = String.valueOf(eachSheet.getLastRowNum() - 1);
            }
            wb.close();
            allSheet = arrayReversed(allSheet); // reverse array จากหน้าไปหลัง หลังไปหน้า
        } catch (NullPointerException nex) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void checkRecentSheet() { // เช็คว่า sheet ก่อนหน้ามีการสรุปยอดหรือยัง
        try (FileInputStream fis = new FileInputStream("log.xlsx")) {
            wb = new XSSFWorkbook(fis);
            int sheetCount = wb.getNumberOfSheets();

            DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now();

            String recentShName = wb.getSheetAt(sheetCount - 1).getSheetName();
            if (!recentShName.equals(dayFormat.format(now))) {
                XSSFSheet recentSh = wb.getSheet(recentShName);
                int lastRowN = recentSh.getLastRowNum();
                XSSFRow lastRow = recentSh.getRow(lastRowN);
                XSSFSheet sh = wb.getSheet(recentShName);
                int rowCount = sh.getLastRowNum() + 1;

                if (rowCount == 1) {
                    forceFillResult(recentShName);
                } else if (lastRow.getCell(0).getCellStyle().getFillForegroundColor() == 64) {
                    double total = 0;
                    for (int i = 1; i < rowCount; i++) {
                        total += sh.getRow(i).getCell(0).getNumericCellValue();
                    }
                    sh.createRow(rowCount).createCell(0).setCellValue(total);

                    CellStyle yellowCell = wb.createCellStyle();
                    yellowCell.setFillForegroundColor(IndexedColors.YELLOW1.index);
                    yellowCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    yellowCell.setAlignment(HorizontalAlignment.LEFT);

                    sh.getRow(rowCount).getCell(0).setCellStyle(yellowCell);

                    FileOutputStream fos = new FileOutputStream("log.xlsx");
                    wb.write(fos);
                    wb.close();
                }
                AdminFrame.setStatus('f');
            }

            wb.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void saveLog(Object[][] data2) { // บันทึก log ของโต๊ะที่บริการเสร็จ
        try (FileInputStream fis = new FileInputStream("log.xlsx")) {
            wb = new XSSFWorkbook(fis);

            DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // format วันที่
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss"); // format เวลา
            LocalDateTime now = LocalDateTime.now();

            CellStyle leftAlign = wb.createCellStyle(); // สร้าง Object cell style
            leftAlign.setAlignment(HorizontalAlignment.LEFT); // จัดให้ข้อมูลชิดซ้าย

            XSSFSheet sh = wb.getSheet(dayFormat.format(now)); // get ณ วันที่ปัจจุบัน
            int rowCount = sh.getLastRowNum() + 1; // get จำนวนแถวของ sheet
            sh.autoSizeColumn(0); // set ความกว้างอัตโนมัติให้ column 0
            sh.autoSizeColumn(1);
            XSSFRow currentRow = sh.createRow(rowCount); // สร้าง Object แถวใหม่
            double total = 0;
            int cellAt = 2;
            for (int i = 0; i < data2.length; i++) {
                String menuName = (String) data2[i][0]; // ชื่อเมนู
                currentRow.createCell(cellAt).setCellValue(menuName); // สร้างและ set ค่าลงใน cell
                currentRow.getCell(cellAt).setCellStyle(leftAlign); // set cell style จาก Object ที่สร้าง
                sh.autoSizeColumn(cellAt); // set ความกว้างอัตโนมัติให้ column นั้นๆ
                cellAt++; // ขยับไป cell ถัดไป

                int menuCount = (int) data2[i][1]; // จำนวนเมนูนั้นๆ ที่สั่ง
                currentRow.createCell(cellAt).setCellValue(menuCount);
                currentRow.getCell(cellAt).setCellStyle(leftAlign);
                sh.autoSizeColumn(cellAt);
                cellAt++;

                double menuCost = (double) data2[i][2]; // ราคารวมเมนูนั้นๆ
                total += menuCost; // + ลงใน total
                currentRow.createCell(cellAt).setCellValue(menuCost);
                currentRow.getCell(cellAt).setCellStyle(leftAlign);
                sh.autoSizeColumn(cellAt);
                cellAt++;
            }

            currentRow.createCell(0).setCellValue(total * 1.07); // set ยอดสรุปลงใน cell 0 พร้อมกับรวม VAT 7%
            currentRow.getCell(0).setCellStyle(leftAlign);

            currentRow.createCell(1).setCellValue(timeFormat.format(now)); // set time stamp ที่บริการเสร็จแล้ว
            currentRow.getCell(1).setCellStyle(leftAlign);

            FileOutputStream fos = new FileOutputStream("log.xlsx"); // ส่งออกลงไฟล์
            wb.write(fos); // เขียนข้อมูลลงไฟล์ที่จะส่งออก
            wb.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void logInitial() { // สร้าง sheet วันใหม่หรือเปิดใช้บริการร้านอีกรอบ
        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // format วันที่
        LocalDateTime now = LocalDateTime.now();

        try (FileInputStream fis = new FileInputStream("log.xlsx")) {
            wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.createSheet(dayFormat.format(now)); // สร้าง sheet สำหรับวันใหม่

            org.apache.poi.ss.usermodel.Font font = wb.createFont(); // สร้าง Object excel font
            font.setColor(IndexedColors.WHITE.index); /* กำหนด properties ต่างๆ */
            font.setBold(true);

            CellStyle summary = wb.createCellStyle(); // สร้าง Object cell style สำหรับ header ยอดสรุป
            summary.setVerticalAlignment(VerticalAlignment.CENTER); /* กำหนด properties ต่างๆ */
            summary.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
            summary.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            summary.setAlignment(HorizontalAlignment.CENTER);
            summary.setFont(font);

            CellStyle timeStamp = wb.createCellStyle(); // สร้าง Object cell style สำหรับ header time stamp
            timeStamp.setVerticalAlignment(VerticalAlignment.CENTER);
            timeStamp.setFillForegroundColor(IndexedColors.SEA_GREEN.index);
            timeStamp.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            timeStamp.setAlignment(HorizontalAlignment.CENTER);
            timeStamp.setFont(font);

            sh.createRow(0).createCell(0).setCellType(CellType.STRING); // สร้าง cell สำหรับ header ยอดสรุป
            sh.getRow(0).getCell(0).setCellValue("Summary"); // กำหนดค่า
            sh.getRow(0).getCell(0).setCellStyle(summary); // กำหนด cell style
            sh.autoSizeColumn(0);

            sh.getRow(0).createCell(1).setCellType(CellType.STRING); // สร้าง cell สำหรับ header ยอดสรุป
            sh.getRow(0).getCell(1).setCellValue("Time Stamp");
            sh.getRow(0).getCell(1).setCellStyle(timeStamp);
            sh.autoSizeColumn(1);

            CellStyle style1 = wb.createCellStyle(); // สร้าง Object cell style สำหรับ header ตำแหน่งเมนูเลขคู่
            style1.setVerticalAlignment(VerticalAlignment.CENTER);
            style1.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
            style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style1.setAlignment(HorizontalAlignment.CENTER);
            style1.setFont(font);

            CellStyle style2 = wb.createCellStyle(); // สร้าง Object cell style สำหรับ header ตำแหน่งเมนูเลขคี่
            style2.setVerticalAlignment(VerticalAlignment.CENTER);
            style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
            style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setFont(font);

            CellStyle styleArr[] = {style1, style2};
            int count = 1;
            int index = 0;
            for (int i = 2; i < MenuAPI.getMenuList().length * 3 + 2; i++) {
                if (count == 1) { // เงื่อนไชสำหรับเช็ค cell ที่แสดงชื่อเมนู
                    sh.getRow(0).createCell(i).setCellType(CellType.STRING); // สร้าง cell และกำหนดชนิดของข้อมูล
                    sh.getRow(0).getCell(i).setCellValue(" Name "); // กำหนดค่า
                    sh.getRow(0).getCell(i).setCellStyle(styleArr[index]); // กำหนด cell style ตาม index
                } else if (count == 2) { // เงื่อนไชสำหรับเช็ค cell ที่แสดงจำนวนเมนู
                    sh.getRow(0).createCell(i).setCellType(CellType.STRING);
                    sh.getRow(0).getCell(i).setCellValue("Amount");
                    sh.getRow(0).getCell(i).setCellStyle(styleArr[index]);
                } else if (count == 3) { // เงื่อนไชสำหรับเช็ค cell ที่แสดงผลรวมราคาเมนู
                    sh.getRow(0).createCell(i).setCellType(CellType.STRING);
                    sh.getRow(0).getCell(i).setCellValue("  Total  ");
                    sh.getRow(0).getCell(i).setCellStyle(styleArr[index]);
                    count = 0; // เริ่มนับใหม่
                    index = index == 0 ? 1 : 0; // ใช้สลับตัวบ่ง styleArr index
                }
                sh.autoSizeColumn(i);
                count++;
            }

            sh.getRow(0).setHeight((short) 360); // กำหนดความสูงของแถวที่ 0

            /* กำหนด permission เมื่อ sheet ล็อกอยู่ */
            sh.lockSelectLockedCells(false);
            sh.lockSelectUnlockedCells(false);
            sh.lockDeleteColumns(true);
            sh.lockDeleteRows(true);
            sh.lockFormatCells(true);
            sh.lockFormatColumns(true);
            sh.lockFormatRows(true);
            sh.lockInsertColumns(true);
            sh.lockInsertRows(true);
            sh.protectSheet("12345"); // กำหนด password
            sh.enableLocking(); // ล็อก sheet
            /* */

            FileOutputStream fos = new FileOutputStream("log.xlsx"); // ส่งออกไฟล์
            wb.lockStructure(); // ล็อกโครงสร้างไฟล์
            wb.write(fos); // เขียนไฟล์
            wb.close();
        } catch (Exception ex) { // กรณีเกิด Exception ที่ sheet ที่จะสร้างมีอยู่แล้ว
            try (FileInputStream fis = new FileInputStream("log.xlsx")) {
                XSSFSheet sh = wb.getSheet(dayFormat.format(now));
                XSSFRow lastRow = sh.getRow(sh.getLastRowNum());
                sh.removeRow(lastRow); // ลบแถวที่สรุปยอดไปแล้ว เพื่อให้บริการต่อในวันนั้นได้

                FileOutputStream fos = new FileOutputStream("log.xlsx");
                wb.write(fos);
            } catch (Exception ex2) {
                System.out.println("[LogAPI] logInitial(): Error, " + ex2);
            }
        }
    }

    public static void logResultADay() {
        try (FileInputStream fis = new FileInputStream("log.xlsx")) {
            wb = new XSSFWorkbook(fis);

            DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now();

            XSSFSheet sh = wb.getSheet(dayFormat.format(now));
            String shName = sh.getSheetName();
            int rowCount = sh.getLastRowNum() + 1;

            if (rowCount == 1) {
                forceFillResult(shName);
            }

            double total = 0;
            for (int i = 1; i < rowCount; i++) {
                total += sh.getRow(i).getCell(0).getNumericCellValue();
            }
            sh.createRow(rowCount).createCell(0).setCellValue(total);

            CellStyle yellowCell = wb.createCellStyle();
            yellowCell.setFillForegroundColor(IndexedColors.YELLOW1.index);
            yellowCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            yellowCell.setAlignment(HorizontalAlignment.LEFT);

            sh.getRow(rowCount).getCell(0).setCellStyle(yellowCell);

            FileOutputStream fos = new FileOutputStream("log.xlsx");
            wb.write(fos);
            wb.close();
        } catch (IllegalArgumentException iaex) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void openSheet(String shName) {
        try (FileInputStream fis = new FileInputStream("log.xlsx")) {
            wb = new XSSFWorkbook(fis);

            int shIndex = wb.getSheetIndex(shName);
            wb.setActiveSheet(shIndex);
            wb.setSelectedTab(shIndex);

            FileOutputStream fos = new FileOutputStream("log.xlsx");
            wb.write(fos);

            Desktop.getDesktop().open(new File("log.xlsx"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void forceFillResult(String shName) {
        try (FileInputStream fis = new FileInputStream("log.xlsx")) {
            wb = new XSSFWorkbook(fis);

            XSSFSheet sh = wb.getSheet(shName);

            CellStyle yellowCell = wb.createCellStyle();
            yellowCell.setFillForegroundColor(IndexedColors.YELLOW1.index);
            yellowCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            yellowCell.setAlignment(HorizontalAlignment.LEFT);

            sh.createRow(1).createCell(0).setCellValue(0.0);
            sh.getRow(1).getCell(0).setCellStyle(yellowCell);

            FileOutputStream fos = new FileOutputStream("log.xlsx");
            wb.write(fos);
            wb.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getServicedCountToDay() {
        try (FileInputStream fis = new FileInputStream("log.xlsx")) {
            wb = new XSSFWorkbook(fis);

            DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now();

            XSSFSheet sh = wb.getSheet(dayFormat.format(now));
            int rowCount = sh.getLastRowNum();

            return rowCount;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public void writeDataInExcel(String menuName, double menuPrice) {
    }

    public static String[][] arrayReversed(String[][] arr) {
        int j = 0;
        String temp[][];
        temp = new String[arr.length][arr[0].length];
        for (int i = arr.length - 1; i >= 0; i--) {
            temp[j] = arr[i];
            j++;
        }
        return temp;
    }

    public static String[][] getAllSheet() {
        return allSheet;
    }
}
