package sg.edu.ntu.scse.cz2002.ui;
import java.io.IOException;
import java.util.Scanner;

import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;
import sg.edu.ntu.scse.cz2002.util.MenuItemCSVHelper; //to call menu functions.

/**
 * The Food Items Menu UI
 *
 * @author Kenneth Soh, Arthur Koh
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
        		System.out.println("This is the current menu:");
        		//calls print function from below
            	printMenu();
                break;
            case 2: // Create new menu item
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
            	
				//calls add function from below
				addNewMenuItem(newItemName, newItemType, newItemDescription, newItemPrice);
				
				System.out.println(newItemName+" has been successfully added.");
				
                break;
            case 3: // Edit an existing menu item
				int editItemID;
				String editItemName;
				String editItemType;
				String editItemDescription;
				double editItemPrice;
				
				//probably need to check for targetItemID data type input
				System.out.println("Enter the ID of the menu item to be edited: ");
				editItemID = sc.nextInt();
				sc.nextLine(); //clear for I.F.D.
				
				
				//consideration: retrieve object first, then do edit later?
				//CURRENT WAY: one shot do the edit and passing in of values.
				
				
				//consider calling method to retrieve current item's name and printing it
				//e.g. Enter "SCS Ovaltine's new name:"
				System.out.println("Enter new item name: ");
				editItemName = sc.nextLine();
				
				System.out.println("Enter new item type: ");
				editItemType = sc.nextLine();
				
				System.out.println("Enter new item description: ");
				editItemDescription = sc.nextLine();
				
				System.out.println("Enter new item price: ");
				editItemPrice = sc.nextDouble();
				sc.nextLine(); //need to clear buffer for Int, Float or Double
            	
				//calls add function from below
				editMenuItem(editItemID, editItemName, editItemType, editItemDescription, editItemPrice);
				
				System.out.println("Item has been successfully edited.");
				
                break;
            case 4: // Delete an existing menu item
				int targetItemID;
				
				System.out.println("Enter the ID of the menu item to be deleted: ");
				targetItemID = sc.nextInt();
				sc.nextLine(); //clear for I.F.D.
				
				//calls delete function from below
				deleteMenuItem(targetItemID);
				
				System.out.println("The menu item has been successfully deleted.");
				
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
		
	
	
	/**
	 * Prints the CSV File Menu. @ Arthur
	 * (uses the globally defined "menuItems" ArrayList from MainApp)
	 */
	public void printMenu() {
		for (int i = 0; i < MainApp.menuItems.size(); i++) {
			MenuItem menuItem = (MenuItem) MainApp.menuItems.get(i);
			System.out.println("|============================|");
			System.out.println("ID: " + menuItem.getId());
			System.out.println("Name: " + menuItem.getName());
			System.out.println("Type: " + menuItem.getType());
			System.out.println("Description: " + menuItem.getDescription());
			System.out.println("Price: " + menuItem.getPrice());
		}
	}
	//eventually do an if else to print either all, drinks, food, or etc respectively***
	
	/**
	 * Adds a new menu item. @ Arthur
	 * (uses "writeToCsv" to facilitate I/O operations from MenuItemCSVHelper.)
	 * @params newItemName <Name of the new menu item to be added.>
	 * @params newItemType <Type of the new menu item to be added. Can only be defined as the following enum values: Drink, Main or Dessert.>
	 * @params newItemDescription <Description of the new menu item to be added.>
	 * @params newItemPrice <Price of the new menu item to be added.>
	 */
	public void addNewMenuItem(String newItemName, String newItemType, String newItemDescription, double newItemPrice) {
		
		try {
			
			//basically gets the last object in the menuItem array
			MenuItem menuItemObj = MainApp.menuItems.get((MainApp.menuItems.size())-1);
			
			//gets the ID value from the last object and +1 to create a new PK
			int id = menuItemObj.getId()+1;
			
			MenuItem menuItem = new MenuItem(id, newItemName, newItemType, newItemDescription, newItemPrice);
			MainApp.menuItems.add(menuItem); //adds the menuitem object to the menu array
			
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
	}
	//maybe also do an if-else case to input enum data types, e.g. 1 for Drink, 2 for food***
	//eventually do a handler for double, in particular for newItemPrice***

	/**
	 * Edits an existing item. @ Arthur
	 * (uses "writeToCsv" AND "retrieveMenuItem" to facilitate I/O operations)
	 * @params targetItemID <ID of the existing menu item to be edited.>
	 * @params editItemName <Name of the existing menu item to be edited.>
	 * @params editItemType <Type of the existing menu item to be edited. Can only be defined as the following enum values: Drink, Main or Dessert.>
	 * @params editItemDescription <Description of the existing menu item to be edited.>
	 * @params editItemPrice <Price of the existing menu item to be edited.>
	 */
	public void editMenuItem(int targetItemID, String editItemName, String editItemType, String editItemDescription, double editItemPrice) {

		MenuItemCSVHelper menuHelper = MenuItemCSVHelper.getInstance();
		
		for (int i=0; i<(MainApp.menuItems.size()); i++) {
			
			MenuItem menuItemObj = MainApp.menuItems.get(i);
			if (targetItemID == menuItemObj.getId()) {
				try {
					MenuItem menuItem = retrieveMenuItem(targetItemID); //retrieve target object
					
					menuItem.setName(editItemName);
					menuItem.setType(editItemType);
					menuItem.setDescription(editItemDescription);
					menuItem.setPrice(editItemPrice);
					//at this point, the object has been edited with the new values
					
					menuHelper.writeToCsv(MainApp.menuItems); // calls IO method to save the array into the CSV file
					System.out.println("Target menu item successfully edited!");	
					return;
				}
				catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			}
	
		}

		System.out.println("Edit failed. Target menu item not found.");	
		return;		
	}	
	
	/**
	 * Deletes an existing item. @ Arthur
	 * (uses "writeToCsv" to facilitate I/O operations)
	 * @params targetItemID <ID of the menu item to be deleted.>
	 */
	public void deleteMenuItem(int targetItemID) {

		// logic for this would be
		// to do a search using menu item ID
		// we will decompose every single object
		// and then calling array.remove to remove the menuitem object
		// this would change the id sequence
		// and then the write.csv method IO would be called

		MenuItemCSVHelper menuHelper = MenuItemCSVHelper.getInstance();
	
		//for loop to check if ArrayList contains target item ID
		
		/*
		for (MenuItem m : MainApp.menuItems) {
			m.
		} 
		COOL METHOD TO PRINT THE WHOLE 
		*/ 
		
		for (int i=0; i<(MainApp.menuItems.size()); i++) {
			
			MenuItem menuItemObj = MainApp.menuItems.get(i);
			if (targetItemID == menuItemObj.getId()) {
				try {
					MainApp.menuItems.remove(i); //delete using i as for loop index
					menuHelper.writeToCsv(MainApp.menuItems); // calls IO method to save the new array into the CSV file
					System.out.println("Target menu item successfully removed!");	
					return;
				}
				catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			}
	
		}

		System.out.println("Target menu item not found.");	
		return;		
	}

	/**
	 * Returns a MenuItem object that matches the input targetItemID. @ Arthur
	 * @params targetItemID <ID of the menu item object to be retrieved.>
	 * @return menuItemObj <Object containing menu item attributes.> 
	 */
	public static MenuItem retrieveMenuItem(int targetItemID) throws IOException {
		
		for (int i=0; i<(MainApp.menuItems.size()); i++) {
			
			MenuItem menuItemObj = MainApp.menuItems.get(i);
			
			if (targetItemID == menuItemObj.getId()) {
				System.out.println("Target menu item found.");
				return menuItemObj;
			}
			
		}
		
		System.out.println("Target menu item not found.");
		return null;
	}
}
