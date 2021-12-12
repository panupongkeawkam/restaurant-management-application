
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try { // เรียกใช้ look and feel UI
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // เรียกใช้งาน class ตัว UI และหลังบ้านทุก class
        new MenuAPI();
        new LogAPI();
        new TableInformationAPI();
        new Theme();
        new TableFrame();
        new TableManagerFrame();
        new ChangePINFrame();
        new LoginFrame();
        new AdminFrame();
        new ManageMenuFrame();
        new MainFrame();
    }
}
