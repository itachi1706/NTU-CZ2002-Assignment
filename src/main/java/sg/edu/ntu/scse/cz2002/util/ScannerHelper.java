package sg.edu.ntu.scse.cz2002.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Helper class for Scanner inputs
 * This is such that we can handle all the exception handling here
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-02
 */
public class ScannerHelper {

    public static Scanner instance;

    /**
     * Integer input with validation of invalid characters entered
     * @param prompt Text to prompt for the input, pass in empty string for no prompt
     * @return Integer input value
     */
    public static int getIntegerInput(String prompt) {
        Scanner input = getScannerInput();
        int val;
        while (true) {
            System.out.print(prompt);
            try {
                val = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter numbers only");
            } finally {
                input.nextLine();
            }
        }
        return val;
    }
    /**
     * Get the Scanner instance object
     * @return A scanner instance object with System.in as the InputStream
     */
    public static Scanner getScannerInput() {
        if (instance == null) instance = new Scanner(System.in);
        return new Scanner(System.in);
    }
}
