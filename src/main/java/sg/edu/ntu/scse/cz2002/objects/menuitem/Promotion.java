package sg.edu.ntu.scse.cz2002.objects.menuitem;

import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

public class Promotion implements ICsvSerializable {

    protected int promoID;
    protected String promoName;
    protected double promoPrice;
    protected int promoMain;
    protected int promoDessert;
    protected int promoDrink;
	
    /**
     * Default constructor
     */
    public Promotion() {}

    /**
     * Constructor to pass in all attributes
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
    @Override
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

    ////////////////////////////////
    // Accessor & Mutator Methods // 
    ////////////////////////////////
	public int getPromoID() {
		return promoID;
	}

	public void setPromoID(int promoID) {
		this.promoID = promoID;
	}

	public String getPromoName() {
		return promoName;
	}

	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	public double getPromoPrice() {
		return promoPrice;
	}

	public void setPromoPrice(double promoPrice) {
		this.promoPrice = promoPrice;
	}

	public int getPromoMain() {
		return promoMain;
	}

	public void setPromoMain(int promoMain) {
		this.promoMain = promoMain;
	}

	public int getPromoDessert() {
		return promoDessert;
	}

	public void setPromoDessert(int promoDessert) {
		this.promoDessert = promoDessert;
	}

	public int getPromoDrink() {
		return promoDrink;
	}

	public void setPromoDrink(int promoDrink) {
		this.promoDrink = promoDrink;
	}
    
    
}
