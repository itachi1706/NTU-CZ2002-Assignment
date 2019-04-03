package sg.edu.ntu.scse.cz2002.features;

import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * The Reservation Class
 *
 * @author Francis Lim
 * @version 1.0
 * @since 2019-03-29
 */

public class Reservation {
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
     * Constructor for Reservation object
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

    public int getResvId() {
        return resvId;
    }

    public void setResvId(int resvId) {
        this.resvId = resvId;
    }

    public LocalDate getResvDate() {
        return resvDate;
    }

    public void setResvDate(LocalDate resvDate) {
        this.resvDate = resvDate;
    }

    public LocalTime getResvTime() {
        return resvTime;
    }

    public void setResvTime(LocalTime resvTime) {
        this.resvTime = resvTime;
    }

    public ReservationSession getResvSession() {
        return resvSession;
    }

    public void setResvSession(ReservationSession resvSession) {
        this.resvSession = resvSession;
    }

    public int getNumPax() {
        return numPax;
    }

    public void setNumPax(int numPax) {
        this.numPax = numPax;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustTelNo() {
        return custTelNo;
    }

    public void setCustTelNo(String custTelNo) {
        this.custTelNo = custTelNo;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    /**
     * This function executes the steps to make a reservation.
     * It will check the availability of the table via the Table class, and (other stuff).
     * @param tbl The Table object that is involved in the reservation
     * @return An integer value depicting success or failure of reservation creation.
     * Return value of 1 indicates success, 0 indicates error
    private int makeReservation(Table tbl)
    {
        if (!tbl.checkReserved()) {

        }
        return 0;
    }*/

    private boolean checkTableReserved (Table tbl) {
        return tbl.checkReserved();
    }

}
