package sg.edu.ntu.scse.cz2002.features;

public class OrderItem {

    public enum OrderMenuItemType { TYPE_MENU, TYPE_PROMO }

    private int itemId;
    private int quantity;
    private double itemTotal;
    private OrderMenuItemType itemType;

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param otherData the split CSV string object for the order item, be it menuitems or promotions
     */
    public OrderItem(String[] otherData) {
        this.itemId = Integer.parseInt(otherData[0]);
        this.quantity = Integer.parseInt(otherData[1]);
        this.itemTotal = Double.parseDouble(otherData[2]);
        this.itemType = (otherData[3].equals("M")) ? OrderMenuItemType.TYPE_MENU : OrderMenuItemType.TYPE_PROMO;
    }

    /**
     * A method to convert to string
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    public String toCompiledString() {
        return this.itemId + ":" + this.quantity + ":" + this.itemTotal + ((this.itemType == OrderMenuItemType.TYPE_MENU) ? "M" : "P");
    }
}
