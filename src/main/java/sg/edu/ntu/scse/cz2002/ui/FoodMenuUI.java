package sg.edu.ntu.scse.cz2002.ui;

import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.objects.restaurantItem.MenuItem;
import sg.edu.ntu.scse.cz2002.objects.restaurantItem.PromotionItem;
import sg.edu.ntu.scse.cz2002.util.MenuItemCSVHelper;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;
import sg.edu.ntu.scse.cz2002.util.PromoCSVHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Food Items Menu UI
 *
 * @author Kenneth Soh, Arthur Koh
 * @version 1.0
 * @since 2019-03-22
 */
public class FoodMenuUI extends BaseMenu {
<<<<<<< Updated upstream
=======

	private Scanner sc = ScannerHelper.getScannerInput();

>>>>>>> Stashed changes
    /**
     * The Food Menu Items Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
	  @Override
    protected int generateMenuScreen() {
<<<<<<< Updated upstream
  
		Scanner sc = ScannerHelper.getScannerInput();
  
=======

>>>>>>> Stashed changes
		printHeader("Menu Items Management");
		System.out.println("1) Print existing menu");
		System.out.println("2) Create a new menu item");
        System.out.println("3) Edit an existing menu item's details");
        System.out.println("4) Delete a menu item");
        System.out.println("5) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();
        
        int choice = doMenuChoice(5, 0);
        switch (choice) {
            case 1: // Prints menu
				this.printMenu();
                break;
            case 2: // Create new menu item
				this.addNewMenuItem();
                break;
            case 3: // Edit an existing menu item
				this.editMenuItem();
                break;
            case 4: // Delete an existing menu item
				this.deleteMenuItem();
                break;
            case 5:
                return -1;
            case 0:
                return 1;
                
            default:
                throw new MenuChoiceInvalidException("Food Item Menu");
        }
        return 0;
    }

	/**
	 * Prints the CSV File Menu.
	 * (uses the globally defined "menuItems" ArrayList from MainApp)
	 **/
	private void printMenu() {

		int selection;

		selection = ScannerHelper.getIntegerInput("Enter the number of the item type you would like to print: \n" +
						"(1) Main | " +
						"(2) Dessert | " +
						"(3) Drink | " +
						"(4) All\n",
				0,5);
		MenuItem.MenuItemType printType;
		switch (selection) {
			case 1: printType = MenuItem.MenuItemType.MAIN; break;
			case 2: printType = MenuItem.MenuItemType.DESSERT; break;
			case 3: printType = MenuItem.MenuItemType.DRINK; break;
			case 4: printType = MenuItem.MenuItemType.ALL; break;
			case 0: return;
			default: throw new MenuChoiceInvalidException("Choose menu to print");
		}
		ArrayList<MenuItem> filteredMenu = FoodMenuUI.retrieveMenuItemListFiltered(printType);
		System.out.println("This is the current menu:");

		for (int i = 0; i < filteredMenu.size(); i++) {
			MenuItem menuItem = filteredMenu.get(i);
			System.out.println("|============================|");
			System.out.println("ID: " + menuItem.getId());
			System.out.println("Name: " + menuItem.getName());
			System.out.println("Type: " + menuItem.getType());
			System.out.println("Description: " + menuItem.getDescription());
			System.out.println("Price: $" + menuItem.getPrice());
		}
	}

	/**
	 * Method to add a new menu item.
	 * (uses "writeToCsv" to facilitate I/O operations from MenuItemCSVHelper.)
	 * */
	private void addNewMenuItem() {

		String newItemName;
		int newItemType;
		String newItemDescription;
		double newItemPrice;

		System.out.println("Enter new menu item name: ");
		sc.nextLine(); //required if previous scanner takes in int, and now string is required
		newItemName = sc.nextLine();

		newItemType = ScannerHelper.getIntegerInput("Enter the number of the new menu item type: \n" +
						"(1) Main | " +
						"(2) Dessert | " +
						"(3) Drink\n",
				0,4);

		System.out.println("Enter new menu item description: ");
		newItemDescription = sc.nextLine();

		newItemPrice = ScannerHelper.getDoubleInput("Enter new menu item price: ");

		//Basically the actual add function
		try {
			
			//basically gets the last object in the menuItem array
			MenuItem menuItemObj = MainApp.menuItems.get((MainApp.menuItems.size())-1);
			
			//gets the ID value from the last object and +1 to create a new PK
			int id = menuItemObj.getId()+1;
			
			MenuItem menuItem = new MenuItem(id, newItemName, newItemType, newItemDescription, newItemPrice);
			MainApp.menuItems.add(menuItem); //adds the RestaurantItem object to the menu array
			
			MenuItemCSVHelper menuHelper = MenuItemCSVHelper.getInstance();
			//getInstance does 2 things: 
			//First, it checks to see if an object has been previously created.
			//If so, use it, (so don't anyhow create object and waste memory).
			//If not, creates a new object.
			
			menuHelper.writeToCsv(MainApp.menuItems); //calls IO method to save the new array into the CSV file!
			//MainApp.saveAll(); //this is the last resort if lazy af
			
			
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}

		System.out.println("Add successful. New menu item has been successfully added.");
	}

	/**
	 * Method to edit an existing item.
	 * (uses "writeToCsv" AND "retrieveMenuItem" to facilitate I/O operations)
	 */
	private void editMenuItem() {

		int editItemID;
		String editItemName;
		int editItemType;
		String editItemDescription;
		double editItemPrice;

		//probably need to check for targetItemID data type input
		editItemID = ScannerHelper.getIntegerInput("Enter the ID of the menu item to be edited: \n");
		//sc.nextLine(); //clear for I.F.D.

		//retrieve menu item with editItemID check.
		if (retrieveMenuItem(editItemID) == null){
			System.out.println("Invalid ID. Menu item not found.");
			return;
		}


		//consideration: retrieve object first, then do edit later?
		//CURRENT WAY: one shot do the edit and passing in of values.


		//consider calling method to retrieve current item's name and printing it
		//e.g. Enter "SCS Ovaltine's new name:"
		System.out.println("Enter new item name: ");
		editItemName = sc.nextLine();

		editItemType = ScannerHelper.getIntegerInput("Enter the number of the new menu item type: \n" +
						"(1) Main | " +
						"(2) Dessert | " +
						"(3) Drink\n",
				0,4);

		System.out.println("Enter new item description: ");
		editItemDescription = sc.nextLine();

		editItemPrice = ScannerHelper.getDoubleInput("Enter new item price: ");

		MenuItemCSVHelper menuHelper = MenuItemCSVHelper.getInstance();

		//implement flag check
		boolean found = false;

		for (int i=0; i<(MainApp.menuItems.size()); i++) {

			MenuItem menuItemObj = MainApp.menuItems.get(i); //when you do this, you actually retrieve the whole object

			if (editItemID == menuItemObj.getId()) {
				try {
					menuItemObj.setName(editItemName);
					menuItemObj.setType(editItemType);
					menuItemObj.setDescription(editItemDescription);
					menuItemObj.setPrice(editItemPrice);
					//at this point, the object has been edited with the new values
					
					menuHelper.writeToCsv(MainApp.menuItems); // calls IO method to save the array into the CSV file
					found = true; //when found
					System.out.println("Edit successful. Target menu item edited!");
					return;
				}
				catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			}
	
		}

		if (found == false){
			System.out.println("Edit failed. Target menu item not found.");
			return;
		}

	}	
	
	/**
	 * Method to delete an existing item.
	 * (uses "writeToCsv" to facilitate I/O operations)
	 */
	private void deleteMenuItem() {

		int targetItemID =  ScannerHelper.getIntegerInput("Enter the ID of the menu item to be deleted. Note: any promotions linked to this item will be deleted. \n");

		// logic for this would be
		// to do a search using menu item ID
		// we will decompose every single object
		// and then calling array.remove to remove the RestaurantItem object
		// this would change the id sequence
		// and then the write.csv method IO would be called

		MenuItemCSVHelper menuHelper = MenuItemCSVHelper.getInstance();

		//implement flag check
		boolean found = false;

		for (int i=0; i<(MainApp.menuItems.size()); i++) {

			MenuItem menuItemObj = MainApp.menuItems.get(i);

			if (targetItemID == menuItemObj.getId()) {

				try {

					PromoCSVHelper promoHelper = PromoCSVHelper.getInstance();

					for(int j=0; j<(MainApp.promotions.size()); j++){

						PromotionItem promoItemObj = MainApp.promotions.get(j);

						//if found
						if (targetItemID == promoItemObj.getPromoMain() || targetItemID == promoItemObj.getPromoDessert() || targetItemID == promoItemObj.getPromoDrink()) {
							MainApp.promotions.remove(j);
							promoHelper.writeToCsv(MainApp.promotions);
							System.out.println("Associated Promotion Deleted.");
						}
						//else business as usual

					}



					MainApp.menuItems.remove(i); //delete using i as for loop index
					menuHelper.writeToCsv(MainApp.menuItems); // calls IO method to save the new array into the CSV file
					System.out.println("Delete Successful. Target menu item deleted!");
					found = true;
					return;
				}
				catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			}
	
		}

		if (found == false){
			System.out.println("Delete failed. Target menu item not found.");
			return;
		}

	}

	/**
	 * Method returning a MenuItem object that matches the input targetItemID.
	 * @param targetItemID ID of the menu item object to be retrieved.
	 * @return menuItemObj Object containing menu item attributes.
	 */
	@Nullable
	public static MenuItem retrieveMenuItem(int targetItemID) {

		for (int i=0; i<(MainApp.menuItems.size()); i++) {

			MenuItem menuItemObj = MainApp.menuItems.get(i);

			if (targetItemID == menuItemObj.getId()) { //"Target menu item found."
				return menuItemObj;
			}

		}

		return null; //"Target menu item not found."
	}

	/**
	 * Method returning an ArrayList filtered by enum type.
	 * @param targetItemType type of the menu item objects to be retrieved.
	 * @return menuItemsFiltered ArrayList containing the menu item type selected
	 */
	@Nullable
	public static ArrayList<MenuItem> retrieveMenuItemListFiltered(MenuItem.MenuItemType targetItemType) {

		ArrayList<MenuItem> menuItemsFiltered = new ArrayList<>(); //declare new empty arraylist

		//send in master first if ALL
		if (targetItemType == MenuItem.MenuItemType.ALL) {
			return MainApp.menuItems; //returns original array if ALL is selected.
		}

		for (int i=0; i<(MainApp.menuItems.size()); i++) { //for loop to run through menuitems and to filter out

			MenuItem menuItemObj = MainApp.menuItems.get(i); //gets a menu item object while the loop is running

			//need to change to enum
			if (targetItemType == menuItemObj.getType()) { //"Menu item of target item types found."
				menuItemsFiltered.add(menuItemObj); //add the found object into the filtered array list
			}

		}
		return menuItemsFiltered;
	}

}
