package sg.edu.ntu.scse.cz2002.ui;

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

    /**
     * Scanner for use in retrieving user input.
     */
    private Scanner sc = ScannerHelper.getScannerInput();

    /**
     * The Food Menu Items Management Menu
     *
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
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
     * Prints the menu of items stored in the CSV file.
     * Uses {@link MainApp#menuItems} to facilitate printing operations.
     **/
    private void printMenu() {

        int selection;

        selection = ScannerHelper.getIntegerInput("Enter the number of the item type you would like to print: \n" +
                        "(1) Main | " +
                        "(2) Dessert | " +
                        "(3) Drink | " +
                        "(4) All\n",
                0, 5);
        MenuItem.MenuItemType printType;
        switch (selection) {
            case 1:
                printType = MenuItem.MenuItemType.MAIN;
                printHeader("Mains");
                break;
            case 2:
                printType = MenuItem.MenuItemType.DESSERT;
                printHeader("Desserts");
                break;
            case 3:
                printType = MenuItem.MenuItemType.DRINK;
                printHeader("Drinks");
                break;
            case 4:
                printType = MenuItem.MenuItemType.ALL;
                printHeader("All Items");
                break;
            case 0:
                return;
            default:
                throw new MenuChoiceInvalidException("Choose menu to print");
        }
        ArrayList<MenuItem> filteredMenu = MenuItem.retrieveMenuItemListFiltered(printType);

        int i = 1;
        System.out.printf("%-5s %-50s %-10s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
        printBreaks();
        for (MenuItem mi : filteredMenu) {
            System.out.printf("%-5s %-50s %-10s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
            i++;
        }

    }

    /**
     * Method to add a new menu item.
     * Uses {@link MenuItemCSVHelper#writeToCsv(ArrayList)} to facilitate I/O operations.
     */
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
                0, 4);

        System.out.println("Enter new menu item description: ");
        newItemDescription = sc.nextLine();

        newItemPrice = ScannerHelper.getDoubleInput("Enter new menu item price: ");

        //Basically the actual add function
        try {

            //basically gets the last object in the menuItem array
            MenuItem menuItemObj = MainApp.menuItems.get((MainApp.menuItems.size()) - 1);

            //gets the ID value from the last object and +1 to create a new PK
            int id = menuItemObj.getId() + 1;

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
     * Uses {@link MenuItem#retrieveMenuItem(int)} to facilitate checking operations.
     * Uses {@link MenuItemCSVHelper#writeToCsv(ArrayList)} to facilitate I/O operations.
     */
    private void editMenuItem() {

        int editItemID = 1;
        String editItemName;
        int editItemType;
        String editItemDescription;
        double editItemPrice;

        //probably need to check for targetItemID data type input
        //sc.nextLine(); //clear for I.F.D.

        //Code to print all menu items
        printHeader("All Menu Items");
        int j = 1;
        System.out.printf("%-5s %-50s %-10s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
        printBreaks();
        for (MenuItem mi : MainApp.menuItems) {
            System.out.printf("%-5s %-50s %-10s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
            j++;
        }

        boolean menuItemFound = false;

        while (!menuItemFound) {
            editItemID = ScannerHelper.getIntegerInput("Enter the ID of the menu item to be edited: \n");

            if (MenuItem.retrieveMenuItem(editItemID) == null) System.out.println("Invalid ID. Please enter a valid menu item ID.");

            else menuItemFound = true;
        }

        System.out.println("Enter new item name: ");
        editItemName = sc.nextLine();

        editItemType = ScannerHelper.getIntegerInput("Enter the number of the new menu item type: \n" +
                        "(1) Main | " +
                        "(2) Dessert | " +
                        "(3) Drink\n",
                0, 4);

        System.out.println("Enter new item description: ");
        editItemDescription = sc.nextLine();

        editItemPrice = ScannerHelper.getDoubleInput("Enter new item price: ");

        MenuItemCSVHelper menuHelper = MenuItemCSVHelper.getInstance();

        for (int i = 0; i < (MainApp.menuItems.size()); i++) {

            MenuItem menuItemObj = MainApp.menuItems.get(i); //when you do this, you actually retrieve the whole object

            if (editItemID == menuItemObj.getId()) {
                try {
                    menuItemObj.setName(editItemName);
                    menuItemObj.setType(editItemType);
                    menuItemObj.setDescription(editItemDescription);
                    menuItemObj.setPrice(editItemPrice);
                    //at this point, the object has been edited with the new values

                    menuHelper.writeToCsv(MainApp.menuItems); // calls IO method to save the array into the CSV file
                    System.out.println("Edit successful. Target menu item edited!");
                    return;
                } catch (IOException e) {
                    System.out.println("IOException > " + e.getMessage());
                }
            }

        }

    }

    /**
     * Method to delete an existing item.
     * Uses {@link MenuItemCSVHelper#writeToCsv(ArrayList)} to facilitate I/O operations.
     */
    private void deleteMenuItem() {


        // logic for this would be
        // to do a search using menu item ID
        // we will decompose every single object
        // and then calling array.remove to remove the RestaurantItem object
        // this would change the id sequence
        // and then the write.csv method IO would be called


        int targetItemID = 1;

        //Code to print all menu items
        printHeader("All Menu Items");
        int k = 1;
        System.out.printf("%-5s %-50s %-10s %-6s %-9s\n", "ID", "Name", "Type", "Price", "Description");
        printBreaks();
        for (MenuItem mi : MainApp.menuItems) {
            System.out.printf("%-5s %-50s %-10s %-6s %-9s\n", mi.getId(), mi.getName(), mi.getType(), mi.getPrice(), mi.getDescription());
            k++;
        }

        boolean menuItemFound = false;

        while (!menuItemFound) {
            targetItemID = ScannerHelper.getIntegerInput("Enter the ID of the menu item to be deleted. Note: any promotions linked to this item will be deleted. \n");

            if (MenuItem.retrieveMenuItem(targetItemID) == null) System.out.println("Invalid ID. Please enter a valid menu item ID.");

            else menuItemFound = true;
        }

        MenuItemCSVHelper menuHelper = MenuItemCSVHelper.getInstance();

        String tempPromoName;

        for (int i = 0; i < (MainApp.menuItems.size()); i++) {

            MenuItem menuItemObj = MainApp.menuItems.get(i);

            if (targetItemID == menuItemObj.getId()) { //if we can find the target item

                try {

                    PromoCSVHelper promoHelper = PromoCSVHelper.getInstance(); //bring in promo helper class to do I/O

                    //problem here is the promotions size becomes smaller, and it prematurely exits the loop.

                    for (int j = 0; j < (MainApp.promotions.size()); j++) { //

                        PromotionItem promoItemObj = MainApp.promotions.get(j);

                        //if found
                        if (targetItemID == promoItemObj.getPromoMain() || targetItemID == promoItemObj.getPromoDessert() || targetItemID == promoItemObj.getPromoDrink()) {

                            tempPromoName = MainApp.promotions.get(j).getName();
                            MainApp.promotions.remove(j);

                            System.out.println("Associated Promotion '"+tempPromoName+"' Deleted.");
                        }
                        //else business as usual

                    }
                    promoHelper.writeToCsv(MainApp.promotions);

                    MainApp.menuItems.remove(i); //delete using i as for loop index
                    menuHelper.writeToCsv(MainApp.menuItems); // calls IO method to save the new array into the CSV file
                    System.out.println("Delete Successful. Target menu item deleted!");
                    return;
                } catch (IOException e) {
                    System.out.println("IOException > " + e.getMessage());
                }
            }

        }

    }

}
