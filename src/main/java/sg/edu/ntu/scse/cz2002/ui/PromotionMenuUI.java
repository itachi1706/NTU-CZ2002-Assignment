package sg.edu.ntu.scse.cz2002.ui;

/**
 * Created by Kenneth on 22/3/2019.
 * for sg.edu.ntu.scse.cz2002.ui in assignment-fsp6-grp2
 */
public class PromotionMenuUI extends BaseMenu {

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
                throw new IllegalStateException("Invalid Choice (Promotion Menu)");
        }
        return 0;
    }
}
