package sg.edu.ntu.scse.cz2002.features;

/**
 * The Table class
 *
 * @author Francis Lim, and others
 * @version 1.0
 * @since 2019-03-29
 */

public class Table {

    /**
     * The number that is allocated to the table.
     */
    private int tableNum;
    /**
     *The state of whether the table is reserved.
     */
    private boolean isReserved;

    /**
     * The states of the table, whether it has been reserved, occupied or vacated.
     */
    public enum TableState {TABLE_VACATED, TABLE_OCCUPIED, TABLE_RESERVED};

    /**
     * The number of seats at the table
     */
    private enum TableSeats {TEN_SEATER, EIGHT_SEATER, FOUR_SEATER, TWO_SEATER};

    public Table () {}

    /**
     * A method to check if a table has already been reserved
     * Returns a boolean value determining whether table has been reserved.
     */
    public boolean checkReserved(){
        return this.isReserved;
    }

}
