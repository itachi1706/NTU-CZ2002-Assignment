package sg.edu.ntu.scse.cz2002;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

public class MainApp {

    /**
     * Constant for defining what the max pretty print size is for console printing
     */
    private static final int PRINT_WINDOW_MAX_SIZE = 40;

    /**
     * Initializes all of the necessary items of the application
     * Items include loading saved menu items etc
     */
    private static void init() {
        // TODO: Init Items
        System.out.println("Initializing Program...");
    }

    /**
     * Pre-exit actions to be executed here
     */
    private static void shutdown() {
        // TODO: Do pre shutdown items
        System.out.println("Shutting down program...");
        System.exit(0);
    }

    /**
     * The application main menu
     * @return Exit Code. Return a non-zero value to exit the program
     */
    private static int generateMainMenu() {
        printHeader("Main Menu");
        System.out.println("1) Menu Items Management");
        System.out.println("2) Promotion Items Management");
        System.out.println("3) Order Management");
        System.out.println("4) Reservation Manangement");
        System.out.println("5) Check Table Availability");
        System.out.println("6) Print Bill Invoice");
        System.out.println("7) Print sale revenue report");
        System.out.println("8) Exit");
        printBreaks();

        // Process Choice
        int choice = doMenuChoice(8);
        switch (choice) {
            case 1: break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5: break;
            case 6: break;
            case 7: break;
            case 8: shutdown(); return 1;
            default: throw new IllegalStateException("Invalid Choice");
        }
        return 0;
    }

    /**
     * The main application entry point
     * @param args Any console arguments entered by the user
     */
    public static void main(String... args) {
        init();

        int exit = 0;
        while (exit == 0) {
            exit = generateMainMenu();
        }
        shutdown(); // Just in case it comes here for some reason
    }

    // Sub Menus
    // TODO: Discuss if we should move this to respective classes for OO or leave them hear


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
     * @return The menu option selected by the user
     */
    public static int doMenuChoice(int max) {
        Scanner input = new Scanner(System.in);
        int selection;
        do {
            System.out.print("Enter menu option: ");
            try {
                selection = input.nextInt();
                if (selection > max || selection <= 0) System.out.println("Invalid Selection. Please select an option from 1 - " + max);
            } catch (InputMismatchException e) {
                selection = -1;
                System.out.println("Invalid Input. Please only enter numbers");
                input.nextLine();
            }
            System.out.println();
        } while (selection > max || selection <= 0);
        return selection;
    }
}
