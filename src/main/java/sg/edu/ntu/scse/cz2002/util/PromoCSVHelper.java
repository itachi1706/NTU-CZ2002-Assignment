package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.objects.menuitem.Promotion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Promotions
 *
 * @author Arthur Koh, Kenneth Soh
 * @version 1.1
 * @since 2019-03-19
 */

public class PromoCSVHelper extends CSVBaseHelper {

	/**
	 * Path to Promotion CSV File in the data folder. Defaults to promotion.csv
	 */
	private String promotionCsv = "promotion.csv";
	
    /**
     * Singleton instance of this class
     * (used for "instanceof" which is better than creating a new object constantly
     */
    private static PromoCSVHelper pInstance;
    
    /**
     * Default Constructor to initialize this class with promotion.csv as the CSV file
     */
    private PromoCSVHelper() {}
    
    /**
     * Gets the singleton instance of PromotionCSVHelper that reads from promotion.csv
     * (if instance does not exist, declares a new instance)
     * (else, just returns existing instance)
     * @return Instance of this class
     */
    public static PromoCSVHelper getInstance() {
        if (pInstance == null) pInstance = new PromoCSVHelper();
        return pInstance;
    }
    
    /**
     * Reads the CSV file and parse it into an array list of promotion objects
     * @return ArrayList of Promotion Objects
     * @throws IOException Unable to read from file
     */
    public ArrayList<Promotion> readFromCsv() throws IOException {
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.promotionCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Promotion> promotions = new ArrayList<>();
        if (csvLines.size() == 0) return promotions;
        csvLines.forEach((str) -> {
        	Promotion promotion = new Promotion(str); //Create based on type
            promotions.add(promotion); });
        return promotions;
    }

    /**
     * Writes to the CSV File
     * @param promotions ArrayList of items to save
     * @throws IOException Unable to write to file
     */
    public void writeToCsv(ArrayList<Promotion> promotions) throws IOException {
        String[] header = {"PromoID", "PromoName", "PromoPrice", "PromoMain", "PromoDessert", "PromoDrink"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.promotionCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        promotions.forEach((i) -> toWrite.add(i.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
