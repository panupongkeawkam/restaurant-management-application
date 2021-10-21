
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Admin implements ActionListener {

    private JPanel panel;
    private JButton backBtn;
    private MainFrame mf;

    public Admin(MainFrame mf) {
        this.mf = mf;
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setSize(960, 720);
        panel.setLayout(new FlowLayout());
        panel.add(backBtn);
        panel.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backBtn)) {
            this.panel.setVisible(false);
            mf.getTable().getPanel().setVisible(true);
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }

}
