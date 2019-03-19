package sg.edu.ntu.scse.cz2002.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Helper class for File IO to the CSV files in the data folder
 *
 * The data folder is where we will be storing the application data
 *
 * @author Kenneth
 * @version 1.0
 * @since 2019-03-19
 */
public class FileIOHelper {

    /**
     * Ensure that the data folder exists. Otherwise creates it
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void init() {
        File folder = new File("/data");
        if (!folder.exists()) folder.mkdir();
    }

    /**
     * Gets the file object in the data folder
     * @param name Filename with extension
     * @return File object if valid, null otherwise
     */
    public static File getFile(String name) {
        init();
        return new File("/data/" + name);
    }

    /**
     * Returns a concatenated String read from the CSV files
     * @param filename The filename with extension
     * @return A single string of text in the file
     * @throws IOException If any error occurs
     */
    public static String readStringFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(getFile(filename)));

        StringBuilder sb = new StringBuilder();
        Stream<String> fileString = reader.lines();
        fileString.forEach((s) -> sb.append(s).append("\n"));
        reader.close();
        return sb.toString();
    }

    /**
     * Writes an arraylist of Strings to the file
     *
     * @param filename Filename with extension where the list of Strings are being saved into
     * @param strings The Arraylist of Strings to be written to the file
     * @return true if operation completes
     * @throws IOException Any error occurs
     */
    public static boolean writeStringToFile(String filename, ArrayList<String> strings) throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(getFile(filename))));

        for (String s : strings) {
            writer.println(s);
        }
        writer.close();
        return true;
    }

    /**
     * Writes a single string to the file
     *
     * @param filename Filename with extension where the string is being written into
     * @param toWrite The string to write into the file
     * @return true if operation completes
     * @throws IOException Any error occurs
     */
    public static boolean writeStringToFile(String filename, String toWrite) throws IOException {
        ArrayList<String> splitStr = new ArrayList<>(Arrays.asList(toWrite.split("\n")));
        return writeStringToFile(filename, splitStr);
    }

    // TODO: Method to read and parse CSV
    // TODO: Method to write CSV to file
}
