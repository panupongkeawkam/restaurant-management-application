
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class TableFrame implements ActionListener {

    private static JPanel container;
    private static JButton btn[], adminBtn;
    private static int tableBusyCount;
    private JPanel panelNavBar, navLeft, navRight, panelTable, panelInfo, infoCTN[];
    private static RoundedPanel infoIconCTN[];
    private static Color infoColor[];
    private Clock clock;
    private static JLabel infoSign[], resStatLabel, tableStatMsg[], tableStatIcon[];
    private Thread thread;
    private static TableInformation tableInfo[];

    public TableFrame() {
        /* กำหนด properties UI page bar ด้านบน (จนถึงบรรทัด 69) */
        navLeft = new JPanel();
        navLeft.setLayout(new BorderLayout());
        JPanel navLeftGrid = new JPanel(new GridLayout(1, 2));
        JLabel tableSign = new JLabel("TABLES ·");
        tableSign.setFont(new Font("Sans Serif", Font.BOLD, 26)); // กำหนดฟอนต์
        tableSign.setForeground(Theme.WHITE); // กำหนดสีฟอนต์
        tableSign.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 6)); // ใช้ empty border ดัน component ภายใน
        resStatLabel = new JLabel();
        resStatLabel.setText("opened");
        resStatLabel.setForeground(Theme.GREEN);
        resStatLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        navLeftGrid.add(tableSign);
        navLeftGrid.add(resStatLabel);
        navLeftGrid.setOpaque(false); // กำหนดให้ component โปร่งแสง
        navLeft.add(navLeftGrid, BorderLayout.WEST);
        navLeft.setOpaque(false);

        navRight = new JPanel();
        navRight.setLayout(new BorderLayout());
          /* เรียนใช้งาน class นาฬิกาโดยทำงานแบบ Thread */
        clock = new Clock();
        thread = new Thread(clock);
        thread.start();
          /* */
        JPanel btnCTN = new JPanel(new GridLayout(1, 1));
        btnCTN.setPreferredSize(new Dimension(180, 100)); // กำหนดขนาด component ที่เป็นตัวลูก
        btnCTN.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 12));
        btnCTN.setOpaque(false);
        adminBtn = new RoundedButton(12, Theme.BLUE); // เรียกใช้ class ปุ่มขอบมนและกำหนดค่า args เป็นค่า ความมน สี
        adminBtn.setName("admin");
        adminBtn.setLayout(new GridBagLayout());
        adminBtn.addActionListener(this);
        adminBtn.setOpaque(false);
        JLabel adminBtnSign = new JLabel("Administrator");
        adminBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        adminBtnSign.setForeground(Theme.WHITE);
        adminBtn.add(adminBtnSign);
        btnCTN.add(adminBtn);
        navRight.add(clock, BorderLayout.CENTER);
        navRight.add(btnCTN, BorderLayout.EAST);
        navRight.setOpaque(false);

        panelNavBar = new JPanel();
        panelNavBar.setLayout(new GridLayout(1, 2));
        panelNavBar.setPreferredSize(new Dimension(100, 50));
        panelNavBar.setBackground(Theme.BLACK);
        panelNavBar.add(navLeft);
        panelNavBar.add(navRight);
        /* */

        /* กำหนด properties UI ของตัวโต๊ะทั้งหมด (จนถึงบรรทัด 108) */
        panelTable = new JPanel();
        panelTable.setOpaque(false);
        panelTable.setLayout(new GridLayout(6, 4, 6, 6));
        panelTable.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        tableStatMsg = new JLabel[24]; // ประกาศ label เป็น array
        tableStatIcon = new JLabel[24];
        btn = new JButton[24]; // ประกาศ button เป็น array
        tableInfo = new TableInformation[24]; // ประกาศ Object ของโต๊ะทั้งหมด 24 โต๊ะเป็น array
        for (int i = 0; i < 24; i++) {
            btn[i] = new RoundedButton(20, Theme.WHITE); // เรียกใช้ class ปุ่มขอบมนและกำหนดค่า args เป็นค่า ความมน สี
            btn[i].setName("table"); // ตั้งชื่อปุ่มเพื่อใช้เป็นการแยกเงื่อนไขพิเศษบางปุ่ม
            btn[i].setLayout(new BorderLayout());

            JPanel topP = new JPanel(new GridLayout(1, 2));
            topP.setOpaque(false);
            JLabel tableNum = new JLabel("TABLE " + (i + 1));
            tableNum.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
            tableNum.setForeground(Theme.DISABLE_FG);
            tableStatIcon[i] = new JLabel(new ImageIcon("icon/table-free.png")); // กำหนด icon ลงใน label
            JPanel tsicContainer = new JPanel(new BorderLayout());
            tsicContainer.add(tableStatIcon[i], BorderLayout.EAST);
            tsicContainer.setOpaque(false);
            topP.add(tableNum);
            topP.add(tsicContainer);

            tableStatMsg[i] = new JLabel("Free");
            tableStatMsg[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
            tableStatMsg[i].setFont(new Font("Sans Serif", Font.BOLD, 24));

            btn[i].add(topP, BorderLayout.NORTH);
            btn[i].add(tableStatMsg[i], BorderLayout.SOUTH);
            btn[i].addActionListener(this);

            panelTable.add(btn[i]);
        }
        /* */
        
        /* กำหนด properties UI ของตัวแสดงสถานะโต๊ะโดยรวม (จนถึงบรรทัด 169) */
        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(1, 3, 12, 12));
        panelInfo.setOpaque(false);
        panelInfo.setPreferredSize(new Dimension(100, 120));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(0, 16, 40, 16));
        panelInfo.setOpaque(false);

        infoCTN = new JPanel[3]; // ประกาศ panel เป็น array
        infoIconCTN = new RoundedPanel[3]; // ประกาศ button เป็น array
        infoSign = new JLabel[3]; // ประกาศ label เป็น array
        infoColor = new Color[]{ // กำหนดสีของ component ตาม index
            Theme.RED,
            Theme.GREEN,
            Theme.YELLOW
        };
        ImageIcon infoIcon[] = new ImageIcon[]{ // กำหนด icon ของ component ตาม index
            new ImageIcon("icon/busy.png"),
            new ImageIcon("icon/free.png"),
            new ImageIcon("icon/total.png")
        };
        String infoTextHead[] = new String[]{ // กำหนดข้อความของ component ตาม index
            "Busy Tables: " + tableBusyCount,
            "Free Tables: " + (24 - tableBusyCount),
            "Total Serviced: --"
        };
        String infoTextCaption[] = new String[]{ // กำหนดข้อความของ component ตาม index
            "จำนวนโต๊ะที่ลูกค้าใช้บริการอยู่",
            "จำนวนโต๊ะว่าง",
            "จำนวนโต๊ะรวมที่ใช้บริการแล้ว (วันนี้)"
        };
        for (int i = 0; i < 3; i++) {
            infoCTN[i] = new JPanel();
            infoCTN[i].setLayout(new BorderLayout());

            infoIconCTN[i] = new RoundedPanel(20, infoColor[i], "start"); /* เรียกใช้ class panel ขอบมน
                                                                           * และกำหนดค่า args เป็น ค่าความมน สี ทิศทาง
                                                                           */
            infoIconCTN[i].setOpaque(false);
            infoIconCTN[i].setLayout(new GridBagLayout());
            infoIconCTN[i].setPreferredSize(new Dimension(80, 100));

            JLabel icon = new JLabel(infoIcon[i]); // กำหนด icon ตาม index ของ array ที่ประกาศไว้
            infoIconCTN[i].add(icon);

            JPanel infoTextCTN = new RoundedPanel(0, Theme.BLACK); /* เรียกใช้ class panel ขอบมน
                                                                    * และกำหนดค่า args เป็น ค่าความมน สี
                                                                    */
            infoTextCTN.setLayout(new BorderLayout());
            infoSign[i] = new JLabel(infoTextHead[i]); // กำหนดข้อความตาม index ของ array ที่ประกาศไว้
            infoSign[i].setForeground(infoColor[i]); // กำหนดสีฟอนต์ตาม index ของ array ที่ประกาศไว้
            infoSign[i].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
            JLabel infoCaption = new JLabel(infoTextCaption[i]);
            infoCaption.setForeground(Theme.DISABLE_BG); // กำหนดสีฟอนต์โดยเรียกใช้ผ่าน class Theme
            infoCaption.setFont(Theme.sarabun12); // กำหนดฟอนต์โดยเรียกใช้ผ่าน class Theme
            infoCaption.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));
            infoTextCTN.add(infoSign[i], BorderLayout.NORTH);
            infoTextCTN.add(infoCaption, BorderLayout.SOUTH);

            infoCTN[i].add(infoIconCTN[i], BorderLayout.WEST);
            infoCTN[i].add(infoTextCTN, BorderLayout.CENTER);

            panelInfo.add(infoCTN[i]);
        }
        /* */

        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(Theme.GREY);
        container.setSize(960, 720);
        container.add(panelNavBar, BorderLayout.NORTH);
        container.add(panelTable, BorderLayout.CENTER);
        container.add(panelInfo, BorderLayout.SOUTH);
        container.setVisible(true);

        setRestaurantStatus(); // เรียกฟังก์ชันการแสดงโต๊ะ (ร้านปิดหรือร้านเปิด)
    }

    public static void setRestaurantStatus() {
        /* อ่านและนำเข้าไฟล์ที่ใช้เก็บสถานะร้านว่าปิดหรือเปิด */
        try (FileInputStream fis = new FileInputStream("status.dat")) {
            int data = fis.read(); // อ่านตัวอักษร ('t' == เปิด, 'f' == ปิด)
            if (data == 'f') { // ถ้าร้านปิด
                TableInformationAPI.tableInitial(); // กำหนดเมนูของทุกโต๊ะให้เป็นค่าเริ่มต้นตามเมนูในร้าน
                for (int i = 0; i < 24; i++) {
                    btn[i].setEnabled(false); // ปิดการใช้งานปุ่มไม่ให้กดได้
                    ((RoundedButton) btn[i]).setColor(Theme.DISABLE_BG); // กำหนดสีปุ่มเป็นสีเทาผ่าน method class RoundedButton
                    tableStatIcon[i].setIcon(new ImageIcon("icon/table-disable.png")); // กำหนด icon เป็นสีเทา
                    tableStatMsg[i].setText("");
                    tableInfo[i] = new TableInformation(i + 1); // เรียกใช้งาน class TableInfomation ใหม่ให้เป็นค่าเริ่มต้น
                }
                String infoTextHead[] = new String[]{ // กำหนดข้อความตาม index
                    "Busy Tables: --",
                    "Free Tables: --",
                    "Total Serviced: --"
                };
                for (int i = 0; i < 3; i++) {
                    ((RoundedPanel) infoIconCTN[i]).setColor(Theme.DISABLE_FG); /* กำหนดสี panel แสดงสถานะเป็นสีเทา
                                                                                 * ผ่าน method class RoundedPanel
                                                                                 */
                    infoSign[i].setForeground(Theme.DISABLE_BG);
                    infoSign[i].setText(infoTextHead[i]); // กำหนดข้อความตาม index
                }
                resStatLabel.setText("closed");
                resStatLabel.setForeground(Theme.RED);
            } else if (data == 't') { // ถ้าร้านเปิด
                if (!new File("table-info.xlsx").exists()) { // ถ้าไม่พบไฟล์ดังกล่าว
                    TableInformationAPI.tableInitial(); // กำหนดเมนูของทุกโต๊ะให้เป็นค่าเริ่มต้นตามเมนูในร้าน
                }
                for (int i = 0; i < 24; i++) {
                    btn[i].setEnabled(true); // เปิดการใช้งานปุ่มไม่ให้กดได้
                    ((RoundedButton) btn[i]).setColor(Theme.WHITE); // กำหนดสีปุ่มเป็นสีขาวผ่าน method class RoundedButton
                    tableInfo[i] = new TableInformation(i + 1); /* เรียกใช้งาน class TableInfomation ใหม่
                                                                 * ให้เป็นค่าเริ่มต้นหรือข้อเดิมที่ buffer ไว้
                                                                 */
                }
                for (int i = 0; i < 3; i++) {
                    ((RoundedPanel) infoIconCTN[i]).setColor(infoColor[i]);
                    infoSign[i].setForeground(infoColor[i]);
                }
                setTableStatus(); // เรียก method กำหนดสถานะของแต่ละโต๊ะ (ว่าง, ไม่ว่าง)
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } // สิ้นสุด method setRestaurantStatus()
    }

    public static void setTableStatus() { // กำหนดสถานะของแต่ละโต๊ะ
        tableBusyCount = 0; // จำนวนโต๊ะไม่ว่าง
        for (int i = 0; i < 24; i++) {
            if (tableInfo[i].isBusy()) { // ถ้า Object TableInformation ตำแหน่ง i ไม่ว่าง
                tableStatMsg[i].setText("Busy");
                tableStatIcon[i].setIcon(new ImageIcon("icon/table-busy.png"));
                tableBusyCount++; // จำนวนโต๊ะไม่ว่างเพิ่มขึ้น 1
            } else if (!tableInfo[i].isBusy()) { // ถ้า Object TableInformation ตำแหน่ง i ว่าง
                tableStatMsg[i].setText("Free");
                tableStatIcon[i].setIcon(new ImageIcon("icon/table-free.png"));
            }
        }
        /* กำหนดค่าสถานะโต๊ะโดยรวมต่างๆ */
        infoSign[0].setText("Busy Tables: " + tableBusyCount); 
        infoSign[1].setText("Free Tables: " + (24 - tableBusyCount));
        infoSign[2].setText("Total Terviced: " + LogAPI.getServicedCountToDay());
    }

    public static void tableInfoInit() {
        for (int i = 0; i < 24; i++) {
            tableInfo[i] = new TableInformation(i + 1);
        }
    }

    public static TableInformation[] getTableInfo() {
        return tableInfo;
    }

    public static JPanel getContainer() {
        return container;
    }

    public static int getTableBusyCount() {
        return tableBusyCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(adminBtn)) { // เมื่อกดปุ่ม administrator ให้สลับหน้าต่าง
            LoginFrame.getFrame().setLocationRelativeTo(null);
            LoginFrame.getFrame().setVisible(true);
        }
        for (int i = 0; i < 24; i++) {
            if (e.getSource().equals(btn[i])) { // เมื่อกดปุ่มของโต๊ะตำแหน่ง i 
                container.setVisible(false); // สลับหน้าต่าง
                LoginFrame.getFrame().dispose(); // สลับหน้าต่าง
                TableManagerFrame.setTableIndex(i); // กำหนดตำแหน่งของโต๊ะที่จะแสดงและโยนให้ UI
                TableManagerFrame.getContainer().setVisible(true);
                TableManagerFrame.getContainer().setBackground(Color.YELLOW); /* เปลี่ยน property ของ panel 
                                                                               * เพื่อให้มีการเกิด PropertyChangeEvent 
                                                                               * จากนั้นให้มีการเรียก propertyChange()
                                                                               * เพื่อให้มีการ refresh ข้อมูล
                                                                               */
            }
        }
    }
}
