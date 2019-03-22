package sg.edu.ntu.scse.cz2002.ui;

/**
 * The Promotion Menu UI
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-22
 */
public class PromotionMenuUI extends BaseMenu {

    /**
     * The Promotion Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
    @Override
    protected int generateMenuScreen() {
        printHeader("Promotion Management");
        System.out.println("1) Create a new promotion");
        System.out.println("2) Update a promotion");
        System.out.println("3) Delete promotion");
        System.out.println("4) Back to main menu");
        System.out.println("0) Exit Application");
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
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Promotion Menu)");
        }
        return 0;
    }
}
