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

    /**
     * Integer input with validation of invalid characters entered
     * @param input Scanner object
     * @param prompt Text to prompt for the input, pass in empty string for no prompt
     * @return Integer input value
     */
    public static int getIntegerInput(Scanner input, String prompt) {
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
}
