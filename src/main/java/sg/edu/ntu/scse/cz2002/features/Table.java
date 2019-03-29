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
     * The states of the table, whether it has been reserved, occupied or vacated.
     */
    public enum TableState {TABLE_VACATED, TABLE_OCCUPIED, TABLE_RESERVED, TABLE_STATE_UNKNOWN};

    /**
     * The varying number of seats at different tables
     */
    private enum TableSeats {TEN_SEATER, EIGHT_SEATER, FOUR_SEATER, TWO_SEATER, UNKNOWN_SEATER};

    /**
     * The number that is allocated to the table.
     */
    private int tableNum;

    /**
     *The state of whether the table is reserved.
     */
    private boolean isReserved;

    /**
     * State of the table
     */
    private TableState state;

    /**
     * Number of seats at the table
     */
    private TableSeats numSeats;


    public Table(int num, boolean res, int seats, int state) {
        this.tableNum = num;
        this.isReserved = res;
        this.numSeats   = seats == 2 ? TableSeats.TWO_SEATER      :
                            seats == 4 ? TableSeats.FOUR_SEATER   :
                            seats == 8 ? TableSeats.EIGHT_SEATER  :
                            seats == 10 ? TableSeats.TEN_SEATER   :
                                     TableSeats.UNKNOWN_SEATER;
        this.state      = state == 0 ? TableState.TABLE_OCCUPIED    :
                            state == 1 ? TableState.TABLE_VACATED   :
                            state == 2 ? TableState.TABLE_RESERVED  :
                                         TableState.TABLE_STATE_UNKNOWN;
    }

    /**
     * A method to check if a table has already been reserved
     * Returns a boolean value determining whether table has been reserved.
     */
    public boolean checkReserved(){
        return this.isReserved;
    }

}
