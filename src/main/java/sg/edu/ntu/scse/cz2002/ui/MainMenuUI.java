package sg.edu.ntu.scse.cz2002.ui;

/**
 * Created by Kenneth on 22/3/2019.
 * for sg.edu.ntu.scse.cz2002.ui in assignment-fsp6-grp2
 */
public class MainMenuUI extends BaseMenu {

    @Override
    protected int generateMenuScreen() {
        printHeader("Main Menu");
        System.out.println("1) Menu Items Management");
        System.out.println("2) Promotion Items Management");
        System.out.println("3) Order Management");
        System.out.println("4) Reservation Manangement");
        System.out.println("5) Check Table Availability");
        System.out.println("6) Print Bill Invoice");
        System.out.println("7) Print sale revenue report");
        System.out.println("0) Exit");
        printBreaks();

        // Process Choice
        int choice = doMenuChoice(7, 0);
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
                break;
            case 6:
                break;
            case 7:
                break;
            case 0:
                return 1; // Shutdown
            default:
                throw new IllegalStateException("Invalid Choice (Main Menu)");
        }
        return 0;
    }
}
