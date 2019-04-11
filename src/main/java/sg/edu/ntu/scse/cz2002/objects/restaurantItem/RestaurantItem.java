package sg.edu.ntu.scse.cz2002.objects.restaurantItem;

import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

public class RestaurantItem implements ICsvSerializable {

    protected int restaurantItemID;
    protected String restaurantItemName;
    protected double restaurantItemPrice;

    /**
     * Constructor to pass in all attributes
     * @param restaurantItemID This restaurant item's ID.
     * @param restaurantItemName This restaurant item's name.
     * @param restaurantItemPrice This restaurantItemPrice price.
     */
    public RestaurantItem(int restaurantItemID, String restaurantItemName, double restaurantItemPrice) {
        this.restaurantItemID = restaurantItemID;
        this.restaurantItemName = restaurantItemName;
        this.restaurantItemPrice = restaurantItemPrice;
    }

    /**
     * A method to READ from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public RestaurantItem(String[] csv) {
        this.restaurantItemID = Integer.parseInt(csv[0]);
        this.restaurantItemName = csv[1];
        this.restaurantItemPrice = Double.parseDouble(csv[2]);
    }

    /**
     * A method to CONVERT to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        String[] s = new String[3]; //6 columns of data
        s[0] = this.restaurantItemID + ""; //need to add + "" for numerics
        s[1] = this.restaurantItemName;
        s[2] = this.restaurantItemPrice + "";
        return s;
    }

    /**
     * @return  Gets the menu item's ID.
     */
    public int getId() {
        return restaurantItemID;
    }

    public void setId(int restaurantItemID) {
        this.restaurantItemID = restaurantItemID;
    }

    public String getName() {
        return restaurantItemName;
    }

    public void setName(String restaurantItemName) {
        this.restaurantItemName = restaurantItemName;
    }

    public double getPrice() {
        return restaurantItemPrice;
    }

    public void setPrice(double restaurantItemPrice) {
        this.restaurantItemPrice = restaurantItemPrice;
    }

}
