package sg.edu.ntu.scse.cz2002.util;

/**
 * Interface that should be implemented for writing to CSV files
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-05
 */
public interface ICsvSerializable {

    /**
     * An interface that objects who are ICsvSerializable should implement
     * @return Array of Strings to write to CSV
     */
    public String[] toCsv();
}
