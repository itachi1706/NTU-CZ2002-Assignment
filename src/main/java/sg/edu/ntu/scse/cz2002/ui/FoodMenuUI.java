package sg.edu.ntu.scse.cz2002.ui;

/**
 * The Food Items Menu UI
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-22
 */
public class FoodMenuUI extends BaseMenu {

    /**
     * The Food Menu Items Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
    @Override
    protected int generateMenuScreen() {
        printHeader("Menu Items Management");
        System.out.println("1) Create a new menu item");
        System.out.println("2) Update a new menu items");
        System.out.println("3) Delete a menu item");
        System.out.println("4) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(4, 0);
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Food Item Menu)");
        }
        return 0;
    }
}
