package sg.edu.ntu.scse.cz2002.ui;

import org.jetbrains.annotations.NotNull;
import sg.edu.ntu.scse.cz2002.features.Order;
import sg.edu.ntu.scse.cz2002.features.Table;
import sg.edu.ntu.scse.cz2002.objects.menuitem.ItemNotFoundException;
import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Menu UI Flow for Checkout and Invoice Printing
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-08
 */
public class CheckoutMenuUI extends BaseMenu {
    @Override
    protected int generateMenuScreen() {
        printHeader("Checkout");
        System.out.println("1) Checkout");
        System.out.println("2) Reprint Invoice");
        System.out.println("3) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(34, 0);
        switch (choice) {
            case 1:
                // TODO: Checkout
                if (checkout()) return -1; // If completed, go back to main menu
                break;
            case 2:
                // TODO: Reprint Invoice for completed orders
                reprint();
                break;
            case 3:
                return -1;
            case 0:
                return 1;

            default:
                throw new MenuChoiceInvalidException("Checkout");
                //throw new IllegalStateException("Invalid Choice (Checkout)");
        }
        return 0;
    }

    private boolean checkout() {
        if (OrderMenuUI.incompleteOrders == null || OrderMenuUI.incompleteOrders.size() == 0) {
            System.out.println("No orders ready for checkout. Cancelling Operation");
            return false;
        }
        HashMap<Integer, Order> tOrders = new HashMap<>();
        OrderMenuUI.incompleteOrders.forEach((s) -> {
            Table c = s.getTable();
            if (c == null) return;
            tOrders.put(c.getTableNum(), s);
        });
        tOrders.put(-1, null);
        OrderMenuUI.printOrderList(OrderMenuUI.incompleteOrders, "Ready for Checkout", true);
        int tableNo = ScannerHelper.getIntegerInput("Select Table to checkout (-1 to cancel): ", new ArrayList<>(tOrders.keySet()), "Invalid Table Number. Please select a valid table number or enter -1 to cancel");
        if (tableNo == -1) {
            System.out.println("Checkout Operation Cancelled");
            System.out.println();
            return false;
        }
        Order o = tOrders.get(tableNo);
        printOrderDetails(o);
        // TODO: Ask what payment mode
        // TODO: If card, process immediately and skip next step
        // TODO: If cash, get cash and find change (if cash < total, ask for more cash)
        // TODO: Set table to VACANT, move order to complete
        // TODO: Print receipt, save receipt in data/receipts/ordernumber.txt
        // TODO: Create invoice object that basically extends the order object with receipt path

        return true;
    }

    private void reprint() {
        // TODO: Print list of invoices in a table
        // TODO: Select invoice to reprint (by id)
        // TODO: Get receipt from data/receipts/ordernumber.txt
        // TODO: If that fails, regenerate and save
        // TODO: Show receipt :D
    }

    /**
     * Print specific order details for invoice
     * @param o Order object to print details of
     */
    @SuppressWarnings("Duplicates")
    private void printOrderDetails(@NotNull Order o) {
        printHeader("Order #" + o.getOrderID() + "", 60);
        System.out.println("Order ID: " + o.getOrderID());
        System.out.println("Order State: " + ((o.getOrderState() == Order.OrderState.ORDER_PAID) ? "Paid" : "Unpaid"));
        System.out.println("Order Started On: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCreatedAt()));
        System.out.println("Staff Handling Order: " + ((o.getStaff() == null) ? "Unknown Staff" : "[" + o.getStaff().getStaffId() + "] " + o.getStaff().getStaffName()));
        System.out.println("Table Number: " + ((o.getTable() == null) ? "Unknown Table" : o.getTable().getTableNum()));
        System.out.println("List of Order Items:");
        printBreaks(60);
        if (o.getOrderItems().size() == 0) System.out.println("No Items in Order");
        else {
            o.calculateSubtotal(); // Calculate Subtotal
            try {
                OrderMenuUI.printOrderItems(o.getOrderItems(), true);
            } catch (ItemNotFoundException e) {
                System.out.println("An error occurred retrieving Order Items. A brief description of the error is listed below\n" + e.getLocalizedMessage());
            }
        }
        printBreaks(60);
        // Do final caclulation
        o.calculateSubtotal();
        double tax = 0.07 * o.getSubtotal();
        double total = tax + o.getSubtotal();
        System.out.printf("%50s $%-6.2f\n", "Order Subtotal: ", o.getSubtotal());
        System.out.printf("%50s $%-6.2f\n", "GST (7%): ", tax);
        printBreaks(60);
        System.out.printf("%50s $%-6.2f\n", "Total: ", total);
        printBreaks(60);
        System.out.println("\n");
    }
}
