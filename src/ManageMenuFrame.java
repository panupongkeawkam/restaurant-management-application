
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class ManageMenuFrame implements ActionListener, FocusListener {

    private static JFrame frame;
    private JPanel container, menuListCTN, addMenuCTN;
    private JPanel inputCTN, blankCTN, menuFormCTN, priceFormCTN, addBtnCTN;
    private JTextField menuForm, priceForm;
    private static JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private String[] columns;
    private String[][] data;
    private int rowCount;
    private JButton addBtn;
    private JButton button[];
    private boolean menuPlaceHolder, pricePlaceHolder;

    public ManageMenuFrame() {
        /* กำหนด properties UI ของ ManageMenuFrame ทั้งหมด (จนถึงบรรทัด 124) */
        table = new JTable();
        this.tableUpdate(); // update หรือสร้างข้อมูลในตาราง

        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(Theme.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.WHITE, 0));
        menuListCTN = new RoundedPanel(16, Theme.WHITE);
        menuListCTN.setLayout(new BorderLayout());
        menuListCTN.add(scrollPane);

        addMenuCTN = new RoundedPanel(16, Theme.BLACK);
        addMenuCTN.setLayout(new BorderLayout());
        addMenuCTN.setPreferredSize(new Dimension(100, 40));

        blankCTN = new JPanel();
        blankCTN.setOpaque(false);
        blankCTN.setPreferredSize(new Dimension(48, 40));

        inputCTN = new JPanel();
        inputCTN.setOpaque(false);
        inputCTN.setLayout(new BorderLayout());

        menuFormCTN = new JPanel();
        menuFormCTN.setLayout(new BorderLayout());
        menuFormCTN.setOpaque(false);
        menuFormCTN.setPreferredSize(new Dimension(162, 40));
        menuFormCTN.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));
        menuForm = new JTextField();
        menuForm.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        menuForm.setForeground(Theme.BLACK);
        menuForm.setFont(Theme.sarabun14);
        menuForm.addFocusListener(this);
        menuFormCTN.add(menuForm);

        priceFormCTN = new JPanel();
        priceFormCTN.setLayout(new BorderLayout());
        priceFormCTN.setOpaque(false);
        priceFormCTN.setPreferredSize(new Dimension(76, 40));
        priceFormCTN.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));
        priceForm = new JTextField();
        priceForm.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        priceForm.setForeground(Theme.BLACK);
        priceForm.setFont(Theme.sarabun14);
        priceForm.addFocusListener(this);
        priceFormCTN.add(priceForm);

        addBtnCTN = new JPanel();
        addBtnCTN.setLayout(new BorderLayout());
        addBtnCTN.setOpaque(false);
        addBtnCTN.setPreferredSize(new Dimension(94, 40));
        addBtnCTN.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        addBtn = new RoundedButton(12, Theme.GREEN);
        addBtn.setName("add");
        addBtn.setLayout(new GridBagLayout());
        addBtn.addActionListener(this);
        JLabel addSign = new JLabel("Add");
        addSign.setForeground(Theme.WHITE);
        addSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        addBtn.add(addSign);
        addBtnCTN.add(addBtn);

        inputCTN.add(menuFormCTN, BorderLayout.LINE_START);
        inputCTN.add(priceFormCTN, BorderLayout.CENTER);
        inputCTN.add(addBtnCTN, BorderLayout.LINE_END);
        this.setPlaceHolder(menuForm, true);
        this.setPlaceHolder(priceForm, true);

        addMenuCTN.add(blankCTN, BorderLayout.LINE_START);
        addMenuCTN.add(inputCTN, BorderLayout.CENTER);

        container = new JPanel();
        container.setLayout(new BorderLayout(8, 8));
        container.setBackground(Theme.GREY);
        container.setBorder(BorderFactory.createEmptyBorder(8, 8, 12, 8));
        container.add(menuListCTN, BorderLayout.CENTER);
        container.add(addMenuCTN, BorderLayout.SOUTH);

        ImageIcon icon = new ImageIcon("icon/app-logo.png");
        frame = new JFrame("Manage Menu");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { // เมื่อปิดหน้าต่าง
                menuForm.setForeground(Theme.DISABLE_FG); // คืนค่าเริ่นต้นต่างๆ
                menuForm.setFont(Theme.sarabunItalic14);
                menuForm.setText("(ชื่อเมนู)");
                menuPlaceHolder = true;
                priceForm.setForeground(Theme.DISABLE_FG);
                priceForm.setFont(Theme.sarabunItalic14);
                priceForm.setText("(ราคา)");
                pricePlaceHolder = true;
            }
        });
        frame.setLayout(new BorderLayout());
        frame.setSize(450, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // align to center
        frame.setIconImage(icon.getImage());
        frame.add(container, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(false);
        /* */
    }

    public void tableUpdate() {
        columns = new String[]{"ID", "Menu Name", "Price", "Action"};
        Object dataTemp[][] = MenuAPI.getMenuList();

        int size = dataTemp.length;
        if (size == 1 && ((String) dataTemp[0][0]).trim().length() == 0) {
            size = 0;
        }
        data = new String[size][3];
        for (int i = 0; i < size; i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = (String) dataTemp[i][0];
            data[i][2] = String.valueOf(dataTemp[i][1]) + "฿";
        }

        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // กำหนดแค่ col index 3 ที่สามารถแก้ไขได้
            }
        };
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        rowCount = table.getRowCount();
        button = new JButton[rowCount];

        for (int i = 0; i < rowCount; i++) {
            button[i] = new RoundedButton(12, Theme.RED);
            button[i].addActionListener(this);
        }

        table.setRowHeight(40);
        table.setBackground(Theme.WHITE);
        table.setFont(Theme.sarabun14);
        table.setForeground(Theme.BLACK);
        table.setShowGrid(false);
        table.getTableHeader().setBackground(Theme.WHITE);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        table.getTableHeader().setForeground(Theme.DISABLE_FG);
        table.setRowSelectionAllowed(false);
        table.getColumn("ID").setMaxWidth(48);
        table.getColumn("Menu Name").setMinWidth(160);
        table.getColumn("Price").setMinWidth(100);

        TableColumn action = table.getColumn("Action");
        action.setCellRenderer(new ButtonRenderer());
        action.setCellEditor(new ButtonEditor(new JCheckBox()));
        if (size == 0) {
            action.setCellRenderer(null);
            action.setCellEditor(null);
        }
    }

    public void setPlaceHolder(JTextField form, boolean set) {
        if (set) {
            form.setForeground(Theme.DISABLE_FG);
            form.setFont(Theme.sarabunItalic14);
            if (form.equals(menuForm)) {
                menuPlaceHolder = true;
                form.setText("(ชื่อเมนู)");
            } else if (form.equals(priceForm)) {
                pricePlaceHolder = true;
                form.setText("(ราคา)");
            }
        } else {
            form.setForeground(Theme.BLACK);
            form.setFont(Theme.sarabun14);
            if (form.equals(menuForm)) {
                menuPlaceHolder = false;
            } else if (form.equals(priceForm)) {
                pricePlaceHolder = false;
            }
            form.setText("");
        }
    }

    public static JFrame getFrame() {
        return frame;
    }

    public void actionPerformed(ActionEvent evt) {
        ImageIcon icon = new ImageIcon("icon/incorrect.png");
        String header = "  Incorrect";
        String msg = "";
        String menuName = menuPlaceHolder ? "" : menuForm.getText().trim();
        String menuPriceStr = pricePlaceHolder ? "" : priceForm.getText().trim();
        if (evt.getSource().equals(addBtn)) {
            try {
                if (menuName.equals("") || menuPriceStr.equals("")) {
                    msg = "กรุณากรอกข้อมูลให้ครบถ้วน";
                } else {
                    Double menuPrice = Double.parseDouble(menuPriceStr);
                    if (menuPrice <= 0) {
                        menuPriceStr = "";
                        msg = "กรุณากรอกราคาเป็นตัวเลขจำนวนจริงที่มากกว่าศูนย์";
                    } else {
                        header = "   Success";
                        msg = "เพิ่ม \"" + menuName + "\" ราคา \"" + menuPrice + "฿\" ลงในร้านสำเร็จ";
                        icon = new ImageIcon("icon/success.png");
                        new MenuAPI().writeDataInExcel(menuName, menuPrice);
                        this.tableUpdate();
                        menuName = "";
                        menuPriceStr = "";
                    }
                }
            } catch (Exception ex) {
                menuPriceStr = "";
                msg = "กรุณากรอกราคาเป็นตัวเลขจำนวนจริงที่มากกว่าศูนย์";
            }
            JOptionPane.showMessageDialog(null, msg, header, 0, icon);
            menuForm.setText(menuName.trim());
            priceForm.setText(menuPriceStr.trim());
            if (menuName.trim().equals("")) {
                this.setPlaceHolder(menuForm, true);
            }
            if (menuPriceStr.trim().equals("")) {
                this.setPlaceHolder(priceForm, true);
            }
        }
        for (int i = 0; i < rowCount; i++) {
            if (evt.getSource().equals(button[i])) { // เมื่อกดปุ่มลบเมนู ณ ตำแหน่ง i
                String menuNameToDel = (String) table.getValueAt(i, 1);
                int reply = JOptionPane.showConfirmDialog( // YES/NO เพื่อยืนยัน
                        null,
                        "คุณแน่ใจว่าจะลบ \"" + menuNameToDel + "\" ออกจากร้าน?",
                        "  Question",
                        0,
                        0,
                        new ImageIcon("icon/question.png")
                );
                if (reply == JOptionPane.YES_OPTION) { // เมื่อกด YES
                    new MenuAPI().deleteMenuInExcel(i); // เรียก method ลบเมนู ณ ตำแหน่ง i
                    this.tableUpdate(); // refresh ตาราง
                }
            }
        }
    }

    @Override
    public void focusGained(FocusEvent fe) {
        if (fe.getSource().equals(priceForm) && pricePlaceHolder) {
            this.setPlaceHolder(priceForm, false);
        } else if (fe.getSource().equals(menuForm) && menuPlaceHolder) {
            this.setPlaceHolder(menuForm, false);
        }
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if (fe.getSource().equals(priceForm) && priceForm.getText().equals("")) {
            this.setPlaceHolder(priceForm, true);
        } else if (fe.getSource().equals(menuForm) && menuForm.getText().equals("")) {
            this.setPlaceHolder(menuForm, true);
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 12));
            this.setOpaque(false);

            RoundedButton btn = new RoundedButton(12, Theme.RED);
            btn.setName("delete");
            btn.setLayout(new GridBagLayout());
            JLabel btnSign = new JLabel("Delete");
            btnSign.setForeground(Theme.WHITE);
            btnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
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

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            JPanel jp = new JPanel();
            jp.setLayout(new BorderLayout());
            jp.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 12));
            label = "Delete";
            button[row].setLayout(new GridBagLayout());
            JLabel btnSign = new JLabel(label);
            btnSign.setForeground(Theme.WHITE);
            btnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            button[row].removeAll();
            button[row].add(btnSign);
            button[row].setName("delete");
            jp.add(button[row]);
            return jp;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }
}
