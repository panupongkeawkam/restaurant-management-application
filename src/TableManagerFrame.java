
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableManagerFrame implements ActionListener {

//    JFrame frame;
    private static JPanel container;
    JPanel p2, p3, p4, p5, p6;
    JButton backBtn;
    JLabel tableTxt;
    JTextArea billTxt;
    JScrollPane scrollPane;
    JButton reset, total, print;

    //data
    String data[][] = {{"Row1/1", "Row1/2", "Button"},
    {"Row2/1", "Row2/2", "Button"},
    {"Row3/1", "Row3/2", "Button"}
    };
    String header[] = {"Col 1", "Col 2", ""};

    String data2[][] = {{"Row1/1", "Row1/2", "Button"},
    {"Row2/1", "Row2/2", "Button"},
    {"Row3/1", "Row3/2", "Button"}
    };
    String header2[] = {"Col 1", "Col 2", ""};

    JTable table, table2;

    TableManagerFrame() {
//        frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(960, 720);

        //TableModel
        DefaultTableModel model = new DefaultTableModel(data, header);

        //Button
        reset = new JButton("Reset");
        total = new JButton("Total");
        print = new JButton("Print");

        //Table1
        table = new JTable(model);
        table.setBounds(0, 0, 0, 0);
        table.setRowHeight(50);

        //Table2
        table2 = new JTable(new DefaultTableModel(data2, header2));
        table2.setBounds(0, 0, 0, 0);
        table2.setRowHeight(50);

        //Main Panel
        container = new JPanel();
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
//        p4.setBackground(Color.RED);

        //Sub panel in p3 (Order)
        p5 = new JPanel();
//        p5.setBackground(Color.yellow);

        //Sub panel in p3 (Bill)
        p6 = new JPanel();
        p6.setLayout(new GridLayout(1, 3, 10, 20));
//        p6.setBackground(Color.blue);
        p6.setPreferredSize(new Dimension(100, 50));
        p6.add(reset);
        p6.add(total);
        p6.add(print);

        //Bill TextField
        billTxt = new JTextArea();

        //Add to CENTER
        p3.add(table);
        p3.add(table2);
        p3.add(billTxt);

        //Add to Main Panel
        container.setSize(960, 640);
        container.add(p2, BorderLayout.NORTH);
        container.add(p3, BorderLayout.CENTER);
        container.add(p6, BorderLayout.SOUTH);
        container.setVisible(false);

        //Add to Frame
//        frame.add(p1);
//        frame.setVisible(true);
    }

    public static JPanel getContainer() {
        return container;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(backBtn)) {
            this.container.setVisible(false);
            TableFrame.getContainer().setVisible(true);
        }
    }

}
