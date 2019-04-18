package sg.edu.ntu.scse.cz2002.objects.restaurantItem;

import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

/**
 * Restaurant Item Class
 *
 * @author Arthur Koh, Kenneth Soh
 * @version 1.0
 * @since 2019-04-17
 */
public class RestaurantItem implements ICsvSerializable {

    /**
     * The ID of the restaurant item.
     */
    protected int restaurantItemID;
    /**
     * The name of the restaurant item.
     */
    protected String restaurantItemName;
    /**
     * The price of the restaurant item.
     */
    protected double restaurantItemPrice;


    /**
     * Constructor to pass in all required parameters for a restaurant item.
     *
     * @param restaurantItemID    This restaurant item's ID.
     * @param restaurantItemName  This restaurant item's name.
     * @param restaurantItemPrice This restaurant item's price.
     */
    public RestaurantItem(int restaurantItemID, String restaurantItemName, double restaurantItemPrice) {
        this.restaurantItemID = restaurantItemID;
        this.restaurantItemName = restaurantItemName;
        this.restaurantItemPrice = restaurantItemPrice;
    }

    /**
     * A method to read from a CSV string to convert to an object instance.
     * This needs to be overridden if CSV data must be retrieved from a file.
     *
     * @param csv A string array of the CSV file.
     */
    public RestaurantItem(String[] csv) {
        this.restaurantItemID = Integer.parseInt(csv[0]);
        this.restaurantItemName = csv[1];
        this.restaurantItemPrice = Double.parseDouble(csv[2]);
    }

    /**
     * A method to convert to CSV.
     * This needs to be overridden if files need to be saved to CSV.
     *
     * @return A String array of the CSV file.
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
     * Accessor for Restaurant Item type.
     *
     * @return Gets the restaurant item's ID.
     */
    public int getId() {
        return restaurantItemID;
    }

    /**
     * Mutator for Restaurant Item ID.
     *
     * @param restaurantItemID Sets the restaurant item's ID.
     */
    public void setId(int restaurantItemID) {
        this.restaurantItemID = restaurantItemID;
    }

    /**
     * Accessor for Restaurant Item name.
     *
     * @return Gets the restaurant item's name.
     */
    public String getName() {
        return restaurantItemName;
    }

    /**
     * Mutator for Restaurant Item name.
     *
     * @param restaurantItemName Sets the restaurant item's name.
     */
    public void setName(String restaurantItemName) {
        this.restaurantItemName = restaurantItemName;
    }

    /**
     * Accessor for Restaurant Item price.
     *
     * @return Gets the restaurant item's price.
     */
    public double getPrice() {
        return restaurantItemPrice;
    }

    /**
     * Mutator for Restaurant Item price.
     *
     * @param restaurantItemPrice Sets the restaurant item's price.
     */
    public void setPrice(double restaurantItemPrice) {
        this.restaurantItemPrice = restaurantItemPrice;
    }

}
