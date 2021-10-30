
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class EditMenu implements ActionListener { // edit

//    private static JFrame frame;
    private static JFrame frame;
    private JPanel container;
    private static JPanel bottomPanel;
    private static JTable table, table2; // edit
    private JScrollPane scrollPane, scrollPane2;
    private String[] columns;
    private String[] columns2 = new String[4];
    private String[][] data;
    private String[][] data2 = new String[4][3];
    private int rowCount; // edit
    private JButton addBtn; // edit
    private JButton button[]; // edit

    public static void hide() {
//        new AdminFrame();
//        frame.setVisible(false);
    }

    public EditMenu() {
        frame = new JFrame("Manage Menu");
        frame.setBounds(735, 240, 450, 600);
//        frame = new JFrame("JButton in JTable"); // edit
//        frame.setSize(960, 720); // edit
        container = new JPanel();
        container.setLayout(new BorderLayout());
//        frame.add(topPanel); // edit
        columns = new String[]{"Id", "Name", "Price", "Action"};
        data = MenuAPI.getMenu();
//        data = new String[][]{
//            {"1", "a", "100.00"},
//            {"2", "b", "200.00"},
//            {"3", "c", "300.00"},
//            {"4", "d", "400.00"},
//            {"5", "e", "500.00"},
//            {"6", "f", "600.00"},
//            {"7", "g", "700.00"},
//            {"8", "h", "800.00"},
//            {"9", "k", "900.00"},};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        table = new JTable();
        table.setModel(model);
        rowCount = table.getRowCount(); // edit
        table.setRowHeight(48); // edit
        table.setRowSelectionAllowed(false); // edit
        table.getColumn("Action").setMaxWidth(60); // edit
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        scrollPane = new JScrollPane(table);

        columns2 = new String[]{"Id", "Name", "Price", "Action"};
        data2 = new String[][]{
            {"", "", ""} // edit
        };

        DefaultTableModel model2 = new DefaultTableModel(data2, columns2);
        table2 = new JTable();
        table2.setModel(model2);
        table2.setRowSelectionAllowed(false);
        table2.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table2.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        table2.setRowHeight(48);  // edit
        table2.getColumn("Action").setMaxWidth(60); // edit
        scrollPane2 = new JScrollPane(table2);
        JPanel testP = new JPanel(new GridLayout(1, 1)); // edit
        testP.setSize(960, 480); // edit
        testP.add(table2); // edit
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(testP, BorderLayout.SOUTH); // edit
        addBtn = new JButton(); // edit
        addBtn.addActionListener(this); // edit
        button = new JButton[rowCount]; // edit
        for (int i = 0; i < rowCount; i++) { // edit
            button[i] = new JButton();
            button[i].addActionListener(this);
        }

        frame.add(container);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(false);

//        new EditMenu();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // edit
//        frame.setVisible(true); // edit
    }

    public static JFrame getFrame() {
        return frame;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(addBtn)) {
//            String menuName = (String) table2.getValueAt(0, 1);
//            Double menuPrice = Double.parseDouble((String) table2.getValueAt(0, 2));
//            new MenuAPI().writeDataInExcel(menuName, menuPrice);
//            table2.setValueAt(null, 0, 1);
//            table2.setValueAt(null, 0, 2);
//            data = MenuAPI.getMenu();
//            table.setModel(new DefaultTableModel(data, columns));
            JOptionPane.showMessageDialog(null, "This is add button!");
//            hide();
        }
        for (int i = 0; i < rowCount; i++) {
            if (evt.getSource().equals(button[i])) {
                String value = (String) table.getValueAt(i, 2);
                JOptionPane.showMessageDialog(null, "price: " + value);
            }
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
//            setText((value == null) ? "-" : value.toString());
//            setText("-");
            String type = table.equals(EditMenu.table) ? "-" : "+";
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8)); // edit
            this.setBackground(Color.WHITE); // edit
            this.add(new JButton(type)); // edit
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
//            label = (value == null) ? "Modify" : value.toString();
            JPanel jp = new JPanel(); // edit
            jp.setLayout(new BorderLayout()); // edit
            jp.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8)); // edit
            if (table.equals(EditMenu.table)) { // edit
                label = "-";
                button[row].setText(label);
                jp.add(button[row]);
            } else if (table.equals(EditMenu.table2)) {
                label = "+";
                addBtn.setText(label);
                jp.add(addBtn);
            }
            return jp; // edit
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }
}
