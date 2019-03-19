package sg.edu.ntu.scse.cz2002;

import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;
import sg.edu.ntu.scse.cz2002.util.MenuItemCSVHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Main Application Class
 * Entry point for the RRPSS application
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-16
 */
public class MainApp {

    /**
     * Constant for defining what the max pretty print size is for console printing
     */
    private static final int PRINT_WINDOW_MAX_SIZE = 40;

    public static ArrayList<MenuItem> menuItems;

    /**
     * Initializes all of the necessary items of the application
     * Items include loading saved menu items etc
     */
    private static void init() {
        // TODO: Init Items
        MenuItemCSVHelper menuItemCsv = new MenuItemCSVHelper("menu.csv");
        try {
            System.out.println("Loading Menu Items from file...");
            menuItems = menuItemCsv.readFromCsv();
            System.out.println(menuItems.size() + " menu items loaded from file");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        }
        System.out.println("Initializing Program...");
    }

    /**
     * Pre-exit actions to be executed here
     */
    private static void shutdown() {
        // TODO: Do pre shutdown items
        MenuItemCSVHelper menuItemCSVHelper = new MenuItemCSVHelper("menu.csv");
        try {
            System.out.println("Saving current menu item list to file...");
            menuItemCSVHelper.writeToCsv(menuItems);
            System.out.println("Menu Item List Saved!");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] Failed to save menu items to file. (" + e.getLocalizedMessage() + ")");
        }
        System.out.println("Shutting down program...");
        System.exit(0);
    }

    /**
     * The application main menu
     */
    private static void generateMainMenu() {
        while (true) {
            printHeader("Main Menu");
            System.out.println("1) Menu Items Management");
            System.out.println("2) Promotion Items Management");
            System.out.println("3) Order Management");
            System.out.println("4) Reservation Manangement");
            System.out.println("5) Check Table Availability");
            System.out.println("6) Print Bill Invoice");
            System.out.println("7) Print sale revenue report");
            System.out.println("0) Exit");
            printBreaks();

            // Process Choice
            int choice = doMenuChoice(7, 0);
            switch (choice) {
                case 1:
                    if (generateFoodMenuItemMenu()) return;
                    break;
                case 2:
                    if (generatePromotionMenu()) return;
                    break;
                case 3:
                    if (generateOrderMenu()) return;
                    break;
                case 4:
                    if (generateReservationMenu()) return;
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 0:
                    return; // Shutdown
                default:
                    throw new IllegalStateException("Invalid Choice (Main Menu)");
            }
        }
    }

    /**
     * The main application entry point
     * @param args Any console arguments entered by the user
     */
    public static void main(String... args) {
        init();
        generateMainMenu();
        shutdown();
    }

    // Sub Menus
    // TODO: Discuss if we should move this to respective classes for OO or leave them hear
    /**
     * The Food Menu Items Management Menu
     * @return Exit Code. Return true to exit the program
     */
    private static boolean generateFoodMenuItemMenu() {
        while (true) {
            printHeader("Menu Items Management");
            System.out.println("1) Create a new menu item");
            System.out.println("2) Update a new menu items");
            System.out.println("3) Delete a menu item");
            System.out.println("4) Back to main menu");
            System.out.println("0) Exit Application");
            printBreaks();

            int choice = doMenuChoice(4, 0);
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    return false;
                case 0:
                    return true;
                default:
                    throw new IllegalStateException("Invalid Choice (Food Item Menu)");
            }
        }
    }

    /**
     * The Promotion Management Menu
     * @return Exit Code. Return true to exit the program
     */
    private static boolean generatePromotionMenu() {
        while (true) {
            printHeader("Promotion Management");
            System.out.println("1) Create a new promotion");
            System.out.println("2) Update a promotion");
            System.out.println("3) Delete promotion");
            System.out.println("4) Back to main menu");
            System.out.println("0) Exit Application");
            printBreaks();

            int choice = doMenuChoice(4, 0);
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    return false;
                case 0:
                    return true;
                default:
                    throw new IllegalStateException("Invalid Choice (Promotion Menu)");
            }
        }
    }

    /**
     * The Order Management Menu
     * @return Exit Code. Return true to exit the program
     */
    private static boolean generateOrderMenu() {
        while (true) {
            printHeader("Order Management");
            System.out.println("1) Create a new order");
            System.out.println("2) View order");
            System.out.println("3) Add item to order");
            System.out.println("4) Remove item from order");
            System.out.println("5) Back to main menu");
            System.out.println("0) Exit Application");
            printBreaks();

            int choice = doMenuChoice(5, 0);
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    return false;
                case 0:
                    return true;
                default:
                    throw new IllegalStateException("Invalid Choice (Order Menu)");
            }
        }
    }

    /**
     * The Reservation Management Menu
     * @return Exit Code. Return true to exit the program
     */
    private static boolean generateReservationMenu() {
        while (true) {
            printHeader("Reservation Booking Management");
            System.out.println("1) Create a new reservation booking");
            System.out.println("2) Check reservation booking");
            System.out.println("3) Remove reservation booking");
            System.out.println("4) Back to main menu");
            System.out.println("0) Exit Application");
            printBreaks();

            int choice = doMenuChoice(4, 0);
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    return false;
                case 0:
                    return true;
                default:
                    throw new IllegalStateException("Invalid Choice (Reservation Menu)");
            }
        }
    }


    // Common Menu Helper Methods
    /**
     * Helper method to print the header of a menu
     * @param headerName Name of the menu
     */
    public static void printHeader(String headerName) {
        printBreaks();
        // Do fancy parsing of header to center it (size 40)
        int noOfSpaces = (PRINT_WINDOW_MAX_SIZE - headerName.length()) / 2;
        Stream.generate(() -> " ").limit(noOfSpaces).forEach(System.out::print);
        System.out.println(headerName);
        printBreaks();
    }

    /**
     * Helper method to create a menu separator
     * (e.g -----------)
     */
    public static void printBreaks() {
        Stream.generate(() -> "-").limit(PRINT_WINDOW_MAX_SIZE).forEach(System.out::print);
        System.out.println();
    }

    /**
     * Helper method to select a menu option with the appropriate exception handling.
     * If the user exceeds the max option defined or enters a non numeric character an appropriate error message will occur
     * @param max The max option a user can select
     * @param specialEscape A special option that will be used for special operations like exiting the program
     * @return The menu option selected by the user
     */
    public static int doMenuChoice(int max, int specialEscape) {
        Scanner input = new Scanner(System.in);
        int selection;
        do {
            System.out.print("Enter menu option: ");
            try {
                selection = input.nextInt();
                if ((selection > max && selection != specialEscape) || selection < 0)
                    System.out.println("Invalid Selection. Please select an option from 1 - " + max);
            } catch (InputMismatchException e) {
                selection = -1;
                System.out.println("Invalid Input. Please only enter numbers");
                input.nextLine();
            }
            System.out.println();
        } while ((selection > max && selection != specialEscape) || selection < 0);
        return selection;
    }
}
