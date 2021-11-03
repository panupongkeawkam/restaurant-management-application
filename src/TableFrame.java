import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class TableFrame implements ActionListener {

    private static JPanel container;
    private JPanel panelTable, panelText, panelTextLeft;
    private JButton btn[], admin;
    private MainFrame mf;
    private JLabel text1, text2;

    public TableFrame() {
//        this.mf = mf;
        text1 = new JLabel("<html><div style='margin-right:100px;'>ชื่อร้าน<br>วันที่...</div></html>");
        text2 = new JLabel("<html><div style='margin-right:100px;'>เวลา...<br>มีรายการอาหาร ....จาน<br>ว่าง ...ที่</div></html>");
        container = new JPanel();
        panelTable = new JPanel();
        panelTextLeft = new JPanel();
        panelText = new JPanel();
        btn = new JButton[24];
        admin = new JButton("Admin");
        admin.addActionListener(this);

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
        panelTextLeft.add(admin, BorderLayout.EAST);
        panelText.add(panelTextLeft);
        container.add(panelText, BorderLayout.CENTER);

        container.setVisible(true);
    }

    public static JPanel getContainer() {
        return container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(admin)) {
            LoginFrame.getFrame().setVisible(true);
//            this.container.setVisible(false);
            AdminFrame.getContainer().setVisible(true);
        }
        for (int i = 0; i < 24; i++) {
            if (e.getSource().equals(btn[i])) {
                this.container.setVisible(false);
                TableManagerFrame.getContainer().setVisible(true);
//               mf.getAdmin().getPanel().setVisible(true);
//               mf.getTableBacken().showInfo();
            }
        }
    }

}