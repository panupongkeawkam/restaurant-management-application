
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Table implements ActionListener {

    private JPanel panel;
    private JButton btn[];
    private TableBacken x[];
    private MainFrame mf;

    public Table(MainFrame mf) {
        this.mf = mf;
        panel = new JPanel();
        btn = new JButton[16];
        x = new TableBacken[16];

        panel.setBackground(Color.yellow);
        panel.setLayout(new FlowLayout());
        panel.setSize(960, 720);
        panel.setAutoscrolls(true);

        for (int i = 0; i < 16; i++) {
            btn[i] = new JButton("Table " + (i + 1));
            x[i] = new TableBacken("Table " + (i + 1));
            btn[i].addActionListener(this);
            btn[i].setSize(300, 200);
            panel.add(btn[i]);
        }

        panel.setVisible(true);
    }
    
    public JPanel getPanel() {
        return this.panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 16; i++) {
            if (e.getSource().equals(btn[i])) {
//                x[i].showInfo();
               this.panel.setVisible(false);
               mf.getAdmin().getPanel().setVisible(true);
               mf.getTableBacken().showInfo();
            }
        }
    }

}
