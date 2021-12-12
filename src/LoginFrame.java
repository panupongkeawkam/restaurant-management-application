
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginFrame implements ActionListener {

    private static JFrame frame;
    private JPanel container, topPanel, numPad;
    private JLabel label;
    private JTextField textField;
    private JButton numBtn[];
    private String numChar[];
    private String PIN;
    private String PINInput;

    public LoginFrame() {
        /* กำหนด properties UI ของ LoginFrame ทั้งหมด (จนถึงบรรทัด 80) */
        PINInput = new String("");
        PIN = ChangePINFrame.getPIN().trim(); // get PIN จาก class ChangePINFrame

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 64, 8, 64));
        topPanel.setOpaque(false);
        label = new JLabel("PIN");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Theme.WHITE);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        topPanel.add(label);
        textField = new JTextField();
        textField.setEditable(false);
        textField.setOpaque(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setForeground(Theme.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Theme.DISABLE_FG, 1));
        topPanel.add(textField);

        numPad = new JPanel();
        numPad.setLayout(new GridLayout(4, 3, 10, 10));
        numPad.setOpaque(false);
        numPad.setBorder(BorderFactory.createEmptyBorder(8, 24, 32, 24));

        numBtn = new JButton[12]; // ปุ่ม num pad ทั้งหมด
        numChar = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "C"};
        for (int i = 0; i < 12; i++) {
            numBtn[i] = new RoundedButton(100, Theme.WHITE);
            JLabel numSign = new JLabel(numChar[i]);
            numSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            numSign.setForeground(Theme.BLACK);
            if (i == 11) { // ปุ่ม clear text
                numBtn[i] = new RoundedButton(100, Theme.YELLOW);
                numSign.setForeground(Theme.WHITE);
            }
            numBtn[i].setName("num");
            numBtn[i].setLayout(new GridBagLayout());
            numBtn[i].addActionListener(this);
            numBtn[i].add(numSign);
            numPad.add(numBtn[i]);
            if (i == 9) { // ปุ่มเว้นว่าง
                numBtn[i].setVisible(false);
            }
        }

        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(Theme.BLACK);
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        container.add(topPanel, BorderLayout.NORTH);
        container.add(numPad, BorderLayout.CENTER);

        ImageIcon icon = new ImageIcon("icon/app-logo.png"); // กำหนด icon ของ JFrame
        frame = new JFrame("Login");
        frame.setSize(240, 360);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        frame.add(container);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(false);
        /* */

        System.out.println("PIN: " + PIN);
    }

    public static JFrame getFrame() {
        return frame;
    }

    public void actionPerformed(ActionEvent evt) {
        PIN = ChangePINFrame.getPIN().trim(); // get PIN จาก class ChangePINFrame
        for (int i = 0; i < 12; i++) {
            if (evt.getSource().equals(numBtn[i])) { // เมื่อกด num pad ของแต่ละปุ่ม
                String num = numChar[i]; // เลขของปุ่ม
                if (num.equals("C")) { // ปุ่ม clear text
                    textField.setText("");
                    PINInput = "";
                    break;
                }
                PINInput += num; // concat ตัวเลขมารวมกัน
                textField.setText(textField.getText() + "●"); // แสดงเป็นปุ่มวงกลมใน TextField
            }
        }
        if (PINInput.length() == 6 && PINInput.equals(PIN)) { // เมื่อกดครบ 6 ตัวและรหัสผ่านถูกต้อง
            textField.setText("");
            PINInput = "";
            frame.dispose();
            TableFrame.getContainer().setVisible(false); // สลับไปหน้า AdminFrame
            AdminFrame.getContainer().setVisible(true);
        } else if (PINInput.length() == 6) { // เมื่อกดครบ 6 ตัวและรหัสผ่านไม่ถูกต้อง
            JOptionPane.showMessageDialog(
                    null,
                    "รหัสผ่านไม่ถูกต้อง กรุณาลองใหม่",
                    "  Incorrect",
                    0,
                    new ImageIcon("icon/incorrect.png")
            );
            textField.setText("");
            PINInput = ""; // reset input ทั้งหมด
        }
    }
}
