package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.features.Invoice;

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
public class InvoiceCSVHelper extends CSVBaseHelper {

    /**
     * Path to Menu Items CSV File in the data folder. Defaults to menu.csv
     */
    private String orderCsv = "orders.csv";

    /**
     * Singleton instance of this class
     */
    private static InvoiceCSVHelper mInstance;

    /**
     * Default Constructor to initialize this class with menu.csv as the CSV file
     */
    private InvoiceCSVHelper() {
    }

    /**
     * Gets the singleton instance of MenuItemCSVHelper that reads from menu.csv
     *
     * @return Instance of this class
     */
    public static InvoiceCSVHelper getInstance() {
        if (mInstance == null) mInstance = new InvoiceCSVHelper();
        return mInstance;
    }

    /**
     * Reads the CSV file and parse it into an array list of menu item objects
     *
     * @return ArrayList of Menu Item Objects
     * @throws IOException Unable to read from file
     */
    public ArrayList<Invoice> readFromCsv() throws IOException {
        if (!FileIOHelper.exists(this.orderCsv)) return new ArrayList<>(); // Empty array list
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.orderCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Invoice> items = new ArrayList<>();
        if (csvLines.size() == 0) return items;
        csvLines.forEach((str) -> items.add(new Invoice(str)));
        return items;
    }

    /**
     * Writes to the CSV File
     *
     * @param items ArrayList of items to save
     * @throws IOException Unable to write to file
     */
    public void writeToCsv(ArrayList<Invoice> items) throws IOException {
        String[] header = {"ID", "Items", "Subtotal", "State", "CreatedAt", "CompletedAt", "StaffID", "TableID", "Total", "AmountPaid", "PaymentType", "Receipt"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.orderCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        items.forEach((i) -> toWrite.add(i.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
