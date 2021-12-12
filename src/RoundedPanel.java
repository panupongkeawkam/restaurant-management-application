
import java.awt.*;
import javax.swing.*;

public class RoundedPanel extends JPanel {

    private int value;
    private String direction;
    private Color color;
    private Graphics2D g2;

    public RoundedPanel(int value, Color color) {
        this.value = value;
        this.color = color;
        this.direction = "none";

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    public RoundedPanel(int value, Color color, String direction) {
        this.value = value;
        this.color = color;
        this.direction = direction;

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g2 = (Graphics2D) g.create();
        RenderingHints qualityHints
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        g2.setPaint(new GradientPaint(new Point(0, 0), color, new Point(0, getHeight()),
                color));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), value, value);

        if (direction.equals("start")) {
            g2.fillRoundRect(0, 0, getWidth() + value, getHeight(), value, value);
        } else if (direction.equals("top")) {
            g2.fillRoundRect(0, 0, getWidth(), getHeight() + value, value, value);
        }

        g2.dispose();
    }
}
