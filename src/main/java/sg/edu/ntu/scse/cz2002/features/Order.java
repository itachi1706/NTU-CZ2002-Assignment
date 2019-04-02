package sg.edu.ntu.scse.cz2002.features;

import java.util.ArrayList;

public class Order {

    public enum OrderState {ORDER_PAID, ORDER_UNPAID }

    private int orderID;
    private ArrayList<OrderItem> orderItems;
    private double subtotal;
    private OrderState orderState;

    public Order(int orderID) {
        this.subtotal = 0;
        this.orderItems = new ArrayList<>();
        this.orderState = OrderState.ORDER_UNPAID;
        this.orderID = orderID;
    }

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public Order(String[] csv) {
        this.orderID = Integer.parseInt(csv[0]);
        String[] menuItemIDs = csv[1].split(",");
        this.orderItems = new ArrayList<>();
        for (String s : menuItemIDs) {
            String[] mItem = s.split(":");
            this.orderItems.add(new OrderItem(mItem));
        }
        this.subtotal = Double.parseDouble(csv[2]);
        this.orderState = (csv[3].equals("1")) ? OrderState.ORDER_PAID : OrderState.ORDER_UNPAID;
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    public String[] toCsv() {
        String[] s = new String[4];
        s[0] = this.orderID + "";
        StringBuilder b = new StringBuilder();
        for (OrderItem i : this.orderItems) {
            b.append(i.toCompiledString()).append(",");
        }
        s[1] = b.toString().replaceAll(", $", ""); // Remove the comma
        s[2] = this.subtotal + "";
        s[3] = (this.orderState == OrderState.ORDER_PAID) ? "1" : "0";
        return s;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void calculateSubtotal() {
        // TODO: Calculate subtotal
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void markPaid() {
        this.orderState = OrderState.ORDER_PAID;
    }
}
