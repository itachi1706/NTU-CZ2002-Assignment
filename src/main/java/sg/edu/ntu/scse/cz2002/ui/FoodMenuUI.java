package sg.edu.ntu.scse.cz2002.ui;
import java.util.Scanner;

import sg.edu.ntu.scse.cz2002.util.MenuItemCSVHelper; //to call menu functions.

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
	
	@SuppressWarnings("resource")
	Scanner sc = new Scanner(System.in);
	
	@Override
    protected int generateMenuScreen() {
        
		String filename = "menu.csv";
		
		printHeader("Menu Items Management");
		System.out.println("1) Print existing menu");
		System.out.println("2) Create a new menu item");
        System.out.println("3) Edit an existing menu item's details");
        System.out.println("4) Delete a menu item");
        System.out.println("5) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

    	MenuItemCSVHelper helper = new MenuItemCSVHelper(filename);
        
        int choice = doMenuChoice(5, 0);
        switch (choice) {
            case 1:
        		System.out.println("This is the current menu:");
            	helper.printMenu();
                break;
            case 2:
				String newItemName;
				String newItemType;
				String newItemDescription;
				double newItemPrice;
				
				System.out.println("Enter new menu item name: ");
				newItemName = sc.nextLine();
				
				System.out.println("Enter new menu item type: ");
				newItemType = sc.nextLine();

				System.out.println("Enter new menu item description: ");
				newItemDescription = sc.nextLine();
				
				System.out.println("Enter new menu item price: ");
				newItemPrice = sc.nextDouble();
				sc.nextLine(); //need to clear buffer for Int, Float or Double
            	
				helper.addNewMenuItem(newItemName, newItemType, newItemDescription, newItemPrice);
				
				System.out.println(newItemName+" has been successfully added.");
				
                break;
            case 3:
                // TODO: To Implement
                break;
            case 4:
				int targetItemID;
				
				System.out.println("Enter the ID of the menu item to be deleted: ");
				targetItemID = sc.nextInt();
				sc.nextLine(); //clear for I.F.D.
				
				helper.deleteMenuItem(targetItemID);
				
				System.out.println("The menu item has has been successfully deleted.");
				
                break;
            case 5:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Food Item Menu)");
        }
        return 0;
    }
}
