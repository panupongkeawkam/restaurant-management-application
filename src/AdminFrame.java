
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;

public class AdminFrame implements ActionListener {

    private static JPanel container;
    private JPanel navBar, navLeft, navRight, body, row1, row2;
    private JButton manageMenuBtn, openAndCloseBtn, changePINBtn, logoutBtn;
    private JButton showDetailBtn[];
    private JLabel resStatLabel;
    private JLabel openAndCloseBtnIcon, openAndCloseBtnSign, openAndCloseBtnCaption;
    private JLabel manageMenuBtnIcon, manageMenuBtnSign, manageMenuBtnCaption;
    private JLabel changePINBtnIcon, changePINBtnSign, changePINBtnCaption;
    private JTable table;
    private JScrollPane scroll;
    private String allSheet[][];
    private String column[];
    private int rowCount;
    private Clock clock;
    private Thread thread;

    public AdminFrame() {
        /* กำหนด properties UI ของ AdminFrame ทั้งหมด (จนถึงบรรทัด 164) */
        navLeft = new JPanel();
        navLeft.setLayout(new BorderLayout());
        JPanel navLeftGrid = new JPanel(new GridLayout(1, 2));
        JLabel tableSign = new JLabel("ADMINISTRATOR ·");
        tableSign.setFont(new Font("Sans Serif", Font.BOLD, 26));
        tableSign.setForeground(Theme.WHITE);
        tableSign.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 6));
        resStatLabel = new JLabel();
        resStatLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        navLeftGrid.add(tableSign);
        navLeftGrid.add(resStatLabel);
        navLeftGrid.setOpaque(false);
        navLeft.add(navLeftGrid, BorderLayout.WEST);
        navLeft.setOpaque(false);

        navRight = new JPanel();
        navRight.setLayout(new BorderLayout());
        clock = new Clock();
        thread = new Thread(clock);
        thread.start();
        JPanel btnCTN = new JPanel(new GridLayout(1, 1));
        btnCTN.setPreferredSize(new Dimension(180, 100));
        btnCTN.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 12));
        btnCTN.setOpaque(false);
        logoutBtn = new RoundedButton(12, Theme.RED);
        logoutBtn.setName("logout");
        logoutBtn.setLayout(new GridBagLayout());
        logoutBtn.addActionListener(this);
        logoutBtn.setOpaque(false);
        JLabel logoutBtnSign = new JLabel("Logout & Exit");
        logoutBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        logoutBtnSign.setForeground(Theme.WHITE);
        logoutBtn.add(logoutBtnSign);
        btnCTN.add(logoutBtn);
        navRight.add(clock, BorderLayout.CENTER);
        navRight.add(btnCTN, BorderLayout.EAST);
        navRight.setOpaque(false);

        navBar = new JPanel();
        navBar.setLayout(new GridLayout(1, 2));
        navBar.setPreferredSize(new Dimension(100, 50));
        navBar.setBackground(Theme.BLACK);
        navBar.add(navLeft);
        navBar.add(navRight);

        openAndCloseBtn = new RoundedButton(16, Theme.BLACK);
        openAndCloseBtn.setName("openAndClose");
        openAndCloseBtn.addActionListener(this);
        openAndCloseBtn.setLayout(new BorderLayout());
        openAndCloseBtn.setBorder(BorderFactory.createEmptyBorder(24, 24, 40, 0));
        openAndCloseBtnIcon = new JLabel();
        openAndCloseBtnIcon.setHorizontalAlignment(JLabel.LEFT);
        openAndCloseBtnSign = new JLabel();
        openAndCloseBtnSign.setForeground(Theme.WHITE);
        openAndCloseBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        openAndCloseBtnSign.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        openAndCloseBtnCaption = new JLabel();
        openAndCloseBtnCaption.setForeground(Theme.GREY);
        openAndCloseBtnCaption.setFont(Theme.sarabun12);
        openAndCloseBtn.add(openAndCloseBtnIcon, BorderLayout.NORTH);
        openAndCloseBtn.add(openAndCloseBtnSign, BorderLayout.CENTER);
        openAndCloseBtn.add(openAndCloseBtnCaption, BorderLayout.SOUTH);

        manageMenuBtn = new RoundedButton(16, Theme.WHITE);
        manageMenuBtn.setName("manageMenu");
        manageMenuBtn.addActionListener(this);
        manageMenuBtn.addActionListener(this);
        manageMenuBtn.setLayout(new BorderLayout());
        manageMenuBtn.setBorder(BorderFactory.createEmptyBorder(24, 24, 40, 0));
        manageMenuBtnIcon = new JLabel();
        manageMenuBtnIcon.setHorizontalAlignment(JLabel.LEFT);
        manageMenuBtnSign = new JLabel("Manage Menu");
        manageMenuBtnSign.setForeground(Theme.PURPLE);
        manageMenuBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        manageMenuBtnSign.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        manageMenuBtnCaption = new JLabel();
        manageMenuBtnCaption.setForeground(Theme.DISABLE_FG);
        manageMenuBtnCaption.setFont(Theme.sarabun12);
        manageMenuBtn.add(manageMenuBtnIcon, BorderLayout.NORTH);
        manageMenuBtn.add(manageMenuBtnSign, BorderLayout.CENTER);
        manageMenuBtn.add(manageMenuBtnCaption, BorderLayout.SOUTH);

        if (isOpen()) { // ถ้าร้านเปิดอยู่
            this.setBtnStat("close"); // กำหนดปุ่มเปิดร้านหรือปุ่มต่างๆ มี action เป็น 'ปิด'
        } else if (!isOpen()) {
            this.setBtnStat("open");
        }

        changePINBtn = new RoundedButton(16, Theme.WHITE);
        changePINBtn.setName("changePIN");
        changePINBtn.addActionListener(this);
        changePINBtn.setLayout(new BorderLayout());
        changePINBtn.setBorder(BorderFactory.createEmptyBorder(24, 24, 40, 0));
        changePINBtnIcon = new JLabel(new ImageIcon("icon/PIN.png"));
        changePINBtnIcon.setHorizontalAlignment(JLabel.LEFT);
        changePINBtnSign = new JLabel("Change PIN");
        changePINBtnSign.setForeground(Theme.YELLOW);
        changePINBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        changePINBtnSign.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        changePINBtnCaption = new JLabel("เปลี่ยน PIN สำหรับเข้าหน้าต่าง Administrator");
        changePINBtnCaption.setForeground(Theme.DISABLE_FG);
        changePINBtnCaption.setFont(Theme.sarabun12);
        changePINBtn.add(changePINBtnIcon, BorderLayout.NORTH);
        changePINBtn.add(changePINBtnSign, BorderLayout.CENTER);
        changePINBtn.add(changePINBtnCaption, BorderLayout.SOUTH);

        row1 = new JPanel();
        row1.setLayout(new GridLayout(1, 3, 16, 16));
        row1.setPreferredSize(new Dimension(100, 200));
        row1.setOpaque(false);
        row1.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        row1.add(openAndCloseBtn);
        row1.add(manageMenuBtn);
        row1.add(changePINBtn);

        row2 = new RoundedPanel(16, Theme.WHITE);
        row2.setLayout(new BorderLayout());
        row2.setOpaque(false);
        table = new JTable();
        this.tableUpdate(); // เรียก method เพื่อให้มีการสร้างหรืออัพเดทข้อมูลใน table
        scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.WHITE, 0));
        row2.add(scroll, BorderLayout.CENTER);

        body = new JPanel();
        body.setLayout(new BorderLayout());
        body.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        body.setBackground(Theme.GREY);
        body.add(row1, BorderLayout.NORTH);
        body.add(row2, BorderLayout.CENTER);

        container = new JPanel();
        container.setSize(960, 720);
        container.setLayout(new BorderLayout());
        container.setBackground(Color.gray);
        container.add(navBar, BorderLayout.NORTH);
        container.add(body, BorderLayout.CENTER);
        container.setVisible(false);
        /* */
    }

    public void tableUpdate() {
        allSheet = LogAPI.getAllSheet(); // get ข้อมูล overview ของทุก log sheet (String[][])
        column = new String[]{"ID", "Date", "Summary", "Table Serviced", "View Detail"};
        DefaultTableModel model = new DefaultTableModel(allSheet, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // กำหนดแค่ col index 4 ที่สามารถแก้ไขได้
            }
        };
        table.setModel(model);
        rowCount = table.getRowCount();
        showDetailBtn = new JButton[rowCount];
        for (int i = 0; i < rowCount; i++) {
            showDetailBtn[i] = new RoundedButton(12, new Color(0, 139, 93));
            showDetailBtn[i].addActionListener(this);
            
            if (!table.getValueAt(i, 2).equals("อยู่ระหว่างการดำเนินการ")) { // ถ้าข้อมูลในตำแหน่งดังกล่างไม่ได้อยู่ระหว่างการดำเนินการ
                table.setValueAt(table.getValueAt(i, 2) + "฿", i, 2); // format ข้อมูลที่จะแสดง
                table.setValueAt(table.getValueAt(i, 3) + " โต๊ะ", i, 3);
            }
        }

        table.getTableHeader().setForeground(Theme.DISABLE_FG); // กำหนด properties
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(Theme.WHITE);
        table.setBackground(Theme.WHITE);
        table.setForeground(Theme.BLACK);
        table.setFont(Theme.sarabun14);
        table.setShowGrid(false);
        table.setRowSelectionAllowed(false);
        table.setRowHeight(40);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumn("ID").setMaxWidth(48);
        table.getColumn("View Detail").setCellRenderer(new ButtonRenderer());
        table.getColumn("View Detail").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    public void setBtnStat(String stat) { // กำหนดประเภท action ที่จะทำของปุ่ม
        if (stat.equals("open")) {
            resStatLabel.setForeground(Theme.RED);
            resStatLabel.setText("closed");

            ((RoundedButton) openAndCloseBtn).setColor(Theme.BLUE);
            openAndCloseBtnIcon.setIcon(new ImageIcon("icon/door-open.png"));
            openAndCloseBtnSign.setText("Open");
            openAndCloseBtnCaption.setText("เปิดให้บริการร้าน");

            manageMenuBtn.setEnabled(true);
            ((RoundedButton) manageMenuBtn).setColor(Theme.WHITE);
            manageMenuBtnIcon.setIcon(new ImageIcon("icon/manage-menu-enable.png"));
            manageMenuBtnSign.setForeground(Theme.PURPLE);
            manageMenuBtnCaption.setText("เพิ่ม ลบ เมนูอาหารในร้าน");
        } else {
            resStatLabel.setForeground(Theme.GREEN);
            resStatLabel.setText("opened");

            ((RoundedButton) openAndCloseBtn).setColor(Theme.RED);
            openAndCloseBtnIcon.setIcon(new ImageIcon("icon/door-close.png"));
            openAndCloseBtnSign.setText("Close");
            openAndCloseBtnCaption.setText("ปิดร้านและสรุปยอดวันนี้");

            manageMenuBtn.setEnabled(false);
            ((RoundedButton) manageMenuBtn).setColor(Theme.DISABLE_BG);
            manageMenuBtnIcon.setIcon(new ImageIcon("icon/manage-menu-disable.png"));
            manageMenuBtnSign.setForeground(Theme.DISABLE_FG);
            manageMenuBtnCaption.setText("จำเป็นต้องปิดร้านก่อน ถึงจัดการเมนูได้");
        }
    }

    public static JPanel getContainer() {
        return container;
    }

    public void actionPerformed(ActionEvent evt) {
        Object event = evt.getSource();
        for (int i = 0; i < rowCount; i++) {
            if (event.equals(showDetailBtn[i])) { // เมื่อกดปุ่ม show detail 
                LogAPI.openSheet((String) table.getValueAt(i, 1)); // จะเปิดไฟล์ log ณ วันที่ที่แสดงใน table
            }
        }
        if (event.equals(openAndCloseBtn)) { // เมื่อกดปุ่มเปิดปิดร้าน
            if (isOpen() && TableFrame.getTableBusyCount() == 0) { // ถ้าร้านเปิดอยู่และไม่มีโต๊ะที่ใช้บริการอยู่
                int reply = JOptionPane.showConfirmDialog( // YES/NO เพื่อยืนยัน
                    null,
                    "ดำเนินการปิดร้านและสรุปยอดของวันนี้หรือไม่?",
                    "  Question",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.YES_NO_OPTION,
                    new ImageIcon("icon/question.png")
                );
                if (reply == JOptionPane.YES_OPTION) {
                    this.setBtnStat("open"); // กำหนดค่า action ของปุ่ม
                    setStatus('f'); // ทำการเรียก method ปิดร้าน
                    LogAPI.logResultADay(); // สรุปยอดลงใน excel
                }
            } else if (isOpen() && TableFrame.getTableBusyCount() > 0) { // ถ้าร้านเปิดอยู่และมีโต๊ะที่ใช้บริการอยู่
                JOptionPane.showMessageDialog(
                    null,
                    "จำเป็นต้องเคลียร์ออเดอร์ในโต๊ะที่เหลือก่อน ถึงดำเนินการได้",
                    "  Warning",
                    0,
                    new ImageIcon("icon/caution.png")
                );
            } else if (!isOpen()) { // ถ้าร้านปิดอยู่
                int reply = JOptionPane.showConfirmDialog( // YES/NO เพื่อยืนยัน
                    null,
                    "ดำเนินการเปิดร้านหรือไม่?",
                    "  Question",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.YES_NO_OPTION,
                    new ImageIcon("icon/question.png")
                );
                if (reply == JOptionPane.YES_OPTION) {
                    this.setBtnStat("close"); // กำหนดค่า action ของปุ่ม
                    ManageMenuFrame.getFrame().dispose();
                    setStatus('t'); // ทำการเรียก method เปิดร้าน
                    TableInformationAPI.tableInitial();
                    LogAPI.logInitial();
                }
            }
            LogAPI.readDataFromExcel(); // อ่านข้อมูลใน log ใหม่
            this.tableUpdate(); // refresh ตาราง
            TableFrame.setRestaurantStatus(); // กำหนดให้ component ต่างๆ หรือข้อความ มีค่าตามสถานะร้าน
        } else if (event.equals(manageMenuBtn)) {
            ChangePINFrame.getFrame().dispose(); // สลับหน้าต่าง
            ManageMenuFrame.getFrame().setLocationRelativeTo(null); // กำหนดให้ frame อยู่กลางหน้าจอ
            ManageMenuFrame.getFrame().setVisible(true);
        } else if (event.equals(changePINBtn)) {
            ManageMenuFrame.getFrame().dispose(); // สลับหน้าต่าง
            ChangePINFrame.getFrame().setLocationRelativeTo(null); // กำหนดให้ frame อยู่กลางหน้าจอ
            ChangePINFrame.getFrame().setVisible(true);
        } else if (event.equals(logoutBtn)) {
            ManageMenuFrame.getFrame().dispose(); // สลับหน้าต่าง
            ChangePINFrame.getFrame().dispose();
            container.setVisible(false);
            TableFrame.getContainer().setVisible(true);
        }
    }

    public static void setStatus(char st) { // กำหนดสถานะร้าน ('t', 'f')
        if (st == 't') {
            TableInformationAPI.tableInitial();
            TableFrame.tableInfoInit();
        }

        /* เขียนลงในไฟล์ */
        try (FileOutputStream fos = new FileOutputStream("status.dat")) {
            fos.write(st);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isOpen() { // ตรวจสอบสถานะร้านผ่านไฟล์
        try (FileInputStream fis = new FileInputStream("status.dat")) {
            int st = fis.read();
            if (st == 't') {
                return true;
            } else if (st == 'f') {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {

        public ButtonRenderer() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 100));
            this.setOpaque(false);
            RoundedButton btn = new RoundedButton(12, new Color(0, 139, 93));
            btn.setName("openExcel");
            btn.setLayout(new GridBagLayout());
            JLabel btnSign = new JLabel("Excel");
            btnSign.setForeground(Theme.WHITE);
            btnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            btn.add(btnSign);
            this.add(btn);
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JPanel jp = new JPanel();
            jp.setLayout(new BorderLayout());
            jp.setOpaque(false);
            jp.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 100));
            label = "Excel";
            showDetailBtn[row].setText(label);
            showDetailBtn[row].setLayout(new GridBagLayout());
            JLabel btnSign = new JLabel("Excel");
            btnSign.setForeground(Theme.WHITE);
            btnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            showDetailBtn[row].removeAll();
            showDetailBtn[row].add(btnSign);
            showDetailBtn[row].setName("openExcel");
            jp.add(showDetailBtn[row]);
            return jp;
        }

        @Override
        public Object getCellEditorValue() {
            return new String(label);
        }
    }
}
