package sg.edu.ntu.scse.cz2002.ui;

import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Reservation;
import sg.edu.ntu.scse.cz2002.objects.person.Staff;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Staff Management UI
 *
 * @author Kenneth Soh, Weibin
 * @version 1.1
 * @since 2019-03-30
 */
public class StaffMgmtMenuUI extends BaseMenu {

	/**
	 * The Staff Management Menu
	 * 
	 * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
	 */
	@Override
	protected int generateMenuScreen() {
		printHeader("Staff Management Menu");
		System.out.println("1) Create new Staff");
		System.out.println("2) Edit Staff Information");
		System.out.println("3) Remove Staff");
		System.out.println("4) List All Staff");
		System.out.println("5) Return to Main Menu");
		System.out.println("0) Exit");
		printBreaks();

		int choice = doMenuChoice(5, 0);
		switch (choice) {
		case 1:
			this.createNewStaff();
			break;
		case 2:
			this.editStaffInfo();
			break;
		case 3:
			this.removeStaff();
			break;
		case 4:
			this.listAllStaff();
			break;
		case 5:
			return -1;
		case 0:
			return 1;
		default:
			throw new IllegalStateException("Invalid Choice (Staff Menu)");
		}
		return 0;
	}

	/**
	 * Method for creating a new Staff Staff's id will be generated starting from
	 * '1001' Each Staff ID is unique, keep incrementing if already taken Assigned
	 * ID once non-taken ID is found.
	 */
	private void createNewStaff() {
		int newId = 1001; // ID starts from 1001
		String name, title;
		char gender;
		int choice;
		Staff s = null;

		printHeader("Create New Staff");
		Scanner input = new Scanner(System.in);

		System.out.print("Enter Staff's name: ");
		name = input.nextLine();
		System.out.println("1) M");
		System.out.println("2) F");
		do {
			System.out.print("Select Staff's gender (M/F): ");
			try {
				choice = input.nextInt();
				if (choice > 2 || choice < 1)
					System.out.println("Invalid Selection. Please select an option from 1 or 2");
			} catch (InputMismatchException e) {
				choice = -1;
				System.out.println("Invalid Input. Please only enter numbers");
				input.nextLine();
			}
		} while (choice > 2 || choice < 1);

		if (choice == 1)
			gender = 'M';
		else
			gender = 'F';

		System.out.println("1) Manager");
		System.out.println("2) Assistant Manager");
		System.out.println("3) Supervisor");
		System.out.println("4) Waiter");
		do {
			System.out.print("Select Staff's job title: ");
			try {
				choice = input.nextInt();
				if (choice > 4 || choice < 1)
					System.out.println("Invalid Selection. Please select an option from 1 - 4");
			} catch (InputMismatchException e) {
				choice = -1;
				System.out.println("Invalid Input. Please only enter numbers");
				input.nextLine();
			}
		} while (choice > 4 || choice < 1);

		switch (choice) {
		case 1:
			title = "Manager";
			break;
		case 2:
			title = "Assistant Manager";
			break;
		case 3:
			title = "Supervisor";
			break;
		case 4:
			title = "Waiter";
			break;
		default :
			title = "Waiter";
			break;
		}

		for (Staff st : MainApp.staffs) {
			if (st.getStaffId() == newId) {
				newId++;
			}
		}

		s = new Staff(newId, name, gender, title);
		MainApp.staffs.add(s);
		System.out.println("Successfully added Staff");
		System.out.printf("%-5s %-20s %-6s %-9s\n", "ID", "Name", "Gender", "Title");
		printBreaks();
		System.out.printf("%-5s %-20s %-6s %-9s\n", s.getStaffId(), s.getStaffName(), s.getGender(),
				s.getJobTitle());
	}

	/**
	 * Method to edit either Staff's name, gender or title
	 */
	private void editStaffInfo() {
		int choice;
		String name, title;
		char gender;
		Scanner input = new Scanner(System.in);

		listAllStaff();
		printBreaks();
		do {
			System.out.print("Enter index of Staff to edit: ");
			try {
				choice = input.nextInt();
				if (choice > MainApp.staffs.size() || choice < 1)
					System.out.println("Invalid Selection. Please select an option from 1 - " + MainApp.staffs.size());
			} catch (InputMismatchException e) {
				choice = -1;
				System.out.println("Invalid Input. Please only enter numbers");
				input.nextLine();
			}
		} while (choice > MainApp.staffs.size() || choice < 1);

		Staff st = MainApp.staffs.get(choice - 1);
		System.out.printf("%-5s %-20s %-6s %-9s\n", "ID", "Name", "Gender", "Title");
		printBreaks();
		System.out.printf("%-5s %-20s %-6s %-9s\n", st.getStaffId(), st.getStaffName(), st.getGender(),
				st.getJobTitle());
		printBreaks();
		System.out.println("1) Edit Name");
		System.out.println("2) Edit Gender");
		System.out.println("3) Edit Job Title");
		System.out.println("0) Return to Staff Management Menu");

		do {
			System.out.print("Enter edit option: ");
			try {
				choice = input.nextInt();
				if (choice > 3 || choice < 0)
					System.out.println("Invalid Selection. Please select an option from 1 - 3");
			} catch (InputMismatchException e) {
				choice = -1;
				System.out.println("Invalid Input. Please only enter numbers");
				input.nextLine();
			}
		} while (choice > 3 || choice < 0);
		input.nextLine();

		switch (choice) {
		case 1:
			System.out.print("Enter Staff's name: ");
			name = input.nextLine();
			st.setStaffName(name);
			break;
		case 2:
			System.out.println("1) M");
			System.out.println("2) F");
			do {
				System.out.print("Select Staff's gender (M/F): ");
				try {
					choice = input.nextInt();
					if (choice > 2 || choice < 1)
						System.out.println("Invalid Selection. Please select an option from 1 or 2");
				} catch (InputMismatchException e) {
					choice = -1;
					System.out.println("Invalid Input. Please only enter numbers");
					input.nextLine();
				}
			} while (choice > 2 || choice < 1);

			if (choice == 1)
				gender = 'M';
			else
				gender = 'F';

			st.setGender(gender);
			break;
		case 3:
			System.out.println("1) Manager");
			System.out.println("2) Assistant Manager");
			System.out.println("3) Supervisor");
			System.out.println("4) Waiter");
			do {
				System.out.print("Select Staff's job title: ");
				try {
					choice = input.nextInt();
					if (choice > 4 || choice < 1)
						System.out.println("Invalid Selection. Please select an option from 1 - 4");
				} catch (InputMismatchException e) {
					choice = -1;
					System.out.println("Invalid Input. Please only enter numbers");
					input.nextLine();
				}
			} while (choice > 4 || choice < 1);

			switch (choice) {
			case 1:
				title = "Manager";
				break;
			case 2:
				title = "Assistant Manager";
				break;
			case 3:
				title = "Supervisor";
				break;
			case 4:
				title = "Waiter";
				break;
			default :
				title = "Waiter";
				break;
			}
			
			st.setJobTitle(title);
			break;
		}
		System.out.println("Successfully updated Staff's information");
		System.out.printf("%-5s %-20s %-6s %-9s\n", "ID", "Name", "Gender", "Title");
		printBreaks();
		System.out.printf("%-5s %-20s %-6s %-9s\n", st.getStaffId(), st.getStaffName(), st.getGender(),
				st.getJobTitle());
		printBreaks();
	}

	/**
	 * Method for removing Staff
	 */
	private void removeStaff() {
		int choice;
		Scanner input = new Scanner(System.in);

		listAllStaff();
		printBreaks();
		do {
			System.out.print("Enter index of Staff to remove: ");
			try {
				choice = input.nextInt();
				if (choice > MainApp.staffs.size() || choice < 1)
					System.out.println("Invalid Selection. Please select an option from 1 - " + MainApp.staffs.size());
			} catch (InputMismatchException e) {
				choice = -1;
				System.out.println("Invalid Input. Please only enter numbers");
				input.nextLine();
			}
		} while (choice > MainApp.staffs.size() || choice < 1);
		
		MainApp.staffs.remove(choice-1);
		System.out.println("Successfully removed Staff");
	}
	/**
	 * Method for listing all Staff
	 */
	private void listAllStaff() {
		int i = 1;
		printHeader("List of All Staff");
		System.out.printf("%-5s %-5s %-20s %-6s %-9s\n", "Index", "ID", "Name", "Gender", "Title");
		printBreaks();
		for (Staff st : MainApp.staffs) {
			System.out.printf("%-5s %-5s %-20s %-6s %-9s\n", i, st.getStaffId(), st.getStaffName(), st.getGender(),
					st.getJobTitle());
			i++;
		}
	}
}
