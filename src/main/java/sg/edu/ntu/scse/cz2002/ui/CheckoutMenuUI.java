package sg.edu.ntu.scse.cz2002.ui;

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
        // TODO: Print list of unpaid orders by table
        // TODO: Select table number to checkout (-1 to cancel)
        // TODO: Show order details with subtotal and GST and final total
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
}
