package sg.edu.ntu.scse.cz2002.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.InputMismatchException;

/**
 * Helper class for conversion of Calendar values to printable String values
 * <p>
 * Helper class also contains validation methods for validating whether a certain date is valid
 *
 * @author Francis Lim, Kenneth Soh
 * @version 1.2
 * @since 2019-04-01
 */

public class DateTimeFormatHelper {

    /**
     * Constant attribute determining the factor to convert milliseconds to days
     */
    private final static long MILLIS_TO_DAYS = 1000 * 60 * 60 * 24;
    private final static long TO_UTC_PLUS_8 = 28800000;

    /**
     * Method for formatting LocalDate values into formatted String
     *
     * @param date LocalDate object date
     * @return String of date formatted in d/MM/yyyy.
     */
    public static String formatToStringDate(LocalDate date) {
        String day, month, year;
        year = date.getYear() + "";
        month = date.getMonthValue() + "";
        day = date.getDayOfMonth() + "";
        return day + "/" + month + "/" + year;
    }

    /**
     * Method for formatting LocalTime values into formatted String
     *
     * @param time LocalTime object date
     * @return String of time formatted in HH:mm
     */
    public static String formatToStringTime(LocalTime time) {
        String hour, minute;
        hour = time.getHour() + "";
        minute = ((time.getMinute() == 0) ? "00" : time.getMinute()) + "";
        return hour + ":" + minute;
    }

    /**
     * Formats a passed in String to a LocalDate object
     * The String must strictly follow a given format: DD/MM/YYYY
     *
     * @param date String containing date in the specified format
     * @return A LocalDate variable
     * @throws DateTimeParseException When an incorrect format of date and time String has been passed in
     */
    public static LocalDate formatToLocalDate(String date) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return LocalDate.parse(date, formatter);
    }

    /**
     * Formats a passed in String to a LocalTime object
     * The String must strictly follow a given format: HH:MM
     *
     * @param time String containing  time in the specified format
     * @return A LocalTime variable
     * @throws DateTimeParseException When an incorrect format of date and time String has been passed in
     */
    public static LocalTime formatToLocalTime(String time) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, formatter);
    }

    /**
     * Method to get today's date or 30 days later in LocalDate object
     *
     * @param getNextMonth boolean variable to determine if getting today's date or date one month from now
     * @return LocalDate object containing today's date and time value
     */
    public static LocalDate getTodayDate(boolean getNextMonth) {
        if (!getNextMonth)
            return LocalDate.ofEpochDay(getSysTimeMillisWithSGTimeZone() / MILLIS_TO_DAYS);
        else {
            return LocalDate.ofEpochDay(getSysTimeMillisWithSGTimeZone() / MILLIS_TO_DAYS + 30);
        }
    }

    /**
     * Function to get time now (without date), in UTC+8 (Singapore Time),
     * truncated to include seconds, without nanoseconds
     *
     * @return LocalTime variable containing the time now.
     */
    public static LocalTime getTimeNow() {
        return LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * Method to compare between two times, determining how much time left until the desired time
     *
     * @param time1 first LocalTime attribute
     * @param time2 second LocalTime attribute
     * @return long variable determining the time in minutesremaining from time1 until time2
     */
    public static long getTimeDifferenceMinutes(LocalTime time1, LocalTime time2) {
        return time1.until(time2, ChronoUnit.MINUTES);
    }

    /**
     * Method for comparing if input date is after current date/time.
     *
     * @param inputDate LocalDate variable to be compared
     * @return True is input date is after today, false if is same or before today.
     */
    public static boolean compareIfBeforeToday(LocalDate inputDate) {

        //For debug purposes
        //Printout passed in date and today's date.
        return inputDate.isBefore(LocalDate.ofEpochDay(getSysTimeMillisWithSGTimeZone() / MILLIS_TO_DAYS));
    }

    /**
     * Formats time since Unix Epoch (1/1/2017 00:00:00) to a DateTime String
     *
     * @param millis time in milliseconds since Epoch
     * @return Formatted DateTime String
     */
    public static String formatMillisToDateTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm");
        return sdf.format(new Date(millis));
    }


    /**
     * Checks if resv time is between any of the AM or PM sessions
     *
     * @param resvTime     User input reservation time formatted in HH:mm to LocalTime
     * @param sessionStart time of the start of session
     * @param sessionEnd   time of the end of session
     * @return boolean variable determining whether the resvTime falls between either session
     */
    public static boolean checkResvTimeSession(LocalTime resvTime, LocalTime sessionStart, LocalTime sessionEnd) {
        return ((resvTime.isAfter(sessionStart) && resvTime.isBefore(sessionEnd)) ||
                resvTime.equals(sessionStart) || resvTime.equals(sessionEnd));
    }

    /**
     * Method for validating whether a user-input date is valid
     * <p>
     * Method validates for the following:
     * i) Months that only have 30 days
     * ii) Years that are not leap-years.
     * iii) For years that are leap-years, validate the date
     * iv) Validates if month is valid
     * v) Validates if the date is valid
     *
     * @param date String value unsplit containing date
     * @return Boolean value determining if the date is valid or invalid
     * @throws InputMismatchException         If parsing to integer failed (e.g. received String input)
     * @throws NumberFormatException          If received String input
     * @throws ArrayIndexOutOfBoundsException If user input is anything other than dd/MM/yyyy (e.g. 08/2019343, 08/202019)
     */
    public static boolean validateDate(String date) throws InputMismatchException, NumberFormatException, ArrayIndexOutOfBoundsException {
        try {
            String[] dateSplit;
            dateSplit = date.split("/");
            int d = Integer.parseInt(dateSplit[0]);
            int m = Integer.parseInt(dateSplit[1]);
            int y = Integer.parseInt(dateSplit[2]);
            //Validate non-31 days months.
            if ((((m == 4) || (m == 6)) || ((m == 9) || (m == 11))) && (d >= 31)) {
                System.out.println("The month you have entered does not have more than 30 days.");
                return false;
            }
            //Validate non-leap years.
            else if (((y % 4 != 0) || ((y % 100 == 0) && (y % 400 != 0))) && ((m == 2) && (d >= 29))) {
                System.out.println("The year entered is not a leap year, and as such 29th February does not exist.");
                return false;
            }
            //Validate leap years invalid date.
            else if (((y % 4 == 0) || ((y % 100 == 0) && (y % 400 == 0))) && ((m == 2) && (d >= 30))) {
                System.out.println("February does not have a 30th day.");
                return false;
            }
            //Validate invalid month.
            else if (m < 1 || m > 12) {
                System.out.println("Not a valid month.");
                return false;
            }
            //Validate invalid date.
            else if (d < 1 || d > 31) {
                System.out.println("Not a valid date between 1 and 31.");
                return false;
            }
            //All validations have been passed, date has no errors.
            else
                return true;
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Date has an invalid input. (" + e.getLocalizedMessage() + ")");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid input. (" + e.getLocalizedMessage() + ")");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[ERROR] Date is unable to be parsed through formatter. (" + e.getLocalizedMessage() + "}");
            return false;
        }
    }

    /**
     * Method to add 8 hours to retrieval of system time to make it UTC+8
     * Note: Plus 8 hours = 28,800,000 millis
     *
     * @return currentTimeMillis plus 8 hours in long
     */
    public static long getSysTimeMillisWithSGTimeZone() {
        return System.currentTimeMillis() + TO_UTC_PLUS_8;
    }


}
