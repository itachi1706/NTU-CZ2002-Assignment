package sg.edu.ntu.scse.cz2002.ui;

import org.jetbrains.annotations.NotNull;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Invoice;
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
                if (checkout()) {
                    System.out.println();
                    return -1; // If completed, go back to main menu
                }
                System.out.println();
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
            return false;
        }
        Order o = tOrders.get(tableNo);
        double total = printOrderDetails(o);
        System.out.println();
        System.out.println("Payment Mode");
        System.out.println("1) Cash");
        System.out.println("2) NETS");
        System.out.println("3) Debit/Credit Card");
        System.out.println("4) EZ-Link");
        System.out.println("0) Cancel");
        int choice = ScannerHelper.getIntegerInput("Select Payment Mode (0 to cancel): ", -1, 5);
        double paid;
        Invoice.PaymentType paymentType;
        switch (choice) {
            case 1:
                paid = requestCashPayment(total);
                paymentType = Invoice.PaymentType.PAYMENT_CASH;
                break;
            case 2:
                paymentType = Invoice.PaymentType.PAYMENT_NETS;
                paid = total; // All card payments are presumed paid fully
                break;
            case 3:
                paymentType = Invoice.PaymentType.PAYMENT_CARD;
                paid = total; // All card payments are presumed paid fully
                break;
            case 4:
                paymentType = Invoice.PaymentType.PAYMENT_EZLINK;
                paid = total; // All card payments are presumed paid fully
                break;
            case 0:
                System.out.println("Operation Cancelled");
                return false;
            default: throw new MenuChoiceInvalidException("Checkout Payment");
        }
        System.out.println("Payment Complete! Payment Mode: " + paymentType.toString());
        if (paymentType == Invoice.PaymentType.PAYMENT_CASH) {
            System.out.println("Change: $" + String.format("%.2f", (paid - total)));
        }

        System.out.println("Generating Receipt...");
        // TODO: Set table to VACANT, move order to complete
        // TODO: Print receipt, save receipt in data/receipts/ordernumber.txt
        // TODO: Create invoice object that basically extends the order object with receipt path
        // TODO: Remove order from incomplete orders
        o.markPaid();
        Invoice i = new Invoice(o, "path", paymentType, total, paid);
        MainApp.invoices.add(i);
        MainApp.saveAll();
        System.out.println("Returning to Main Menu...");
        return true;
    }

    private void reprint() {
        // TODO: Print list of invoices in a table
        // TODO: Select invoice to reprint (by id)
        // TODO: Get receipt from data/receipts/ordernumber.txt
        // TODO: If that fails, regenerate and save
        // TODO: Show receipt :D
    }

    private double requestCashPayment(double total) {
        double paid = 0;
        while (paid < total) {
            double entered = ScannerHelper.getDoubleInput("Enter amount paid: ", 0);
            paid += entered;
            System.out.printf("Accepted $%.2f in cash, Remaining amount to pay: $%.2f\n", entered, (total - paid >= 0) ? total - paid : 0);
        }
        return paid;
    }

    /**
     * Print specific order details for invoice
     * @param o Order object to print details of
     */
    @SuppressWarnings("Duplicates")
    private double printOrderDetails(@NotNull Order o) {
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
        return total;
    }
}
