package sg.edu.ntu.scse.cz2002.objects.menuitem;
import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

/**
 * @author Arthur
 *
 */
public class MenuItem implements ICsvSerializable {



    protected int id;
    protected String name;
    protected int type;

    //use caps for enum just like final
    //remember that enums are actually classes e.g.
    //public num Class
    public enum MenuItemType {TBA, MAIN, DESSERT, DRINK} //these values actually correspond to 0,1,2,3
    protected String description;
    protected double price;


    private MenuItemType eType;


    /**
     * Constructor to pass in all required parameters for menu item.
     * @param id This menu items's ID.
     * @param name This menu item's name.
     * @param type This menu item's type.
     * @param description This menu item's description.
     * @param price This menu item's price.
     */
    public MenuItem(int id, String name, int type, String description, double price) {
        this.id = id;
        this.name = name;
        this.eType = convertToItemType(type);
        this.description = description;
        this.price = price;
    }

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public MenuItem(String[] csv) {
        this.id = Integer.parseInt(csv[0]);
        this.name = csv[1];

        this.eType = convertToItemType(Integer.parseInt(csv[2]));

        this.price = Double.parseDouble(csv[3]);
        this.description = csv[4];
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        String[] s = new String[5];
        s[0] = this.id + "";
        s[1] = this.name;

        //this is based on what enum it is, converts to the right
        s[2] =  this.eType == MenuItemType.MAIN      ? "1"    :
                this.eType == MenuItemType.DESSERT   ? "2" :
                this.eType ==  MenuItemType.DRINK    ? "3"   :
                                                        "0";

        s[3] = this.price + "";
        s[4] = this.description;
        return s;
    }

    /**
     * @return  Gets the menu item's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id Sets the menu item's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Gets the menu item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Sets the menu item's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Gets the menu item's type.
     */
    public MenuItemType getType() {
        return eType;
    } //my getters return MenuItemType


    /**
     * @param type Sets the menu item's type.
     */
    public void setType(int type) {
        this.eType = convertToItemType(type);
    } //my setters can be integer

    // Compartmentalized method that takes in integer and converts
    public MenuItemType convertToItemType(int type) {
        return  type == 1   ? MenuItemType.MAIN     :
                type == 2   ? MenuItemType.DESSERT  :
                        type == 3   ? MenuItemType.DRINK    :
                                MenuItemType.TBA;
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
     * @return Gets the menu item's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price Sets the menu item's price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Prints details regarding this item
     * This is formatted to fit a console table of size 60
     * @return Parsed string of the Menu Item
     */
    public String printItemDetail() {
        return "Name: " + this.getName() + "\n" +
                "Description: " + this.getDescription() + "\n" +
                "Price: $" + String.format("%.2f", this.getPrice());
    }
}
