
public class TableInformation {

    private int tableNumber;
    private boolean busy;
    private double total;
    private Object[][] menuInOrders;
    private Object[][] menuList;

    public TableInformation(int tableNumber) {
        this.tableNumber = tableNumber;
        menuList = MenuAPI.getMenuList();
        this.infoInit();
    }

    public void infoInit() {
        menuInOrders = TableInformationAPI.getMenuInOrders(tableNumber);
        busy = false;
        total = 0;
        for (Object data[] : menuInOrders) {
            if ((int) data[1] != 0) {
                busy = true;
                total += (double) data[2];
            }
        }
    }

    public void addMenu(int menuIndex) {
        int count = (int) menuInOrders[menuIndex][1];
        double price = (double) menuList[menuIndex][1];
        menuInOrders[menuIndex][1] = ++count;
        menuInOrders[menuIndex][2] = price * count;
        total += price;
        busy = true;
    }

    public void removeMenu(int menuIndex) {
        int count = (int) menuInOrders[menuIndex][1];
        double price = (double) menuList[menuIndex][1];
        menuInOrders[menuIndex][1] = --count;
        menuInOrders[menuIndex][2] = price * count;
        total -= price;
        checkBusy();
    }

    public void resetMenu() {
        busy = false;
        total = 0;
        for (int i = 0; i < menuInOrders.length; i++) {
            menuInOrders[i][1] = 0;
            menuInOrders[i][2] = 0.0;
        }
    }

    public void checkBusy() {
        busy = false;
        for (Object data[] : menuInOrders) {
            if ((int) data[1] != 0) {
                busy = true;
                break;
            }
        }
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public boolean isBusy() {
        return busy;
    }

    public double getTotal() {
        return total;
    }

    public Object[][] getMenuInOrders() {
        return menuInOrders;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
