
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class MainFrame {

    private static JFrame frame;

    public MainFrame() {
        frame = new JFrame("Main Frame");
        frame.setBounds(480, 160, 960, 720);
        frame.setLayout(new BorderLayout());
        frame.add(TableFrame.getContainer());
        frame.add(TableManagerFrame.getContainer());
        frame.add(AdminFrame.getContainer());
//        frame.add(EditMenu.getContainer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
