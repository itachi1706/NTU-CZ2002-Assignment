package sg.edu.ntu.scse.cz2002.objects.restaurantItem;
import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Arthur
 *
 */
public class MenuItem extends RestaurantItem implements ICsvSerializable {

    public enum MenuItemType {ALL, MAIN, DESSERT, DRINK} //these values actually correspond to 0,1,2,3

    private MenuItemType eType;
    protected String description;


    /**
     * Constructor to pass in all required parameters for menu item.
     * @param id This menu items's ID.
     * @param name This menu item's name.
     * @param type This menu item's type.
     * @param description This menu item's description.
     * @param price This menu item's price.
     */
    public MenuItem(int id, String name, int type, String description, double price) {

        super(id, name, price); //called from parent

        this.eType = convertToItemType(type);
        this.description = description;
    }

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public MenuItem(String[] csv) {
        super(csv);
        this.eType = convertToItemType(Integer.parseInt(csv[3]));
        this.description = csv[4];
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        ArrayList<String> stuff = new ArrayList<>();
        Collections.addAll(stuff, super.toCsv()); //these are the details called from the super class
        stuff.add(this.eType == MenuItemType.MAIN      ? "1"    :
                this.eType == MenuItemType.DESSERT   ? "2" :
                        this.eType ==  MenuItemType.DRINK    ? "3"   :
                                "0"); //this is for the 5th column
        stuff.add(this.description); //this is for the 6th column
        return stuff.toArray(new String[0]);
    }


    /**
     * @return Gets the menu item's type. 
     */
    public MenuItemType getType() {
        return eType;
    }

    /**
     * @param type Sets the menu item's type.
     */
    public void setType(int type) {
        this.eType = convertToItemType(type);
    }

    // Compartmentalized method that takes in integer and converts
    public MenuItemType convertToItemType(int type) {
        return  type == 1   ? MenuItemType.MAIN     :
                type == 2   ? MenuItemType.DESSERT  :
                        type == 3   ? MenuItemType.DRINK    :
                                MenuItemType.ALL;
    }

    /**
     * @return Gets the menu item's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description Sets the menu item's description.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Prints details regarding this item
     * This is formatted to fit a console table of size 60
     * @return Parsed string of the Menu Item
     */
    public String printItemDetail() {
        return "Name: " + super.getName() + "\n" + //correct implementation of calling super, instead of this.getName()
                "Description: " + this.getDescription() + "\n" +
                "Price: $" + String.format("%.2f", super.getPrice());
    }
}
