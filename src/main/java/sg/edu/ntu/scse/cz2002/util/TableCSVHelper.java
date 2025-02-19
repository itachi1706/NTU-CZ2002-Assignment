package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.features.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Table
 *
 * @author Francis Lim
 * @version 1.0
 * @since 2019-04-01
 */

public class TableCSVHelper extends CSVBaseHelper {

    /**
     * Path to Menu Items CSV File in the data folder. Defaults to reservation.csv
     */
    private String tableCsv = "table.csv";

    /**
     * Singleton instance of this class
     */
    private static TableCSVHelper mInstance;

    /**
     * Default Constructor to initialize this class with reservation.csv as the CSV file
     */
    private TableCSVHelper() {
    }

    /**
     * Gets the singleton instance of MenuItemCSVHelper that reads from menu.csv
     *
     * @return Instance of this class
     */
    public static TableCSVHelper getInstance() {
        if (mInstance == null) mInstance = new TableCSVHelper();
        return mInstance;
    }

    /**
     * Reads the CSV file and parse it into an array list of menu item objects
     *
     * @return ArrayList of Menu Item Objects
     * @throws IOException Unable to read from file
     */
    public ArrayList<Table> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.tableCsv)) return new ArrayList<>(); // Empty array list
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.tableCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Table> tables = new ArrayList<>();
        if (csvLines.size() == 0) return tables;
        for (String[] str : csvLines) {
            Table t = new Table(str); // Create based on type
            tables.add(t);
        }
        return tables;
    }
}
