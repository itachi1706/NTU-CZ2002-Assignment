package sg.edu.ntu.scse.cz2002.objects.restaurantItem;

import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Menu Item Class
 *
 * @author Arthur Koh
 * @version 1.0
 * @since 2019-04-17
 */
public class MenuItem extends RestaurantItem implements ICsvSerializable {

    /**
     * The possible types of the menu item, whether it is a main, a dessert or a drink.
     */
    public enum MenuItemType {ALL, MAIN, DESSERT, DRINK} //these values actually correspond to 0,1,2,3



    /**
     * The type of the menu item.
     */
    private MenuItemType eType;
    /**
     * The description of the menu item.
     */
    protected String description;



    /**
     * Constructor to pass in all required parameters for menu item.
     *
     * @param id          Menu item's ID.
     * @param name        Menu item's name.
     * @param type        Menu item's type.
     * @param description Menu item's description.
     * @param price       Menu item's price.
     */
    public MenuItem(int id, String name, int type, String description, double price) {

        super(id, name, price); //called from parent

        this.eType = convertToItemType(type);
        this.description = description;
    }



    /**
     * A method to read from a CSV string to convert to an object instance.
     * This needs to be overridden if CSV data must be retrieved from a file.
     *
     * @param csv A string array of the CSV file.
     */
    public MenuItem(String[] csv) {
        super(csv);
        this.eType = convertToItemType(Integer.parseInt(csv[3]));
        this.description = csv[4];
    }

    /**
     * A method to convert to CSV.
     * This needs to be overridden if files need to be saved to CSV.
     *
     * @return A String array of the CSV file.
     */
    @Override
    public String[] toCsv() {
        ArrayList<String> stuff = new ArrayList<>();
        Collections.addAll(stuff, super.toCsv()); //these are the details called from the super class
        stuff.add(  this.eType == MenuItemType.MAIN     ?   "1" :
                this.eType == MenuItemType.DESSERT  ?   "2" :
                        this.eType == MenuItemType.DRINK    ?   "3" :
                                "0"); //this is for the 5th column
        stuff.add(  this.description); //this is for the 6th column
        return stuff.toArray(new String[0]);
    }



    /**
     * Accessor for Menu Item type.
     *
     * @return Gets the menu item's type.
     */
    public MenuItemType getType() {
        return eType;
    }

    /**
     * Mutator for Menu Item ID.
     *
     * @param type Sets the menu item's type.
     */
    public void setType(int type) {
        this.eType = convertToItemType(type);
    }

    /**
     * Accessor for Menu Item description.
     *
     * @return Gets the menu item's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Mutator for Menu Item description.
     *
     * @param description Sets the menu item's description.
     */
    public void setDescription(String description) {
        this.description = description;
    }



    /**
     * Method that takes in integer of menu type to convert into its corresponding enum type equivalent.
     *
     * @param type The menu item's type, in integer form.
     * @return The menu item's type in its enum equivalent form.
     */
    public MenuItemType convertToItemType(int type) {
        return  type == 1 ? MenuItemType.MAIN :
                type == 2 ? MenuItemType.DESSERT :
                        type == 3 ? MenuItemType.DRINK :
                                MenuItemType.ALL;
    }

    /**
     * Prints a menu item's details.
     * Formatted to fit a console table of size 60.
     *
     * @return Parsed string of the Menu Item.
     */
    public String printItemDetail() {
        return "Name: " + super.getName() + "\n" + //correct implementation of calling super, instead of this.getName()
                "Description: " + this.getDescription() + "\n" + //getDescription is unique to this class
                "Price: $" + String.format("%.2f", super.getPrice());
    }

    /**
     * Returns true if the filtered array list contains the menu item type.
     * @param menuArrayList filtered array list that contains menu items based on enum specified
     * @param newPromoItemType type of the menu item added to the promotion
     * @param textParameter text for the menu item type
     */
    public static boolean menuTypeChecker(ArrayList<MenuItem> menuArrayList, int newPromoItemType, String textParameter){

        boolean found = false;
        for (int i=0; i<menuArrayList.size(); i++){

            MenuItem menuItemObj = menuArrayList.get(i);
            if (newPromoItemType == menuItemObj.getId()) { //"Target menu item found."
                found = true;
                return true;
            }

        }
        //implied else
        System.out.println("No "+textParameter+" exists with this ID.");
        return false;
    }



    /**
     * Method returning a MenuItem object that matches the input targetItemID.
     * Uses {@link MainApp#menuItems} to retrieval operations.
     *
     * @param targetItemID ID of the menu item object to be retrieved.
     * @return menuItemObj Object containing menu item attributes.
     */
    @Nullable
    public static MenuItem retrieveMenuItem(int targetItemID) {

        for (int i = 0; i < (MainApp.menuItems.size()); i++) {

            MenuItem menuItemObj = MainApp.menuItems.get(i);

            if (targetItemID == menuItemObj.getId()) { //"Target menu item found."
                return menuItemObj;
            }

        }

        return null; //"Target menu item not found."
    }

    /**
     * Method returning an ArrayList filtered by enum type.
     * Uses {@link MainApp#menuItems} to retrieval operations.
     *
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

        for (int i = 0; i < (MainApp.menuItems.size()); i++) { //for loop to run through menuitems and to filter out

            MenuItem menuItemObj = MainApp.menuItems.get(i); //gets a menu item object while the loop is running

            //need to change to enum
            if (targetItemType == menuItemObj.getType()) { //"Menu item of target item types found."
                menuItemsFiltered.add(menuItemObj); //add the found object into the filtered array list
            }

        }
        return menuItemsFiltered;
    }
}
