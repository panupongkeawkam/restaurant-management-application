
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class EditMenu implements ActionListener {
    
    private static JFrame frame;
    private JPanel container;
    private static JTable table, table2;
    private DefaultTableModel model;
    private JScrollPane scrollPane, scrollPane2;
    private String[] columns;
    private String[] columns2;
    private String[][] data;
    private String[][] data2;
    private int rowCount;
    private JButton addBtn;
    private JButton button[];
    
    public EditMenu() {
        frame = new JFrame("Manage Menu");
        frame.setBounds(735, 240, 450, 600);
        
        container = new JPanel();
        container.setLayout(new BorderLayout());
        
        table = new JTable();
        this.tableUpdate(); // update ข้อมูลในตาราง

        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        columns2 = new String[]{"Id", "Name", "Price", "Action"};
        data2 = new String[][]{
            {"", "", ""}
        };
        
        DefaultTableModel model2 = new DefaultTableModel(data2, columns2);
        table2 = new JTable();
        table2.setModel(model2);
        table2.setRowSelectionAllowed(false);
        table2.setRowHeight(48);
        table2.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table2.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        table2.getColumn("Action").setMaxWidth(60);
//        scrollPane2 = new JScrollPane(table2);

        JPanel testP = new JPanel(new GridLayout(1, 1));
        testP.setSize(960, 480);
        testP.add(table2);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(testP, BorderLayout.SOUTH);
        
        addBtn = new JButton();
        addBtn.addActionListener(this);
        
        frame.add(container);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(false);
    }
    
    public void tableUpdate() {
        columns = new String[]{"Id", "Name", "Price", "Action"};
        data = MenuAPI.getMenu();
        model = new DefaultTableModel(data, columns);
        table.setModel(model);
        rowCount = table.getRowCount();
        button = new JButton[rowCount];
        for (int i = 0; i < rowCount; i++) {
            button[i] = new JButton();
            button[i].addActionListener(this);
        }
        table.setRowHeight(48);
        table.setRowSelectionAllowed(false);
        TableColumn action = table.getColumn("Action");
        action.setMaxWidth(60);
        action.setCellRenderer(new ButtonRenderer());
        action.setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
    public static JFrame getFrame() {
        return frame;
    }
    
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(addBtn)) {
            String menuName = (String) table2.getValueAt(0, 1);
            Double menuPrice = Double.parseDouble((String) table2.getValueAt(0, 2));
            new MenuAPI().writeDataInExcel(menuName, menuPrice);
            this.tableUpdate();
            table2.setValueAt(null, 0, 1); // รีช่อง add menu
            table2.setValueAt(null, 0, 2); // รีช่อง add menu
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
            JPanel jp = new JPanel();
            jp.setLayout(new BorderLayout());
            jp.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
            if (table.equals(EditMenu.table)) {
                label = "-";
                button[row].setText(label);
                jp.add(button[row]);
            } else if (table.equals(EditMenu.table2)) {
                label = "+";
                addBtn.setText(label);
                jp.add(addBtn);
            }
            return jp;
        }
        
        public Object getCellEditorValue() {
            return new String(label);
        }
    }
}
