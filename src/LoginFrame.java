
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.HashMap;

public class LoginFrame implements ActionListener {
    
    private static JFrame frame;
    private JPanel container, topPanel, numPad;
    private JLabel label;
    private JTextField textField;
    private JButton numBtn[];
    String password = "111111";
    String passwordInput = "";
    
    public LoginFrame() {
        frame = new JFrame("Login");
        container = new JPanel();
        topPanel = new JPanel();
        numPad = new JPanel();
        label = new JLabel("PIN");
        textField = new JTextField();
        numBtn = new JButton[12];
        
        topPanel.setLayout(new GridLayout(2, 1, 5, 5));
        topPanel.add(label);
        topPanel.add(textField);
        numPad.setLayout(new GridLayout(4, 3, 10, 10));
        
        String numChar[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "C"};
        for (int i = 0; i < 12; i++) {
            numBtn[i] = new JButton(numChar[i]);
            numPad.add(numBtn[i]);
            if (i == 9) {
                numBtn[i].setVisible(false);
                continue;
            }
            numBtn[i].addActionListener(this);
        }

//        container.setBackground(Color.yellow);
        container.setLayout(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        container.add(topPanel, BorderLayout.NORTH);
        container.add(numPad, BorderLayout.CENTER);
        
        frame.setBounds(860, 440, 200, 300);
        frame.add(container);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(false);
    }
    
    public static JFrame getFrame() {
        return frame;
    }
    
    public void actionPerformed(ActionEvent evt) {
        for (int i = 0; i < 12; i++) {
            if (evt.getSource().equals(numBtn[i])) {
                String num = numBtn[i].getText();
                if (num.equals("C")) {
                    textField.setText("");
                    passwordInput = "";
                    break;
                }
                passwordInput += num;
                textField.setText(textField.getText() + "*");
            }
        }
        if (passwordInput.length() == 6 && passwordInput.equals(password)) {
            this.frame.setVisible(false);
            AdminFrame.getContainer().setVisible(true);
            TableFrame.getContainer().setVisible(false);
            textField.setText("");
            passwordInput = "";
        } else if (passwordInput.length() == 6) {
            JOptionPane.showMessageDialog(null, "Access Denined!");
            textField.setText("");
            passwordInput = "";
        }
    }
}
