package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.objects.restaurantItem.MenuItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Menu Items
 *
 * @author Arthur Koh, Kenneth Soh
 * @version 1.1
 * @since 2019-03-19
 */
public class MenuItemCSVHelper extends CSVBaseHelper {

    /**
     * Path to Menu Items CSV File in the data folder. Defaults to menu.csv.
     */
    private String menuItemCsv = "menu.csv";

    /**
     * Singleton instance of this class.
     */
    private static MenuItemCSVHelper mInstance;

    /**
     * Default Constructor to initialize this class with menu.csv as the CSV file.
     */
    private MenuItemCSVHelper() {
    }

    /**
     * Gets the singleton instance of MenuItemCSVHelper that reads from menu.csv
     *
     * @return Instance of this class
     */
    public static MenuItemCSVHelper getInstance() {
        if (mInstance == null) mInstance = new MenuItemCSVHelper();
        return mInstance;
    }

    /**
     * Reads the CSV file and parses it into an array list of menu item objects.
     *
     * @return ArrayList of Menu Item Objects.
     * @throws IOException Unable to read from file.
     */
    public ArrayList<MenuItem> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.menuItemCsv)) return new ArrayList<>(); // Empty array list
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.menuItemCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<MenuItem> items = new ArrayList<>();
        if (csvLines.size() == 0) return items;
        csvLines.forEach((str) -> items.add(new MenuItem(str)));
        return items;
    }

    /**
     * Writes to the CSV File.
     *
     * @param items ArrayList of Menu items to save.
     * @throws IOException Unable to write to file.
     */
    public void writeToCsv(ArrayList<MenuItem> items) throws IOException {
        String[] header = {"ID", "Name", "Price", "Type", "Description"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.menuItemCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        items.forEach((i) -> toWrite.add(i.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }

}
