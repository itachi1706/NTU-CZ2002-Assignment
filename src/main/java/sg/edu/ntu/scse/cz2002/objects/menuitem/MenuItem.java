package sg.edu.ntu.scse.cz2002.objects.menuitem;

/**
 * @author Arthur
 *
 */
public class MenuItem {

    protected int id;
    protected String name;
    protected String type;
    protected String description;
    protected double price;

    /**
     * Default constructor for menu item.
     */
    public MenuItem() {
    }

    
    /**
     * Constructor to pass in all required parameters for menu item.
     * @param id This menu items's ID.
     * @param name This menu item's name.
     * @param type This menu item's type.
     * @param description This menu item's description.
     * @param price This menu item's price.
     */
    public MenuItem(int id, String name, String type, String description, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
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
        this.type = csv[2];
        this.price = Double.parseDouble(csv[3]);
        this.description = csv[4];
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    public String[] toCsv() {
        String[] s = new String[5];
        s[0] = this.id + "";
        s[1] = this.name;
        s[2] = this.type;
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
    public String getType() {
        return type;
    }

    /**
     * @param type Sets the menu item's type.
     */
    public void setType(String type) {
        this.type = type;
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
}
