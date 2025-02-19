package sg.edu.ntu.scse.cz2002.features;

import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.ui.ReservationMenuUI;
import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The Table class
 *
 * @author Francis Lim, and others
 * @version 1.0
 * @since 2019-03-29
 */

public class Table implements ICsvSerializable {

    /**
     * The states of the table, whether it has been reserved, occupied or vacated.
     */
    public enum TableState {TABLE_VACATED, TABLE_OCCUPIED, TABLE_RESERVED, TABLE_STATE_UNKNOWN}

    /**
     * The varying number of seats at different tables
     */
    public enum TableSeats {TWO_SEATER, FOUR_SEATER, EIGHT_SEATER, TEN_SEATER, UNKNOWN_SEATER}

    /**
     * The number that is allocated to the table.
     */
    private int tableNum;

    /**
     * The state of whether the table is reserved.
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

    /**
     * Constructor class to create table instances
     *
     * @param num   Table Number
     * @param res   Whether the table has been reserved
     * @param seats Number of seats
     * @param state Current occupancy state of the table
     */
    public Table(int num, boolean res, int seats, int state) {
        this.tableNum = num;
        this.isReserved = res;
        this.numSeats = seats == 2 ? TableSeats.TWO_SEATER :
                seats == 4 ? TableSeats.FOUR_SEATER :
                        seats == 8 ? TableSeats.EIGHT_SEATER :
                                seats == 10 ? TableSeats.TEN_SEATER :
                                        TableSeats.UNKNOWN_SEATER;
        this.state = state == 0 ? TableState.TABLE_OCCUPIED :
                state == 1 ? TableState.TABLE_VACATED :
                        state == 2 ? TableState.TABLE_RESERVED :
                                TableState.TABLE_STATE_UNKNOWN;
    }

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     *
     * @param csv A string array of the CSV file
     */
    public Table(String[] csv) {
        this.tableNum = Integer.parseInt(csv[0]);
        int seats = Integer.parseInt(csv[1]);
        this.numSeats = seats == 2 ? TableSeats.TWO_SEATER :
                seats == 4 ? TableSeats.FOUR_SEATER :
                        seats == 8 ? TableSeats.EIGHT_SEATER :
                                seats == 10 ? TableSeats.TEN_SEATER :
                                        TableSeats.UNKNOWN_SEATER;

        this.isReserved = (Integer.parseInt(csv[2]) == 1);

        int state = Integer.parseInt(csv[3]);
        this.state = state == 0 ? TableState.TABLE_OCCUPIED :
                state == 1 ? TableState.TABLE_VACATED :
                        state == 2 ? TableState.TABLE_RESERVED :
                                TableState.TABLE_STATE_UNKNOWN;
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     *
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        String[] s = new String[4];
        s[0] = this.tableNum + "";
        s[1] = this.getNumSeatsInt() + "";
        s[2] = (this.isReserved ? 1 : 0) + "";

        s[3] = (this.state == TableState.TABLE_OCCUPIED ? 0 :
                this.state == TableState.TABLE_VACATED ? 1 :
                        this.state == TableState.TABLE_RESERVED ? 2 : -1) + "";

        return s;
    }

    /**
     * Mutator for table number
     *
     * @param tableNum Table number
     */
    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    /**
     * Accessor for table number
     *
     * @return integer containing table number
     */
    public int getTableNum() {
        return tableNum;
    }

    /**
     * Mutator for number of seats
     *
     * @param numSeats enum for number of seats
     */
    public void setNumSeats(TableSeats numSeats) {
        this.numSeats = numSeats;
    }

    /**
     * Accessor for number of seaters
     *
     * @return enum value of numSeats
     */
    public TableSeats getNumSeats() {
        return numSeats;
    }

    /**
     * Gets number of seats based on the enum
     *
     * @return Number of seats
     */
    public int getNumSeatsInt() {
        return this.numSeats == TableSeats.TWO_SEATER ? 2 :
                this.numSeats == TableSeats.FOUR_SEATER ? 4 :
                        this.numSeats == TableSeats.EIGHT_SEATER ? 8 :
                                this.numSeats == TableSeats.TEN_SEATER ? 10 : -1;
    }

    /**
     * Mutator for table state
     *
     * @param state enum for table state
     */
    public void setState(TableState state) {
        this.state = state;
    }

    /**
     * Accessor for table state
     *
     * @return enum value of table state
     */
    public TableState getState() {
        return state;
    }

    /**
     * Mutator for separate table reserved value
     * Accesses {@link Table#setState(TableState state)} to simultaneously change table state
     *
     * @param reserved True or false boolean variable.
     */
    public void setReserved(boolean reserved) {
        isReserved = reserved;

        if (isReserved)
            this.setState(TableState.TABLE_RESERVED);
        else
            this.setState(TableState.TABLE_VACATED);

    }

    /**
     * A method to check if a table has already been reserved
     * Returns a boolean value determining whether table has been reserved.
     *
     * @return a boolean value determining whether table has been reserved.
     */
    public boolean checkReserved() {
        return this.isReserved;
    }

    /**
     * A method to get Table object from global Table ArrayList based on table number
     *
     * @param tableNum Table number
     * @return Table object that matches table number input. Returns null if table number is -1;
     */
    @Nullable
    public static Table getTableByNumber(int tableNum) {
        for (Table t : MainApp.tables) {
            if (t.getTableNum() == tableNum)
                return t;
        }
        return null;
    }

    /**
     * Method to obtain all tables with TABLE_VACANT table state for the session based on numPax
     * Method to obtain all tables that have no clashes with bookedTables ArrayList
     *
     * @param numPax       Number of people
     * @param bookedTables ArrayList containing tables booked on a specified date. This parameter will only be passed in
     *                     if invoked from {@link ReservationMenuUI}
     *                     Otherwise, MainApp.tables should be passed in if invoked from other functions
     * @return ArrayList of Tables
     */
    @Nullable
    public static ArrayList<Table> getVacantTablesByNumPax(int numPax, ArrayList<Table> bookedTables) {
        ArrayList<Table> tablesByNumPax = new ArrayList<>();
        final Table.TableSeats tableSeats;
        if (numPax <= 0 || numPax > 10) return null;
        else if (numPax > 8) tableSeats = TableSeats.TEN_SEATER;
        else if (numPax > 4) tableSeats = TableSeats.EIGHT_SEATER;
        else if (numPax > 2) tableSeats = TableSeats.FOUR_SEATER;
        else tableSeats = TableSeats.TWO_SEATER;

        MainApp.tables.forEach((t) -> {
            if (t.getNumSeats().ordinal() >= tableSeats.ordinal())
                tablesByNumPax.add(t);
        });

        tablesByNumPax.sort(Comparator.comparingInt(o -> o.getNumSeats().ordinal()));

        Iterator<Table> iter = tablesByNumPax.iterator();

        if (bookedTables.equals(MainApp.tables)) {
            if (MainApp.DEBUG) System.out.println("DEBUG: Walk-in check");
            Table tab;
            while (iter.hasNext()) {
                tab = iter.next();
                if (tab.getState() != TableState.TABLE_VACATED) iter.remove();
            }
        } else {
            if (MainApp.DEBUG) System.out.println("DEBUG: Reservation check");
            tablesByNumPax.removeAll(bookedTables);
        }
        return tablesByNumPax;
    }

}
