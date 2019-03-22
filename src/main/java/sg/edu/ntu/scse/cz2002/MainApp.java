package sg.edu.ntu.scse.cz2002;

import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;
import sg.edu.ntu.scse.cz2002.ui.MainMenuUI;
import sg.edu.ntu.scse.cz2002.util.MenuItemCSVHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Main Application Class
 * Entry point for the RRPSS application
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-16
 */
public class MainApp {

    /**
     * The list of menuitems loaded into the program
     */
    public static ArrayList<MenuItem> menuItems;

    /**
     * Initializes all of the necessary items of the application
     * Items include loading saved menu items etc
     */
    private static void init() {
        // TODO: Init Items
        MenuItemCSVHelper menuItemCsv = new MenuItemCSVHelper("menu.csv");
        try {
            System.out.println("Loading Menu Items from file...");
            menuItems = menuItemCsv.readFromCsv();
            System.out.println(menuItems.size() + " menu items loaded from file");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] Failed to read CSV from data folder. (" + e.getLocalizedMessage() + ")");
        }
        System.out.println("Initializing Program...");
    }

    /**
     * Pre-exit actions to be executed here
     */
    private static void shutdown() {
        // TODO: Do pre shutdown items
        MenuItemCSVHelper menuItemCSVHelper = new MenuItemCSVHelper("menu.csv");
        try {
            System.out.println("Saving current menu item list to file...");
            menuItemCSVHelper.writeToCsv(menuItems);
            System.out.println("Menu Item List Saved!");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] Failed to save menu items to file. (" + e.getLocalizedMessage() + ")");
        }
        System.out.println("Shutting down program...");
        System.exit(0);
    }

    /**
     * The main application entry point
     * @param args Any console arguments entered by the user
     */
    public static void main(String... args) {
        init();
        new MainMenuUI().startMainMenu();
        shutdown();
    }
}
