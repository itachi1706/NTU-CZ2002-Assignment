package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.features.Reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Reservations
 *
 * @author Francis Lim
 * @version 1.0
 * @since 2019-03-29
 */
@SuppressWarnings("Duplicates")
public class ReservationCSVHelper extends CSVBaseHelper {

    /**
     * Path to Menu Items CSV File in the data folder. Defaults to reservation.csv
     */
    private String reservationCsv = "reservation.csv";

    /**
     * Singleton instance of this class
     */
    private static ReservationCSVHelper mInstance;

    /**
     * Default Constructor to initialize this class with reservation.csv as the CSV file
     */
    private ReservationCSVHelper() {}

    /**
     * Initialize the Helper object
     * @deprecated Call {@link ReservationCSVHelper#getInstance()} instead
     * @param filename Path to MenuItems CSV File
     */
    @Deprecated
    public ReservationCSVHelper(String filename) {
        this.reservationCsv = filename;
    }

    /**
     * Gets the singleton instance of MenuItemCSVHelper that reads from menu.csv
     * @return Instance of this class
     */
    public static ReservationCSVHelper getInstance() {
        if (mInstance == null) mInstance = new ReservationCSVHelper();
        return mInstance;
    }

    /**
     * Reads the CSV file and parse it into an array list of menu item objects
     * @return ArrayList of Menu Item Objects
     * @throws IOException Unable to read from file
     * @throws ParseException Wrong format of date time passed in
     */
    public ArrayList<Reservation> readFromCsv() throws IOException, DateTimeParseException {
        if (!FileIOHelper.exists(this.reservationCsv)) return new ArrayList<>(); // Empty array list
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.reservationCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<Reservation> reservations = new ArrayList<>();
        if (csvLines.size() == 0) return reservations;
        for (String[] str : csvLines) {
            Reservation resv = new Reservation(str); // Create based on type
            reservations.add(resv);
        }
        return reservations;
    }

    /**
     * Writes to the CSV File
     * @param reservations ArrayList of items to save
     * @throws IOException Unable to write to file
     */
    public void writeToCsv(ArrayList<Reservation> reservations) throws IOException {
        String[] header = {"ID", "Name", "TelNo", "NumPax", "ResvDate", "ResvTime", "TableNum" };
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.reservationCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        reservations.forEach((r) -> toWrite.add(r.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
}
