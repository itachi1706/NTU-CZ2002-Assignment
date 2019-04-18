package sg.edu.ntu.scse.cz2002.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.objects.restaurantItem.MenuItem;
import sg.edu.ntu.scse.cz2002.objects.restaurantItem.PromotionItem;
import sg.edu.ntu.scse.cz2002.util.PromoCSVHelper;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;
//import sun.rmi.rmic.Main;


/**
 * PromotionItem Menu UI
 *
 * @author Arthur Koh, Kenneth Soh
 * @version 1.0
 * @since 2019-04-17
 */
public class PromotionMenuUI extends BaseMenu {


	/**
	 * Scanner for use in retrieving user input.
	 */
	private Scanner sc = ScannerHelper.getScannerInput();

	/**
	 * The PromotionItem Management Menu
	 * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
	 */
    @Override
    protected int generateMenuScreen() {
        printHeader("Promotion Item Management");
        System.out.println("1) View existing promotions");
        System.out.println("2) Create a new promotion");
        System.out.println("3) Update an existing promotion");
        System.out.println("4) Delete a promotion");
        System.out.println("5) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(5, 0);
        switch (choice) {
        
            case 1: // Prints existing promotions
            	this.printPromotion();
                break;
            case 2: // Create a new promotion
				this.addNewPromotion();
				break;

            case 3: //Edit an existing promotion
				this.editPromotion();
            	break;
            case 4: //Delete an existing promotion
				this.deletePromotion();
                break;
            case 5:
                return -1;
            case 0:
                return 1;
            default:
                throw new MenuChoiceInvalidException("Promotion Menu");
        }
        return 0;
    }




	/**
	 * Prints the menu of promotions stored in the CSV file.
	 * Uses {@link MainApp#promotions} and {@link MenuItem#retrieveMenuItem(int)}.
	 */
	public void printPromotion() {

		//Code to print all promotions
		printHeader("All Promotions");
		int n = 1;
		System.out.printf("%-5s %-10s %-10s %-10s\n", "ID", "Name", "Price",  "Description");
		printBreaks();

		for (int i = 0; i < MainApp.promotions.size(); i++) {
			PromotionItem pi = MainApp.promotions.get(i);
			MenuItem mainItem = MenuItem.retrieveMenuItem(pi.getPromoMain());
			MenuItem dessertItem = MenuItem.retrieveMenuItem(pi.getPromoDessert());
			MenuItem drinkItem = MenuItem.retrieveMenuItem(pi.getPromoDrink());

			String tempDescription = ("Served with the following items: ");
			String tempMain = ("[" + pi.getPromoMain() + "]");
			String tempDessert = ("[" + pi.getPromoDessert() + "]");
			String tempDrink = ("[" + pi.getPromoDrink() + "]");

			System.out.printf("%-5s %-10s %-10s %-10s\n", pi.getId(), pi.getName(), pi.getPrice(), tempDescription);
			System.out.printf("%-5s %-10s %-10s %-10s %-5s %-5s\n", "", "", "", "Main: ", tempMain, mainItem.getName());
			System.out.printf("%-5s %-10s %-10s %-10s %-5s %-5s\n", "", "", "", "Dessert: ", tempDessert, dessertItem.getName());
			System.out.printf("%-5s %-10s %-10s %-10s %-5s %-5s\n\n", "", "", "", "Drink: ", tempDrink, drinkItem.getName());
		}

	}

	/**
	 * Method to add a new promotion.
	 * Uses {@link PromoCSVHelper#writeToCsv(ArrayList)} to facilitate I/O operations.
	 */
	public void addNewPromotion() {

		String newPromoName;
		double newPromoPrice;
		int newPromoMain = 1;
		int newPromoDessert = 2;
		int newPromoDrink = 3;
		boolean mainFound = false;
		boolean dessertFound = false;
		boolean drinkFound = false;

		//have to create 3 temporary arrays here to filter out.
		ArrayList<MenuItem> filteredMainMenu = MenuItem.retrieveMenuItemListFiltered(MenuItem.MenuItemType.MAIN); //the main here is just for main dishes
		ArrayList<MenuItem> filteredDessertMenu = MenuItem.retrieveMenuItemListFiltered(MenuItem.MenuItemType.DESSERT);
		ArrayList<MenuItem> filteredDrinkMenu = MenuItem.retrieveMenuItemListFiltered(MenuItem.MenuItemType.DRINK);

		System.out.println("Enter new promotion set name: ");
		sc.nextLine(); //required if previous scanner takes in int, and now string is required
		newPromoName = sc.nextLine();

		newPromoPrice = ScannerHelper.getDoubleInput("Enter new promotion's price: ");

		//Code to print filtered menu of mains
		printHeader("All Main Items");
		int k = 1;
		System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
		printBreaks();
		for (MenuItem mi : filteredMainMenu) {
			System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
			k++;
		}

		while (!mainFound) {
			newPromoMain = ScannerHelper.getIntegerInput("Enter new promotion's main ID: ");
			mainFound = MenuItem.menuTypeChecker(filteredMainMenu, newPromoMain, "Main");
			if (mainFound == false) System.out.println("Main not found. Please enter a valid main ID.");
		}

		//Code to print filtered menu of desserts
		printHeader("All Dessert Items");
		int l = 1;
		System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
		printBreaks();
		for (MenuItem mi : filteredDessertMenu) {
			System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
			l++;
		}

		while (!dessertFound) {
			newPromoDessert = ScannerHelper.getIntegerInput("Enter new promotion's dessert ID: ");
			dessertFound = MenuItem.menuTypeChecker(filteredDessertMenu, newPromoDessert, "Dessert");
			if (dessertFound == false) System.out.println("Dessert not found. Please enter a valid dessert ID.");
		}

		//Code to print filtered menu of drinks
		printHeader("All Drink Items");
		int m = 1;
		System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
		printBreaks();
		for (MenuItem mi : filteredDrinkMenu) {
			System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
			m++;
		}

		while (!drinkFound) {
			newPromoDrink = ScannerHelper.getIntegerInput("Enter new promotion's drink ID: ");
			drinkFound = MenuItem.menuTypeChecker(filteredDrinkMenu, newPromoDrink, "Drink");
			if (drinkFound == false) System.out.println("Drink not found. Please enter a valid drink ID.");
			//need to find way to reprompt for input!
		}
		//Basically the actual addNewPromotion method
		try {

			PromotionItem promotionObj = MainApp.promotions.get((MainApp.promotions.size())-1);
			int promoID = promotionObj.getId()+1;

			PromotionItem promotion = new PromotionItem(promoID, newPromoName, newPromoPrice, newPromoMain, newPromoDessert, newPromoDrink);
			MainApp.promotions.add(promotion);

			PromoCSVHelper promotionHelper = PromoCSVHelper.getInstance();
			promotionHelper.writeToCsv(MainApp.promotions);
			
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}

		System.out.println("Add successful. New promotion has been successfully added.");
	}

	/**
	 * Method to edit an existing promotion.
	 * Uses {@link PromoCSVHelper#writeToCsv(ArrayList)} and {@link PromotionItem#retrievePromotion(int)} to facilitate I/O operations.
	 */
	public void editPromotion() {

		int editPromoID = 1;
		String editPromoName;
		double editPromoPrice = 0.0;
		int editPromoMain = 1;
		int editPromoDessert = 2;
		int editPromoDrink = 3;
		boolean mainFound = false;
		boolean dessertFound = false;
		boolean drinkFound = false;
		boolean promoFound = false;

		//have to create 3 temporary arrays here to filter out.
		ArrayList<MenuItem> filteredMainMenu = MenuItem.retrieveMenuItemListFiltered(MenuItem.MenuItemType.MAIN); //the main here is just for main dishes
		ArrayList<MenuItem> filteredDessertMenu = MenuItem.retrieveMenuItemListFiltered(MenuItem.MenuItemType.DESSERT);
		ArrayList<MenuItem> filteredDrinkMenu = MenuItem.retrieveMenuItemListFiltered(MenuItem.MenuItemType.DRINK);

		printPromotion();

		while (!promoFound) {
			editPromoID = ScannerHelper.getIntegerInput("Enter the ID of the promotion to be edited: ");
			if (PromotionItem.retrievePromotion(editPromoID) == null) System.out.println("Invalid ID. Please enter a valid promo item ID.");
			else promoFound = true;
		}

		//retrieve menu item with editItemID check.
		if (PromotionItem.retrievePromotion(editPromoID) == null){
			System.out.println("Invalid ID. Promotion not found.");
			return;
		}

		System.out.println("Enter new name of the promotion to be edited: ");
		editPromoName = sc.nextLine();


		editPromoPrice= ScannerHelper.getDoubleInput("Enter the new price of the promotion to be edited: ");

		/*
		editPromoMain = ScannerHelper.getIntegerInput("Enter the main's ID of the promotion to be edited: ");
		editPromoDessert = ScannerHelper.getIntegerInput("Enter the dessert's ID of the promotion to be edited: ");
		editPromoDrink = ScannerHelper.getIntegerInput("Enter the drink's ID of the promotion to be edited: ");
		 */

		//Code to print filtered menu of mains
		printHeader("All Main Items");
		int k = 1;
		System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
		printBreaks();
		for (MenuItem mi : filteredMainMenu) {
			System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
			k++;
		}

		while (!mainFound) {
			editPromoMain = ScannerHelper.getIntegerInput("Enter the main's ID of the promotion to be edited: ");
			mainFound = MenuItem.menuTypeChecker(filteredMainMenu, editPromoMain, "Main");
			if (mainFound == false) System.out.println("Main not found. Please enter a valid main ID.");
		}

		//Code to print filtered menu of desserts
		printHeader("All Dessert Items");
		int l = 1;
		System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
		printBreaks();
		for (MenuItem mi : filteredDessertMenu) {
			System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
			l++;
		}

		while (!dessertFound) {
			editPromoDessert = ScannerHelper.getIntegerInput("Enter the dessert's ID of the promotion to be edited: ");
			dessertFound = MenuItem.menuTypeChecker(filteredDessertMenu, editPromoDessert, "Dessert");
			if (dessertFound == false) System.out.println("Dessert not found. Please enter a valid dessert ID.");
		}

		//Code to print filtered menu of drinks
		printHeader("All Drink Items");
		int m = 1;
		System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
		printBreaks();
		for (MenuItem mi : filteredDrinkMenu) {
			System.out.printf("%-5s %-50s %-6s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
			m++;
		}

		while (!drinkFound) {
			editPromoDrink = ScannerHelper.getIntegerInput("Enter the drink's ID of the promotion to be edited: ");
			drinkFound = MenuItem.menuTypeChecker(filteredDrinkMenu, editPromoDrink, "Drink");
			if (drinkFound == false) System.out.println("Drink not found. Please enter a valid drink ID.");
			//need to find way to reprompt for input!
		}

		PromoCSVHelper promoHelper = PromoCSVHelper.getInstance();

		//implement flag check
		boolean found = false;

		for (int i=0; i<(MainApp.promotions.size()); i++) {
			
			PromotionItem promoObj = MainApp.promotions.get(i);
			if (editPromoID == promoObj.getId()) {
				try {
					
					PromotionItem promo = PromotionItem.retrievePromotion(editPromoID);
					
					promo.setName(editPromoName);
					promo.setPrice(editPromoPrice);
					promo.setPromoMain(editPromoMain);
					promo.setPromoDessert(editPromoDessert);
					promo.setPromoDrink(editPromoDrink);
					//at this point, the object has been edited with the new values
					
					promoHelper.writeToCsv(MainApp.promotions); // calls IO method to save the array into the CSV file
					System.out.println("Edit successful. Target promotion successfully edited!");
					return;
				}
				catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			}
	
		}

		if (found == false){
			System.out.println("Edit failed. Target promotion not found.");
			return;
		}

	}	
	
	/**
	 * Method to delete an existing promotion.
	 * Uses {@link PromoCSVHelper#writeToCsv(ArrayList)} to facilitate I/O operations.
	 */
	public void deletePromotion() {

		int targetPromoID = 1;
		boolean promoFound = false;

		printPromotion();

		while (!promoFound) {
			targetPromoID = ScannerHelper.getIntegerInput("Enter the ID of the promotion to be deleted: ");
			if (PromotionItem.retrievePromotion(targetPromoID) == null) System.out.println("Invalid ID. Please enter a valid promo item ID.");
			else promoFound = true;
		}

		PromoCSVHelper promoHelper = PromoCSVHelper.getInstance();

		//implement flag check
		boolean found = false;

		for (int i=0; i<(MainApp.promotions.size()); i++) {
			PromotionItem promoObj = MainApp.promotions.get(i);
			if (targetPromoID == promoObj.getId()) {
				try {
					MainApp.promotions.remove(i); //delete using i as for loop index
					promoHelper.writeToCsv(MainApp.promotions); // calls IO method to save the new array into the CSV file
					System.out.println("Target promotion has been successfully removed!");
					found = true;
					return;
				}
				catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			}
		}

		if (found == false) {
			System.out.println("Delete failed. Target promotion not found.");
			return;
		}
	}


}
