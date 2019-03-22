package sg.edu.ntu.scse.cz2002.ui;

/**
 * Created by Kenneth on 22/3/2019.
 * for sg.edu.ntu.scse.cz2002.ui in assignment-fsp6-grp2
 */
public class OrderMenuUI extends BaseMenu {

    @Override
    protected int generateMenuScreen() {
        printHeader("Order Management");
        System.out.println("1) Create a new order");
        System.out.println("2) View order");
        System.out.println("3) Add item to order");
        System.out.println("4) Remove item from order");
        System.out.println("5) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(5, 0);
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Order Menu)");
        }
        return 0;
    }
}
