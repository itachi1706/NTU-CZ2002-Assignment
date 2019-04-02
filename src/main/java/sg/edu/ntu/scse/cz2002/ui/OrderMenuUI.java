package sg.edu.ntu.scse.cz2002.ui;

import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Order;
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

    private void editOrderMenuScreen(final int orderNumber) {
        // TODO: Code Stub
        // TODO: Update this. This will run after progress is done
        while (true) {
            printHeader("Order #" + orderNumber);
            System.out.println("1) View items in order");
            System.out.println("2) Add item to order");
            System.out.println("3) Remove item from order");
            System.out.println("0) Exit Order Editing Screen");
            printBreaks();

            int choice = doMenuChoice(3, 0);
            switch (choice) {
                case 1:
                    // TODO: To Implement
                    break;
                case 2:
                    // TODO: To Implement
                    break;
                case 3:
                    // TODO: To Implement
                    break;
                case 0:
                    return;
                default:
                    throw new IllegalStateException("Invalid Choice (Order Edit Menu)");
            }
        }
    }

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

    // View Order List
    private void viewOrder() {
        // TODO: Code Stub
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
                // TODO: View specific order id
                break;
            case 0:
                return;
            default: throw new IllegalStateException("Invalid Choice (View Order Sub Menu)");
        }
    }

    private void printOrderList(ArrayList<Order> orders, String tag) {
        printHeader("Order List (" + tag + ")", 80);
        if (orders.size() == 0) System.out.println("No orders found");
        else orders.forEach(o -> {
            String date = ""; // TODO: Get datetime string of order
            String state = (o.getOrderState() == Order.OrderState.ORDER_PAID) ? "Paid" : "Unpaid";
            System.out.print("Order #" + o.getOrderID() + ", No of Unique items: " + o.getOrderItems().size() + ", Created On: " + date + ", Status: " + state);
            System.out.printf(", Subtotal: %.2f\n", o.getSubtotal());
        });
        printBreaks(80);
    }

    private void editOrder() {
        // TODO: Code Stub
        Scanner in = new Scanner(System.in);
        int orderNo = ScannerHelper.getIntegerInput(in, "Enter Order Number: ");
         // TODO: Check if order exists
    }
}
