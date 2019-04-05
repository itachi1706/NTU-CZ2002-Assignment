package sg.edu.ntu.scse.cz2002.features;

import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;
import sg.edu.ntu.scse.cz2002.objects.menuitem.Promotion;
import sg.edu.ntu.scse.cz2002.ui.FoodMenuUI;
import sg.edu.ntu.scse.cz2002.ui.PromotionMenuUI;

/**
 * Order Item Object
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-03
 */
public class OrderItem {

    /**
     * Enums for type of order item
     */
    public enum OrderItemType { TYPE_MENU, TYPE_PROMO }

    /**
     * Item ID (Menu/Promotion Set)
     */
    private int itemId;
    /**
     * Quantity of items in the order
     */
    private int quantity;
    /**
     * Total sum of the items in the order
     */
    private double itemTotal;
    /**
     * Item type
     */
    private OrderItemType itemType;

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param otherData the split CSV string object for the order item, be it menuitems or promotions
     */
    public OrderItem(String[] otherData) {
        this.itemId = Integer.parseInt(otherData[0]);
        this.quantity = Integer.parseInt(otherData[1]);
        this.itemTotal = Double.parseDouble(otherData[2]);
        this.itemType = (otherData[3].equals("M")) ? OrderItemType.TYPE_MENU : OrderItemType.TYPE_PROMO;
    }

    /**
     * Constructor
     * @param itemId Item ID
     * @param quantity Quantity of the item
     * @param itemType Type of Item
     */
    public OrderItem(int itemId, int quantity, OrderItemType itemType) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.itemType = itemType;
        this.calculateTotal();
    }

    /**
     * A method to convert to string
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    public String toCompiledString() {
        return this.itemId + ":" + this.quantity + ":" + this.itemTotal + ((this.itemType == OrderItemType.TYPE_MENU) ? "M" : "P");
    }

    public Promotion getPromo() {
        if (this.itemType != OrderItemType.TYPE_PROMO) return null; // Not a promotion
        return PromotionMenuUI.retrievePromotion(this.itemId);
    }

    public MenuItem getMenuItem() {
        if (this.itemType != OrderItemType.TYPE_MENU) return null; // Not a menu item
        return FoodMenuUI.retrieveMenuItem(this.itemId);
    }

    public Object getItem() {
        if (this.isPromotion()) return getPromo();
        return getMenuItem();
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPromotion() {
        return this.itemType == OrderItemType.TYPE_PROMO;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    /**
     * Calculates the total and stores it into itemTotal
     * Uses the price from finding the item and the quantity
     */
    public void calculateTotal() {
        Object o = this.getItem();
        if (o instanceof MenuItem) {
            MenuItem mi = (MenuItem) o;
            this.itemTotal = mi.getPrice() * this.quantity;
        } else if (o instanceof Promotion) {
            Promotion p = (Promotion) o;
            this.itemTotal = p.getPromoPrice() * this.quantity;
        }
    }
}
