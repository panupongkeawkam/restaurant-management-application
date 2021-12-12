
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public class RoundedButton extends JButton implements MouseListener {

    private Color displayColor;
    private Color colorDefault;
    private Color colorHover;
    private Color colorActive;
    private Graphics2D g2;
    private int value;

    public RoundedButton(int value, Color color) {
        this.value = value;
        this.colorDefault = color;
        this.colorInit(); // เรียก method เพื่อกำหนดสสี

        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // set cursor เป็นนิ้วชี้
        addMouseListener(this); // เพิ่ม Mouse event
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    public void colorInit() { // กำหนดสีของ hover และ active ของสีหลักแบบอัตโนมัติ
        int r = colorDefault.getRed(); // รับค่าสีเป็น RGB
        int g = colorDefault.getGreen();
        int b = colorDefault.getBlue();
        colorHover = new Color(Math.abs(r - 10), Math.abs(g - 10), Math.abs(b - 10)); // กำหนดสีตอน hover
        colorActive = new Color(Math.abs(r - 15), Math.abs(g - 15), Math.abs(b - 15)); // กำหนดสีตอน active

        displayColor = colorDefault; // กำหนดสีที่แสดงเป็นสีหลัก
        if (colorDefault.equals(Theme.DISABLE_BG)) { // เงื่อนไขเมื่อสีพื้นหลังเป็น disable 
            colorHover = colorDefault; // ไม่มีการกำหนดสี hover ใหม่
            colorActive = colorDefault; // ไม่มีการกำหนดสี active ใหม่
        }
    }

    public void setColor(Color Color) {
        this.colorDefault = Color; // กำหนดสี parameter
        this.colorInit();
    }

    @Override
    protected void paintComponent(Graphics g) { // วาด graphic
        g2 = (Graphics2D) g.create(); // สร้าง Object
        RenderingHints qualityHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON // กำหนดค่าการ render ของ graphic จาก Object render
        );
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints); // set ลงใน Object graphic
        g2.setPaint(
                new GradientPaint(
                        new Point(0, 0),
                        displayColor,
                        new Point(0, getHeight()),
                        displayColor
                )
        ); // กำหนดสีของปุ่มและตำแหน่งการไล่สี
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), value, value); // กำหนดขนาดและความมนของ graphic
        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) { // เพื่อกดปุ่มค้าง
        displayColor = colorActive;
    }

    @Override
    public void mouseReleased(MouseEvent me) { // เพื่อกดปุ่มเสร็จ
        displayColor = colorDefault;
    }

    @Override
    public void mouseEntered(MouseEvent me) { // เพื่อเอาเมาส์เข้า
        displayColor = colorHover;
    }

    @Override
    public void mouseExited(MouseEvent me) { // เพื่อเอาเมาส์ออก
        displayColor = colorDefault;
    }
}
