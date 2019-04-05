package sg.edu.ntu.scse.cz2002.objects.menuitem;

/**
 * @author Arthur
 *
 */
public class Promotion {

    protected int promoID;
    protected String promoName;
    protected double promoPrice;
    protected int promoMain;
    protected int promoDessert;
    protected int promoDrink;
	
    /**
     * Default constructor
     */
    public Promotion() {
    }

    /**
     * Constructor to pass in all attributes
     * @param promoID This promotion's ID.
     * @param promoName This promotion's name.
     * @param promoPrice This promotion's price.
     * @param promoMain This promotion's main.
     * @param promoDessert This promotion's drink.
     * @param promoDrink This promotion's drink.
     */
	public Promotion(int promoID, String promoName, double promoPrice, int promoMain, int promoDessert, int promoDrink) {
		this.promoID = promoID;
		this.promoName = promoName;
		this.promoPrice = promoPrice;
		this.promoMain = promoMain;
		this.promoDessert = promoDessert;
		this.promoDrink = promoDrink;
	}
    
    /**
     * A method to READ from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public Promotion(String[] csv) {
        this.promoID = Integer.parseInt(csv[0]); //each array index corrsponders to a column
        this.promoName = csv[1];
        this.promoPrice = Double.parseDouble(csv[2]);
        this.promoMain = Integer.parseInt(csv[3]);
        this.promoDessert = Integer.parseInt(csv[4]);
        this.promoDrink = Integer.parseInt(csv[5]);
    }

    /**
     * A method to CONVERT to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    public String[] toCsv() {
        String[] s = new String[6]; //6 columns of data
        s[0] = this.promoID + ""; //need to add + "" for numerics
        s[1] = this.promoName;
        s[2] = this.promoPrice + "";
        s[3] = this.promoMain + "";
        s[4] = this.promoDessert + "";
        s[5] = this.promoDrink + "";
        return s;
    }


	/**
	 * @return Gets this promotion's ID.
	 */
	public int getPromoID() {
		return promoID;
	}

	/**
	 * @param promoID Sets this promotion's ID.
	 */
	public void setPromoID(int promoID) {
		this.promoID = promoID;
	}

	/**
	 * @return Gets this promotion's name.
	 */
	public String getPromoName() {
		return promoName;
	}

	/**
	 * @param promoName Sets this promotion's name.
	 */
	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	/**
	 * @return Gets this promotion's price.
	 */
	public double getPromoPrice() {
		return promoPrice;
	}

	/**
	 * @param promoPrice Sets this promotion's price.
	 */
	public void setPromoPrice(double promoPrice) {
		this.promoPrice = promoPrice;
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
    
    
}
