package sg.edu.ntu.scse.cz2002.ui;

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

    private boolean editOrderMenuScreen(int orderNumber) {
        // TODO: Code Stub
        // TODO: Update this. This will run after progress is done
        while (true) {
            printHeader("Order #" + orderNumber);
            System.out.println("1) View items in order");
            System.out.println("2) Add item to order");
            System.out.println("3) Remove item from order");
            System.out.println("4) Save order");
            System.out.println("0) Exit without saving");
            printBreaks();

            int choice = doMenuChoice(4, 0);
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
                case 4:
                    // TODO: To Implement
                    return true;
                case 0:
                    return false;
                default:
                    throw new IllegalStateException("Invalid Choice (Order Edit Menu)");
            }
        }
    }

    private void createOrder() {
        // TODO: Code Stub
    }

    private void viewOrder() {
        // TODO: Code Stub

    }

    private void editOrder() {
        // TODO: Code Stub
        Scanner in = new Scanner(System.in);
        int orderNo = ScannerHelper.getIntegerInput(in, "Enter Order Number: ");
         // TODO: Check if order exists
    }
}
