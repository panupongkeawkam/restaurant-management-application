
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.io.*;

public class TableFrame implements ActionListener {

    private static JPanel container;
    private static JButton btn[], adminBtn;
    private static int tableBusyCount;
    private JPanel panelTable, panelText, panelTextLeft;
    private JLabel text1, text2;

    public TableFrame() {
        text1 = new JLabel("<html><div style='margin-right:100px;'>ชื่อร้าน<br>วันที่...</div></html>");
        text2 = new JLabel("<html><div style='margin-right:100px;'>เวลา...<br>มีรายการอาหาร ....จาน<br>ว่าง ...ที่</div></html>");
        container = new JPanel();
        panelTable = new JPanel();
        panelTextLeft = new JPanel();
        panelText = new JPanel();
        btn = new JButton[24];
        adminBtn = new JButton("Admin");
        adminBtn.addActionListener(this);

        container.setBackground(Color.yellow);
        container.setLayout(new BorderLayout());
        container.setSize(960, 720);
        container.setAutoscrolls(true);
        panelTable.setLayout(new GridLayout(6, 4, 6, 6));
        panelText.setLayout(new FlowLayout());
        panelTextLeft.setLayout(new BorderLayout());

        for (int i = 0; i < 24; i++) {
            btn[i] = new JButton("Table " + (i + 1));
            btn[i].addActionListener(this);
            btn[i].setSize(300, 200);
            panelTable.add(btn[i]);
        }
        container.add(panelTable, BorderLayout.NORTH);
        panelTextLeft.setBorder(BorderFactory.createEmptyBorder(300, 0, 0, 160));
        panelTextLeft.add(text1, BorderLayout.WEST);
        panelTextLeft.add(text2, BorderLayout.CENTER);
        panelTextLeft.add(adminBtn, BorderLayout.EAST);
        panelText.add(panelTextLeft);
        container.add(panelText, BorderLayout.CENTER);
        container.setVisible(true);

        setRestaurantStatus();
    }

    public static void setRestaurantStatus() {
        try (FileInputStream fis = new FileInputStream("status.dat")) {
            int data = fis.read();
            if (data == 'f') {
                TableInformation.tableInitial();
                for (int i = 0; i < 24; i++) {
                    btn[i].setEnabled(false);
                    btn[i].setBackground(Color.GRAY);
                }
            } else if (data == 't') {
                setTableStatus();
                for (int i = 0; i < 24; i++) {
                    btn[i].setEnabled(true);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void setTableStatus() {
        tableBusyCount = 0;
        for (int i = 0; i < 24; i++) {
            Object data[][] = TableInformation.getAddedMenu("table-" + i);
            boolean busy = false;
            for (int j = 0; j < data.length; j++) {
                if ((int) data[j][2] != 0) {
                    busy = true;
                    btn[i].setBackground(Color.RED);
                    tableBusyCount++;
                    break;
                }
            }
            if (!busy) {
                btn[i].setBackground(Color.GREEN);
            }
        }
        System.out.println("Table busy: " + tableBusyCount);
    }

    public static JPanel getContainer() {
        return container;
    }

    public static int getTableBusyCount() {
        return tableBusyCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(adminBtn)) {
            LoginFrame.getFrame().setVisible(true);
        }
        for (int i = 0; i < 24; i++) {
            if (e.getSource().equals(btn[i])) {
                container.setVisible(false);
                TableManagerFrame.setTableNumber(i);
                TableManagerFrame.getContainer().setVisible(true);
                TableManagerFrame.getContainer().setBackground(Color.yellow); // ดัก PropertyListener
            }
        }
    }

}
