package sg.edu.ntu.scse.cz2002.ui;

import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Order;
import sg.edu.ntu.scse.cz2002.features.OrderItem;
import sg.edu.ntu.scse.cz2002.objects.menuitem.ItemNotFoundException;
import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;
import sg.edu.ntu.scse.cz2002.objects.menuitem.Promotion;
import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Order Menu UI
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-22
 */
public class OrderMenuUI extends BaseMenu {

    /**
     * Incomplete Orders that has not printed receipt yet
     * These orders will not be saved should the system crash
     */
    public static ArrayList<Order> incompleteOrders = null;

    /**
     * The Order Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
    @Override
    protected int generateMenuScreen() {
        // Init
        if (incompleteOrders == null) incompleteOrders = new ArrayList<>();
        printHeader("Order Management");
        System.out.println("1) Create a new order");
        System.out.println("2) View orders");
        System.out.println("3) Edit order");
        System.out.println("4) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(4, 0);
        switch (choice) {
            case 1:
                createOrder();
                break;
            case 2:
                viewOrder();
                break;
            case 3:
                editOrder();
                break;
            case 4:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Order Menu)");
        }
        return 0;
    }

    /**
     * Edit Order Menu UI
     * @param orderNumber Order Number of the order currently being edited
     */
    private void editOrderMenuScreen(final int orderNumber) {
        // TODO: Code Stub
        Order o = findOrder(orderNumber, false);
        if (o == null) {
            System.out.println("Unable to find order. Exiting...");
            return;
        }
        while (true) {
            printHeader("Order #" + orderNumber);
            System.out.println("1) View items in order");
            System.out.println("2) Add item to order");
            System.out.println("3) Change item quantity in order");
            System.out.println("4) Remove item from order");
            System.out.println("0) Exit Order Editing Screen");
            printBreaks();

            int choice = doMenuChoice(3, 0);
            switch (choice) {
                case 1:
                    System.out.println("List of items in Order #" + orderNumber + ":");
                    if (o.getOrderItems().size() == 0) {
                        System.out.println("No items in order. Add some by selection option 2!");
                        System.out.println();
                        break;
                    }
                    try {
                        printOrderItems(o.getOrderItems());
                    } catch (ItemNotFoundException e) {
                        System.out.println("An error occurred obtaining items in the order. A brief description of the error is listed below\n" + e.getLocalizedMessage());
                    }
                    System.out.println();
                    break;
                case 2:
                    // TODO: To Implement
                    break;
                case 3:
                    // TODO: To Implement
                    break;
                case 4:
                    // TODO: To Implement
                    break;
                case 0:
                    return;
                default:
                    throw new IllegalStateException("Invalid Choice (Order Edit Menu)");
            }
        }
    }

    private void addOrderItem(Order o) {
        System.out.println("Select Item Type:");
        System.out.println("1) Ala-carte Items");
        System.out.println("2) Promotion Set");
        System.out.println("0) Cancel");
        int selection = doMenuChoice(2, 0);

        switch (selection) {
            case 1:
                // TODO: Select Item Type
                break;
            case 2:
                // TODO: Print Promotion Set
                break;
            case 0: return;
            default: throw new IllegalStateException("Invalid Choice (Order Item Add)");
        }
    }

    /**
     * Creates an order and send you to the edit order UI at {@link OrderMenuUI#editOrderMenuScreen(int)}
     */
    private void createOrder() {
        // Create a new order (completed + incompleted check and get ID after)
        int newId = 1;
        if (incompleteOrders.size() > 0) {
            newId = incompleteOrders.get(incompleteOrders.size() - 1).getOrderID() + 1;
        } else if (MainApp.completedOrders.size() > 0) {
            newId = MainApp.completedOrders.get(MainApp.completedOrders.size() - 1).getOrderID() + 1;
        }
        Order o = new Order(newId);
        incompleteOrders.add(o);
        System.out.println("New Order #" + o.getOrderID() + " created!");
        editOrderMenuScreen(o.getOrderID()); // Bring user to the order item edit screen
    }

    /**
     * View options for Orders
     * You are able to view paid orders, unpaid orders, all orders and specific orders
     */
    private void viewOrder() {
        // Check view completed or view others
        System.out.println("Choose any of the following options");
        System.out.println("1) View Paid Orders");
        System.out.println("2) View Unpaid Orders");
        System.out.println("3) View All");
        System.out.println("4) View Specific Order Details");
        System.out.println("0) Cancel");

        int choice = doMenuChoice(4, 0);
        switch (choice) {
            case 1:
                printOrderList(MainApp.completedOrders, "Paid");
                break;
            case 2:
                printOrderList(incompleteOrders, "Unpaid");
                break;
            case 3:
                ArrayList<Order> consolidate = new ArrayList<>();
                consolidate.addAll(MainApp.completedOrders);
                consolidate.addAll(incompleteOrders);
                printOrderList(consolidate, "All");
                break;
            case 4:
                int orderid = ScannerHelper.getIntegerInput("Enter Order ID: ");
                Order o = findOrder(orderid, true); // Attempt to find order
                if (o == null)
                    System.out.println("No Orders found");
                else
                    printOrderDetails(o);
                break;
            case 0:
                return;
            default: throw new IllegalStateException("Invalid Choice (View Order Sub Menu)");
        }
    }

    /**
     * Print specific order details
     * @param o Order object to print details of
     */
    private void printOrderDetails(Order o) {
        printHeader("Order #" + o.getOrderID() + " Details", 100);
        System.out.println("Order ID: " + o.getOrderID());
        System.out.println("Order State: " + ((o.getOrderState() == Order.OrderState.ORDER_PAID) ? "Paid" : "Unpaid"));
        System.out.println("Order Started On: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCreatedAt()));
        if (o.getOrderState() == Order.OrderState.ORDER_PAID) System.out.println("Order Completed On: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCompletedAt()));
        System.out.println("List of Order Items:");
        printBreaks(100);
        if (o.getOrderItems().size() == 0) System.out.println("No Items in Order");
        else {
            try {
                printOrderItems(o.getOrderItems());
            } catch (ItemNotFoundException e) {
                System.out.println("An error occurred retrieving Order Items. A brief description of the error is listed below\n" + e.getLocalizedMessage());
            }
        }
        printBreaks(100);
        System.out.println("Order Subtotal: " + o.getSubtotal());
        printBreaks(100);
        System.out.println("\n");
    }

    private void printOrderItems(ArrayList<OrderItem> items) throws ItemNotFoundException {
        for (OrderItem i : items) {
            Object item = i.getItem();
            if (item == null) {
                System.out.println("Item Not Found in Database");
                continue; // Cannot find item, print Unknown Item
            }
            String itemName;
            double price = 0;
            if (i.isPromotion() && item instanceof Promotion) {
                Promotion promo = (Promotion) item;
                itemName = "[PROMO] " + promo.getPromoName();
                price = promo.getPromoPrice();
            } else if (item instanceof MenuItem) {
                MenuItem mi = (MenuItem) item;
                itemName = mi.getName();
                price = mi.getPrice();
            } else throw new ItemNotFoundException("Item is not Promotion or MenuItem"); // Exception for invalid item in database. Exception as we need to handle and fix
            System.out.printf("%88s%-6dx%-6.2f\n", itemName, i.getQuantity(), price);
        }
    }

    /**
     * Finds an order from its Order ID
     * @param id Order ID to find
     * @param allowFromPaid Whether to check from the Paid Orders. Note that those orders cannot be edited
     * @return An order object if found, null otherwise
     */
    @Nullable
    private Order findOrder(int id, boolean allowFromPaid) {
        // Find from incomplete orders first
        for (Order o : incompleteOrders) {
            if (o.getOrderID() == id) return o;
        }
        if (allowFromPaid) {
            // Check completed orders
            for (Order o : MainApp.completedOrders) {
                if (o.getOrderID() == id) return o;
            }
        }
        return null; // Cannot find
    }

    /**
     * Prints the list of Orders
     * @param orders A list of order
     * @param tag A tag to append to the header
     */
    private void printOrderList(ArrayList<Order> orders, String tag) {
        printHeader("Order List (" + tag + ")", 100);
        if (orders.size() == 0) System.out.println("No orders found");
        else orders.forEach(o -> {
            String date = DateTimeFormatHelper.formatMillisToDateTime(o.getCreatedAt());
            String state = (o.getOrderState() == Order.OrderState.ORDER_PAID) ? "Paid" : "Unpaid";
            System.out.print("Order #" + o.getOrderID() + ", No of Unique items: " + o.getOrderItems().size() + ", Created On: " + date + ", Status: " + state);
            if (o.getOrderState() == Order.OrderState.ORDER_PAID)
                System.out.print(", Paid On: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCompletedAt()));
            System.out.printf(", Subtotal: %.2f\n", o.getSubtotal());
        });
        printBreaks(100);
    }

    /**
     * Edit Order option
     * This method will request an order number and provide you the relevant edit order page if available
     * If the order is already paid or does not exist, an error message will appear and you will exit back to the Order Menu
     */
    private void editOrder() {
        Scanner in = new Scanner(System.in);
        int orderNo = ScannerHelper.getIntegerInput("Enter Order Number: ");
        Order o = findOrder(orderNo, false);
        if (o == null) System.out.println("Unable to find order. Note that orders that are paid for cannot be edited");
        else editOrderMenuScreen(orderNo);
    }
}
