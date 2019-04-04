package sg.edu.ntu.scse.cz2002.ui;

import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;
import sg.edu.ntu.scse.cz2002.objects.menuitem.Promotion;
import sg.edu.ntu.scse.cz2002.util.PromoCSVHelper;

import java.io.IOException;
import java.util.Scanner;

/**
 * The Promotion Menu UI
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-22
 */
public class PromotionMenuUI extends BaseMenu {

    /**
     * The Promotion Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
	
	@SuppressWarnings("resource")
	Scanner sc = new Scanner(System.in);
	
    @Override
    protected int generateMenuScreen() {
        printHeader("Promotion Management");
        System.out.println("1) View existing promotions");
        System.out.println("2) Create a new promotion");
        System.out.println("3) Update an existing promotion");
        System.out.println("4) Delete a promotion");
        System.out.println("5) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(5, 0);
        switch (choice) {
        
            case 1: // Prints existing promotions
            	System.out.println("This is the list of current promotions:");
            	printPromotion();
                break;
            case 2: // Create a new promotion
				String newPromoName;
				double newPromoPrice;
				int newPromoMain;
				int newPromoDessert;
				int newPromoDrink;
				
				System.out.println("Enter new promotion set name: ");
				newPromoName = sc.nextLine();
				System.out.println("Enter new promotion set price: ");
				newPromoPrice = sc.nextDouble();
				sc.nextLine(); //need to clear buffer for Int, Float or Double
				System.out.println("Enter new promotion's main ID: ");
				newPromoMain = sc.nextInt();
				sc.nextLine(); //need to clear buffer for Int, Float or Double
				System.out.println("Enter new promotion's dessert ID: ");
				newPromoDessert = sc.nextInt();
				sc.nextLine(); //need to clear buffer for Int, Float or Double
				System.out.println("Enter new promotion's drink ID: ");
				newPromoDrink = sc.nextInt();
				sc.nextLine(); //need to clear buffer for Int, Float or Double
				
				addNewPromotion(newPromoName, newPromoPrice, newPromoMain, newPromoDessert, newPromoDrink);
				
				System.out.println(newPromoName+" promotion has been successfully added.");
                break;
            case 3: //Edit an existing promotion
				int editPromoID;
				String editPromoName;
				double editPromoPrice;
				int editPromoMain;
				int editPromoDessert;
				int editPromoDrink;
				
				System.out.println("Enter the ID of the promotion to be edited: ");
				editPromoID = sc.nextInt();
				sc.nextLine(); //clear for I.F.D.
				
				System.out.println("Enter new name of the promotion to be edited: ");
				editPromoName = sc.nextLine();
				
				System.out.println("Enter the new price of the promotion to be edited: ");
				editPromoPrice= sc.nextDouble();
				sc.nextLine(); //clear for I.F.D.
				
				System.out.println("Enter the main's ID of the promotion to be edited: ");
				editPromoMain = sc.nextInt();
				sc.nextLine(); //clear for I.F.D.
				
				System.out.println("Enter the dessert's ID of the promotion to be edited: ");
				editPromoDessert = sc.nextInt();
				sc.nextLine(); //clear for I.F.D.
				
				System.out.println("Enter the drink's ID of the promotion to be edited: ");
				editPromoDrink = sc.nextInt();
				sc.nextLine(); //clear for I.F.D.
				
				editPromotion(editPromoID, editPromoName, editPromoPrice, editPromoMain, editPromoDessert, editPromoDrink);
				
				System.out.println("Promotion has been successfully edited.");
				
            	break;
            case 4: //Delete an existing promotion
            	
            	int targetPromoID;
            	
				System.out.println("Enter the ID of the promotion to be deleted: ");
				targetPromoID = sc.nextInt();
				sc.nextLine(); //clear for I.F.D.
				
				//calls delete function from below
				deletePromotion(targetPromoID);
				
				System.out.println("The promotion has been successfully deleted.");
				
                break;
            case 5:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Promotion Menu)");
        }
        return 0;
    }
    
    
    
	/**
	 * Prints the CSV File of Promotions. @ Arthur
	 * (uses the globally defined "promotions" ArrayList from MainApp)
	 * (also calls the retrieveMenuItem method from FoodMenuUI)
	 */
	public void printPromotion() {
		for (int i = 0; i < MainApp.promotions.size(); i++) {
			Promotion promotion = MainApp.promotions.get(i);
			
			MenuItem mainItem = FoodMenuUI.retrieveMenuItem(promotion.getPromoMain());
			MenuItem dessertItem = FoodMenuUI.retrieveMenuItem(promotion.getPromoDessert());
			MenuItem drinkItem = FoodMenuUI.retrieveMenuItem(promotion.getPromoDrink());

			System.out.println("|============================|");
			System.out.println("Promotion ID: " + promotion.getPromoID());
			System.out.println("Promotion Name: " + promotion.getPromoName());
			System.out.println("Promotion Price: " + promotion.getPromoPrice());
			System.out.println("Promotion Main: [" + promotion.getPromoMain() + "] "+ mainItem.getName());
			System.out.println("Promotion Dessert: [" + promotion.getPromoDessert() + "] "+ dessertItem.getName());
			System.out.println("Promotion Drink: [" + promotion.getPromoDrink() + "] "+ drinkItem.getName());
			
		}
	}

	/**
	 * Adds a new promotion. @ Arthur
	 * (uses "writeToCsv" to facilitate I/O operations from PromotionCSVHelper.)
	 * @params newPromoName <Name of the new promotion to be added.>
	 * @params newPromoPrice <Price of the new promotion to be added.>
	 * @params newPromoMain <ID of the promotion's main.>
	 * @params newPromoDessert <ID of the promotion's dessert.>
	 * @params newPromoDrink <ID of the promotion's drink.>
	 */
	public void addNewPromotion(String newPromoName, double newPromoPrice, int newPromoMain, int newPromoDessert, int newPromoDrink) {
		
		try {
			
			Promotion promotionObj = MainApp.promotions.get((MainApp.promotions.size())-1);
			int promoID = promotionObj.getPromoID()+1;
			
			Promotion promotion = new Promotion(promoID, newPromoName, newPromoPrice, newPromoMain, newPromoDessert, newPromoDrink);
			MainApp.promotions.add(promotion);
			
			PromoCSVHelper promotionHelper = PromoCSVHelper.getInstance();
			promotionHelper.writeToCsv(MainApp.promotions);
			
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}

	/**
	 * Edits an existing promotion. @ Arthur
	 * (uses "writeToCsv" AND "retrievePromotion" to facilitate I/O operations)
	 * @params targetPromoID <ID of the new menu item to be edited.>
	 * @params editPromoName <Name of the new menu item to be edited.>
	 * @params editPromoType <Type of the new menu item to be edited. Can only be defined as the following enum values: Drink, Main or Dessert.>
	 * @params newItemDescription <Description of the new menu item to be edited.>
	 * @params newItemPrice <Price of the new menu item to be edited.>
	 */
	public void editPromotion(int targetPromoID, String editPromoName, double editPromoPrice, int editPromoMain, int editPromoDessert, int editPromoDrink) {

		PromoCSVHelper promoHelper = PromoCSVHelper.getInstance();
		
		for (int i=0; i<(MainApp.promotions.size()); i++) {
			
			Promotion promoObj = MainApp.promotions.get(i);
			if (targetPromoID == promoObj.getPromoID()) {
				try {
					
					Promotion promo = retrievePromotion(targetPromoID);
					
					promo.setPromoName(editPromoName);
					promo.setPromoPrice(editPromoPrice);
					promo.setPromoMain(editPromoMain);
					promo.setPromoDessert(editPromoDessert);
					promo.setPromoDrink(editPromoDrink);
					//at this point, the object has been edited with the new values
					
					promoHelper.writeToCsv(MainApp.promotions); // calls IO method to save the array into the CSV file
					System.out.println("Target promotion successfully edited!");	
					return;
				}
				catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			}
	
		}

		System.out.println("Edit failed. Target menu item not found.");	
		return;		
	}	
	
	/**
	 * Deletes an existing promotion. @ Arthur
	 * (uses "writeToCsv" to facilitate I/O operations)
	 * @params targetPromoID <ID of the promotion to be deleted.>
	 */
	public void deletePromotion(int targetPromoID) {
		PromoCSVHelper promoHelper = PromoCSVHelper.getInstance();
	
		for (int i=0; i<(MainApp.promotions.size()); i++) {
			Promotion promoObj = MainApp.promotions.get(i);
			if (targetPromoID == promoObj.getPromoID()) {
				try {
					MainApp.promotions.remove(i); //delete using i as for loop index
					promoHelper.writeToCsv(MainApp.promotions); // calls IO method to save the new array into the CSV file
					System.out.println("Target promotion has been successfully removed!");	
					return;
				}
				catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			}
		}

		System.out.println("Target promotion not found.");	
		return;		
	}
	
	/**
	 * Returns a Promotion object that matches the input targetPromoID. @ Arthur
	 * @params targetPromoID <ID of the promotion object to be retrieved.>
	 * @return promoObj <Object containing a promotion's attributes.> 
	 */
	@Nullable
	public static Promotion retrievePromotion(int targetPromoID) {
		
		for (int i=0; i<(MainApp.promotions.size()); i++) {
			
			Promotion promoObj = MainApp.promotions.get(i);
			
			if (targetPromoID == promoObj.getPromoID()) {
				System.out.println("Target promotion found.");
				return promoObj;
			}
			
		}
		
		System.out.println("Target promotion not found.");
		return null;
	}
	

}
