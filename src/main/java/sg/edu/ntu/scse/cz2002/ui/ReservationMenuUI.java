package sg.edu.ntu.scse.cz2002.ui;

import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Reservation;
import sg.edu.ntu.scse.cz2002.features.Table;
import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * The Reservation Menu UI
 *
 * @author Kenneth Soh, Francis Lim
 * @version 1.1
 * @since 2019-04-01
 */
public class ReservationMenuUI extends BaseMenu {

    /**
     * The Reservation Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
    @Override
    protected int generateMenuScreen(){
        printHeader("Reservation Booking Management");
        System.out.println("1) Create a new reservation booking");
        System.out.println("2) Check reservation booking");
        System.out.println("3) Remove reservation booking");
        System.out.println("4) List all current reservations");
        System.out.println("5) Check for expired reservations");
        System.out.println("6) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(6, 0);
        switch (choice) {
            case 1:
                this.createReservationBooking();
                break;
            case 2:
                this.checkReservationBooking();
                break;
            case 3:
                this.removeReservationBooking();
                break;
            case 4:
                this.listReservations();
                break;
            case 5:
                this.checkExpiredReservations();
                break;
            case 6:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Reservation Menu)");
        }
        return 0;
    }

    /**
     * Method for creating a new reservation booking
     */
    private void createReservationBooking() {
        String custName, custTelNo;
        //String resvDate, resvTime = "";
        int numPax, tableNum = 0;
        LocalDate resvDate;
        LocalTime resvTime;
        char resvSession;

        Reservation r = null;

        String nextMonthDate = DateTimeFormatHelper.formatToStringDate(DateTimeFormatHelper.getDate(true));

        printHeader("Create Reservation Booking");
        Scanner input = new Scanner(System.in);

        try {

            System.out.print("Enter the name you would like to reservation to be booked under: ");
            custName = input.nextLine();
            System.out.print("Enter the contact number (this will be used tp identify your reservation upon arrival): ");
            custTelNo = input.nextLine();

            System.out.println("Enter reservation date (note that date must be "
                    + nextMonthDate + " or earlier)");
            System.out.println("<Note the date format of dd/mm/yyyy>");
            System.out.print("Your input: ");
            resvDate = DateTimeFormatHelper.formatToLocalDate(input.nextLine());

            System.out.println("Enter reservation time (restaurant operates in 2 sessions: " +
                    "\n11:00 to 15:00, and 18:00 to 22:00)");
            System.out.println("<Note the 24-hour time format of hh:mm>");
            System.out.print("Your input: ");
            resvTime = DateTimeFormatHelper.formatToLocalTime(input.nextLine());

            //TODO: Find a better code for the if fragment below
            if (DateTimeFormatHelper.checkResvTimeSession
                    (resvTime, LocalTime.of(11,0), LocalTime.of(15,0)))
                resvSession = 'A';
            else if (DateTimeFormatHelper.checkResvTimeSession
                    (resvTime, LocalTime.of(18,0), LocalTime.of(22,0)))
                resvSession = 'P';
            else {
                System.out.println("The restaurant is not in operation at the time you entered.");
                return;
            }

            System.out.print("Enter pax amount (table for how many?): ");
            numPax = input.nextInt();

            if (numPax <= 2) {
                for (Table t : MainApp.tables) {
                    if (t.getNumSeats() == Table.TableSeats.TWO_SEATER &&
                            t.getState() == Table.TableState.TABLE_VACATED) {
                        tableNum = t.getTableNum();
                        t.setReserved(true);
                        break;
                    }
                }
            } else if (numPax <= 4) {
                for (Table t : MainApp.tables) {
                    if (t.getNumSeats() == Table.TableSeats.FOUR_SEATER &&
                            t.getState() == Table.TableState.TABLE_VACATED) {
                        tableNum = t.getTableNum();
                        t.setReserved(true);
                        break;
                    }
                }

            } else if (numPax <= 8) {
                for (Table t : MainApp.tables) {
                    if (t.getNumSeats() == Table.TableSeats.EIGHT_SEATER &&
                            t.getState() == Table.TableState.TABLE_VACATED) {
                        tableNum = t.getTableNum();
                        t.setReserved(true);
                        break;
                    }
                }

            } else if (numPax <= 10) {
                for (Table t : MainApp.tables) {
                    if (t.getNumSeats() == Table.TableSeats.TEN_SEATER &&
                            t.getState() == Table.TableState.TABLE_VACATED) {
                        tableNum = t.getTableNum();
                        t.setReserved(true);
                        break;
                    }
                }
            } else {
                System.out.println("Sorry! The restaurant's maximum seating is 10 people.");
            }

            //Conditional loop to determine is available table is found.
            if (tableNum > 0) {
                int lastNum = MainApp.reservations.get(MainApp.reservations.size()-1).getResvId() + 1;
                r = new Reservation(lastNum, resvDate, resvTime, resvSession, custTelNo, custName, numPax, tableNum);
                MainApp.reservations.add(r);

                System.out.println("Your reservation has been successfully recorded! Your assigned table is " + tableNum + ".");
            } else {
                System.out.println("All tables all current booked for the day and session. We're sorry!");
            }

            //TODO: Further validation
        } catch (DateTimeParseException e) {
            //Only thrown for failure to parse Date and Time in custom format
            System.out.println("[ERROR] Input date or time format is wrong. (" + e.getLocalizedMessage() + ")");
        } catch (InputMismatchException e) {
            //Only thrown for failure to parse String to int
            System.out.println("[ERROR] Please input a valid number of pax. (" + e.getLocalizedMessage() + "}");
        }
    }

    /**
     * Method to check reservation booking(s) under a user-input telephone number.
     */
    private void checkReservationBooking() {
        int count = 0;
        Scanner input = new Scanner(System.in);
        printHeader("Checking of reservation booking");
        System.out.print("Enter your telephone number linked to reservation(s): ");
        String telNo = input.nextLine();

        count = printReservationLine(telNo);
        /*printHeader("Below are the reservations linked to the number " + telNo);
        for(Reservation r : MainApp.reservations) {
            if (telNo.equals(r.getCustTelNo())) {
                printReservationLine(r);
                count++ = true;
            }
        }
*/
        if (count == 0) {
            System.out.println("There are no reservation bookings linked to the telephone number.");
        }
    }

    /**
     * Method to remove reservation booking.
     * As the reservation is uniquely identified by telephone number,
     * assume that the user inputs telephone number before deciding which reservation booking to delete.
     */
    private void removeReservationBooking() {
        Scanner input = new Scanner(System.in);
        int count = 0;
        this.listReservations();
        printHeader("Remove Reservation Booking");
        System.out.print("Enter telephone number linked to the reservation to be deleted: ");
        String telNo = input.nextLine();

        count = printReservationLine(telNo);


        if (count == 1) {
            System.out.print("Are you sure you want to delete this reservation (Y/N)? ");
            switch (Character.toUpperCase(input.nextLine().charAt(0))) {
                case 'Y':
                    Iterator<Reservation> i = MainApp.reservations.iterator();
                    while (i.hasNext()) {
                        Reservation r = i.next();
                        if (r.getCustTelNo().equals(telNo))
                            i.remove();
                    }
                    System.out.println("Reservation has been successfully removed.");
                    break;
                case 'N':
                    break;
                default:
                    System.out.println("Invalid option. Returning Reservation Menu...");
                    break;
            }
        } else if (count > 1) {
            System.out.println("System has found " + count + " reservations under the telephone number " + telNo + ".");

            int resvId = ScannerHelper.getIntegerInput("\nEnter the Reservation ID that is to be deleted: ");
            Iterator<Reservation> iter = MainApp.reservations.iterator();
            while (iter.hasNext()) {
                Reservation r = iter.next();
                if (r.getCustTelNo().equals(telNo) && r.getResvId() == resvId) {
                    iter.remove();
                    break;
                }
            }
            System.out.println("Reservation ID " + resvId +
                    " under telephone number " + telNo + " has been successfully removed.");
        }
        else
            System.out.println("There are no reservation bookings linked to the telephone number.");


    }
    /**
     * Method for listing all reservations made by customers, in ascending order of Reservation ID.
     */
    private void listReservations() {
        printHeader("List of all Reservations");
        System.out.printf("%-4s %-15s %-10s %-10s %-10s %-20s %-3s %-9s\n", "ID", "Date", "Session", "Time", "Tel. No", "Name", "Pax", "Table No.");
        printBreaks();
        for (Reservation r : MainApp.reservations) {
            printReservationLine(r);
        }
    }

    /**
     * Checks if the current Reservation object has expired
     * Expiry applies if reservation's date is today, and the time now is 30 minutes or more past the reservation time
     */
    private void checkExpiredReservations() {
        int expiredCount = 0;
        Reservation r;
        Iterator<Reservation> iter = MainApp.reservations.iterator();
        while (iter.hasNext()) {
            r = iter.next();
            if (r.getResvDate().equals(LocalDate.now()))
                if (DateTimeFormatHelper.getTimeDifferenceMinutes(r.getResvTime(), LocalTime.now()) <= 0) {
                    iter.remove();
                    expiredCount++;
                }
        }
        System.out.println(expiredCount + " reservations have since expired, and deleted from the system.");

    }

    private int printReservationLine(String telNo)
    {
        int count = 0;
        System.out.println("Below are the reservations linked to the number " + telNo);
        System.out.printf("%-4s %-15s %-10s %-10s %-10s %-20s %-3s %-9s\n", "ID", "Date", "Session", "Time", "Tel. No", "Name", "Pax", "Table No.");
        printBreaks();
        for(Reservation r : MainApp.reservations) {
            if (telNo.equals(r.getCustTelNo())) {
                printReservationLine(r);
                count++;
            }
        }
        return count;
    }

    /**
     * Method to print details of a single reservation
     * @param r Reservation object
     */
    private void printReservationLine(Reservation r) {
        System.out.printf("%-4d %-15s %-10s %-10s %-10s %-20s %-3d %-9d\n",
                r.getResvId(),
                DateTimeFormatHelper.formatToStringDate(r.getResvDate()),
                r.getResvSession() == Reservation.ReservationSession.AM_SESSION ? 'A' : 'P',
                DateTimeFormatHelper.formatToStringTime(r.getResvTime()),
                r.getCustTelNo(),
                r.getCustName(),
                r.getNumPax(),
                r.getTableNum());
    }


}
