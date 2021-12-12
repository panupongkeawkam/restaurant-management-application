
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.table.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TableManagerFrame implements ActionListener, PropertyChangeListener {

    private static JPanel container;
    private JPanel p2, p3, p4, p5, p6, pBlank;
    private static JLabel tableTxt, totalValueSign, barCode;
    private static JButton resetBtn, totalBtn, billingBtn, printBtn;
    private JButton backBtn, addMenuBtn[];
    private ArrayList<JButton> removeMenuBtn;
    private static JTable menuTable, addedMenuTable;
    private DefaultTableModel menuModel, addedMenuModel;
    private JScrollPane menuScroll, addedMenuScroll;
    private JTextArea billTextArea;
    private Object[][] menu;
    private ArrayList<Object[]> addedMenuToShow;
    private double totalValue;
    private static int tableIndex = 0;
    private TableInformation tableInfo[];

    public TableManagerFrame() {
        /* กำหนด properties UI ของหน้าต่าง TableManagerFrame ทั้งหมด (จนถึงบรรทัด 227) */
        tableInfo = TableFrame.getTableInfo();
        p2 = new JPanel();
        p2.setLayout(new GridLayout(1, 5));
        p2.setBackground(Theme.BLACK);
        p2.setPreferredSize(new Dimension(100, 50));
        tableTxt = new JLabel("TABLE ??");
        tableTxt.setHorizontalAlignment(JLabel.CENTER);
        tableTxt.setForeground(Theme.WHITE);
        tableTxt.setFont(new Font("Sans Serif", Font.BOLD, 20));
        backBtn = new RoundedButton(12, Theme.RED);
        backBtn.setLayout(new GridBagLayout());
        backBtn.setName("back");
        JLabel backBtnSign = new JLabel("Back");
        backBtnSign.setForeground(Theme.WHITE);
        backBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        backBtn.add(backBtnSign);
        backBtn.addActionListener(this);
        backBtn.setPreferredSize(new Dimension(100, 50));
        JPanel backBtnCTN = new JPanel(new GridLayout(1, 1));
        backBtnCTN.setOpaque(false);
        backBtnCTN.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 72));
        backBtnCTN.add(backBtn);
        p2.add(backBtnCTN);
        p2.add(new JLabel());
        p2.add(tableTxt);
        p2.add(new JLabel());
        p2.add(new JLabel());

        p4 = new JPanel();
        p4.setOpaque(false);
        p4.setLayout(new BorderLayout());
        menuTable = new JTable();
        this.menuTableInit(); // เรียก method เพื่อให้มีการสร้างหรืออัพเดทข้อมูลใน table
        JPanel menuSignCTN = new RoundedPanel(8, Theme.DISABLE_FG, "top");
        menuSignCTN.setLayout(new BorderLayout());
        menuSignCTN.setPreferredSize(new Dimension(100, 40));
        JLabel menuSign = new JLabel("Menus");
        menuSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        menuSign.setForeground(Theme.WHITE);
        JLabel menuIcon = new JLabel(new ImageIcon("icon/cook.png"));
        menuSignCTN.add(menuSign, BorderLayout.WEST);
        menuSignCTN.add(menuIcon, BorderLayout.EAST);
        menuScroll = new JScrollPane(menuTable);
        menuScroll.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        menuScroll.getViewport().setBackground(Theme.WHITE);
        resetBtn = new RoundedButton(12, Theme.YELLOW);
        resetBtn.setName("reset");
        resetBtn.setLayout(new GridBagLayout());
        resetBtn.addActionListener(this);
        JLabel resetSign = new JLabel("Reset");
        resetSign.setForeground(Theme.WHITE);
        resetSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        resetBtn.add(resetSign);
        totalBtn = new RoundedButton(12, Theme.BLUE);
        totalBtn.setName("total");
        totalBtn.setLayout(new GridBagLayout());
        totalBtn.addActionListener(this);
        JLabel totalBtnSign = new JLabel("Total");
        totalBtnSign.setForeground(Theme.WHITE);
        totalBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        totalBtn.add(totalBtnSign);
        JPanel leftBtnCTN = new JPanel();
        leftBtnCTN.setLayout(new GridLayout(1, 2, 8, 8));
        leftBtnCTN.setOpaque(false);
        leftBtnCTN.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        leftBtnCTN.setPreferredSize(new Dimension(100, 48));
        leftBtnCTN.add(resetBtn);
        leftBtnCTN.add(totalBtn);
        p4.add(menuSignCTN, BorderLayout.NORTH);
        p4.add(menuScroll, BorderLayout.CENTER);
        p4.add(leftBtnCTN, BorderLayout.SOUTH);

        p5 = new JPanel();
        p5.setOpaque(false);
        p5.setLayout(new BorderLayout());
        addedMenuTable = new JTable();
        this.ordersTableUpdate(); // เรียก method เพื่อให้มีการสร้างหรืออัพเดทข้อมูลใน table
        JPanel inOrderSignCTN = new RoundedPanel(8, Theme.DISABLE_FG, "top");
        inOrderSignCTN.setLayout(new BorderLayout());
        inOrderSignCTN.setPreferredSize(new Dimension(100, 40));
        JLabel inOrderSign = new JLabel("In Order");
        inOrderSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        inOrderSign.setForeground(Theme.WHITE);
        JLabel inOrderIcon = new JLabel(new ImageIcon("icon/food-inorder.png"));
        inOrderSignCTN.add(inOrderSign, BorderLayout.LINE_START);
        inOrderSignCTN.add(inOrderIcon, BorderLayout.LINE_END);
        addedMenuScroll = new JScrollPane(addedMenuTable);
        addedMenuScroll.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        addedMenuScroll.getViewport().setBackground(Theme.WHITE);
        JPanel totalSignCTN = new JPanel();
        totalSignCTN.setOpaque(false);
        totalSignCTN.setLayout(new GridLayout(1, 1));
        totalSignCTN.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        totalSignCTN.setPreferredSize(new Dimension(100, 48));
        JPanel totalSignInner = new JPanel();
        totalSignInner.setLayout(new BorderLayout());
        totalSignInner.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        totalSignInner.setBackground(Theme.WHITE);
        JLabel totalSign = new JLabel("Total");
        totalSign.setForeground(Theme.DISABLE_FG);
        totalSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        totalValueSign = new JLabel("--");
        totalValueSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        totalValueSign.setForeground(Theme.DISABLE_FG);
        totalSignInner.add(totalSign, BorderLayout.WEST);
        totalSignInner.add(totalValueSign, BorderLayout.EAST);
        totalSignCTN.add(totalSignInner);
        p5.add(inOrderSignCTN, BorderLayout.NORTH);
        p5.add(addedMenuScroll, BorderLayout.CENTER);
        p5.add(totalSignCTN, BorderLayout.SOUTH);

        p6 = new JPanel();
        p6.setLayout(new BorderLayout());
        p6.setOpaque(false);
        p6.setBorder(BorderFactory.createEmptyBorder(36, 12, 36, 12));

        JPanel receiptSignCTN = new JPanel();
        receiptSignCTN.setLayout(new GridBagLayout());
        receiptSignCTN.setPreferredSize(new Dimension(100, 32));
        receiptSignCTN.setOpaque(false);
        JLabel receiptSign = new JLabel("RECEIPT");
        receiptSign.setForeground(Theme.DISABLE_FG);
        receiptSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        receiptSignCTN.add(receiptSign);

        JPanel billTextCTN = new RoundedPanel(8, Theme.WHITE);
        billTextCTN.setLayout(new BorderLayout());
        billTextArea = new JTextArea();
        billTextArea.setOpaque(false);
        billTextArea.setEnabled(false);
        billTextArea.setFont(Theme.sarabun12);
        JScrollPane billScroll = new JScrollPane(billTextArea);
        billScroll.getViewport().setBackground(Theme.WHITE);
        billScroll.setHorizontalScrollBar(null);
        billScroll.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        JPanel barCodeCTN = new JPanel();
        barCodeCTN.setLayout(new GridBagLayout());
        barCodeCTN.setOpaque(false);
        barCodeCTN.setPreferredSize(new Dimension(100, 75));
        barCode = new JLabel();
        barCodeCTN.add(barCode);
        billTextCTN.add(billScroll, BorderLayout.CENTER);
        billTextCTN.add(barCodeCTN, BorderLayout.SOUTH);

        billingBtn = new RoundedButton(12, Theme.BLACK);
        ((RoundedButton) billingBtn).setColor(Theme.DISABLE_BG);
        billingBtn.setName("billing");
        billingBtn.addActionListener(this);
        billingBtn.setEnabled(false);
        JLabel billingBtnSign = new JLabel("Billing");
        billingBtnSign.setForeground(Theme.WHITE);
        billingBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        billingBtnSign.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        billingBtn.add(billingBtnSign);
        printBtn = new RoundedButton(12, Theme.BLACK);
        ((RoundedButton) printBtn).setColor(Theme.DISABLE_BG);
        printBtn.setName("print");
        printBtn.setLayout(new GridBagLayout());
        printBtn.addActionListener(this);
        printBtn.setEnabled(false);
        JLabel printBtnSign = new JLabel("Print");
        printBtnSign.setForeground(Theme.WHITE);
        printBtnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        printBtnSign.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        printBtn.add(printBtnSign);
        JPanel BillBtnCTN = new JPanel();
        BillBtnCTN.setOpaque(false);
        BillBtnCTN.setLayout(new FlowLayout());
        BillBtnCTN.setPreferredSize(new Dimension(100, 50));
        BillBtnCTN.add(billingBtn);
        BillBtnCTN.add(printBtn);

        p6.add(receiptSignCTN, BorderLayout.NORTH);
        p6.add(billTextCTN, BorderLayout.CENTER);
        p6.add(BillBtnCTN, BorderLayout.SOUTH);

        p3 = new JPanel();
        p3.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        p3.setBackground(Theme.GREY);
        p3.setLayout(new GridLayout(1, 3, 10, 10));
        p3.add(p4);
        p3.add(p5);
        p3.add(p6);

        pBlank = new JPanel();
        pBlank.setPreferredSize(new Dimension(100, 60));
        pBlank.setBackground(Theme.GREY);

        container = new JPanel();
        container.addPropertyChangeListener(this);
        container.setLayout(new BorderLayout());
        container.setBounds(0, 0, 100, 100);
        container.setSize(960, 720);
        container.add(p2, BorderLayout.NORTH);
        container.add(p3, BorderLayout.CENTER);
        container.add(pBlank, BorderLayout.SOUTH);
        container.setVisible(false);
        /* */
    }

    public void menuTableInit() { // สร้างหรืออัพเดทข้อมูลใน table
        menu = MenuAPI.getMenuList(); // get เมนูจากฐานข้อมูลผ่าน class MenuAPI (แสดงเป็น Object[][2])
        String header[] = {"Menu Name", "Price", "Add Menu"};

        addMenuBtn = new JButton[menu.length];
        menuModel = new DefaultTableModel();
        menuModel = new DefaultTableModel(menu, header) { // กำหนดข้อมูลในตาราง
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // กำหนดแค่ column index 2 ที่สามารถแก้ไขได้
            }
        };
        int size = menu.length;
        if (size == 1 && ((String) menu[0][0]).trim().length() == 0) { // เงื่อนไขที่เมนูข้อมูลเป็นข้อมูลเปล่า
            size = 0;
        }
        for (int i = 0; i < size; i++) {
            menuModel.setValueAt("   " + menu[i][0], i, 0); // format ค่าข้อมูลในตาราง
            menuModel.setValueAt(menu[i][1] + "฿", i, 1); // format ค่าข้อมูลในตาราง
            addMenuBtn[i] = new RoundedButton(12, Theme.GREEN); // เรียกใช้ class ปุ่มขอบมนและกำหนดค่า args เป็นค่า ความมน สี
            addMenuBtn[i].addActionListener(this);
        }

        menuTable.setModel(menuModel); // กำหนด properties ของ table
        menuTable.setShowGrid(false);
        menuTable.setTableHeader(null);
        menuTable.setFont(Theme.sarabun14);
        menuTable.setBackground(Theme.WHITE);
        menuTable.setForeground(Theme.BLACK);
        menuTable.setRowHeight(44);
        menuTable.setRowSelectionAllowed(false);
        menuTable.getColumn("Add Menu").setCellRenderer(new ButtonRenderer()); // กำหนด cell render เป็น class ที่สร้างขึ้น
        menuTable.getColumn("Add Menu").setCellEditor(new ButtonEditor(new JCheckBox())); // กำหนด cell editor เป็น class ที่สร้างขึ้น
        menuTable.getColumn("Add Menu").setMaxWidth(60);
        if (size == 0) { // ถ้าไม่มีข้อมูลให้นำ cell render และ editor ออก
            menuTable.getColumn("Add Menu").setCellRenderer(null);
            menuTable.getColumn("Add Menu").setCellEditor(null);
        }
    }

    public void ordersTableUpdate() { // สร้างหรืออัพเดทข้อมูลใน table
        Object[][] menuInOrders = tableInfo[tableIndex].getMenuInOrders(); // get เมนูที่อยู่ในออเดอร์ของโต๊ะที่ i ผ่าน Object TableInformation
        addedMenuToShow = new ArrayList<Object[]>(); // นำข้อมูลที่จะแสดงเก็บไว้ใน ArrayList
        String header2[] = {"Menu Name", "Price", "Remove Menu"};

        removeMenuBtn = new ArrayList<JButton>(); // กำหนดปุ่มลบเมนูเป็น ArrayList
        addedMenuModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // กำหนดแค่ column index 2 ที่สามารถแก้ไขได้
            }
        };
        addedMenuModel.setColumnIdentifiers(header2);
        for (int i = 0; i < menuInOrders.length; i++) {
            if ((int) menuInOrders[i][1] != 0) { // ถ้าจำนวนเมนูอาหารในออเดอร์ ไม่เท่ากับ 0 ให้นำไปแสดง
                addedMenuToShow.add(new Object[]{i, menuInOrders[i][0], menuInOrders[i][1], menuInOrders[i][2]});
                removeMenuBtn.add(new RoundedButton(12, Theme.RED)); // เรียกใช้ class ปุ่มขอบมนและกำหนดค่า args เป็นค่า ความมน สี
                removeMenuBtn.get(removeMenuBtn.size() - 1).addActionListener(this);
            }
        }

        addedMenuModel.setRowCount(addedMenuToShow.size());
        for (int i = 0; i < addedMenuToShow.size(); i++) { // format ข้อมูลที่จะแสดงในตาราง
            addedMenuModel.setValueAt("   " + addedMenuToShow.get(i)[1] + " x" + addedMenuToShow.get(i)[2], i, 0);
            addedMenuModel.setValueAt(addedMenuToShow.get(i)[3] + "฿", i, 1);
        }

        if (addedMenuToShow.size() != 0) { // เงื่อนไขที่เช็คว่ามีเมนูในออเดอร์หรือไม่
            totalBtn.setEnabled(true); // ถ้ามีให้ปุ่ม total สามารถกดได้
            ((RoundedButton) totalBtn).setColor(Theme.BLUE);
        } else {
            totalBtn.setEnabled(false);
            ((RoundedButton) totalBtn).setColor(Theme.DISABLE_BG);
        }

        addedMenuTable.setModel(addedMenuModel); // กำหนด properties ของ table
        addedMenuTable.setShowGrid(false);
        addedMenuTable.setFont(Theme.sarabun14);
        addedMenuTable.setBackground(Theme.WHITE);
        addedMenuTable.setForeground(Theme.BLACK);
        addedMenuTable.setTableHeader(null);
        addedMenuTable.setBounds(0, 0, 0, 0);
        addedMenuTable.setRowHeight(44);
        addedMenuTable.setRowSelectionAllowed(false);
        addedMenuTable.getColumn("Price").setMaxWidth(80);
        addedMenuTable.getColumn("Remove Menu").setCellRenderer(new ButtonRenderer());
        addedMenuTable.getColumn("Remove Menu").setCellEditor(new ButtonEditor(new JCheckBox()));
        addedMenuTable.getColumn("Remove Menu").setMaxWidth(60);
    }

    public void totalBill() {
        String text = "";
        barCode.setIcon(new ImageIcon("icon/bar-code.png"));

        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        text += "\t          TABLE " + (tableIndex + 1) + "\n";
        text += "\n===============================================================================\n";
        text += "Date: " + dayFormat.format(now) + "\n";
        text += "Time Stamp: " + timeFormat.format(now) + "\n";
        text += "---------------------------------------------------------------------------------\n";
        for (int i = 0; i < addedMenuToShow.size(); i++) { // เกิน 19 บรรทัด scroll bar จะปรากฎ
            String name = (String) addedMenuToShow.get(i)[1];
            int count = (int) addedMenuToShow.get(i)[2];
            String sumPrice = String.format("               %.2f", addedMenuToShow.get(i)[3]);

            if (name.length() > 14) {
                name = name.substring(0, 13) + "..";
            }
            text += String.format("%-30s", name + " x" + count) + "\t";
            text += sumPrice + " ฿\n";
        }
        String totalValue = String.format("               %.2f ฿", tableInfo[tableIndex].getTotal());
        String totalValueWithVAT = String.format("               %.2f ฿", tableInfo[tableIndex].getTotal() * 1.07);
        text += "---------------------------------------------------------------------------------\n";
        text += String.format("%-45s", "Total: ") + totalValue + "\n";
        text += String.format("%-60s", "VAT: ") + "7 %" + "\n";
        text += String.format("%-40s", "Summary: ") + totalValueWithVAT + "\n";
        text += "---------------------------------------------------------------------------------\n\n";
        text += "\t        Thank You\n";
        text += "\n===============================================================================\n";

        billTextArea.setText(text);
    }

    public void btnAndTextInit() { // กำหนดค่าเริ่มต้นของปุ่มหรือข้อความในหน้า TableManagerFrame
        billingBtn.setEnabled(false);
        printBtn.setEnabled(false);
        ((RoundedButton) billingBtn).setColor(Theme.DISABLE_BG);
        ((RoundedButton) printBtn).setColor(Theme.DISABLE_BG);
        totalValueSign.setText("--");
        totalValueSign.setForeground(Theme.DISABLE_FG);
        billTextArea.setText("");
        barCode.setIcon(null);
    }

    public static void setTableIndex(int tableIndexArg) { // กำหนดตำแหน่งของโต๊ะที่แสดงอยู่
        ((RoundedButton) printBtn).setColor(Theme.DISABLE_BG);
        tableIndex = tableIndexArg;
        tableTxt.setText("TABLE " + (tableIndex + 1));
    }

    public static JPanel getContainer() {
        return container;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(backBtn)) { // ถ้ากดปุ่ม back
            this.btnAndTextInit(); // เรียก method กำหนดค่าเริ่มต้นของปุ่ม
            container.setVisible(false);
            container.setBackground(Color.RED); /* เปลี่ยน property ของ panel 
                                                 * เพื่อให้มีการเกิด PropertyChangeEvent 
                                                 * จากนั้นให้มีการเรียก propertyChange()
                                                 * เพื่อให้มีการ refresh ข้อมูล
                                                 */
            TableFrame.setTableStatus(); // เรียก method กำหนดสถานะของแต่ละโต๊ะ
            TableFrame.getContainer().setVisible(true);
        } else if (evt.getSource().equals(resetBtn)) { // ถ้ากดปุ่ม reset
            if (totalBtn.isEnabled()) { // ถ้าปุ่ม total ใช้งานได้ (แสดงว่ามีเมนูอยู่ในออเดอร์)
                /* ให้แสดง option YES/NO เพื่อยืนยันการ reset เมนูในออเดอร์ */
                int reply = JOptionPane.showConfirmDialog(null,
                                                          "คุณต้องการล้างเมนูในออเดอร์ทั้งหรือไม่?",
                                                          "  Question", JOptionPane.YES_NO_OPTION,
                                                          JOptionPane.YES_NO_OPTION,
                                                          new ImageIcon("icon/question.png")
                );
                if (reply == JOptionPane.YES_OPTION) { // เมื่อกด YES
                    this.btnAndTextInit(); // เรียก method กำหนดค่าเริ่มต้นของปุ่ม
                    tableInfo[tableIndex].resetMenu(); // เรียก method reset เมนูในออเดอร์ทั้งหมด
                }
            }
        } else if (evt.getSource().equals(totalBtn)) { // ถ้ากดปุ่ม total
            totalValue = tableInfo[tableIndex].getTotal(); // get ผลรวมในเมนูในออเดอร์ทั้งหมด
            totalValueSign.setText(totalValue + " ฿"); // กำหนด properties และแสดงค่า
            totalValueSign.setForeground(Theme.BLUE);
            billingBtn.setEnabled(true);
            ((RoundedButton) billingBtn).setColor(Theme.BLACK);
        } else if (evt.getSource().equals(printBtn)) { // ถ้ากดปุ่ม print
            LogAPI.saveLog(tableInfo[tableIndex].getMenuInOrders()); // บันทักเมนูในออเดอร์ตำแหน่ง i ลงใน log (Excel)
            this.btnAndTextInit(); // เรียก method กำหนดค่าเริ่มต้นของปุ่ม
            tableInfo[tableIndex].resetMenu(); // เรียก method reset เมนูในออเดอร์ทั้งหมด
            TableFrame.setTableStatus();  // เรียก method กำหนดสถานะของแต่ละโต๊ะ
            JOptionPane.showMessageDialog(null, // แสดงข้อความ print สำเร็จ
                                          "ดำเนินการสั่งพิมพ์ใบเสร็จ เสร็จสิ้น",
                                          "  Success",
                                          0,
                                          new ImageIcon("icon/success.png")
            );
            container.setVisible(false); // สลับหน้าต่างไป TableFrame
            container.setBackground(Color.red); /* เปลี่ยน property ของ panel 
                                                 * เพื่อให้มีการเกิด PropertyChangeEvent 
                                                 * จากนั้นให้มีการเรียก propertyChange()
                                                 * เพื่อให้มีการ refresh ข้อมูล
                                                 */
            TableFrame.getContainer().setVisible(true);
        } else if (evt.getSource().equals(billingBtn)) { // ถ้ากดปุ่ม bill
            printBtn.setEnabled(true); // กำหนดให้ปุ่ม print สามารถกดได้
            ((RoundedButton) printBtn).setColor(Theme.BLACK);
            this.totalBill(); // เรียก method เพื่อพิมพ์ใบเสร็จขึ้นบน TextArea
        } else {
            for (int i = 0; i < menu.length; i++) {
                if (evt.getSource().equals(addMenuBtn[i])) { // ถ้ากดปุ่ม '+' ณ ตำแหน่ง i
                    this.btnAndTextInit();
                    tableInfo[tableIndex].addMenu(i); // +1 จำนวนเมนูตำแหน่ง i
                }
            }
            for (int i = 0; i < removeMenuBtn.size(); i++) {
                if (evt.getSource().equals(removeMenuBtn.get(i))) { // ถ้ากดปุ่ม '-' ณ ตำแหน่ง i
                    this.btnAndTextInit();
                    tableInfo[tableIndex].removeMenu((int) addedMenuToShow.get(i)[0]); // -1 จำนวนเมนูตำแหน่ง i
                }
            }
        }
        this.ordersTableUpdate(); // refresh ข้อมูลในตาราง
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) { // เมื่อการเปลียนแปลง properties
        this.menuTableInit(); // refrash ตาราง
        this.ordersTableUpdate(); // refrash ตาราง
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {

        public ButtonRenderer() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            /* กำหนด properties ของ component ที่แสดงใน table cell */
            String type = table.equals(TableManagerFrame.menuTable) ? "+" : "-";
            Color color = table.equals(TableManagerFrame.menuTable) ? Theme.GREEN : Theme.RED;
            this.setLayout(new BorderLayout());
            this.setOpaque(false);
            this.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
            this.setBackground(Theme.WHITE);
            RoundedButton btn = new RoundedButton(12, color);
            btn.setLayout(new GridBagLayout());
            JLabel label = new JLabel(type);
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            label.setForeground(Theme.WHITE);
            btn.add(label);
            this.add(btn);
            return this; // คืนค่า component ให้ table cell (JPanel)
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            /* กำหนด properties ของ component ที่แสดงใน table cell เมื่อการ editing table */
            JPanel jp = new JPanel();
            jp.setLayout(new BorderLayout());
            jp.setOpaque(false);
            jp.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
            JLabel labelBtn = new JLabel();
            labelBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            labelBtn.setForeground(Theme.WHITE);
            if (table.equals(TableManagerFrame.menuTable)) { // เมื่อเป็นตารางของลิสเมนู
                label = "+";
                labelBtn.setText(label);
                addMenuBtn[row].setName("add");
                addMenuBtn[row].setLayout(new GridBagLayout());
                addMenuBtn[row].removeAll();
                addMenuBtn[row].add(labelBtn);
                jp.add(addMenuBtn[row]);
            } else if (table.equals(TableManagerFrame.addedMenuTable)) { // เมื่อเป็นตารางของเมนูในออเดอร์
                label = "-";
                labelBtn.setText(label);
                removeMenuBtn.get(row).setName("remove");
                removeMenuBtn.get(row).setLayout(new GridBagLayout());
                removeMenuBtn.get(row).removeAll();
                removeMenuBtn.get(row).add(labelBtn);
                jp.add(removeMenuBtn.get(row));
            }
            return jp; // คืนค่าเป็น JPanel ให้ table cell
        }

        @Override
        public Object getCellEditorValue() {
            return new String(label);
        }
    }
}
