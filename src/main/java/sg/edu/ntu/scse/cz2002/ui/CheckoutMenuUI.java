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
                break;
            case 2:
                // TODO: Reprint Invoice for completed orders
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
}
