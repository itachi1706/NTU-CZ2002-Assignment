package sg.edu.ntu.scse.cz2002;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

public class MainApp {

    /**
     * Constant for defining what the max pretty print size is for console printing
     */
    private static final int PRINT_WINDOW_MAX_SIZE = 40;

    private static void init() {
        // TODO: Init Items
        System.out.println("Initializing Program...");
    }

    private static void shutdown() {
        // TODO: Do pre shutdown items
        System.out.println("Shutting down program...");
        System.exit(0);
    }

    public static void main(String[] args) {
        init();

        int exit = 0;
        while (exit == 0) {
            exit = generateMainMenu();
        }
        shutdown(); // Just in case it comes here for some reason
    }

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



    public static void printHeader(String headerName) {
        printBreaks();
        // Do fancy parsing of header to center it (size 40)
        int noOfSpaces = (PRINT_WINDOW_MAX_SIZE - headerName.length()) / 2;
        Stream.generate(() -> " ").limit(noOfSpaces).forEach(System.out::print);
        System.out.println(headerName);
        printBreaks();
    }

    public static void printBreaks() {
        Stream.generate(() -> "-").limit(PRINT_WINDOW_MAX_SIZE).forEach(System.out::print);
        System.out.println();
    }

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
