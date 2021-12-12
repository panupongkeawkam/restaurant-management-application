
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame {

    private static JFrame frame;
    private ImageIcon icon;

    public MainFrame() {
        /* สร้าง Frame เพื่อเป็น Frame หลักของโปรแกรม */
        frame = new JFrame("Restaurant Management");
        icon = new ImageIcon("icon/app-logo.png");
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                TableInformationAPI.tableBuffering(); // เพื่อมีการปิดโปรแกรมให้เก็บเมนูในออเดอร์ในแต่ละโต๊ะลง table-info.xlsx
            }
        });
        frame.setSize(960, 720);
        frame.setLocationRelativeTo(null); // กำหนดให้ frame อยู่กึ่งกลางหน้าจอ
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        frame.add(TableFrame.getContainer(), BorderLayout.CENTER); // เพิ่ม panel TableFrame
        frame.add(TableManagerFrame.getContainer(), BorderLayout.CENTER); // เพิ่ม panel TableManagerFrame
        frame.add(AdminFrame.getContainer(), BorderLayout.CENTER); // เพิ่ม panel AdminFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
