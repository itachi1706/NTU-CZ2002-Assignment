package sg.edu.ntu.scse.cz2002.features;

import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;

import java.text.ParseException;

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
    public enum TableSeats {TEN_SEATER, EIGHT_SEATER, FOUR_SEATER, TWO_SEATER, UNKNOWN_SEATER};

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
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     * @throws ParseException When the date time provided from the CSV file has an invalid format,
     * which is unlikely to happen unless CSV file was modified outside of program.
     */
    public Table(String[] csv) throws ParseException {
        this.tableNum= Integer.parseInt(csv[0]);
        int seats = Integer.parseInt(csv[1]);
        this.numSeats = seats == 2 ? TableSeats.TWO_SEATER      :
                        seats == 4 ? TableSeats.FOUR_SEATER   :
                        seats == 8 ? TableSeats.EIGHT_SEATER  :
                        seats == 10 ? TableSeats.TEN_SEATER   :
                                TableSeats.UNKNOWN_SEATER;

        this.isReserved = (Integer.parseInt(csv[2]) == 1);

        int state = Integer.parseInt(csv[3]);
        this.state =    state == 0 ? TableState.TABLE_OCCUPIED    :
                        state == 1 ? TableState.TABLE_VACATED   :
                        state == 2 ? TableState.TABLE_RESERVED  :
                                TableState.TABLE_STATE_UNKNOWN;
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    public String[] toCsv() {
        String[] s = new String[4];
        s[0] = this.tableNum + "";
        s[1] = (this.numSeats == TableSeats.TWO_SEATER      ? 2 :
                this.numSeats == TableSeats.FOUR_SEATER     ? 4 :
                this.numSeats == TableSeats.EIGHT_SEATER    ? 8 :
                this.numSeats == TableSeats.TEN_SEATER      ? 10 : -1) + "";

        s[2] = (this.isReserved ? 1 : 0) + "";

        s[3] = (this.state == TableState.TABLE_OCCUPIED ? 0 :
                this.state == TableState.TABLE_VACATED  ? 1 :
                this.state == TableState.TABLE_RESERVED ? 2 : -1) + "";

        return s;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setNumSeats(TableSeats numSeats) {
        this.numSeats = numSeats;
    }

    public TableSeats getNumSeats() {
        return numSeats;
    }

    public void setState(TableState state) {
        this.state = state;
    }

    public TableState getState() {
        return state;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;

        if(isReserved)
            this.setState(TableState.TABLE_RESERVED);
        else
            this.setState(TableState.TABLE_VACATED);

    }

    /**
     * A method to check if a table has already been reserved
     * Returns a boolean value determining whether table has been reserved.
     */
    public boolean checkReserved(){
        return this.isReserved;
    }

    /*public int checkAvailableTables(int numPax) {

        if (numPax <= 2) {
            if (this.getNumSeats() == TableSeats.TWO_SEATER &&
                    this.getState() == TableState.TABLE_VACATED){
                return this.tableNum;
            }
        } else if (numPax <= 4) {
            if (this.getNumSeats() == TableSeats.FOUR_SEATER &&
                    this.getState() == TableState.TABLE_VACATED) {
                return this.tableNum;
            }
        } else if (numPax <= 8) {
            if (this.getNumSeats() == TableSeats.EIGHT_SEATER &&
                    this.getState() == TableState.TABLE_VACATED) {
                return this.tableNum;
            }
        } else if (numPax <= 10) {
            if (this.getNumSeats() == TableSeats.TEN_SEATER &&
                    this.getState() == TableState.TABLE_VACATED) {
                return this.tableNum;
            }
        }
    }*/

}
