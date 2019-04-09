package sg.edu.ntu.scse.cz2002.ui;

import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Invoice;
import sg.edu.ntu.scse.cz2002.features.Reservation;
import sg.edu.ntu.scse.cz2002.objects.person.Staff;
import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Menu UI Flow for Sales Revenue Printing
 *
 * @author Wei Bin
 * @version 1.0
 * @since 2019-04-09
 */
public class SalesRevenueReportMenuUI extends BaseMenu {
	@Override
	protected int generateMenuScreen() {
		printHeader("Sales Revenue Report");
		System.out.println("1) Generate for a day");
		System.out.println("2) Generate for a period");
		System.out.println("3) Back to main menu");
		System.out.println("0) Exit Application");
		printBreaks();

		int choice = doMenuChoice(3, 0);
		switch (choice) {
		case 1:
			this.generateDayReport();
			break;
		case 2:
			this.generatePeriodReport();
			break;
		case 3:
			return -1;
		case 0:
			return 1;

		default:
			throw new MenuChoiceInvalidException("Checkout");
			// throw new IllegalStateException("Invalid Choice (Checkout)");
		}
		return 0;
	}

	/**
	 * Method to generate a single day sales revenue report, user to enter 1 date to
	 * generate Get from Invoices ArrayList, add total price from each order to get
	 * total sales print total sales and number of orders
	 */
	private void generateDayReport() {
		ArrayList<Invoice> invoiceOrders = new ArrayList<>();
		String userDate = "";
		boolean correctDate = false;
		LocalDate date = LocalDate.now();
		Scanner input = new Scanner(System.in);
		int d;
		int m;
		int y;
		// While loop to check for a valid date
		while (!correctDate) {
			System.out.println("Enter date to generate report");
			System.out.println("<Note the date format of dd/mm/yyyy>");
			System.out.print("Your input (or enter 0 to exit): ");
			userDate = input.nextLine();

			if (userDate.equals("0"))
				return;
			correctDate = DateTimeFormatHelper.validateDate(userDate);

			if (correctDate) {
				String[] dateSplit = new String[3];
				dateSplit = userDate.split("/");
				d = Integer.parseInt(dateSplit[0]);
				m = Integer.parseInt(dateSplit[1]) - 1;
				y = Integer.parseInt(dateSplit[2]);

				Calendar cal = Calendar.getInstance();
				cal.set(y, m, d);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				double total = 0;
				int noOfOrder = 0;
				long completedDate;
				for (Invoice i : MainApp.invoices) {
					completedDate = i.getCompletedAt();
					Calendar inCal = Calendar.getInstance();
					inCal.setTimeInMillis(completedDate);
					inCal.set(Calendar.HOUR_OF_DAY, 0);
					inCal.set(Calendar.MINUTE, 0);
					inCal.set(Calendar.SECOND, 0);
					inCal.set(Calendar.MILLISECOND, 0);

					if (cal.compareTo(inCal) == 0) {
						total += i.getTotal();
						invoiceOrders.add(i);
						noOfOrder++;
					}
				}

				printHeader("Sales Revenue Report for " + userDate);
				//TODO: Print Individual sales item & quantity
				System.out.println("Total number of order: " + noOfOrder);
				System.out.printf("Total sales amount: $%-6.2f\n", total);

			} else {
				System.out.println("[ERROR] Received invalid date input.");
			}
		}
	}

	/**
	 * Method to generate a period sales revenue report, user to enter 2 dates to
	 * generate Get from Invoices ArrayList, add total price from each order to get
	 * total sales print total sales and number of orders
	 */
	private void generatePeriodReport() {
		ArrayList<Invoice> invoiceOrders = new ArrayList<>();
		String userStartDate = "";
		String userEndDate = "";
		boolean correctDate = false;
		LocalDate date = LocalDate.now();
		Scanner input = new Scanner(System.in);
		int d;
		int m;
		int y;
		int eD;
		int eM;
		int eY;

		// While loop to check for a valid date
		while (!correctDate) {
			System.out.println("Enter starting date");
			System.out.println("<Note the date format of dd/mm/yyyy>");
			System.out.print("Your input (or enter 0 to exit): ");
			userStartDate = input.nextLine();

			if (userStartDate.equals("0"))
				return;

			correctDate = DateTimeFormatHelper.validateDate(userStartDate);

			if (correctDate) {
				System.out.println("Enter ending date");
				System.out.println("<Note the date format of dd/mm/yyyy>");
				System.out.print("Your input (or enter 0 to exit): ");
				userEndDate = input.nextLine();
				if (userEndDate.equals("0"))
					return;
				correctDate = DateTimeFormatHelper.validateDate(userEndDate);

				if (correctDate) {
					String[] sDateSplit = new String[3];
					sDateSplit = userStartDate.split("/");
					d = Integer.parseInt(sDateSplit[0]);
					m = Integer.parseInt(sDateSplit[1]) - 1;
					y = Integer.parseInt(sDateSplit[2]);

					String[] eDateSplit = new String[3];
					eDateSplit = userEndDate.split("/");
					eD = Integer.parseInt(eDateSplit[0]);
					eM = Integer.parseInt(eDateSplit[1]) - 1;
					eY = Integer.parseInt(eDateSplit[2]);

					Calendar sCal = Calendar.getInstance();
					sCal.set(y, m, d);
					sCal.set(Calendar.HOUR_OF_DAY, 0);
					sCal.set(Calendar.MINUTE, 0);
					sCal.set(Calendar.SECOND, 0);
					sCal.set(Calendar.MILLISECOND, 0);
					long sDate = sCal.getTimeInMillis();
					
					Calendar eCal = Calendar.getInstance();
					eCal.set(eY, eM, eD);
					eCal.set(Calendar.HOUR_OF_DAY, 0);
					eCal.set(Calendar.MINUTE, 0);
					eCal.set(Calendar.SECOND, 0);
					eCal.set(Calendar.MILLISECOND, 0);
					long eDate = eCal.getTimeInMillis();
					
					double total = 0;
					int noOfOrder = 0;
					long completedDate;
					for (Invoice i : MainApp.invoices) {
						completedDate = i.getCompletedAt();
						Calendar inCal = Calendar.getInstance();
						inCal.setTimeInMillis(completedDate);
						inCal.set(Calendar.HOUR_OF_DAY, 0);
						inCal.set(Calendar.MINUTE, 0);
						inCal.set(Calendar.SECOND, 0);
						inCal.set(Calendar.MILLISECOND, 0);
						long inDate = inCal.getTimeInMillis();

						if (inDate >= sDate && inDate <= eDate) {
							total += i.getTotal();
							invoiceOrders.add(i);
							noOfOrder++;
						}
					}
					printHeader("Sales Revenue Report for " + userStartDate + " - " + userEndDate);
					//TODO: Print Individual sales item & quantity
					System.out.println("Total number of order: " + noOfOrder);
					System.out.printf("Total sales amount: $%-6.2f\n", total);
				}
			}
			else {
				System.out.println("[ERROR] Received invalid date input.");
			}
		}
	}
}
