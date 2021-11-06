
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.ArrayList;

public final class TableManagerFrame implements ActionListener, PropertyChangeListener {

    private static JPanel container;
    private static int tableNumber;
    private static JTable table, table2;
    private JPanel p2, p3, p4, p5, p6;
    private JButton backBtn, addMenuBtn[];
    private ArrayList<JButton> removeMenuBtn;
    private JLabel tableTxt;
    private JTextArea billTxt;
    private JScrollPane scroll, scroll2;
    private DefaultTableModel model, model2;
    private String[][] data;
    private Object[][] data2;
    private ArrayList<Object[]> data2ToShow;
    private JButton reset, total, print;

    public TableManagerFrame() {

        //Button
        reset = new JButton("Reset");
        reset.addActionListener(this);
        total = new JButton("Total");
        total.addActionListener(this);
        print = new JButton("Print");
        print.addActionListener(this);
        print.setEnabled(false);

        //Table1
        table = new JTable();
        this.menuTableUpdate();

        //Table2
        table2 = new JTable();
        this.addedMenuTableUpdate();

        //Main Panel
        container = new JPanel();
        container.addPropertyChangeListener(this);
        container.setLayout(new BorderLayout());
        container.setBounds(0, 0, 100, 100);
        container.setBackground(Color.lightGray);

        //Sub Panel NORTH
        p2 = new JPanel();
        p2.setLayout(new GridLayout(1, 6));
        p2.setPreferredSize(new Dimension(0, 50));

        //Back Button
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        backBtn.setPreferredSize(new Dimension(100, 50));

        //Table Label
        tableTxt = new JLabel("Table");
        tableTxt.setFont(new Font("Serif", Font.BOLD, 20));

        //Add component to NORTH Panel
        p2.add(backBtn);
        p2.add(new JLabel(" "));
        p2.add(new JLabel(" "));
        p2.add(tableTxt);
        p2.add(new JLabel(" "));
        p2.add(new JLabel(" "));

        //Sub panel CENTER
        p3 = new JPanel();
        p3.setLayout(new GridLayout(1, 3, 10, 10));

        //Sub panel in p3 (Menu)
        p4 = new JPanel();

        //Sub panel in p3 (Order)
        p5 = new JPanel();

        //Sub panel in p3 (Bill)
        p6 = new JPanel();
        p6.setLayout(new GridLayout(1, 3, 10, 20));
        p6.setPreferredSize(new Dimension(100, 50));
        p6.add(reset);
        p6.add(total);
        p6.add(print);

        //Bill TextField
        billTxt = new JTextArea();

        //Add to CENTER
        scroll = new JScrollPane(table);
        scroll2 = new JScrollPane(table2);
        p3.add(scroll);
        p3.add(scroll2);
        p3.add(billTxt);

        //Add to Main Panel
        container.setSize(960, 640);
        container.add(p2, BorderLayout.NORTH);
        container.add(p3, BorderLayout.CENTER);
        container.add(p6, BorderLayout.SOUTH);
        container.setVisible(false);
    }

    public static void setTableNumber(int tableNumber) {
        TableManagerFrame.tableNumber = tableNumber;
    }

    public void menuTableUpdate() {
        data = MenuAPI.getMenu();
        String header[] = {"Col 1", "Col 2", "เพิ่มเมนู"};

        addMenuBtn = new JButton[data.length];
        model = new DefaultTableModel();
        model = new DefaultTableModel(data, header);
        for (int i = 0; i < data.length; i++) {
            addMenuBtn[i] = new JButton();
            addMenuBtn[i].addActionListener(this);
        }

        table.setModel(model);
        table.setBounds(0, 0, 0, 0);
        table.setRowHeight(50);
        table.setRowSelectionAllowed(false);
        table.getColumn("Col 2").setMaxWidth(80);
        table.getColumn("เพิ่มเมนู").setCellRenderer(new ButtonRenderer());
        table.getColumn("เพิ่มเมนู").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("เพิ่มเมนู").setMaxWidth(60);
    }

    public void addedMenuTableUpdate() {
        data2 = TableInformation.getAddedMenu("table-" + tableNumber);
        data2ToShow = new ArrayList<Object[]>();
        String header2[] = {"Col 1", "Col 2", "Col 3", "Action"};

        removeMenuBtn = new ArrayList<JButton>();
        model2 = new DefaultTableModel();
        model2.setColumnIdentifiers(header2);
        for (int i = 0; i < data2.length; i++) {
            if ((int) data2[i][2] != 0) {
                data2ToShow.add(new Object[]{i, data2[i][0], data2[i][1], data2[i][2]});
                removeMenuBtn.add(new JButton());
                removeMenuBtn.get(removeMenuBtn.size() - 1).addActionListener(this);
            }
        }

        model2.setRowCount(data2ToShow.size());
        for (int i = 0; i < data2ToShow.size(); i++) {
            model2.setValueAt(data2ToShow.get(i)[1], i, 0);
            model2.setValueAt(data2ToShow.get(i)[2], i, 1);
            model2.setValueAt(data2ToShow.get(i)[3], i, 2);
        }

        table2.setModel(model2);
        table2.setBounds(0, 0, 0, 0);
        table2.setRowHeight(50);
        table2.setRowSelectionAllowed(false);
        table2.getColumn("Col 2").setMaxWidth(80);
        table2.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table2.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        table2.getColumn("Action").setMaxWidth(60);
    }

    public static JPanel getContainer() {
        return container;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(backBtn)) {
            container.setVisible(false);
            container.setBackground(Color.red); // ดัก PropertyListener
            TableFrame.setTableStatus();
            TableFrame.getContainer().setVisible(true);
        } else if (evt.getSource().equals(reset)) {
            TableInformation.resetMenu("table-" + tableNumber);
        } else if (evt.getSource().equals(total)) {
            JOptionPane.showMessageDialog(null, "clicked total");
        } else if (evt.getSource().equals(print)) {
//            TableFrame.getBtn(tableNumber).setBackground(Color.GREEN);
            TableInformation.resetMenu("table-" + tableNumber);
            JOptionPane.showMessageDialog(null, "clicked print");
        } else {
            for (int i = 0; i < data.length; i++) {
                if (evt.getSource().equals(addMenuBtn[i])) {
                    TableInformation.addMenu("table-" + tableNumber, i);
                }
            }
            for (int i = 0; i < removeMenuBtn.size(); i++) {
                if (evt.getSource().equals(removeMenuBtn.get(i))) {
                    TableInformation.removeMenu("table-" + tableNumber, (int) data2ToShow.get(i)[0]);
                }
            }
        }
        this.addedMenuTableUpdate();
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        this.menuTableUpdate();
        this.addedMenuTableUpdate();
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            String type = table.equals(TableManagerFrame.table) ? "+" : "-";
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
            this.setBackground(Color.WHITE);
            this.add(new JButton(type));
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
            jp.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
            if (table.equals(TableManagerFrame.table)) {
                label = "+";
                addMenuBtn[row].setText(label);
                jp.add(addMenuBtn[row]);
            } else if (table.equals(TableManagerFrame.table2)) {
                label = "-";
                removeMenuBtn.get(row).setText(label);
                jp.add(removeMenuBtn.get(row));
            }
            return jp;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }
}
