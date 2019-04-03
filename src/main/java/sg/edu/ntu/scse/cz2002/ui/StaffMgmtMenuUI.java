package sg.edu.ntu.scse.cz2002.ui;

import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Reservation;
import sg.edu.ntu.scse.cz2002.objects.person.Staff;

import java.util.Scanner;


/**
 * The Staff Management UI
 *
 * @author Kenneth Soh,  Weibin
 * @version 1.1
 * @since 2019-03-30
 */
public class StaffMgmtMenuUI extends BaseMenu {

    /**
     * The Staff Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
    @Override
    protected int generateMenuScreen() {
        printHeader("Staff Management Menu");
        System.out.println("1) Create new Staff");
        System.out.println("2) Edit Staff");
        System.out.println("3) Remove Staff");
        System.out.println("4) Change Staff");
        System.out.println("5) View Current Logged in Staff");
        System.out.println("6) Return to Main Menu");
        System.out.println("0) Exit");
        printBreaks();

        int choice = doMenuChoice(6, 0);
        switch (choice) {
            case 1:
            	this.createNewStaff();
                break;
            case 2:
                // TODO: To Implement
                break;
            case 3:
                // TODO: To Implement
                break;
            case 4:
                // TODO: To Implement
                break;
            case 5:
                // TODO: To Implement
                break;
            case 6:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Staff Menu)");
        }
        return 0;
    }
    
    /**
     * Method for creating a new Staff
     */
    private void createNewStaff()
    {
    	int newId = 1001; //ID starts from 1001
    	String name, title;
    	char gender;
    	Staff s = null;
    	
    	printHeader("Create New Staff");
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter Staff's name: ");
        name = input.nextLine();
        System.out.print("Enter Staff's gender: ");
        gender = input.nextLine().charAt(0);
        System.out.print("Enter Staff's job title: ");
        title = input.nextLine();
        
        for (Staff st : MainApp.staffs)
        {
        	if (st.getStaffId() == newId)
        	{
        		newId++;
        	}
        }
        
        s = new Staff(newId, name, gender, title);
        MainApp.staffs.add(s);
    }
}
