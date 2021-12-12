
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ChangePINFrame implements ActionListener, FocusListener {

    private static JFrame frame;
    private JPanel container, inputCTN, oldCTN, newCTN, btnCTN;
    private JLabel oldSign, newSign;
    private JTextField oldTf, newTf;
    private JButton btn;
    private static String PIN;
    private boolean oldPlaceHolder, newPlaceHolder;

    public ChangePINFrame() {
        /* กำหนด properties UI ของ ChangePINFrame ทั้งหมด (จนถึงบรรทัด 118) */
        PIN = new String("");
        getPasswordFromDat(); // เรียก method อ่าน PIN จากไฟล์

        inputCTN = new JPanel();
        inputCTN.setLayout(new BorderLayout());
        inputCTN.setOpaque(false);
        inputCTN.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        oldCTN = new JPanel();
        oldCTN.setLayout(new BorderLayout());
        oldCTN.setOpaque(false);
        oldCTN.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0));
        oldSign = new JLabel("รหัสเก่า");
        oldSign.setForeground(Theme.WHITE);
        oldSign.setFont(Theme.sarabun14);
        oldSign.setBorder(BorderFactory.createEmptyBorder(0, 2, 8, 0));
        oldTf = new JTextField();
        oldTf.addFocusListener(this);
        oldTf.setOpaque(false);
        oldTf.setBorder(null);
        oldTf.setFont(Theme.sarabun12);
        oldTf.setForeground(Theme.BLACK);
        JPanel oldTfCTN = new RoundedPanel(8, Theme.WHITE);
        oldTfCTN.setLayout(new BorderLayout());
        oldTfCTN.setPreferredSize(new Dimension(100, 30));
        oldTfCTN.add(oldTf, BorderLayout.CENTER);
        oldTf.setFont(Theme.sarabun14);
        oldTf.setForeground(Theme.BLACK);
        oldCTN.add(oldSign, BorderLayout.NORTH);
        oldCTN.add(oldTfCTN, BorderLayout.CENTER);

        newCTN = new JPanel();
        newCTN.setLayout(new BorderLayout());
        newCTN.setOpaque(false);
        newCTN.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        newSign = new JLabel("รหัสใหม่");
        newSign.setForeground(Theme.WHITE);
        newSign.setFont(Theme.sarabun12);
        newSign.setBorder(BorderFactory.createEmptyBorder(0, 2, 8, 0));
        newTf = new JTextField();
        newTf.addFocusListener(this);
        newTf.setOpaque(false);
        newTf.setBorder(null);
        newTf.setFont(Theme.sarabun14);
        newTf.setForeground(Theme.BLACK);
        JPanel newTfCTN = new RoundedPanel(8, Theme.WHITE);
        newTfCTN.setLayout(new BorderLayout());
        newTfCTN.setPreferredSize(new Dimension(100, 30));
        newTfCTN.add(newTf, BorderLayout.CENTER);
        newCTN.add(newSign, BorderLayout.NORTH);
        newCTN.add(newTfCTN, BorderLayout.CENTER);

        inputCTN.add(oldCTN, BorderLayout.NORTH);
        inputCTN.add(newCTN, BorderLayout.SOUTH);
        this.setPlaceHolder(oldTf, true); // เรียก method เพื่อทำ placeholder
        this.setPlaceHolder(newTf, true); // เรียก method เพื่อทำ placeholder

        btnCTN = new JPanel();
        btnCTN.setLayout(new GridBagLayout());
        btnCTN.setOpaque(false);
        btn = new RoundedButton(12, Theme.YELLOW);
        btn.setName("changePIN");
        btn.setLayout(new GridBagLayout());
        btn.setPreferredSize(new Dimension(90, 30));
        btn.addActionListener(this);
        JLabel btnSign = new JLabel("Confirm");
        btnSign.setForeground(Theme.WHITE);
        btnSign.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        btn.add(btnSign);
        btnCTN.add(btn);

        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(Theme.BLACK);
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 60, 20));
        container.add(inputCTN, BorderLayout.CENTER);
        container.add(btnCTN, BorderLayout.SOUTH);

        ImageIcon icon = new ImageIcon("icon/app-logo.png");
        frame = new JFrame("Change PIN");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { // ดัก event เมื่อปืดหน้าต่าง
                oldTf.setForeground(Theme.DISABLE_FG); // ทำการคืนค่าเริ่มต้น
                oldTf.setFont(Theme.sarabunItalic14);
                oldTf.setText("(ตัวเลขจำนวนเต็ม 6 หลัก)");
                oldPlaceHolder = true;
                newTf.setForeground(Theme.DISABLE_FG);
                newTf.setFont(Theme.sarabunItalic14);
                newTf.setText("(ตัวเลขจำนวนเต็ม 6 หลัก)");
                newPlaceHolder = true;
            }
        });
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        frame.setSize(240, 300);
        frame.setLocationRelativeTo(null); // align to center
        frame.setLayout(new BorderLayout());
        frame.add(container);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(false);
        /* */
    }

    public void getPasswordFromDat() { // อ่าน PIN จากไฟล์
        try (FileInputStream fis = new FileInputStream("password.dat")) {
            int data = fis.read();
            while (data != -1) {
                PIN += (char) data;
                data = fis.read();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() { // เขียน PIN ใหม่ลงไฟล์
        try (FileOutputStream fos = new FileOutputStream("password.dat")) {
            String data = PIN;
            for (int i = 0; i < data.length(); i++) {
                fos.write(data.charAt(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setPlaceHolder(JTextField tf, boolean set) { // กำหนด placeholder
        if (set) {
            tf.setForeground(Theme.DISABLE_FG);
            tf.setFont(Theme.sarabunItalic14);
            tf.setText("(ตัวเลขจำนวนเต็ม 6 หลัก)");
            if (tf.equals(oldTf)) {
                oldPlaceHolder = true;
            } else if (tf.equals(newTf)) {
                newPlaceHolder = true;
            }
        } else {
            tf.setForeground(Theme.BLACK);
            tf.setFont(Theme.sarabun14);
            tf.setText("");
            if (tf.equals(oldTf)) {
                oldPlaceHolder = false;
            } else if (tf.equals(newTf)) {
                newPlaceHolder = false;
            }
        }
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static String getPIN() {
        return PIN;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(btn)) { // เมื่อกดปุ่ม comfirm
            ImageIcon icon = new ImageIcon("icon/incorrect.png"); // icon ที่จะแสดงใน JOptionPane
            String header = "  Incorrect"; // หัวข้อที่จะแสดงใน JOptionPane
            String msg = ""; // ข้อความที่จะแสดงใน JOptionPane
            String oldPIN = oldPlaceHolder ? "" : oldTf.getText().trim(); // รับ input text พร้อมกับดักเงื่อนไข placeholder
            String newPIN = newPlaceHolder ? "" : newTf.getText().trim();
            try {
                if (oldPIN.equals("") || newPIN.equals("")) { // ถ้าข้อความว่างเปล่า
                    msg = "กรุณากรอกข้อมูลให้ครบช่อง";
                } else if (!oldPIN.equals(PIN)) { // ถ้ารหัสผ่านเก่าไม่ถูกต้อง
                    msg = "รหัสผ่านเก่าไม่ถูกต้อง";
                    oldPIN = "";
                } else if (oldPIN.equals(PIN)) { // ถ้ารหัสผ่านเก่าถูกต้อง
                    if (oldPIN.equals(newPIN)) { // ถ้ารหัสใหม่ซ้ำกับรหัสเก่า
                        msg = "รหัสผ่านเก่าซ้ำกับรหัสผ่านใหม่";
                        newPIN = "";
                    } else if (newPIN.length() == 6) { // ถ้าความยาวตัวอักษรไม่ถึง
                        Integer.parseInt(newPIN); // ดักเงื่อนไขตัวเลข ถ้าแปลงไม่ได้จะเกิด Exception
                        header = "   Success";
                        msg = "เปลี่ยนรหัสผ่านสำเร็จ";
                        icon = new ImageIcon("icon/success.png");
                        PIN = newPIN;
                        System.out.println("PIN: " + PIN);
                        frame.setVisible(false);
                        this.save(); // เรียน method เพื่อบันทึกลงในฐานข้อมูล
                        oldPIN = "";
                        newPIN = "";
                    } else { // อื่นๆ
                        newPIN = "";
                        msg = "รหัสผ่านต้องเป็นตัวเลขจำนวนเต็ม 6 หลักเท่านั้น";
                    }
                }
            } catch (Exception ex) {
                newPIN = "";
                msg = "รหัสผ่านต้องเป็นตัวเลขจำนวนเต็ม 6 หลักเท่านั้น";
            }
            JOptionPane.showMessageDialog(null, msg, header, 0, icon); // แสดงข้อความแจ้งเตือน
            oldTf.setText(oldPIN);
            newTf.setText(newPIN);
            if (oldPIN.trim().equals("")) { // ถ้า text ว่าง
                this.setPlaceHolder(oldTf, true); // เรียน mothod เพื่อกำหนด placeholder
            }
            if (newPIN.trim().equals("")) {
                this.setPlaceHolder(newTf, true);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent fe) { // การดัก TextField focus เพื่อกำหนด placeholder
        if (fe.getSource().equals(oldTf) && oldPlaceHolder) {
            this.setPlaceHolder(oldTf, false);
        } else if (fe.getSource().equals(newTf) && newPlaceHolder) {
            this.setPlaceHolder(newTf, false);
        }
    }

    @Override
    public void focusLost(FocusEvent fe) { // การดัก TextField focus เพื่อกำหนด placeholder
        if (fe.getSource().equals(oldTf) && oldTf.getText().equals("")) {
            this.setPlaceHolder(oldTf, true);
        } else if (fe.getSource().equals(newTf) && newTf.getText().equals("")) {
            this.setPlaceHolder(newTf, true);
        }
    }

}
