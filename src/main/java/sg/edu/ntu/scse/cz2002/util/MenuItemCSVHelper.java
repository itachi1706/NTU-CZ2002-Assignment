package sg.edu.ntu.scse.cz2002.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import sg.edu.ntu.scse.cz2002.objects.menuitem.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Menu Items
 *
 * @author Kenneth
 * @version 1.0
 * @since 2019-03-19
 */
public class MenuItemCSVHelper {

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
     * @throws IOException
     */
    public ArrayList<MenuItem> readFromCsv() throws IOException {
        CSVReader csvReader = new CSVReaderBuilder(FileIOHelper.getFileBufferedReader(this.menuItemCsv)).withSkipLines(1).build(); // Skip Header Line
        List<String[]> csvLines = csvReader.readAll();
        csvReader.close();
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

    public void writeToCsv(ArrayList<MenuItem> items) throws IOException {
        String[] header = {"ID", "Name", "Type", "Price", "Description" };
        CSVWriter csvWriter = new CSVWriter(FileIOHelper.getFileBufferedWriter(this.menuItemCsv));
        csvWriter.writeNext(header, false);
        items.forEach((i) -> csvWriter.writeNext(i.toCsv(), false));
        csvWriter.flush();
    }
}
