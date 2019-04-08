package sg.edu.ntu.scse.cz2002.ui;

import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Abstract class where all menu UIs inherits from
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-22
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseMenu {

    /**
     * Constant for defining what the max pretty print size is for console printing
     */
    private static final int PRINT_WINDOW_MAX_SIZE = 40;

    /**
     * An abstract class that the other classes that inherits this class needs to override and implement
     * @return Exit Codes for {@link BaseMenu#startMainMenu()} to implement. 0 to loop into the method again,
     * negative to return false and positive to return true
     */
    protected abstract int generateMenuScreen();

    /**
     * Other classes calls this method to start the MAIN Menu on a loop
     * @return Exit Code, true to exit the program
     */
    public boolean startMainMenu() {
        // Only exit if <>0, otherwise continue looping
        while (true) {
            try {
                int exit = generateMenuScreen();
                if (exit < 0) return false;
                else if (exit > 0) return true;
            } catch (MenuChoiceInvalidException e) {
                System.out.println(e.getLocalizedMessage());
                if (MainApp.DEBUG) e.printStackTrace();
            }
        }
    }

    // Common Menu Helper Methods
    /**
     * Helper method to print the header of a menu
     * @param headerName Name of the menu
     */
    protected static void printHeader(String headerName) {
        printHeader(headerName, PRINT_WINDOW_MAX_SIZE);
    }

    /**
     * Helper method to print the header of a menu
     * @param headerName Name of the menu
     * @param length Length of the header
     */
    protected static void printHeader(String headerName, int length) {
        printBreaks(length);
        // Do fancy parsing of header to center it (size 40)
        int noOfSpaces = (headerName.length() > length) ? 0 : ((length - headerName.length()) / 2);
        Stream.generate(() -> " ").limit(noOfSpaces).forEach(System.out::print);
        System.out.println(headerName);
        printBreaks(length);
    }

    /**
     * Helper method to create a menu separator
     * (e.g -----------)
     * @param length How many dashes to append
     */
    protected static void printBreaks(int length) {
        Stream.generate(() -> "-").limit(length).forEach(System.out::print);
        System.out.println();
    }

    /**
     * Helper method to create a menu separator
     * (e.g -----------)
     */
    protected static void printBreaks() {
        printBreaks(PRINT_WINDOW_MAX_SIZE);
    }

    /**
     * Helper method to select a menu option with the appropriate exception handling.
     * If the user exceeds the max option defined or enters a non numeric character an appropriate error message will occur
     * @param max The max option a user can select
     * @param specialEscape A special option that will be used for special operations like exiting the program
     * @return The menu option selected by the user
     */
    @SuppressWarnings("SameParameterValue")
    protected static int doMenuChoice(int max, int specialEscape) {
        Scanner input = ScannerHelper.getScannerInput();
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
