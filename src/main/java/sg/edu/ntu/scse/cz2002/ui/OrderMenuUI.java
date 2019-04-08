package sg.edu.ntu.scse.cz2002.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sg.edu.ntu.scse.cz2002.MainApp;
import sg.edu.ntu.scse.cz2002.features.Order;
import sg.edu.ntu.scse.cz2002.features.OrderItem;
import sg.edu.ntu.scse.cz2002.features.Reservation;
import sg.edu.ntu.scse.cz2002.features.Table;
import sg.edu.ntu.scse.cz2002.objects.menuitem.ItemNotFoundException;
import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;
import sg.edu.ntu.scse.cz2002.objects.menuitem.Promotion;
import sg.edu.ntu.scse.cz2002.objects.person.Staff;
import sg.edu.ntu.scse.cz2002.util.DateTimeFormatHelper;
import sg.edu.ntu.scse.cz2002.util.ScannerHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * The Order Menu UI
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-03-22
 */
public class OrderMenuUI extends BaseMenu {

    /**
     * Incomplete {@link Order} that has have its receipt printed yet
     * These orders will not be saved should the system crash
     */
    public static ArrayList<Order> incompleteOrders = null;

    /* Menus */
    /**
     * The Order Management Menu
     * @return Exit Code. Return 1 to exit the program and -1 to exit to main menu
     */
    @Override
    protected int generateMenuScreen() {
        // Init
        if (incompleteOrders == null) incompleteOrders = new ArrayList<>();
        printHeader("Order Management");
        System.out.println("1) Create a new order");
        System.out.println("2) View orders");
        System.out.println("3) Edit order");
        System.out.println("4) Back to main menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(4, 0);
        switch (choice) {
            case 1:
                createOrder();
                break;
            case 2:
                viewOrder();
                break;
            case 3:
                editOrder();
                break;
            case 4:
                return -1;
            case 0:
                return 1;
            default:
                throw new IllegalStateException("Invalid Choice (Order Menu)");
        }
        return 0;
    }

    /**
     * Edit Order Menu UI
     * @param orderNumber Order Number of the order currently being edited
     */
    private void editOrderMenuScreen(final int orderNumber) {
        Order o = findOrder(orderNumber, false);
        if (o == null) {
            System.out.println("Unable to find order. Exiting...");
            return;
        }
        while (true) {
            printHeader("Order #" + orderNumber);
            System.out.println("1) View order details");
            System.out.println("2) Add item to order");
            System.out.println("3) Change item quantity in order");
            System.out.println("4) Remove item from order");
            System.out.println("0) Exit Order Editing Screen");
            printBreaks();

            int choice = doMenuChoice(4, 0);
            switch (choice) {
                case 1:
                    printOrderDetails(o);
                    break;
                case 2:
                    addOrderItem(o);
                    break;
                case 3:
                    editOrderItem(o);
                    break;
                case 4:
                    removeOrderItem(o);
                    break;
                case 0:
                    return;
                default:
                    throw new IllegalStateException("Invalid Choice (Order Edit Menu)");
            }
        }
    }


    /* General Order Methods */

    /**
     * Creates an order and send you to the edit order UI at {@link OrderMenuUI#editOrderMenuScreen(int)}
     */
    private void createOrder() {
        // Ask for staff ID to add order
        printHeader("Staff List");
        IntStream.range(0, MainApp.staffs.size()).forEach((i) -> System.out.println((i+1) + ") " + MainApp.staffs.get(i).getStaffName()));
        printBreaks();
        int staffId = ScannerHelper.getIntegerInput("Enter Staff ID to create the order in (0 to cancel): ", -1);
        if (staffId == 0) {
            System.out.println("Create Order Operation Cancelled");
            System.out.println();
            return; // Cancel Operation
        }
        Staff selectedStaff = MainApp.staffs.get(staffId);

        // Ask for reservation
        Table t = null; // Table that will be used
        if (ScannerHelper.getYesNoInput("Do the customer has an existing reservation?")) {
            // Got reservation
            System.out.print("Enter phone number the customer used for the reservation: ");
            String num = ScannerHelper.getScannerInput().nextLine();
            // Check for reservation
            t = Reservation.hasReservation(num);
            if (t == null) {
                System.out.println("Reservation not found");
                if (!ScannerHelper.getYesNoInput("Create order as walk-in?")) {
                    System.out.println("Cancelling order creation");
                    System.out.println();
                    return;
                }
            }
        }

        if (t == null) {
            // Ask how many people
            int tableSizeNeeded = ScannerHelper.getIntegerInput("How many people in the party? (Max 10): ", 0, 11);
            // Get list of vacant tables matching that criteria
            ArrayList<Table> vacantTables = Table.getVacantTablesByNumPax(tableSizeNeeded, MainApp.tables);
            if (vacantTables == null || vacantTables.size() == 0) {
                System.out.println("There are no tables available that is large enough to accommodate this party");
                System.out.println();
                return;
            }
            t = vacantTables.get(0); // Allocate first possible table as it should be the least
            System.out.println("Allocated Table Number: " + t.getTableNum() + " (Table Size: " + t.getNumSeatsInt() + ")");
        }


        // Create a new order (completed + incompleted check and get ID after)
        int newId = 1;
        if (incompleteOrders.size() > 0) {
            newId = incompleteOrders.get(incompleteOrders.size() - 1).getOrderID() + 1;
        } else if (MainApp.invoices.size() > 0) {
            newId = MainApp.invoices.get(MainApp.invoices.size() - 1).getOrderID() + 1;
        }
        Order o = new Order(newId);
        o.setStaffId(selectedStaff.getStaffId());
        o.setTableId(t.getTableNum());
        incompleteOrders.add(o);
        System.out.println("New Order #" + o.getOrderID() + " created!");
        t.setState(Table.TableState.TABLE_OCCUPIED); // Sets the table specified as occupied
        editOrderMenuScreen(o.getOrderID()); // Bring user to the order item edit screen
    }

    /**
     * View options for Orders
     * You are able to view paid orders, unpaid orders, all orders and specific orders
     */
    private void viewOrder() {
        // Check view completed or view others
        System.out.println("Choose any of the following options");
        System.out.println("1) View Paid Orders");
        System.out.println("2) View Unpaid Orders");
        System.out.println("3) View All");
        System.out.println("4) View Specific Order Details by Order ID");
        System.out.println("5) View Specific Unpaid Order Details by Table Number");
        System.out.println("0) Cancel");

        int choice = doMenuChoice(5, 0);
        switch (choice) {
            case 1:
                // Convert all invoices to a Order arraylist
                printOrderList(new ArrayList<>(MainApp.invoices), "Paid", false);
                break;
            case 2:
                printOrderList(incompleteOrders, "Unpaid", false);
                break;
            case 3:
                ArrayList<Order> consolidate = new ArrayList<>();
                consolidate.addAll(MainApp.invoices);
                consolidate.addAll(incompleteOrders);
                printOrderList(consolidate, "All", false);
                break;
            case 4:
                int orderid = ScannerHelper.getIntegerInput("Enter Order ID: ");
                Order o = findOrder(orderid, true); // Attempt to find order
                if (o == null)
                    System.out.println("No Orders found");
                else
                    printOrderDetails(o);
                break;
            case 5:
                if (incompleteOrders.size() == 0) {
                    System.out.println("No orders found");
                    System.out.println();
                    break;
                }
                HashMap<Integer, Order> tOrders = new HashMap<>();
                incompleteOrders.forEach((s) -> {
                    Table c = s.getTable();
                    if (c == null) return;
                    tOrders.put(c.getTableNum(), s);
                });
                printOrderList(incompleteOrders, "Unpaid", true);
                int table = ScannerHelper.getIntegerInput("Select table number: ", new ArrayList<>(tOrders.keySet()), "Invalid Input. Please make sure you selected a table number");
                printOrderDetails(tOrders.get(table));
                break;
            case 0:
                return;
            default: throw new IllegalStateException("Invalid Choice (View Order Sub Menu)");
        }
    }

    /**
     * Edit Order option
     * This method will request an order number and provide you the relevant edit order page if available
     * If the order is already paid or does not exist, an error message will appear and you will exit back to the Order Menu
     */
    private void editOrder() {
        int orderNo = ScannerHelper.getIntegerInput("Enter Order Number: ");
        Order o = findOrder(orderNo, false);
        if (o == null) System.out.println("Unable to find order. Note that orders that are paid for cannot be edited");
        else editOrderMenuScreen(orderNo);
    }

    /* Edit Order Methods */

    /**
     * Adds item to Order
     * @param o Order Object
     */
    private void addOrderItem(@NotNull Order o) {
        System.out.println("Select Item Type:");
        System.out.println("1) Ala-carte Items");
        System.out.println("2) Promotion Set");
        System.out.println("0) Cancel");
        int selection = doMenuChoice(2, 0);

        switch (selection) {
            case 1:
                addAlaCarteItem(o);
                break;
            case 2:
                if (MainApp.promotions.size() == 0) {
                    System.out.println("No promotions available");
                    return;
                }
                printHeader("Promotion Sets", 40);
                int i = 0;
                for (Promotion p : MainApp.promotions) {
                    // Get each item in promotion
                    System.out.printf("%3d) %-27s $%-6.2f\n", (i+1) , p.getPromoName(), p.getPromoPrice());
                    i++;
                }
                printBreaks(40);
                int promotionSelected = ScannerHelper.getIntegerInput("Enter Set ID (0 to cancel): ", -1, MainApp.promotions.size() + 1) - 1;
                if (promotionSelected < 0) {
                    System.out.println("Operation Cancelled. Returning to Order Edit screen");
                    return;
                }
                int quantity = ScannerHelper.getIntegerInput("Enter Quantity: ", 0);

                // Print promotion detail and ask if we really want to add this
                Promotion p = MainApp.promotions.get(promotionSelected);
                System.out.println();
                printHeader(p.getPromoName() + " Details", 60);
                System.out.println(p.printPromotionDetail());
                System.out.println("Quantity: " + quantity + "");
                System.out.printf("Total Set Price: $%.2f\n", (quantity * p.getPromoPrice()));
                printBreaks(60);
                boolean confirm = ScannerHelper.getYesNoInput("Confirm Promotion Set Selection?");
                if (confirm) {
                    // Add to Order
                    o.getOrderItems().add(new OrderItem(p.getPromoID(), quantity, OrderItem.OrderItemType.TYPE_PROMO));
                    o.calculateSubtotal();
                    System.out.println("Promotion Set Added to Order");
                }
                break;
            case 0: return;
            default: throw new IllegalStateException("Invalid Choice (Order Item Add)");
        }
    }

    private void addAlaCarteItem(@NotNull Order o) {
        System.out.println("Select Ala-Carte Item Types:");
        System.out.println("1) Main Dishes");
        System.out.println("2) Dessert");
        System.out.println("3) Drinks");
        System.out.println("0) Cancel");
        int selection = doMenuChoice(3, 0);
        MenuItem.MenuItemType type;
        switch (selection) {
            case 1: type = MenuItem.MenuItemType.MAIN; break;
            case 2: type = MenuItem.MenuItemType.DESSERT; break;
            case 3: type = MenuItem.MenuItemType.DRINK; break;
            case 0: return;
            default: throw new IllegalStateException("Invalid Choice (Order Ala Carte Item Add");
        }
        ArrayList<MenuItem> foodItems = FoodMenuUI.retrieveMenuItemListFiltered(type);
        if (foodItems.size() == 0) {
            System.out.println("No items in this category. Exiting....");
            return;
        }
        printHeader("List of " + type, 60);
        IntStream.range(0, foodItems.size()).forEach((i) -> {
            MenuItem m = foodItems.get(i);
            System.out.printf("%3d) %-47s $%-6.2f\n", (i+1) , m.getName(), m.getPrice());
        });
        printBreaks(60);
        int itemSel = ScannerHelper.getIntegerInput("Enter Item ID: ", 0, foodItems.size() + 1) - 1;
        if (itemSel < 0) {
            System.out.println("Operation Cancelled. Returning to Order Edit screen");
            return;
        }
        int quantity = ScannerHelper.getIntegerInput("Enter Quantity: ", 0);

        // Print item detail and ask if we really want to add this
        MenuItem mi = foodItems.get(itemSel);
        System.out.println();
        System.out.println("You are about to add the following items to order: ");
        System.out.println(mi.printItemDetail());
        System.out.println("Quantity: " + quantity + "");
        System.out.printf("Total Set Price: $%.2f\n", (quantity * mi.getPrice()));
        boolean confirm = ScannerHelper.getYesNoInput("Confirm?");
        if (confirm) {
            // Add to Order
            o.getOrderItems().add(new OrderItem(mi.getId(), quantity, OrderItem.OrderItemType.TYPE_MENU));
            o.calculateSubtotal();
            System.out.println("Item Added to Order");
        }
    }

    /**
     * Edits item from Order
     * @param o Order Object
     */
    private void editOrderItem(@NotNull Order o) {
        if (o.getOrderItems().size() == 0) {
            System.out.println("No items in order");
            return;
        }

        // View list of order items
        int selection;
        try {
            selection = getOrderItemToEdit(o,"Select an item to change quantity: ");
        } catch (ItemNotFoundException e) {
            System.out.println("An error occurred obtaining items in the order. A brief description of the error is listed below\n" + e.getLocalizedMessage());
            return;
        }
        int quantity = ScannerHelper.getIntegerInput("Enter the new Quantity for the item. (Current Qty: " + o.getOrderItems().get(selection).getQuantity() + "): ", 0);
        // Update quantity
        o.getOrderItems().get(selection).setQuantity(quantity);
        o.calculateSubtotal();
        System.out.println("Quantity has been updated");
    }

    /**
     * Removes item from Order
     * @param o Order Object
     */
    private void removeOrderItem(@NotNull Order o) {
        if (o.getOrderItems().size() == 0) {
            System.out.println("No items in order to remove");
            return;
        }

        int sel;
        try {
            sel = getOrderItemToEdit(o,"Select an item to remove from order: ");
        } catch (ItemNotFoundException e) {
            System.out.println("An error occurred obtaining items in the order. A brief description of the error is listed below\n" + e.getLocalizedMessage());
            return;
        }
        OrderItem i = o.getOrderItems().get(sel);
        String itemName = i.getItemName();
        System.out.println(itemName + " (Qty: " + i.getQuantity() + ") is about to be removed from the order");
        if (ScannerHelper.getYesNoInput("Confirm?")) {
            o.getOrderItems().remove(sel);
            System.out.println("Item removed from order");
        } else {
            System.out.println("Operation cancelled");
        }

    }

    /* Helper Functions */

    /**
     * Finds an order from its Order ID
     * @param id Order ID to find
     * @param allowFromPaid Whether to check from the Paid Orders. Note that those orders cannot be edited
     * @return An order object if found, null otherwise
     */
    @Nullable
    private Order findOrder(int id, boolean allowFromPaid) {
        // Find from incomplete orders first
        for (Order o : incompleteOrders) {
            if (o.getOrderID() == id) return o;
        }
        if (allowFromPaid) {
            // Check completed orders
            for (Order o : MainApp.invoices) {
                if (o.getOrderID() == id) return o;
            }
        }
        return null; // Cannot find
    }

    /**
     * Prints the list of Orders
     * @param orders A list of order
     * @param tag A tag to append to the header
     * @param tableSort Whether to sort by tables Number or by order ID
     */
    public static void printOrderList(@NotNull ArrayList<Order> orders, String tag, boolean tableSort) {
        printHeader("Order List (" + tag + ")", 110);
        if (orders.size() == 0) System.out.println("No orders found");
        else {
            // Sort orders by table number
            if (tableSort) {
                orders.sort((o1, o2) -> {
                    if (o1.getTable() == null) return 1; // o2 higher
                    if (o2.getTable() == null) return -1; // o1 higher
                    return o1.getTable().getTableNum() - o2.getTable().getTableNum();
                });
            }
            orders.forEach(o -> {
                String date = DateTimeFormatHelper.formatMillisToDateTime(o.getCreatedAt());
                String state = (o.getOrderState() == Order.OrderState.ORDER_PAID) ? "Paid" : "Unpaid";
                Table t = o.getTable();
                if (t == null) return; // Don't get no tables ones for some reason
                if (tableSort)
                    System.out.print("Table #" + t.getTableNum() + ": Order #" + o.getOrderID() + ", ");
                else
                    System.out.print("Order #" + o.getOrderID() + ": Table #" + t.getTableNum() + ", ");
                System.out.print("No of Unique items: " + o.getOrderItems().size() + ", Created On: " + date + ", Status: " + state);
                if (o.getOrderState() == Order.OrderState.ORDER_PAID)
                    System.out.print(", Paid On: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCompletedAt()));
                System.out.printf(", Subtotal: %.2f\n", o.getSubtotal());
            });
        }
        printBreaks(110);
    }

    /**
     * Print specific order details
     * @param o Order object to print details of
     */
    private void printOrderDetails(@NotNull Order o) {
        printHeader("Order #" + o.getOrderID() + " Details", 60);
        System.out.println("Order ID: " + o.getOrderID());
        System.out.println("Order State: " + ((o.getOrderState() == Order.OrderState.ORDER_PAID) ? "Paid" : "Unpaid"));
        System.out.println("Order Started On: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCreatedAt()));
        System.out.println("Staff Handling Order: " + ((o.getStaff() == null) ? "Unknown Staff" : "[" + o.getStaff().getStaffId() + "] " + o.getStaff().getStaffName()));
        System.out.println("Table Number: " + ((o.getTable() == null) ? "Unknown Table" : o.getTable().getTableNum()));
        if (o.getOrderState() == Order.OrderState.ORDER_PAID) System.out.println("Order Completed On: " + DateTimeFormatHelper.formatMillisToDateTime(o.getCompletedAt()));
        System.out.println("List of Order Items:");
        printBreaks(60);
        if (o.getOrderItems().size() == 0) System.out.println("No Items in Order");
        else {
            o.calculateSubtotal(); // Calculate Subtotal
            try {
                printOrderItems(o.getOrderItems(), true);
            } catch (ItemNotFoundException e) {
                System.out.println("An error occurred retrieving Order Items. A brief description of the error is listed below\n" + e.getLocalizedMessage());
            }
        }
        printBreaks(60);
        System.out.println("Order Subtotal: $" + String.format("%.2f", o.getSubtotal()));
        printBreaks(60);
        System.out.println("\n");
    }

    /**
     * Gets the list of item from the order and request the user to enter the ID of the item they are selecting
     * @param o Order object
     * @param prompt The prompt to prompt the user. If no prompt pass in an empty string
     * @return the ID of the item they are selecting (based against the arraylist index, NOT the order item's respective ID
     * @throws ItemNotFoundException An item cannot be found in the order
     */
    private int getOrderItemToEdit(@NotNull Order o, String prompt) throws ItemNotFoundException {
        System.out.println("List of items from the order:");
        printOrderItems(o.getOrderItems(), false);
        System.out.println();
        return ScannerHelper.getIntegerInput(prompt, 0, o.getOrderItems().size() + 1) - 1;
    }

    /**
     * Prints the list of items in the order
     * @param items An array list of the items in the order
     * @param prettyPrint Whether or not we should format the string (for displaying in a table format) or as a list format
     * @throws ItemNotFoundException Item not found in the order
     */
    public static void printOrderItems(@NotNull ArrayList<OrderItem> items, boolean prettyPrint) throws ItemNotFoundException {
        int imm = 1;
        for (OrderItem i : items) {
            Object item = i.getItem();
            if (item == null) {
                System.out.println("Item Not Found in Database");
                continue; // Cannot find item, print Unknown Item
            }
            String itemName;
            double price;
            if (i.isPromotion() && item instanceof Promotion) {
                Promotion promo = (Promotion) item;
                itemName = "[PROMO] " + promo.getPromoName();
                price = promo.getPromoPrice();
            } else if (item instanceof MenuItem) {
                MenuItem mi = (MenuItem) item;
                itemName = mi.getName();
                price = mi.getPrice();
            } else throw new ItemNotFoundException("Item is not Promotion or MenuItem"); // Exception for invalid item in database. Exception as we need to handle and fix
            if (prettyPrint) System.out.printf("%3dx %-45s $%-6.2f\n", i.getQuantity(), itemName, price);
            else System.out.println(imm + ") " + i.getQuantity() + "x " + itemName + "\t$" + String.format("%.2f", price));
            imm++;
        }
    }
}
