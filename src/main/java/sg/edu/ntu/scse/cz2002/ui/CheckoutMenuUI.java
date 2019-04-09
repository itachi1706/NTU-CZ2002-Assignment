package sg.edu.ntu.scse.cz2002.ui;

import org.jetbrains.annotations.NotNull;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Invoice;
import sg.edu.ntu.scse.cz2002.features.Order;
import sg.edu.ntu.scse.cz2002.features.Table;
import sg.edu.ntu.scse.cz2002.objects.menuitem.ItemNotFoundException;
import sg.edu.ntu.scse.cz2002.objects.person.Staff;
import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;
import sg.edu.ntu.scse.cz2002.util.FileIOHelper;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Menu UI Flow for Checkout and Invoice Printing
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-08
 */
public class CheckoutMenuUI extends BaseMenu {

    /**
     * Receipt Subfolder path
     */
    private static final String RECEIPT_SUBFOLDER = "receipts" + File.separator;

    /**
     * Generate the menu for checking out orders
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
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
                if (checkout()) {
                    System.out.println();
                    return -1; // If completed, go back to main menu
                }
                System.out.println();
                break;
            case 2:
                reprint();
                break;
            case 3:
                return -1;
            case 0:
                return 1;

            default:
                throw new MenuChoiceInvalidException("Checkout");
        }
        return 0;
    }

    /**
     * Code for checking out created orders that have not been paid
     * @return true if completed, false otherwise
     */
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
        o.markPaid();
        OrderMenuUI.incompleteOrders.remove(o);
        ArrayList<String> receipt = generateReceipt(o, total, paid, paymentType);
        System.out.println();
        receipt.forEach(System.out::println);
        String receiptName = "-";
        if (writeReceipt(receipt, o.getOrderID())) {
            receiptName = o.getOrderID() + ".txt";
        }
        Invoice i = new Invoice(o, receiptName, paymentType, total, paid);
        MainApp.invoices.add(i);
        System.out.println("\n");
        System.out.println("Returning to Main Menu...");
        return true;
    }

    /**
     * Method used to reprint any receipts of paid and completed orders
     */
    private void reprint() {
        HashMap<Integer, Invoice> invoiceHashMap = new HashMap<>();
        printHeader("List of Invoices");
        MainApp.invoices.forEach((invoice -> {
            System.out.println("Receipt #" + invoice.getOrderID() + ", Paid On: " + DateTimeFormatHelper.formatMillisToDateTime(invoice.getCompletedAt()));
            invoiceHashMap.put(invoice.getOrderID(), invoice);
        }));
        printBreaks();
        invoiceHashMap.put(-1, null);
        int selection = ScannerHelper.getIntegerInput("Select invoice to reprint receipt: ", new ArrayList<>(invoiceHashMap.keySet()), "Invalid Receipt Number. Please select a valid receipt number or enter -1 to cancel");
        if (selection == -1) {
            System.out.println("Receipt Cancelled");
            return;
        }
        Invoice selected = invoiceHashMap.get(selection);
        ArrayList<String> receipts;
        if (selected.getReceipt().equals("-")) {
            // No receipt, generate and save
            receipts = generateReceipt(selected, selected.getTotal(), selected.getAmountPaid(), selected.getPaymentType());
        } else {
            String filePath = RECEIPT_SUBFOLDER + selected.getReceipt();
            try {
                BufferedReader reader = FileIOHelper.getFileBufferedReader(filePath);
                receipts = reader.lines().collect(Collectors.toCollection(ArrayList::new));
            } catch (IOException e) {
                System.out.println("Failed to load receipt. Regenerating");
                receipts = generateReceipt(selected, selected.getTotal(), selected.getAmountPaid(), selected.getPaymentType());
            }
        }

        System.out.println("Reprinting Receipt...");
        System.out.println();
        System.out.println();
        receipts.forEach(System.out::println);
        System.out.println("\n");
    }

    /**
     * Generates receipt from data given
     * @param o Order object
     * @param total Total amount of the order
     * @param paid Total amount of money paid
     * @param type Payment Type
     * @return An arraylist containing the strings required to generate the receipt
     */
    private ArrayList<String> generateReceipt(@NotNull Order o, double total, double paid, Invoice.PaymentType type) {
        // Max Length of receipt is 50
        Table t = o.getTable();
        Staff s = o.getStaff();
        int tableNum = (t == null) ? -1 : t.getTableNum();
        String staffName = (s == null) ? "Unknown" : s.getStaffName() + " (" + s.getStaffId() + ")";
        ArrayList<String> receiptStrings = new ArrayList<>();
        receiptStrings.add(centerText(MainApp.APP_NAME, 60, ' '));
        receiptStrings.add(spacer(60, '_'));
        receiptStrings.add(centerText("Block N4, SCSE, NTU Singapore", 60, ' '));
        receiptStrings.add(centerText("50 Nanyang Ave, SG 639798", 60, ' '));
        receiptStrings.add("");
        receiptStrings.add(String.format("%-35s%-20s", "Check #: " + o.getOrderID(), "Server: " + staffName));
        receiptStrings.add(String.format("%-35s%-20s", "Date: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCompletedAt()),  "Table: " + tableNum));
        receiptStrings.add(spacer(60, '_'));
        // Print order items
        o.getOrderItems().forEach((item) -> receiptStrings.add(String.format(" %-3sx %-45s $%6.2f", item.getQuantity(), item.getItemName(), item.getItemTotal())));
        receiptStrings.add(spacer(60, '_'));
        receiptStrings.add(String.format("%51s $%6.2f", "Order Subtotal: ", o.getSubtotal()));
        receiptStrings.add(String.format("%51s $%6.2f", "GST (7%): ", total - o.getSubtotal()));
        receiptStrings.add(spacer(60, '_'));
        receiptStrings.add(String.format("%51s $%6.2f", "Total: ", total));
        receiptStrings.add(spacer(60, '_'));
        receiptStrings.add(String.format("%51s $%6.2f", "Paid (" + type.toString() + "): ", paid));
        if (type == Invoice.PaymentType.PAYMENT_CASH) receiptStrings.add(String.format("%51s $%6.2f\n", "Change: ", (paid > total) ? Math.abs(total - paid) : 0));
        receiptStrings.add(spacer(60, '_'));
        receiptStrings.add("");
        receiptStrings.add("");
        receiptStrings.add(centerText("Thank you and have a nice day!", 60, ' '));
        receiptStrings.add(centerText("Transaction Completed: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCompletedAt()), 60, ' '));
        return receiptStrings;
    }

    /**
     * Centers the text and gives it to you
     * The text will be centered based on the length passed in and will be centered by appending the spacers before it
     *
     * Note: This will only append BEFORE the text inserted. It will not do so AFTER
     * @param toCenter Text to center
     * @param length Width of your "screen"
     * @param spacer Spacing used for the "centering"
     * @return Centered String
     */
    private String centerText(String toCenter, int length, char spacer) {
        // Do fancy centering
        StringBuilder sb = new StringBuilder();
        int noOfSpaces = (toCenter.length() > length) ? 0 : (length - toCenter.length()) / 2;
        Stream.generate(() -> spacer).limit(noOfSpaces).forEach(sb::append);
        sb.append(toCenter);
        return sb.toString();
    }

    /**
     * Appends a spacer character to the entire line
     * @param length Length of the line
     * @param spacer Character to append
     * @return Spacer Appended String
     */
    private String spacer(int length, char spacer) {
        // Do fancy centering
        StringBuilder sb = new StringBuilder();
        Stream.generate(() -> spacer).limit(length).forEach(sb::append);
        return sb.toString();
    }

    /**
     * Requests cash payment from the user
     * This will keep requesting for payment until the total amount paid exceeds the total passed into the method
     * @param total Total amount to request cash payment from
     * @return Total amount paid by the user
     */
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
     * Writes the receipt to a file in the data directory
     * @param receipt ArrayList of Receipt Strings to write to file
     * @param receiptId Receipt ID to save the file as
     * @return true if written successfully
     */
    private boolean writeReceipt(ArrayList<String> receipt, int receiptId) {
        FileIOHelper.createFolder(RECEIPT_SUBFOLDER);
        try (PrintWriter w = new PrintWriter(FileIOHelper.getFileBufferedWriter(RECEIPT_SUBFOLDER + receiptId + ".txt"))) {
            receipt.forEach(w::println);
        } catch (IOException e) {
            System.out.println("Error saving receipt to file");
            return false;
        }
        return true;
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
