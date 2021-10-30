
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class AdminFrame implements ActionListener {

//    static JFrame f;
    private static JPanel container;
    private JPanel row1, row2;
    private JPanel btnDivider1, btnDivider2, btnDivider3;
    private JButton openAndClose, menuManager, changePIN, logout;
    private JButton showDetail[];
    private String data[][];
    private String column[];
    private int rowCount;
    private JTable table;
    private JScrollPane scroll;

    public AdminFrame() {
        data = LogAPI.getLog();
//        data = new String[][]{
//            {"1", "20-10-2564", "56772.20", "58"},
//            {"2", "21-10-2564", "48644.5", "42"},
//            {"3", "22-10-2564", "74253.2", "37"},
//            {"4", "23-10-2564", "45657", "51"}
//        };
        column = new String[]{"ID", "DATE", "SUMMARY", "TABLE SERVICED", "EXCEL"};

//        f = new JFrame();
        container = new JPanel();
        row1 = new JPanel();
        row2 = new JPanel();
        btnDivider1 = new JPanel();
        btnDivider2 = new JPanel();
        btnDivider3 = new JPanel();
        openAndClose = new JButton();
        menuManager = new JButton();
        changePIN = new JButton();
        logout = new JButton();
        DefaultTableModel model = new DefaultTableModel(data, column);
        table = new JTable(model);
        rowCount = table.getRowCount();
        showDetail = new JButton[rowCount];
        for (int i = 0; i < rowCount; i++) {
            showDetail[i] = new JButton();
            showDetail[i].addActionListener(this);
        }

        openAndClose.setText("Close");
        btnDivider1.setLayout(new GridLayout(1, 1));
        openAndClose.addActionListener(this);
        btnDivider1.add(openAndClose);
        btnDivider1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        menuManager.setText("Manage Menu");
        btnDivider2.setLayout(new GridLayout(1, 1));
        menuManager.addActionListener(this);
        btnDivider2.add(menuManager);
        btnDivider2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        changePIN.addActionListener(this);
        changePIN.setText("Change PIN");
        logout.addActionListener(this);
        logout.setText("Logout");

        btnDivider3.setLayout(new GridLayout(2, 1));
        btnDivider3.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        btnDivider3.add(changePIN);
        btnDivider3.add(logout);

        row1.setBounds(0, 0, 960, 240);
        row1.setLayout(new GridLayout(1, 3));
        row1.add(btnDivider1);
        row1.add(btnDivider2);
        row1.add(btnDivider3);

        row2.setBounds(0, 240, 960, 480);
        row2.setLayout(new GridLayout(1, 1));
        table.setCellSelectionEnabled(false);
        table.setRowHeight(48);
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
        if (event.equals(openAndClose)) {
            JOptionPane.showMessageDialog(null, "clicked close btn");
        } else if (event.equals(menuManager)) {
//            this.container.setVisible(false);
            EditMenu.getFrame().setVisible(true);
//            JOptionPane.showMessageDialog(null, "clicked menu manager");
        } else if (event.equals(changePIN)) {
            JOptionPane.showMessageDialog(null, "clicked change PIN");
        } else if (event.equals(logout)) {
            this.container.setVisible(false);
            TableFrame.getContainer().setVisible(true);
//            EditMenu.getTopPanel().setVisible(true);
//            JOptionPane.showMessageDialog(null, "clicked logout");
        }
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
