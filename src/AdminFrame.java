
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;

public class AdminFrame implements ActionListener {

//    static JFrame f;
    private static JPanel container;
    private JPanel row1, row2;
    private JPanel p1, p2, p3;
    private JButton openCloseBtn, menuManagerBtn, changePINBtn, logoutBtn;
    private JButton showDetail[];
    private String data[][];
    private String column[];
    private int rowCount;
    private JTable table;
    private JScrollPane scroll;

    public AdminFrame() {
        data = LogAPI.getLog();
        column = new String[]{"ID", "DATE", "SUMMARY", "TABLE SERVICED", "EXCEL"};

//        f = new JFrame();
        container = new JPanel();
        row1 = new JPanel();
        row2 = new JPanel();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        openCloseBtn = new JButton();
        menuManagerBtn = new JButton();
        changePINBtn = new JButton();
        logoutBtn = new JButton();
        DefaultTableModel model = new DefaultTableModel(data, column);
        table = new JTable(model);
        rowCount = table.getRowCount();
        showDetail = new JButton[rowCount];
        for (int i = 0; i < rowCount; i++) {
            showDetail[i] = new JButton();
            showDetail[i].addActionListener(this);
        }

        openCloseBtn.setText("Close");
        p1.setLayout(new GridLayout(1, 1));
        openCloseBtn.addActionListener(this);
        p1.add(openCloseBtn);
        p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        menuManagerBtn.setText("Manage Menu");
        menuManagerBtn.addActionListener(this);
        p2.setLayout(new GridLayout(1, 1));
        p2.add(menuManagerBtn);
        p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        changePINBtn.addActionListener(this);
        changePINBtn.setText("Change PIN");
        logoutBtn.addActionListener(this);
        logoutBtn.setText("LogoutBtn");

        p3.setLayout(new GridLayout(2, 1));
        p3.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        p3.add(changePINBtn);
        p3.add(logoutBtn);

        row1.setBounds(0, 0, 960, 240);
        row1.setLayout(new GridLayout(1, 3));
        row1.add(p1);
        row1.add(p2);
        row1.add(p3);

        row2.setBounds(0, 240, 960, 480);
        row2.setLayout(new GridLayout(1, 1));
        table.setCellSelectionEnabled(false);
        table.setRowHeight(48);
        table.getTableHeader().setReorderingAllowed(false);
//        table.getColumn("EXCEL").setMaxWidth(100);
        table.getColumn("EXCEL").setCellRenderer(new ButtonRenderer());
        table.getColumn("EXCEL").setCellEditor(new ButtonEditor(new JCheckBox()));
        scroll = new JScrollPane(table);
        row2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        row2.add(scroll);

        container.setSize(960, 720);
        container.setLayout(null);
        container.setBackground(Color.gray);
        container.add(row1);
        container.add(row2);
        container.setVisible(false);

//        f.setBounds(480, 160, 960, 720);
//        f.add(container);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setVisible(true);
    }

    public static JPanel getContainer() {
        return container;
    }

    public void actionPerformed(ActionEvent evt) {
        Object event = evt.getSource();
        for (int i = 0; i < rowCount; i++) {
            if (event.equals(showDetail[i])) {
                JOptionPane.showMessageDialog(null, "clicked row " + i + "\n" + table.getValueAt(i, 1));
            }
        }
        if (event.equals(openCloseBtn)) {
            if (isOpen() && TableFrame.getTableBusyCount() == 0) {
                setStatus('f');
                JOptionPane.showMessageDialog(null, "ปิดร้านล๊าา");
            } else if (isOpen() && TableFrame.getTableBusyCount() > 0) {
                JOptionPane.showMessageDialog(null, "เคลียร์โต๊ะก่อนค่อยปิดร้าน ไอสัส!");
            } else if (!isOpen()) {
                setStatus('t');
                TableInformation.tableInitial();
                JOptionPane.showMessageDialog(null, "เปิดร้านแล้วโว้ยยย");
            }
            TableFrame.setRestaurantStatus();
        } else if (event.equals(menuManagerBtn)) {
            if (isOpen()) {
                JOptionPane.showMessageDialog(null, "ปิดร้านก่อนค่อยแก้น้อง เดี๋ยวร้านระเบิด");
            } else {
                EditMenu.getFrame().setVisible(true);
            }
        } else if (event.equals(changePINBtn)) {
            ChangePINFrame.getFrame().setVisible(true);
        } else if (event.equals(logoutBtn)) {
            container.setVisible(false);
            TableFrame.getContainer().setVisible(true);
        }
    }

    public void setStatus(char st) {
        if (st == 't') {
            TableInformation.tableInitial();
        }
        try (FileOutputStream fos = new FileOutputStream("status.dat")) {
            fos.write(st);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean isOpen() {
        try (FileInputStream fis = new FileInputStream("status.dat")) {
            int st = fis.read();
            if (st == 't') {
                return true;
            } else if (st == 'f') {
                return false;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // edit
            this.setBackground(Color.WHITE); // edit
            this.add(new JButton("Excel")); // edit
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JPanel jp = new JPanel(); // edit
            jp.setLayout(new BorderLayout()); // edit
            jp.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // editf
            label = "Excel";
            showDetail[row].setText(label);
            jp.add(showDetail[row]);
            return jp; // edit
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }

    public static void main(String[] args) {
        new LogAPI();
//        new AdminFrame();
    }
}
