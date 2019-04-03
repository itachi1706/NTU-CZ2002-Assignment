package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.features.Order;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Menu Items
 *
 * @author Kenneth Soh
 * @version 1.1
 * @since 2019-03-19
 */
@SuppressWarnings("Duplicates")
public class OrderCSVHelper extends CSVBaseHelper {

    /**
     * Path to Menu Items CSV File in the data folder. Defaults to menu.csv
     */
    private String orderCsv = "orders.csv";

    /**
     * Singleton instance of this class
     */
    private static OrderCSVHelper mInstance;

    /**
     * Default Constructor to initialize this class with menu.csv as the CSV file
     */
    private OrderCSVHelper() {}

    /**
     * Initialize the Helper object
     * @deprecated Call {@link OrderCSVHelper#getInstance()} instead
     * @param filename Path to MenuItems CSV File
     */
    @Deprecated
    public OrderCSVHelper(String filename) {
        this.orderCsv = filename;
    }

    /**
     * Gets the singleton instance of MenuItemCSVHelper that reads from menu.csv
     * @return Instance of this class
     */
    public static OrderCSVHelper getInstance() {
        if (mInstance == null) mInstance = new OrderCSVHelper();
        return mInstance;
    }

    /**
     * Reads the CSV file and parse it into an array list of menu item objects
     * @return ArrayList of Menu Item Objects
     * @throws IOException Unable to read from file
     */
    public ArrayList<Order> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.orderCsv)) return new ArrayList<>(); // Empty array list
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.orderCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Order> items = new ArrayList<>();
        if (csvLines.size() == 0) return items;
        csvLines.forEach((str) -> items.add(new Order(str)));
        return items;
    }

    /**
     * Writes to the CSV File
     * @param items ArrayList of items to save
     * @throws IOException Unable to write to file
     */
    public void writeToCsv(ArrayList<Order> items) throws IOException {
        String[] header = {"ID", "Items", "Subtotal", "State", "CreatedAt", "CompletedAt"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.orderCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        items.forEach((i) -> toWrite.add(i.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
