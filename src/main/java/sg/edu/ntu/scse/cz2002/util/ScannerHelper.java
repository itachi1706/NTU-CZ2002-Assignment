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
     * Integer input with a minimum integer
     * @param prompt Text to prompt for the input, pass in empty string for no prompt
     * @param min Minimum input (exclusive)
     * @return Validated Input
     */
    public static int getIntegerInput(String prompt, int min) {
        while (true) {
            int val = getIntegerInput(prompt);
            if (val > min) return val;
            System.out.println("Invalid Input. Please ensure you enter a number greater than " + min);
        }
    }

    /**
     * Yes/No Prompt
     * @param prompt Text to prompt for the input, pass in empty string for no prompt
     * @return true if yes, false if no
     */
    public static boolean getYesNoInput(String prompt) {
        Scanner input = getScannerInput();
        String ans;
        while (true) {
            System.out.print(prompt + " [Y]es/[N]o: ");
            try {
                ans = input.nextLine().toLowerCase();
                if (ans.charAt(0) == 'y') return true;
                else if (ans.charAt(0) == 'n') return false;
                else throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please answer either yes or no");
            }
        }
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
