package sg.edu.ntu.scse.cz2002.ui;

/**
 * The Staff Management UI
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-28
 */
public class StaffMgmtMenuUI extends BaseMenu {

    /**
     * The Staff Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
    @Override
    protected int generateMenuScreen() {
        printHeader("Staff Management Menu");
        System.out.println("1) Create new Staff");
        System.out.println("2) Edit Staff");
        System.out.println("3) Remove Staff");
        System.out.println("4) Change Staff");
        System.out.println("5) View Current Logged in Staff");
        System.out.println("6) Return to Main Menu");
        System.out.println("0) Exit");
        printBreaks();

        int choice = doMenuChoice(5, 0);
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
                break;
            case 5:
                // TODO: To Implement
                break;
            case 6:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Reservation Menu)");
        }
        return 0;
    }
}
