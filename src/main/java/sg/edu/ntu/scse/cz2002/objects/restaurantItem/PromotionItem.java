package sg.edu.ntu.scse.cz2002.objects.restaurantItem;

import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;

import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Promotion Item Class
 *
 * @author Arthur Koh, Kenneth Soh
 * @version 1.0
 * @since 2019-04-17
 */
public class PromotionItem extends RestaurantItem implements ICsvSerializable {

    /**
     * The ID of the promotion item's main.
     */
    private int promoMain;
    /**
     * The ID of the promotion item's dessert.
     */
    private int promoDessert;
    /**
     * The ID of the promotion item's drink.
     */
    private int promoDrink;


    /**
     * Constructor to pass in all attributes
     *
     * @param promoID      This promotion item's ID.
     * @param promoName    This promotion item's name.
     * @param promoPrice   This promotion item's price.
     * @param promoMain    This promotion item's main ID.
     * @param promoDessert This promotion item's dessert ID.
     * @param promoDrink   This promotion item's drink ID.
     */
    public PromotionItem(int promoID, String promoName, double promoPrice, int promoMain, int promoDessert, int promoDrink) {

        super(promoID, promoName, promoPrice); //called from parent

        this.promoMain = promoMain;
        this.promoDessert = promoDessert;
        this.promoDrink = promoDrink;
    }

    /**
     * A method to read from a CSV string to convert to an object instance.
     * This needs to be overridden if CSV data must be retrieved from a file.
     *
     * @param csv A string array of the CSV file.
     */
    public PromotionItem(String[] csv) {

        super(csv);

        this.promoMain = Integer.parseInt(csv[3]);
        this.promoDessert = Integer.parseInt(csv[4]);
        this.promoDrink = Integer.parseInt(csv[5]);
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
        stuff.add(this.promoMain + ""); //note how we +"" to cast to string; this is for the 5th column
        stuff.add(this.promoDessert + ""); //this is for the 6th column
        stuff.add(this.promoDrink + ""); //this is for the 7th column
        return stuff.toArray(new String[0]);
    }


    /**
     * Accessor for Promotion item's main.
     *
     * @return Gets the Promotion item's main.
     */
    public int getPromoMain() {
        return promoMain;
    }

    /**
     * Mutator for Promotion item's main.
     *
     * @param promoMain Sets the Promotion item's main.
     */
    public void setPromoMain(int promoMain) {
        this.promoMain = promoMain;
    }

    /**
     * Accessor for Promotion item's dessert.
     *
     * @return Gets the Promotion item's dessert.
     */
    public int getPromoDessert() {
        return promoDessert;
    }

    /**
     * Mutator for Promotion item's main.
     *
     * @param promoDessert Sets the Promotion item's dessert.
     */
    public void setPromoDessert(int promoDessert) {
        this.promoDessert = promoDessert;
    }

    /**
     * Accessor for Promotion item's drink.
     *
     * @return Gets the Promotion item's drink.
     */
    public int getPromoDrink() {
        return promoDrink;
    }

    /**
     * Mutator for Promotion item's drink.
     *
     * @param promoDrink Sets the Promotion item's drink.
     */
    public void setPromoDrink(int promoDrink) {
        this.promoDrink = promoDrink;
    }


    /**
     * Prints details of this promotion item.
     * Formatted to fit a console table of size 60.
     *
     * @return Parsed string of the PromotionItem Set.
     */
    public String printPromotionDetail() {
        StringBuilder sb = new StringBuilder();
        MenuItem main = MenuItem.retrieveMenuItem(this.getPromoMain());
        MenuItem drink = MenuItem.retrieveMenuItem(this.getPromoDrink());
        MenuItem dessert = MenuItem.retrieveMenuItem(this.getPromoDessert());
        sb.append("Name: ").append(super.getName()).append("\n")
                .append("Price: $").append(String.format("%.2f", super.getPrice())).append("\n\n")
                .append("Set Contains:").append("\n");
        if (main != null)
            sb.append(String.format("%-52s $%-6.2f", main.getName() + " (" + main.getDescription() + ") ", main.getPrice())).append("\n");
        if (drink != null)
            sb.append(String.format("%-52s $%-6.2f", drink.getName() + " (" + drink.getDescription() + ") ", drink.getPrice())).append("\n");
        if (dessert != null)
            sb.append(String.format("%-52s $%-6.2f", dessert.getName() + " (" + dessert.getDescription() + ") ", dessert.getPrice())).append("\n");
        return sb.toString();
    }


    /**
     * Returns a PromotionItem object that matches the input targetPromoID.
     *
     * @param targetPromoID ID of the promotion object to be retrieved.
     * @return promoObj Object containing a promotion item's attributes.
     */
    @Nullable
    public static PromotionItem retrievePromotion(int targetPromoID) {
        for (int i = 0; i < (MainApp.promotions.size()); i++) {
            PromotionItem promoObj = MainApp.promotions.get(i);
            if (targetPromoID == promoObj.getId()) {
                //System.out.println("Target promotion found.");
                return promoObj;
            }
        }
        return null; //"Target promotion not found."
    }

}
