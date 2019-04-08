package sg.edu.ntu.scse.cz2002.features;

import org.jetbrains.annotations.NotNull;
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
     * Item ID ({@link MenuItem}/{@link Promotion} Set)
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
    public OrderItem(@NotNull String[] otherData) {
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
        return this.itemId + ":" + this.quantity + ":" + this.itemTotal + ":" + ((this.itemType == OrderItemType.TYPE_MENU) ? "M" : "P");
    }

    /**
     * Internal method for getting the {@link Promotion} object
     * @return {@link Promotion} Object
     */
    private Promotion getPromo() {
        if (this.itemType != OrderItemType.TYPE_PROMO) return null; // Not a promotion
        return PromotionMenuUI.retrievePromotion(this.itemId);
    }

    /**
     * Internal method for getting the {@link MenuItem} object
     * @return {@link MenuItem} Object
     */
    private MenuItem getMenuItem() {
        if (this.itemType != OrderItemType.TYPE_MENU) return null; // Not a menu item
        return FoodMenuUI.retrieveMenuItem(this.itemId);
    }

    /**
     * Gets the object from this entry
     * This object can either be a {@link MenuItem} or a {@link Promotion}
     * @return {@link MenuItem} or {@link Promotion} object
     */
    public Object getItem() {
        if (this.isPromotion()) return getPromo();
        return getMenuItem();
    }

    /**
     * Internally tries and get the item name from the item stored in this entry
     * @return The name of the item if possible
     */
    public String getItemName() {
        Object o = this.getItem();
        if (o instanceof MenuItem) {
            return ((MenuItem) o).getName();
        } else if (o instanceof Promotion) {
            return ((Promotion) o).getPromoName();
        }
        return "Invalid Item";
    }

    /**
     * Gets the quantity of item in this entry
     * @return Quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of this entry
     * @param quantity Quantity of the item in the entry
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Checks if the item in this entry is a {@link Promotion} object
     * @return true if {@link Promotion}, false otherwise
     */
    public boolean isPromotion() {
        return this.itemType == OrderItemType.TYPE_PROMO;
    }

    /**
     * Gets the total price of the entry
     * This is calculated by the item's base price * quantity
     * @return Price of the entry
     */
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
