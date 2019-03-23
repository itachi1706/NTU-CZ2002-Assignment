package sg.edu.ntu.scse.cz2002.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base Helper where all CSV I/O files should extend from
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-23
 */
@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public class CSVBaseHelper {

    /**
     * Reads CSV file to a String[] list
     * @param reader The BufferedReader object instance to the CSV File. Retrieve with {@link FileIOHelper#getFileBufferedReader(String)}
     * @param skip How many lines in the file to skip (set 1 for header)
     * @return A list of string arrays containing all the CSV values
     */
    protected List<String[]> readAll(BufferedReader reader, int skip) {
        List<String> tmp = reader.lines().collect(Collectors.toList());
        while (skip > 0) {
            tmp.remove(0);
            skip--; // Skip said lines
        }
        List<String[]> result = new ArrayList<>();
        tmp.forEach((s) -> result.add(s.split("\\|\\|\\|")));
        return result;
    }

    /**
     * Writes a list of String Arrays to a CSV file
     * Note: This will overwrite the file
     *
     * @param writeStrings List of String arrays to write to
     * @param writer The BufferedWriter object instance to the CSV file. Retrieve with {@link FileIOHelper#getFileBufferedWriter(String)}
     */
    protected void writeToCsvFile(List<String[]> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(String.join("|||", s)));
        w.flush();
        w.close();
    }
}
