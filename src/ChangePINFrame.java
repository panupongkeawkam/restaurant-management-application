
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;

public class ChangePINFrame implements ActionListener {

    private static JFrame frame;
    private JPanel container, btnContainer;
    private JLabel label1, label2;
    private JTextField oldTf, newTf;
    private JButton button;
    private static String PIN = "";

    public ChangePINFrame() {
        getPasswordFromDat();
        frame = new JFrame("Change PIN");
        container = new JPanel();
        btnContainer = new JPanel();
//        topPanel = new JPanel();
//        numPad = new JPanel();
        label1 = new JLabel("รหัสเก่า");
        label2 = new JLabel("รหัสใหม่");
        oldTf = new JTextField();
        newTf = new JTextField();
        button = new JButton("Ok");

        container.setLayout(new GridLayout(5, 1));
        container.setBorder(BorderFactory.createEmptyBorder(32, 20, 40, 20));
        btnContainer.setLayout(new FlowLayout());
        button.addActionListener(this);
        btnContainer.add(button);
        container.add(label1);
        container.add(oldTf);
        container.add(label2);
        container.add(newTf);
        container.add(btnContainer);

        frame.setBounds(860, 440, 300, 300);
        frame.setLayout(new BorderLayout());
        frame.add(container);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(false);
    }

    public void getPasswordFromDat() {
        try (FileInputStream fis = new FileInputStream("password.dat")) {
            int data = fis.read();
            while (data != -1) {
                PIN += (char) data;
                data = fis.read();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void save() {
        try (FileOutputStream fos = new FileOutputStream("password.dat")) {
            String data = PIN;
            for (int i = 0; i < data.length(); i++) {
                fos.write(data.charAt(i));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static String getPIN() {
        return PIN;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(button)) {
            try {
                String oldPIN = String.valueOf(oldTf.getText());
                String newPIN = String.valueOf(newTf.getText());
                if (oldPIN.equals(newPIN)) {
                    JOptionPane.showMessageDialog(null, "Please enter valid value", "Warning", JOptionPane.ERROR_MESSAGE);
                } else if (oldPIN.equals(PIN) && newPIN.length() == 6) {
                    Integer.parseInt(newPIN);
                    JOptionPane.showMessageDialog(null, "changed!");
                    PIN = newPIN;
                    System.out.println("PIN: " + PIN);
                    frame.setVisible(false);
                    this.save();
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid value", "Warning", JOptionPane.ERROR_MESSAGE);
                }
                oldTf.setText("");
                newTf.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid value", "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
