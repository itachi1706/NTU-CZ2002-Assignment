package sg.edu.ntu.scse.cz2002.features;

import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;
import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The Reservation Class
 *
 * @author Francis Lim
 * @version 1.0
 * @since 2019-03-29
 */

public class Reservation implements ICsvSerializable {
    /**
     * The sessions enum of the reservation, AM or PM
     */
    public enum ReservationSession {AM_SESSION, PM_SESSION};

    /**
     * Serial number of the reservation, for sorting purposes
     */
    private int resvId;

    /**
     * The reservation date and time. Uses Date library from java.util.
     */
    private LocalDate resvDate;

    /**
     * The reservation date and time. Uses Date library from java.util.
     */
    private LocalTime resvTime;

    /**
     * Session of reservation, AM or PM
     */
    private ReservationSession resvSession;

    /**
     * The customer's telephone number, and candidate key in determining reservation.
     * Can be seen as the reservation ID.
     */
    private String custTelNo;

    /**
     * The name of the customer who made the reservation
     */
    private String custName;

    /**
     * The number of people
     */
    private int numPax;

    /**
     * The table number linked to Reservation
     */
    private int tableNum;

    /**
     * Overriden constructor for Reservation
     * @param id Reservation ID
     * @param rd Reservation Date
     * @param rt Reservation Time
     * @param sess Reservation Session
     * @param telNo Customer Telephone Number
     * @param name Customer Name
     * @param pax Number of pax for table
     * @param t Table number assigned
     */
    public Reservation(int id, LocalDate rd, LocalTime rt, char sess, String telNo, String name, int pax, int t) {
        this.resvId = id;
        this.resvDate = rd;
        this.resvTime = rt;
        this.resvSession = sess == 'A' ?
                ReservationSession.AM_SESSION : ReservationSession.PM_SESSION;
        this.custTelNo = telNo;
        this.custName = name;
        this.numPax = pax;
        this.tableNum = t;
    }

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     * @throws DateTimeParseException When the date time provided from the CSV file has an invalid format,
     * which is unlikely to happen unless CSV file was modified outside of program.
     */
    public Reservation(String[] csv) throws DateTimeParseException {
        this.resvId= Integer.parseInt(csv[0]);
        this.custName = csv[1];
        this.custTelNo = csv[2];
        this.numPax = Integer.parseInt(csv[3]);
        this.resvDate = DateTimeFormatHelper.formatToLocalDate(csv[4]);
        this.resvTime = DateTimeFormatHelper.formatToLocalTime(csv[5]);
        this.resvSession = csv[6].charAt(0) == 'A' ?
                ReservationSession.AM_SESSION : ReservationSession.PM_SESSION;
        this.tableNum = Integer.parseInt(csv[7]);
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        String[] s = new String[8];
        s[0] = this.resvId + "";
        s[1] = this.custName;
        s[2] = this.custTelNo;
        s[3] = this.numPax + "";
        s[4] = DateTimeFormatHelper.formatToStringDate(this.resvDate) + "";
        s[5] = DateTimeFormatHelper.formatToStringTime(this.resvTime) + "";
        s[6] = (this.resvSession == ReservationSession.AM_SESSION ? 'A' : 'P') + "";
        s[7] = this.tableNum + "";
        return s;
    }

    /**
     * Accessor for Reservation ID
     * @return Reservation ID in integer
     */
    public int getResvId() {
        return resvId;
    }

    /**
     * Mutator for Reservation ID
     * @param resvId Reservation ID
     */
    public void setResvId(int resvId) {
        this.resvId = resvId;
    }

    /**
     * Accessor for Reservation Date
     * @return Rservation Date in LocalDate
     */
    public LocalDate getResvDate() {
        return resvDate;
    }

    /**
     * Mutator for Reservation Date
     * @param resvDate Reservation Date
     */
    public void setResvDate(LocalDate resvDate) {
        this.resvDate = resvDate;
    }

    /**
     * Accessor for Reservation Time
     * @return Reservation Time in Local Time
     */
    public LocalTime getResvTime() {
        return resvTime;
    }

    /**
     * Mutator for Reservation TIme
     * @param resvTime Reservation Time
     */
    public void setResvTime(LocalTime resvTime) {
        this.resvTime = resvTime;
    }

    /**
     * Accessor for Reservation Session
     * @return Enum type of ReservationSession
     */
    public ReservationSession getResvSession() {
        return resvSession;
    }

    /**
     * Mutator for Reservation Session
     * @param resvSession Enum type ReservationSession value
     */
    public void setResvSession(ReservationSession resvSession) {
        this.resvSession = resvSession;
    }

    /**
     * Accessor for number of pax
     * @return Number of pax in integer
     */
    public int getNumPax() {
        return numPax;
    }

    /**
     * Mutator for number of pax
     * @param numPax Number of pax
     */
    public void setNumPax(int numPax) {
        this.numPax = numPax;
    }

    /**
     * Accessor for Customer Name
     * @return Customer Name in String
     */
    public String getCustName() {
        return custName;
    }

    /**
     * Mutator for Customer Name
     * @param custName Customer Name
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * Accessor for Customer Telephone Number
     * @return Customer Telephone Number in String
     */
    public String getCustTelNo() {
        return custTelNo;
    }

    /**
     * Mutator for Customer Telephone number
     * @param custTelNo Customer Telephone number
     */
    public void setCustTelNo(String custTelNo) {
        this.custTelNo = custTelNo;
    }

    /**
     * Accessor for Table Number
     * @return Table number in integer
     */
    public int getTableNum() {
        return tableNum;
    }

    /**
     * Mutator for table number
     * @param tableNum Table number in int
     */
    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    /**
     * A method to return a Table object based on the telephone number.
     * Checks through the list of reservations.
     * Invokes method from Table class to return Table object
     * @param telNo Telephone number in String
     * @return Table object. Null if tableNum is eventually -1
     */
    @Nullable
    public static Table hasReservation(String telNo) {
        int tableNum = -1;
        char session = ' ';
        for (Reservation r : MainApp.reservations) {
            if (r.getCustTelNo().equals(telNo)) {
                session = r.getResvSession() == ReservationSession.AM_SESSION ? 'A' : 'P';
                if (r.getResvDate().isEqual(DateTimeFormatHelper.getTodayDate(false))
                        && (session == MainApp.restaurantSession)) {
                    tableNum = r.getTableNum();
                    break;
                }
            }
        }
        return Table.getTableByNumber(tableNum);
    }

    /**
     * This following for-loop does the following functions
     * Retrieves the table numbers that have been booked on the date the user specified
     * and stores into a local instanced ArrayList.
     * This ArrayList will be used for the upcoming for-loops
     *
     * @param resvDate
     * @return
     */
    public static ArrayList<Table> getTablesBookedOnDateBySession(LocalDate resvDate, char resvSession) {
        ArrayList<Table> tablesBooked = new ArrayList<>();
        ReservationSession rs = resvSession == 'A' ?
                ReservationSession.AM_SESSION : ReservationSession.PM_SESSION;

        for (Reservation r : MainApp.reservations) {
            if (r.getResvDate().equals(resvDate) && r.getResvSession().equals(rs))
                tablesBooked.add(Table.getTableByNumber(r.getTableNum()));
        }

        tablesBooked.sort(Comparator.comparingInt(o -> o.getTableNum()));
        return tablesBooked;
    }
}
