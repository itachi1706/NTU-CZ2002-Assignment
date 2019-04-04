package sg.edu.ntu.scse.cz2002.ui;

/**
 * The Main Menu UI
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-22
 */
public class MainMenuUI extends BaseMenu {

    /**
     * The application main menu
     * @return Exit Code. Return 1 to exit the program
     */
    @Override
    protected int generateMenuScreen() {
        printHeader("Main Menu");
        System.out.println("1) Menu Items Management");
        System.out.println("2) Promotions Management");
        System.out.println("3) Order Management");
        System.out.println("4) Reservation Manangement");
        System.out.println("5) Check Table Availability");
        System.out.println("6) Print Bill Invoice");
        System.out.println("7) Print sale revenue report");
        System.out.println("8) Staff Management");
        System.out.println("0) Exit");
        printBreaks();

        // Process Choice
        int choice = doMenuChoice(8, 0);
        switch (choice) {
            case 1:
                if (new FoodMenuUI().startMainMenu()) return 1;
                break;
            case 2:
                if (new PromotionMenuUI().startMainMenu()) return 1;
                break;
            case 3:
                if (new OrderMenuUI().startMainMenu()) return 1;
                break;
            case 4:
                if (new ReservationMenuUI().startMainMenu()) return 1;
                break;
            case 5:
                checkTableAvailability();
                break;
            case 6:
                // TODO: To Implement
                break;
            case 7:
                // TODO: To Implement
                break;
            case 8:
                if (new StaffMgmtMenuUI().startMainMenu()) return 1;
                break;
            case 0:
                return 1; // Shutdown
            default:
                throw new IllegalStateException("Invalid Choice (Main Menu)");
        }
        return 0;
    }

    private void checkTableAvailability() {
        //TODO: Print out list of available tables from Tables ArrayList
        //TODO: If time executed is not within AM or PM session, get the details of the upcoming session.
    }
}
