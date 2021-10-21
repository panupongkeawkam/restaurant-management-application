
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;

public class MainFrame {

    private final JFrame frame;
    private final Table table = new Table(this);
    private final Admin admin = new Admin(this);
    private final DatabaseAPI API = new DatabaseAPI(this);
    private final TableBacken tableBacken = new TableBacken(this);

    public MainFrame() {
        frame = new JFrame();
        frame.setBounds(480, 180, 960, 720);
        frame.setLayout(null);
        frame.add(table.getPanel());
        frame.add(admin.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Table getTable() {
        return table;
    }

    public Admin getAdmin() {
        return admin;
    }

    public DatabaseAPI getAPI() {
        return API;
    }

    public TableBacken getTableBacken() {
        return tableBacken;
    }

}
