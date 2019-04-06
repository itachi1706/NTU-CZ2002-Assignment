package sg.edu.ntu.scse.cz2002.features;

import org.jetbrains.annotations.NotNull;
import sg.edu.ntu.scse.cz2002.objects.person.Staff;
import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

import java.util.ArrayList;

/**
 * Order Object
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-03
 */
public class Order implements ICsvSerializable {

    /**
     * Enum of the order states
     */
    public enum OrderState {ORDER_PAID, ORDER_UNPAID }

    /**
     * Order ID
     */
    private int orderID;
    /**
     * List of items in the order
     */
    private ArrayList<OrderItem> orderItems;
    /**
     * Order subtotal (before taxes etc)
     */
    private double subtotal;
    /**
     * State of the order
     */
    private OrderState orderState;
    /**
     * Time of order creation
     */
    private long createdAt;
    /**
     * Time when the order is paid
     */
    private long completedAt;

    private Staff staff;

    private Table table;

    /**
     * Create a new order object
     * @param orderID Order ID to set this new order as
     */
    public Order(int orderID) {
        this.subtotal = 0;
        this.orderItems = new ArrayList<>();
        this.orderState = OrderState.ORDER_UNPAID;
        this.orderID = orderID;
        this.completedAt = -1;
        this.createdAt = System.currentTimeMillis(); // Current Time
    }

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public Order(@NotNull String[] csv) {
        this.orderID = Integer.parseInt(csv[0]);
        String[] menuItemIDs = csv[1].split(",");
        this.orderItems = new ArrayList<>();
        for (String s : menuItemIDs) {
            String[] mItem = s.split(":");
            this.orderItems.add(new OrderItem(mItem));
        }
        this.subtotal = Double.parseDouble(csv[2]);
        this.orderState = (csv[3].equals("1")) ? OrderState.ORDER_PAID : OrderState.ORDER_UNPAID;
        this.createdAt = Long.parseLong(csv[4]);
        this.completedAt = Long.parseLong(csv[5]);
        // TODO: Read staff ID and Table ID
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        String[] s = new String[6];
        s[0] = this.orderID + "";
        StringBuilder b = new StringBuilder();
        for (OrderItem i : this.orderItems) {
            b.append(i.toCompiledString()).append(",");
        }
        s[1] = b.toString().replaceAll(", $", ""); // Remove the comma
        s[2] = this.subtotal + "";
        s[3] = (this.orderState == OrderState.ORDER_PAID) ? "1" : "0";
        s[4] = this.createdAt + "";
        s[5] = this.completedAt + "";
        // TODO: Write Staff ID and Table ID
        return s;
    }

    /**
     * Gets the order ID
     * @return Order ID
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * Gets the list of order items in the order
     * @return ArrayList of Order Items
     */
    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Updates the list of order items in the order
     * @param orderItems New ArrayList of Order Items
     */
    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Gets the subtotal of the order (before taxes)
     * @return Order Subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Calculates the subtotal of the order based on the order items
     * This subtotal does not include taxes
     */
    public void calculateSubtotal() {
        this.subtotal = 0;
        this.orderItems.forEach((o) -> {
            o.calculateTotal(); // Make sure item is calculated
            this.subtotal += o.getItemTotal();
        });
    }

    /**
     * Gets the state of the order
     * @return Order State
     */
    public OrderState getOrderState() {
        return orderState;
    }

    /**
     * Sets the order as paid. THIS IS NOT REVERSIBLE
     * What this will do is to mark the order as paid and update the time of payment to the current datetime
     * This will not move the item from any object, it is the role of the calling method to move the order from the incomplete
     * orders to complete orders arraylist and persist the data into storage
     */
    public void markPaid() {
        this.orderState = OrderState.ORDER_PAID;
        this.completedAt = System.currentTimeMillis();
    }

    /**
     * Gets the time where this order is created
     * @return Time in milliseconds since Epoch when this order is created
     */
    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the time where this order has been paid and completed
     * Note that if the order is incomplete, this will return -1 instead
     * @return Time in milliseconds since Epoch when this order is completed, or -1 of the order has not been completed (paid)
     */
    public long getCompletedAt() {
        return completedAt;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
