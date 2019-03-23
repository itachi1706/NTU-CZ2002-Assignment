package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.objects.menuitem.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Menu Items
 *
 * @author Kenneth
 * @version 1.1
 * @since 2019-03-19
 */
public class MenuItemCSVHelper extends CSVBaseHelper {

    /**
     * Path to Menu Items CSV File in the data folder
     */
    private String menuItemCsv;

    /**
     * Initialize the Helper object
     * @param filename Path to MenuItems CSV File
     */
    public MenuItemCSVHelper(String filename) {
        this.menuItemCsv = filename;
    }

    /**
     * Reads the CSV file and parse it into an array list of menu item objects
     * @return ArrayList of Menu Item Objects
     * @throws IOException Unable to read from file
     */
    public ArrayList<MenuItem> readFromCsv() throws IOException {
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.menuItemCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<MenuItem> items = new ArrayList<>();
        if (csvLines.size() == 0) return items;
        csvLines.forEach((str) -> {
            MenuItem item; // Create based on type
            switch (str[2].toLowerCase()) {
                case "main": item = new MainCourse(str); break;
                case "dessert": item = new Dessert(str); break;
                case "drink": item = new Drink(str); break;
                case "appetizer": item = new Appetizer(str); break;
                default: item = new MenuItem(str); break;
            }
            items.add(item); });
        return items;
    }

    /**
     * Writes to the CSV File
     * @param items ArrayList of items to save
     * @throws IOException Unable to write to file
     */
    public void writeToCsv(ArrayList<MenuItem> items) throws IOException {
        String[] header = {"ID", "Name", "Type", "Price", "Description" };
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.menuItemCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        items.forEach((i) -> toWrite.add(i.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
