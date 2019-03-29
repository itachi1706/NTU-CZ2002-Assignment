package sg.edu.ntu.scse.cz2002.ui;

import java.util.Scanner;

/**
 * The Reservation Menu UI
 *
 * @author Kenneth Soh, Francis Lim
 * @version 1.0
 * @since 2019-03-22
 */
public class ReservationMenuUI extends BaseMenu {

    /**
     * The Reservation Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
    @Override
    protected int generateMenuScreen() {
        printHeader("Reservation Booking Management");
        System.out.println("1) Create a new reservation booking");
        System.out.println("2) Check reservation booking");
        System.out.println("3) Remove reservation booking");
        System.out.println("4) List all current reservations");
        System.out.println("5) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(5, 0);
        switch (choice) {
            case 1:
                this.createReservationBooking();
                break;
            case 2:
                // TODO: To Implement
                break;
            case 3:
                // TODO: To Implement
                break;
            case 4:
                // TODO: To Implement
                this.listReservations();
                break;
            case 5:
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
        int numPax;
        printHeader("Create Reservation Booking");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name you would like to reservation to be booked under: ");
        custName = input.next();
        System.out.print("Enter the contact number (this will be used tp identify your reservation upon arrival): ");
        custTelNo = input.next();
        System.out.print("Enter pax amount (table for how many?): ");
        numPax = input.nextInt();

        //TODO: Implement further checks on numPax to determine which table will be reserved.
    }

    private void listReservations() {

    }
}
