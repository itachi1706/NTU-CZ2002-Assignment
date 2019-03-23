package sg.edu.ntu.scse.cz2002.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import sg.edu.ntu.scse.cz2002.objects.menuitem.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Deprecated
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

    @Deprecated
    public void writeToCsv(ArrayList<MenuItem> items) throws IOException {
        String[] header = {"ID", "Name", "Type", "Price", "Description" };
        CSVWriter csvWriter = new CSVWriter(FileIOHelper.getFileBufferedWriter(this.menuItemCsv));
        csvWriter.writeNext(header, false);
        items.forEach((i) -> csvWriter.writeNext(i.toCsv(), false));
        csvWriter.flush();
    }

    public ArrayList<MenuItem> readFromCsvNew() throws IOException {
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

    public void writeToCsvNew(ArrayList<MenuItem> items) throws IOException {
        String[] header = {"ID", "Name", "Type", "Price", "Description" };
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.menuItemCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        items.forEach((i) -> toWrite.add(i.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }


    // Test Method for custom CSV Handling
    private List<String[]> readAll(BufferedReader reader, int skip) {
        List<String> tmp = reader.lines().collect(Collectors.toList());
        while (skip > 0) {
            tmp.remove(0);
            skip--; // Skip said lines
        }
        List<String[]> result = new ArrayList<>();
        tmp.forEach((s) -> result.add(s.split("\\|\\|\\|")));
        return result;
    }

    private void writeToCsvFile(List<String[]> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(String.join("|||", s)));
        w.flush();
        w.close();
    }
}
