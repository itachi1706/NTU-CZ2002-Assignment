package sg.edu.ntu.scse.cz2002.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Helper class for conversion of Calendar values to printable String values
 *
 * Helper class also contains validation methods for validating whether a certain date is valid
 *
 * @author Francis Lim
 * @version 1.0
 * @since 2019-03-29
 */

public class DateTimeFormatHelper {

    /**
     * Method for formatting Calendar values into formatted String
     * @param date Calendar object date
     * @return String of date formatted in DD/MM/YYYYY.
     */
    public static String formatToStringDate(Calendar date)
    {
        int day, month, year, hour, minute = 0;
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH) + 1;
        day = date.get(Calendar.DATE);
        hour = date.get(Calendar.HOUR_OF_DAY);
        minute = date.get(Calendar.MINUTE);
        String formatDate = day + "/" + month + "/" + year + " " + hour + ":" + minute;
        return formatDate;
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

    /**
     * Method to get today's date and time
     * @return Date object containing today's date and time value
     */
    public static Date getTodayDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Method for comparing if input date is after current date/time.
     * @return True is input date is after today, false if is same or before today.
     */
    public static boolean compareToday(Calendar inputDate){
        return inputDate.after(Calendar.getInstance().getTime());
    }

    /**
     * Formats a passed in String to a Calendar object
     * The String must strictly follow a given format: DD/MM/YYYY HH:MM
     * @param dateTime String containing date time in the specified format
     * @return A Calendar variable
     * @throws ParseException When an incorrect format of date and time String has been passed in
     */
     public static Calendar formatToCalendarDate(String dateTime) throws ParseException {
         SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm");
         Calendar cal = Calendar.getInstance();
         cal.setTime(sdf.parse(dateTime));
         return cal;
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
}
