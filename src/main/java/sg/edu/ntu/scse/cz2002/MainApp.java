package sg.edu.ntu.scse.cz2002;

import sg.edu.ntu.scse.cz2002.features.Reservation;
import sg.edu.ntu.scse.cz2002.features.Table;
import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;
import sg.edu.ntu.scse.cz2002.ui.MainMenuUI;
import sg.edu.ntu.scse.cz2002.util.FileIOHelper;
import sg.edu.ntu.scse.cz2002.util.MenuItemCSVHelper;
import sg.edu.ntu.scse.cz2002.util.ReservationCSVHelper;
import sg.edu.ntu.scse.cz2002.util.TableCSVHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main Application Class
 * Entry point for the RRPSS application
 * @author Kenneth Soh, Francis Lim
 * @version 1.1
 * @since 2019-03-29
 */
public class MainApp {

    /**
    * The list of tables available in the restaurant
    */
    public static ArrayList<Table> tables;
    /**
     * The list of menuitems loaded into the program
     */
    public static ArrayList<MenuItem> menuItems;

    /**
     * The list of reservations loaded into the program
     */
    public static ArrayList<Reservation> reservations;

    /**
     * Enable debug mode
     */
    private static final boolean DEBUG = false;

    /**
     * Initializes all of the necessary items of the application
     * Items include loading saved menu items etc
     */
    private static void init() {
        // TODO: Init Items
        MenuItemCSVHelper menuItemCsv = MenuItemCSVHelper.getInstance();
        ReservationCSVHelper reservationCsv = ReservationCSVHelper.getInstance();
        TableCSVHelper tableCsv = TableCSVHelper.getInstance();
        try {
            System.out.println("Loading Menu Items from file...");
            menuItems = menuItemCsv.readFromCsv();
            System.out.println(menuItems.size() + " menu items loaded from file");

            System.out.println("Loading Reservations from file...");
            reservations = reservationCsv.readFromCsv();
            System.out.println(reservations.size() + " existing reservations loaded successfully.");

            System.out.println("Loading Table states from file...");
            tables = tableCsv.readFromCsv();
            System.out.println(tables.size() + " tables loaded successfully.");

            checkTodayReservations();

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        } catch (ParseException e) {
            System.out.println("[ERROR] Wrong format of date and time read from CSV. (" + e.getLocalizedMessage() + ")");
        }

        System.out.println("Initializing Program...");

        // Print welcome art
        printWelcomeAscii();
    }

    /**
     * Saves all data into its relevant CSV files on disk
     * @return true if successful, false otherwise
     */
    public static boolean saveAll() {
        MenuItemCSVHelper menuItemCSVHelper = MenuItemCSVHelper.getInstance();
        ReservationCSVHelper reservationCsvHelper = ReservationCSVHelper.getInstance();
        TableCSVHelper tableCsvHelper = TableCSVHelper.getInstance();
        try {
            System.out.println("Saving current menu item list to file...");
            menuItemCSVHelper.writeToCsv(menuItems);
            System.out.println("Menu Item List Saved!");

            System.out.println("Saving current reservation list to file...");
            reservationCsvHelper.writeToCsv(reservations);
            System.out.println("Reservation List Saved!");

            System.out.println("Saving current tables to file...");
            tableCsvHelper.writeToCsv(tables);
            System.out.println("Table List Saved!");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] Failed to save items to file. (" + e.getLocalizedMessage() + ")");
            return false;
        }
        return true;
    }

    /**
     * The main application entry point
     * @param args Any console arguments entered by the user
     */
    public static void main(String... args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // TODO: Do pre shutdown items
            saveAll();
            System.out.println("Shutting down program...");
        }));
        init();
        // TODO: Staff login (move if necessary) This is placed here in case we want to "login" to a staff here. If we are not doing so remove this
        new MainMenuUI().startMainMenu();
        System.exit(0);
    }

    /**
     * Print welcome ascii art/message from data/ascii_welcome.txt
     */
    private static void printWelcomeAscii() {
        try {
            BufferedReader reader = FileIOHelper.getFileBufferedReader("ascii_welcome.txt");
            List<String> ascii = reader.lines().collect(Collectors.toList());
            ascii.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Failed to load ASCII Welcome Art!");
            if (DEBUG) System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    /**
     * Part of initialising to check for today's reservations
     * If found reservation that matches today's date, set the table to reserved.
     */
    private static void checkTodayReservations() {
        for (Reservation r : reservations) {
            //System.out.println(r.getResvDateTime().get(Calendar.));

        }

    }
}
