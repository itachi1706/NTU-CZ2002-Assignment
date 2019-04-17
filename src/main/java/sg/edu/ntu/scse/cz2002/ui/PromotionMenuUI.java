package sg.edu.ntu.scse.cz2002.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.objects.restaurantItem.MenuItem;
import sg.edu.ntu.scse.cz2002.objects.restaurantItem.PromotionItem;
import sg.edu.ntu.scse.cz2002.util.PromoCSVHelper;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;



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
	 * Uses {@link MainApp#promotions} and {@link FoodMenuUI#retrieveMenuItem(int)}.
	 */
	public void printPromotion() {

		System.out.println("This is the list of current promotions:");

		for (int i = 0; i < MainApp.promotions.size(); i++) {
			PromotionItem promotion = (PromotionItem) MainApp.promotions.get(i);
			
			MenuItem mainItem = FoodMenuUI.retrieveMenuItem(promotion.getPromoMain());
			MenuItem dessertItem = FoodMenuUI.retrieveMenuItem(promotion.getPromoDessert());
			MenuItem drinkItem = FoodMenuUI.retrieveMenuItem(promotion.getPromoDrink());

			printHeader(promotion.getName());
			//System.out.println("|============================|");
			System.out.println("Promotion ID: " + promotion.getId());
			//System.out.println("Promotion Name: " + promotion.getName());
			System.out.println("Promotion Price: $" + promotion.getPrice());

			/*
			System.out.println("Promotion Main: [" + promotion.getPromoMain() + "] "+ mainItem.getName());
			System.out.println("Promotion Dessert: [" + promotion.getPromoDessert() + "] "+ dessertItem.getName());
			System.out.println("Promotion Drink: [" + promotion.getPromoDrink() + "] "+ drinkItem.getName());
			 */

			System.out.println("Promotion Description\n"+
					"Served with these 3 items:\n"+
					"Main: [" + promotion.getPromoMain() + "] "+ mainItem.getName()+
					"\nDessert: [" + promotion.getPromoDessert() + "] "+ dessertItem.getName()+
					"\nDrink: [" + promotion.getPromoDrink() + "] "+ drinkItem.getName());
		}
	}

	/**
	 * Method to add a new promotion.
	 * Uses {@link PromoCSVHelper#writeToCsv(ArrayList<PromotionItem>)} to facilitate I/O operations.
	 */
	public void addNewPromotion() {

		String newPromoName;
		double newPromoPrice;
		int newPromoMain = 1;
		int newPromoDessert = 1;
		int newPromoDrink = 1;
		boolean mainFound = false;
		boolean dessertFound = false;
		boolean drinkFound = false;

		//have to create 3 temporary arrays here to filter out.
		ArrayList<MenuItem> filteredMainMenu = FoodMenuUI.retrieveMenuItemListFiltered(MenuItem.MenuItemType.MAIN); //the main here is just for main dishes
		ArrayList<MenuItem> filteredDessertMenu = FoodMenuUI.retrieveMenuItemListFiltered(MenuItem.MenuItemType.DESSERT);
		ArrayList<MenuItem> filteredDrinkMenu = FoodMenuUI.retrieveMenuItemListFiltered(MenuItem.MenuItemType.DRINK);

		System.out.println("Enter new promotion set name: ");
		sc.nextLine(); //required if previous scanner takes in int, and now string is required
		newPromoName = sc.nextLine();

		newPromoPrice = ScannerHelper.getDoubleInput("Enter new menu item price: ");

		//need to find way to reprompt for input!
		while (!mainFound) {
			newPromoMain = ScannerHelper.getIntegerInput("Enter new promotion's main ID: ");
			mainFound = MenuItem.menuTypeChecker(filteredMainMenu, newPromoMain, "Main");
			if (mainFound == false) System.out.println("Main not found. Please enter a valid main ID.");
		}

		while (!dessertFound) {
			newPromoDessert = ScannerHelper.getIntegerInput("Enter new promotion's dessert ID: ");
			dessertFound = MenuItem.menuTypeChecker(filteredDessertMenu, newPromoDessert, "Dessert");
			if (dessertFound == false) System.out.println("Dessert not found. Please enter a valid dessert ID.");
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
	 * Uses {@link PromoCSVHelper#writeToCsv(ArrayList<PromotionItem>)} and {@link PromotionMenuUI#retrievePromotion(int)} to facilitate I/O operations.
	 */
	public void editPromotion() {

		int editPromoID;
		String editPromoName;
		double editPromoPrice;
		int editPromoMain;
		int editPromoDessert;
		int editPromoDrink;

		//probably need to check for targetItemID data type input
		editPromoID = ScannerHelper.getIntegerInput("Enter the ID of the menu item to be edited: \n");
		//sc.nextLine(); //clear for I.F.D.

		//retrieve menu item with editItemID check.
		if (retrievePromotion(editPromoID) == null){
			System.out.println("Invalid ID. Promotion not found.");
			return;
		}

		System.out.println("Enter new name of the promotion to be edited: ");
		editPromoName = sc.nextLine();
		editPromoPrice= ScannerHelper.getDoubleInput("Enter the new price of the promotion to be edited: ");
		editPromoMain = ScannerHelper.getIntegerInput("Enter the main's ID of the promotion to be edited: ");
		editPromoDessert = ScannerHelper.getIntegerInput("Enter the dessert's ID of the promotion to be edited: ");
		editPromoDrink = ScannerHelper.getIntegerInput("Enter the drink's ID of the promotion to be edited: ");

		PromoCSVHelper promoHelper = PromoCSVHelper.getInstance();

		//implement flag check
		boolean found = false;

		for (int i=0; i<(MainApp.promotions.size()); i++) {
			
			PromotionItem promoObj = MainApp.promotions.get(i);
			if (editPromoID == promoObj.getId()) {
				try {
					
					PromotionItem promo = retrievePromotion(editPromoID);
					
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
	 * Uses {@link PromoCSVHelper#writeToCsv(ArrayList<PromotionItem>)} to facilitate I/O operations.
	 */
	public void deletePromotion() {

		int targetPromoID;
		targetPromoID = ScannerHelper.getIntegerInput("Enter the ID of the promotion to be deleted: ");


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




	/**
	 * Returns a PromotionItem object that matches the input targetPromoID.
	 * @param targetPromoID ID of the promotion object to be retrieved.
	 * @return promoObj Object containing a promotion item's attributes.
	 */	
	@Nullable
	public static PromotionItem retrievePromotion(int targetPromoID) {
		
		for (int i=0; i<(MainApp.promotions.size()); i++) {
			
			PromotionItem promoObj = MainApp.promotions.get(i);
			
			if (targetPromoID == promoObj.getId()) {
				//System.out.println("Target promotion found.");
				return promoObj;
			}
			
		}

		return null; //"Target promotion not found."
	}


}
