package sg.edu.ntu.scse.cz2002;

import sg.edu.ntu.scse.cz2002.features.Invoice;
import sg.edu.ntu.scse.cz2002.features.Reservation;
import sg.edu.ntu.scse.cz2002.features.Table;
import sg.edu.ntu.scse.cz2002.objects.person.Staff;
import sg.edu.ntu.scse.cz2002.objects.restaurantItem.MenuItem;
import sg.edu.ntu.scse.cz2002.objects.restaurantItem.PromotionItem;
import sg.edu.ntu.scse.cz2002.ui.MainMenuUI;
import sg.edu.ntu.scse.cz2002.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MAIN Application Class
 * Entry point for the RRPSS application
 * @author Kenneth Soh, Francis Lim
 * @version 1.1
 * @since 2019-03-29
 */
public class MainApp {

    public static final String APP_NAME = "BMT Cookhouse";

    /**
     * The list of tables available in the restaurant
     */
    public static ArrayList<Table> tables;

    /**
     * The list of menuitems loaded into the program
     */
    public static ArrayList<MenuItem> menuItems;

    /**
     * List of completed orders (to be saved to CSV)
     */
    public static ArrayList<Invoice> invoices; //

    /**
     * The list of reservations loaded into the program
     */
    public static ArrayList<Reservation> reservations;

    /**
     * The list of promotions loaded into the program
     */
    public static ArrayList<PromotionItem> promotions;
    
    /**
     * The list of reservations loaded into the program
     */
    public static ArrayList<Staff> staffs;

    /**
     * The current Restaurant Session (AM/PM)
     */
    public static char restaurantSession = ' ';

    /**
     * Enable debug mode
     */
    public static final boolean DEBUG = false;

    /**
     * Initializes all of the necessary items of the application
     * Items include loading saved menu items etc
     */
    private static void init() {
        MenuItemCSVHelper menuItemCsv = MenuItemCSVHelper.getInstance();
        PromoCSVHelper promotionCsv = PromoCSVHelper.getInstance();
        ReservationCSVHelper reservationCsv = ReservationCSVHelper.getInstance();
        TableCSVHelper tableCsv = TableCSVHelper.getInstance();
        StaffCSVHelper staffCsv = StaffCSVHelper.getInstance();
        InvoiceCSVHelper invoiceCsv = InvoiceCSVHelper.getInstance();
        try {
            System.out.println("Loading Menu Items from file...");
            menuItems = menuItemCsv.readFromCsv();
            System.out.println(menuItems.size() + " menu items loaded from file");

            System.out.println("Loading Promotions from file...");
            promotions = promotionCsv.readFromCsv();
            System.out.println(promotions.size() + " promotions loaded from file.");
            
            System.out.println("Loading Reservations from file...");
            reservations = reservationCsv.readFromCsv();
            System.out.println(reservations.size() + " existing reservations loaded successfully.");

            System.out.println("Loading Table states from file...");
            tables = tableCsv.readFromCsv();
            System.out.println(tables.size() + " tables loaded successfully.");

            System.out.println("Loading Staff states from file...");
            staffs = staffCsv.readFromCsv();
            System.out.println(staffs.size() + " staffs loaded successfully.");

            System.out.println("Loading Completed Invoices from file...");
            invoices = invoiceCsv.readFromCsv();
            System.out.println(invoices.size() + " completed invoices loaded successfully.");

            System.out.println(checkTodayReservations() + " reservations have since expired, and deleted from the system.");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        } catch (ParseException e) {
            System.out.println("[ERROR] Wrong format of date and time read from CSV. (" + e.getLocalizedMessage() + ")");
        }

        System.out.println("Initializing Program...");

        // Print welcome art
        printWelcomeAscii();

        System.out.println("\nThe current/next session of restaurant operation is: " +
                ((restaurantSession == 'A') ? "AM session - from 11:00hrs to 15:00hrs" :
                                            "PM session - from 18:00hrs to 22:00hrs") + ".\n");
    }

    /**
     * Saves all data into its relevant CSV files on disk
     *
     * @return true if successful, false otherwise
     */
    public static boolean saveAll() {
        MenuItemCSVHelper menuItemCSVHelper = MenuItemCSVHelper.getInstance();
        PromoCSVHelper promoCSVHelper = PromoCSVHelper.getInstance();
        ReservationCSVHelper reservationCsvHelper = ReservationCSVHelper.getInstance();
        //TableCSVHelper tableCsvHelper = TableCSVHelper.getInstance();
        StaffCSVHelper staffCsvHelper = StaffCSVHelper.getInstance();
        InvoiceCSVHelper invoiceCSVHelper = InvoiceCSVHelper.getInstance();
        try {
            System.out.println("Saving current menu item list to file...");
            menuItemCSVHelper.writeToCsv(menuItems);
            System.out.println("Menu Item List Saved!");
            
            System.out.println("Saving current promotion list to file...");
            promoCSVHelper.writeToCsv(promotions);
            System.out.println("Promotion List Saved!");
            
            System.out.println("Saving current reservation list to file...");
            reservationCsvHelper.writeToCsv(reservations);
            System.out.println("Reservation List Saved!");

            /*System.out.println("Saving current tables to file...");
            tableCsvHelper.writeToCsv(tables);
            System.out.println("Table List Saved!");*/

            System.out.println("Saving current staffs to file...");
            staffCsvHelper.writeToCsv(staffs);
            System.out.println("Staff List Saved!");

            System.out.println("Saving completed invoices to file...");
            invoiceCSVHelper.writeToCsv(invoices);
            System.out.println("Completed Invoices List Saved!");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] Failed to save items to file. (" + e.getLocalizedMessage() + ")");
            return false;
        }
        return true;
    }

    /**
     * The main application entry point
     *
     * @param args Any console arguments entered by the user
     */
    public static void main(String... args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveAll();
            System.out.println("Shutting down program...");
        }));
        init();
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
            System.out.println("[ERROR] Failed to load ASCII Welcome Art!");
            if (DEBUG) System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    /**
     * Part of initialising to check for today's reservations
     * Includes checking for expired reservations
     * If found reservation that matches today's date, set the table to reserved.
     * This function will init the session's (AM or PM) table status according to
     * available reservations
     * @return An integer containing the amount of expired reservations.
     */
    private static int checkTodayReservations() {
        int expiredCount = 0;
        //char session = 'A';
        Reservation r;
        Iterator<Reservation> iter = reservations.iterator();
        
        if (LocalTime.now().isBefore(LocalTime.of(15, 00)) || LocalTime.now().isAfter(LocalTime.of(22,00)))
            restaurantSession = 'A';
        else restaurantSession = 'P';

        while (iter.hasNext()) {
            r = iter.next();

            if (DateTimeFormatHelper.compareIfBeforeToday(r.getResvDate())) {
                iter.remove();
                expiredCount++;
                continue;
            }

            if (r.getResvDate().equals(DateTimeFormatHelper.getTodayDate(false))) {
                if (DateTimeFormatHelper.getTimeDifferenceMinutes(LocalTime.now(), r.getResvTime()) <= -30) {
                    iter.remove();
                    expiredCount++;
                    continue;
                }

                if (restaurantSession == 'A' && r.getResvSession() == Reservation.ReservationSession.AM_SESSION) {
                    for (Table t : tables) {
                        if (t.getTableNum() == r.getTableNum())
                            t.setReserved(true);
                    }
                }
                else if (restaurantSession == 'P' && r.getResvSession() == Reservation.ReservationSession.PM_SESSION) {
                    for (Table t : tables) {
                        if (t.getTableNum() == r.getTableNum())
                            t.setReserved(true);
                    }

                }

            }

        }
        return expiredCount;
    }

}
