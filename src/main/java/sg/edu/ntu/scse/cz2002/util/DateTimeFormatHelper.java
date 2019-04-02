package sg.edu.ntu.scse.cz2002.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Helper class for conversion of Calendar values to printable String values
 *
 * Helper class also contains validation methods for validating whether a certain date is valid
 *
 * @author Francis Lim, Kenneth Soh
 * @version 1.2
 * @since 2019-04-01
 */

public class DateTimeFormatHelper {

    /**
     * Method for formatting LocalDate values into formatted String
     * @param date LocalDate object date
     * @return String of date formatted in d/MM/yyyy.
     */
    public static String formatToStringDate(LocalDate date)
    {
        String day, month, year;
        year = date.getYear() + "";
        month = date.getMonthValue() + "";
        day = date.getDayOfMonth() + "";
        String formatDate = day + "/" + month + "/" + year;
        return formatDate;
    }

    /**
     * Method for formatting LocalTime values into formatted String
     * @param time LocalTime object date
     * @return String of time formatted in HH:mm
     */
    public static String formatToStringTime(LocalTime time)
    {
        String hour, minute;
        hour = time.getHour() + "";
        minute = ((time.getMinute() == 0) ? "00" : time.getMinute()) + "";
        String formatTime = hour + ":" + minute;
        return formatTime;
    }

    /**
     * Formats a passed in String to a LocalDate object
     * The String must strictly follow a given format: DD/MM/YYYY
     * @param date String containing date in the specified format
     * @return A LocalDate variable
     * @throws ParseException When an incorrect format of date and time String has been passed in
     */
    public static LocalDate formatToLocalDate(String date) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        //SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm");
        LocalDate ld = LocalDate.parse(date, formatter);
        //alendar cal = Calendar.getInstance();
        //cal.setTime(sdf.parse(date));
        return ld;
    }

    /**
     * Formats a passed in String to a LocalTime object
     * The String must strictly follow a given format: HH:MM
     * @param time String containing  time in the specified format
     * @return A LocalTime variable
     * @throws ParseException When an incorrect format of date and time String has been passed in
     */
    public static LocalTime formatToLocalTime(String time) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        //SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm");
        LocalTime lt = LocalTime.parse(time, formatter);
        //alendar cal = Calendar.getInstance();
        //cal.setTime(sdf.parse(date));
        return lt;
    }

    /**
     * Method to get date in Calendar object
     * @param getNextMonth boolean variable to determine if getting today's date or date one month from now
     * @return Date object containing today's date and time value
     */
    public static LocalDate getDate(boolean getNextMonth) {
        if (!getNextMonth)
            return LocalDate.now();
        else {
            LocalDate l = LocalDate.now();
            return l.plusDays(30);
        }
    }

    /**
     * Method for comparing if input date is after current date/time.
     * @return True is input date is after today, false if is same or before today.
     */
    public static boolean compareToday(LocalDate inputDate){
        return inputDate.isAfter(LocalDate.now());
    }



    /**
     * Method for validating whether a user-input date is valid
     *
     * Method validates for the following:
     * i) Months that only have 30 days
     * ii) Years that are not leap-years.
     * iii) For years that are leap-years, validate the date
     * iv) Validates if month is valid
     * v) Validates if the date is valid
     *
     * @param d Integer value containing 2-digit DATE
     * @param m Integer value containing 2-digit MONTH
     * @param y Integer value containing YEAR value
     * @return Boolean value determining if the date is valid or invalid
     */
    public static boolean validateDate(int d, int m, int y)
    {
        //Validate non-31 days months.
        if ( (((m == 4) || (m == 6)) || ((m == 9) || (m == 11))) && (d >= 31))
            return false;
            //Validate non-leap years.
        else if (( (y % 4 != 0) || ( (y % 100 == 0) && (y % 400 != 0) )) && ( (m == 2) && (d >= 29) ))
            return false;
            //Validate leap years invalid date.
        else if (( (y % 4 == 0) || ( (y % 100 == 0) && (y % 400 == 0) )) && ( (m == 2) && (d >= 30) ))
            return false;
            //Validate invalid month.
        else if (m < 1 || m > 12)
            return false;
            //Validate invalid date.
        else if (d < 1 || d > 31)
            return false;
            //All validations have been passed, date has no errors.
        else
            return true;
    }

    /**
     * Formats time since Unix Epoch (1/1/2017 00:00:00) to a DateTime String
     * @param millis time in milliseconds since Epoch
     * @return Formatted DateTime String
     */
    public static String formatMillisToDateTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm");
        return sdf.format(new Date(millis));
    }


    /**
     * Method for formatting Calendar values for current date/time into formatted String
     * Note that "today" is a GregorianCalendar date.
     *
     * Method provides the same function as getting today's date, albeit in a String object
     * as compared to a Calendar object.
     *
     * To get today's date as a calendar object, call getTodayDate()
     *
     * @return String of date and time formatted in DD/MM/YYYYY and HH:MM:SS.
     */
    @Deprecated
    public static String formatToStringTodayDateTime()
    {
        Calendar today = new GregorianCalendar();
        int yr = today.get(Calendar.YEAR);
        int mth = today.get(Calendar.MONTH)+1;
        int day = today.get(Calendar.DATE);
        int hr = today.get(Calendar.HOUR_OF_DAY);
        int min = today.get(Calendar.MINUTE);
        int sec = today.get(Calendar.SECOND);
        return day + "/" + mth + "/" + yr + " at " + hr + ":" + min + ":" + sec;
    }
}
