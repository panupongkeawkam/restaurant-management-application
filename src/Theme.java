
import java.awt.*;
import java.io.*;

public class Theme {

    /* กำหนดสีต่างๆ หรือฟอนต์ลงใน public static final attribute */
    public static final Color WHITE = new Color(245, 245, 250);
    public static final Color GREY = new Color(225, 225, 225);
    public static final Color BLACK = new Color(60, 60, 52);
    public static final Color DISABLE_FG = new Color(115, 115, 115);
    public static final Color DISABLE_BG = new Color(200, 200, 200);
    public static final Color RED = new Color(252, 87, 87);
    public static final Color GREEN = new Color(4, 220, 124);
    public static final Color YELLOW = new Color(251, 148, 77);
    public static final Color BLUE = new Color(20, 172, 212);
    public static final Color PURPLE = new Color(218, 145, 255);
    public static Font sarabun12, sarabun14, sarabunItalic14;

    public Theme() {
        try { // import ฟอนต์
            String regular = "font/sarabun/Sarabun-Regular.ttf"; // ไฟล์ path
            String italic = "font/sarabun/Sarabun-Italic.ttf"; // ไฟล์ path
            sarabun14 = Font.createFont(Font.TRUETYPE_FONT, new File(regular)).deriveFont(14f); // สร้างและกำหนดขนาดฟอนต์
            sarabun12 = Font.createFont(Font.TRUETYPE_FONT, new File(regular)).deriveFont(12f); // สร้างและกำหนดขนาดฟอนต์
            sarabunItalic14 = Font.createFont(Font.TRUETYPE_FONT, new File(italic)).deriveFont(14f); // สร้างและกำหนดขนาดฟอนต์
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); // สร้าง object ฟอนต์
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(regular))); // render ฟอนต์
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(italic))); // render ฟอนต์
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
