package sg.edu.ntu.scse.cz2002.util;

import sg.edu.ntu.scse.cz2002.objects.menuitem.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for CSV I/O of Menu Items
 *
 * @author Arthur Koh, Kenneth Soh
 * @version 1.1
 * @since 2019-03-19
 */
@SuppressWarnings("Duplicates")
public class MenuItemCSVHelper extends CSVBaseHelper {

    /**
     * Path to Menu Items CSV File in the data folder. Defaults to menu.csv
     */
    private String menuItemCsv = "menu.csv";

    /**
     * Singleton instance of this class
     */
    private static MenuItemCSVHelper mInstance;

    /**
     * Default Constructor to initialize this class with menu.csv as the CSV file
     */
    private MenuItemCSVHelper() {}

    /**
     * Initialize the Helper object
     * @deprecated Call {@link MenuItemCSVHelper#getInstance()} instead
     * @param filename Path to MenuItems CSV File
     */
    @Deprecated
    public MenuItemCSVHelper(String filename) {
        this.menuItemCsv = filename;
    }

    /**
     * Gets the singleton instance of MenuItemCSVHelper that reads from menu.csv
     * @return Instance of this class
     */
    public static MenuItemCSVHelper getInstance() {
        if (mInstance == null) mInstance = new MenuItemCSVHelper();
        return mInstance;
    }

    /**
     * Reads the CSV file and parse it into an array list of menu item objects
     * @return ArrayList of Menu Item Objects
     * @throws IOException Unable to read from file
     */
    public ArrayList<MenuItem> readFromCsv() throws IOException {
        BufferedReader csvFile = FileIOHelper.getFileBufferedReader(this.menuItemCsv);
        List<String[]> csvLines = readAll(csvFile, 1);
        ArrayList<MenuItem> items = new ArrayList<>();
        if (csvLines.size() == 0) return items;
        csvLines.forEach((str) -> {
            MenuItem item; // Create based on type
            switch (str[2].toLowerCase()) {
                case "main": item = new MainCourse(str); break;
                case "dessert": item = new Dessert(str); break;
                case "drink": item = new Drink(str); break;
                case "appetizer": item = new Appetizer(str); break;
                default: item = new MenuItem(str); break;
            }
            items.add(item); });
        return items;
    }

    /**
     * Writes to the CSV File
     * @param items ArrayList of items to save
     * @throws IOException Unable to write to file
     */
    public void writeToCsv(ArrayList<MenuItem> items) throws IOException {
        String[] header = {"ID", "Name", "Type", "Price", "Description" };
        BufferedWriter csvFile = FileIOHelper.getFileBufferedWriter(this.menuItemCsv);
        ArrayList<String[]> toWrite = new ArrayList<>();
        toWrite.add(header);
        items.forEach((i) -> toWrite.add(i.toCsv()));
        writeToCsvFile(toWrite, csvFile);
    }
    
    /**
     * Prints the CSV File Menu @ Arthur
     */
	public void printMenu() {
		
		try {
			// read file containing Menu Items.
			ArrayList menuArray =  readFromCsv();
					
			for (int i = 0; i < menuArray.size(); i++) {
				MenuItem menuItem = (MenuItem) menuArray.get(i);
				System.out.println("|============================|");
				System.out.println("ID: " + menuItem.getId());				
				System.out.println("Name: " + menuItem.getName());
				System.out.println("Type: " + menuItem.getType());
				System.out.println("Description: " + menuItem.getDescription());
				System.out.println("Price: " + menuItem.getPrice());
			}
			
		} 
		catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}

	/**
	 * Adds a new item to the CSV file Menu @ Arthur
	 * (uses "readfromCsv" AND "writeToCsv" to facilitate IO operations)
	 * @params newItemName <description of parameter>
	 * @params newItemType 
	 * @params  
	 * 
	 */
	public void addNewMenuItem(String newItemName, String newItemType, String newItemDescription, double newItemPrice) {
		
		try {
			// read file containing Menu Items.
			ArrayList menuArray =  readFromCsv();
		
			int id = menuArray.size()+1; //generates id based off the current size of the menu + 1.
			
			MenuItem menuItem = new MenuItem(id, newItemName, newItemType, newItemDescription, newItemPrice);
			menuArray.add(menuItem); //adds the menuitem object to the menu array
	
			writeToCsv(menuArray); //calls IO method to save the new array into the CSV file!
	
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}	
	
	public void deleteMenuItem(int targetItemID) {
		
		//logic for this would be
		//to do a search using menu item ID
		//and then calling array.remove to remove the menuitem object
		//this would change the id sequence 
		//and then the write.csv method IO would be called
		
		try {
			// read file containing Menu Items.
			ArrayList menuArray =  readFromCsv();

			menuArray.remove(targetItemID-1); //-1 to delete the correct one
			writeToCsv(menuArray); //calls IO method to save the new array into the CSV file!
	
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}	
}
