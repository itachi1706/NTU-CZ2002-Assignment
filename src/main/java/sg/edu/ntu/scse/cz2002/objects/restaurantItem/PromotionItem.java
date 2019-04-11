package sg.edu.ntu.scse.cz2002.objects.restaurantItem;
import sg.edu.ntu.scse.cz2002.ui.FoodMenuUI;
import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class handling PromotionItem Sets
 *
 * @author Arthur, Kenneth
 * @version 1.1
 * @since 2019-04-03
 *
 */
public class PromotionItem extends RestaurantItem implements ICsvSerializable {

    protected int promoMain;
    protected int promoDessert;
    protected int promoDrink;


    /**
     * Constructor to pass in all attributes
     * @param promoID This promotion's ID.
     * @param promoName This promotion's name.
     * @param promoPrice This promotion's price.
     * @param promoMain This promotion's main.
     * @param promoDessert This promotion's drink.
     * @param promoDrink This promotion's drink.
     */
	public PromotionItem(int promoID, String promoName, double promoPrice, int promoMain, int promoDessert, int promoDrink) {

		super(promoID, promoName, promoPrice); //called from parent

		this.promoMain = promoMain;
		this.promoDessert = promoDessert;
		this.promoDrink = promoDrink;
	}
    
    /**
     * A method to READ from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public PromotionItem(String[] csv) {

    	super(csv);

        this.promoMain = Integer.parseInt(csv[3]);
        this.promoDessert = Integer.parseInt(csv[4]);
        this.promoDrink = Integer.parseInt(csv[5]);
    }

    /**
     * A method to CONVERT to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
		ArrayList<String> stuff = new ArrayList<>();
		Collections.addAll(stuff, super.toCsv()); //these are the details called from the super class
		stuff.add(this.promoMain+""); //note how we +"" to cast to string; this is for the 5th column
		stuff.add(this.promoDessert+""); //this is for the 6th column
		stuff.add(this.promoDrink+""); //this is for the 7th column
		return stuff.toArray(new String[0]);
    }


	/**
	 * @return Gets this promotion's main.
	 */
	public int getPromoMain() {
		return promoMain;
	}

	/**
	 * @param promoMain Sets this promotion's main.
	 */
	public void setPromoMain(int promoMain) {
		this.promoMain = promoMain;
	}

	/**
	 * @return Gets this promotion's dessert.
	 */
	public int getPromoDessert() {
		return promoDessert;
	}

	/**
	 * @param promoDessert Sets this promotion's dessert.
	 */
	public void setPromoDessert(int promoDessert) {
		this.promoDessert = promoDessert;
	}

	/**
	 * @return Gets this promotion's drink.
	 */
	public int getPromoDrink() {
		return promoDrink;
	}

	/**
	 * @param promoDrink Sets this promotion's drink.
	 */
	public void setPromoDrink(int promoDrink) {
		this.promoDrink = promoDrink;
	}

	/**
	 * Prints details regarding this promotion set
	 * This is formatted to fit a console table of size 60
	 * @return Parsed string of the PromotionItem Set
	 */
	public String printPromotionDetail() {
		StringBuilder sb = new StringBuilder();
		MenuItem main = FoodMenuUI.retrieveMenuItem(this.getPromoMain());
		MenuItem drink = FoodMenuUI.retrieveMenuItem(this.getPromoDrink());
		MenuItem dessert = FoodMenuUI.retrieveMenuItem(this.getPromoDessert());
		sb.append("Name: ").append(super.getName()).append("\n")
				.append("Price: $").append(String.format("%.2f", super.getPrice())).append("\n\n")
				.append("Set Contains:").append("\n");
		if (main != null) sb.append(String.format("%-52s $%-6.2f", main.getName() + " (" + main.getDescription() + ") ", main.getPrice())).append("\n");
		if (drink != null) sb.append(String.format("%-52s $%-6.2f", drink.getName() + " (" + drink.getDescription() + ") ", drink.getPrice())).append("\n");
		if (dessert != null) sb.append(String.format("%-52s $%-6.2f", dessert.getName() + " (" + dessert.getDescription() + ") ", dessert.getPrice())).append("\n");
		return sb.toString();
	}
}
