package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.objects.person.Staff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Staff
 *
 * @author Weibin
 * @version 1.0
 * @since 2019-03-30
 */
@SuppressWarnings("Duplicates")
public class StaffCSVHelper extends CSVBaseHelper {

    /**
     * Path to Menu Items CSV File in the data folder. Defaults to staff.csv
     */
    private String staffCsv = "staff.csv";

    /**
     * Singleton instance of this class
     */
    private static StaffCSVHelper mInstance;

    /**
     * Default Constructor to initialize this class with staff.csv as the CSV file
     */
    private StaffCSVHelper() {}

    /**
     * Initialize the Helper object
     * @deprecated Call {@link StaffCSVHelper#getInstance()} instead
     * @param filename Path to MenuItems CSV File
     */
    @Deprecated
    public StaffCSVHelper(String filename) {
        this.staffCsv = filename;
    }

    /**
     * Gets the singleton instance of MenuItemCSVHelper that reads from menu.csv
     * @return Instance of this class
     */
    public static StaffCSVHelper getInstance() {
        if (mInstance == null) mInstance = new StaffCSVHelper();
        return mInstance;
    }

    /**
     * Reads the CSV file and parse it into an array list of menu item objects
     * @return ArrayList of Menu Item Objects
     * @throws IOException Unable to read from file
     */
    public ArrayList<Staff> readFromCsv() throws IOException{
        if (!FileIOHelper.exists(this.staffCsv)) return new ArrayList<>(); // Empty array list
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.staffCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Staff> staffs = new ArrayList<>();
        if (csvLines.size() == 0) return staffs;
        for (String[] str : csvLines) {
            Staff s = new Staff(str); // Create based on type
            staffs.add(s);
        }
        return staffs;
    }

    /**
     * Writes to the CSV File
     * @param staffs ArrayList of items to save
     * @throws IOException Unable to write to file
     */
    public void writeToCsv(ArrayList<Staff> staffs) throws IOException {
        String[] header = {"ID", "Name", "Gender", "Title"};
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.staffCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        staffs.forEach((s) -> toWrite.add(s.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
