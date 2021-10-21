
import java.awt.Color;
import javax.swing.JOptionPane;

public class TableBacken {
    private MainFrame mf;
    private String tableName;
    private String menuList[];
    private Object orderList[][] = {{"KAI", 0}, {"MAMA", 1}, {"WAIWAI", 0}, {"ROTI", 0}, {"SOMTOM", 0}};

    public TableBacken(String tableName) {
        this.tableName = tableName;
    }
    
    public TableBacken(MainFrame mf) {
        this.mf = mf;
    }
    
    public void showInfo() {
        String str = "";
        this.setMenuList(mf.getAPI().getMemuListFromExcel());
        for (int i = 0; i < menuList.length; i++) {
            str += menuList[i] + "\n";
            str += (Integer) orderList[i][1] > 0 ? " - " + (String) orderList[i][0] + " x" + (Integer) orderList[i][1] + "\n" : "\n";
                    
        }
        JOptionPane.showMessageDialog(null,
                                      str,
                                      this.tableName,
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void setMenuList(String[] menuList) {
        this.menuList = menuList;
    }
}
