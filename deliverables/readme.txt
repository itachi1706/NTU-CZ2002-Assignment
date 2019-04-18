### How to start the program ###
1) Make sure that your java is present in your Windows PATH
2) Run start.bat to launch the program 
(Alternatively you can run the following command in your Command Prompt)
"<path to java.exe> -jar RRPSS.jar"

## How to view the program source files in the Eclipse IDE ##
As we are using the Gradle build system for the project, there might be slightly different methods for opening the project. If not using Eclipse or IntelliJ, there might be a different way needed to open the code.
Or you can instead just open the source files (src/main/java/sg/edu/ntu/scse/cz2002) manually
__Eclipse__
1) Launch Eclipse and select File > Import
2) Select Gradle > Existing Gradle Project
3) Navigate to the root path of the source code (where this file resides in) and press Finish
Note: If there is an error related to Git, just ignore and click OK

__IntelliJ__
1) Launch IntelliJ and select Import Project
2) Navigate and click on the "build.gradle" file
3) Ensure "Use default gradle wrapper" is selected and click OK


The Program is split into various menu items:
1) Managing menu items (adding, editing, removing menu items (ala-carte items) and listing all items)
2) Managing Promotion Sets (adding, editing, removing and listing promotion sets)
3) Managing Orders (create order, view all order, list orders)
4) Managing Reservations (Create, check, remove reservations, list all reservations and do a manual check for expired reservations)
5) Checking availability of a table
6) Doing checkout and printing of invoices for an order
7) Printing sales revenue report per day and for a range period
8) Managing staff for the program

## Order Flow ##
1) Select (3) Order Management
2) Select (1) Create a new order
3) Select the staff ID
4) Specify if the customer has a reservation or not
4.1) If Yes, key in the phone number of the customer's reservation. The system will then check if there is a reservation or not. If there is, the system will return the table allocated in the reservation, otherwise it will proceed to Step 4.2
4.2) If No, enter the amount of people in the party. This will allow the system to allocate a table for you
5) Now you can add an order by selecting (2) Add Item to Order
6) Select if you are adding a promotion or ala-carte item
6.1) If ala-carte item, select the ala-carte category
7) Select the ID of the item you are adding in
8) Enter the quantity you want to add to order
9) Check the item you are adding in (and the total price) and confirm if you wish to add the item to the order or not
10) Repeat step 5 until you are done
10.1) You can always view the full list in the order with (1) View order details
10.2) You can change the quantity or remote the item from the order with (3) and (4) respectively
11) Press (0) Exit Order Editing Screen to exit the order editing screen
11.1) If you ever need to edit this order again you can always press (3) Edit Order and enter the order ID to edit the order again
11.1.1) If you forgot the order ID you can press (2) View Orders and then (2) View Unpaid Orders to view all orders that are editable (as they have not been check out yet)
12) When you are done and ready to checkout head onto the Checkout Flow

## Checkout Flow ##
1) Select (6) Checkout/Print Bill Invoice
2) Select (1) Checkout
3) Enter the table number of the table you are checking out for
4) Select payment mode
4.1) If cash, enter amount of cash provided
5) The receipt will be automatically generated
5.1) Should you wish to view the receipt again, repeat Step (1) and select (2) Reprint Invoice
5.2) Enter receipt ID and you can view the receipt again

## Reservation Flow ##
1) Select (4) Reservation Management
2) Select (1) Create a new reservation booking
3) Enter name of customer which the reservation will be booked under
4) Enter telephone number of the customer - identifier for reservations
5) Enter date in dd/MM/yyyy for the intended date of reservation
5.1) If date is invalid (e.g. 30th day in February, 31st day in April, 29th February when it is not leap year, invalid date, month, year, etc), user will be prompted to input date again.
5.2) If input date is valid
5.2.1) If input date has no reservation slots available, request user to input another date
5.2.2) If input date has reservation slots
5.2.2.1) Console will show number of AM session slots if AM session slots are available, and when PM slots are full.
5.2.2.2) Console will show number of PM session slots if PM session slots are available, and when AM slots are full.
5.2.2.3) Console will show number of AM and PM session slots if both sessions have available slots.
6) Enter number of pax for the reservation
6.1) If number of pax is invalid (i.e. number of pax <= 0 or number of pax > 10), prompt user to input another value.
6.2) If number of pax is valid
6.2.1) If appropriate tables for the number of pax is not available, system will show message that it is unavailable, and return to Reservation Menu.
6.2.2) If appropriate tables are available, system will retrieve the first available table, assign it to the reservation. System will then display successful message and the assigned table number for that reservation, and return to Reservation Menu.
7) User can also (2) Check for reservation booking
8) User will be prompted to input their telephone number to identify reservation booking.
8.1) If telephone number is not found within the list of reservation, display that the reservation is not found. Returns to Reservation menu.
8.2) If telephone number is found, display all of the reservations linked under the single telephone number. Return to Reservation menu.
9) User can also (3) Remove reservation booking.
10) User will be prompted to input telephone number to identify reservation booking.
10.1) If telephone number not found, system shows not found message and returns to Reservation menu,
10.2) If telephone number is found
10.2.1) If telephone number is linked to only 1 reservation, user is prompted to confirm whether they want to delete
10.2.1.1) If yes, proceed with deletion, show successful message and return to Reservation menu
10.2.1.2) If no, return to Reservation menu
10.2.2) If telephone number is linked to more than 1 reservations, system displays all the reservations linked to the telephone number.
10.2.2.1) Prompts user to input reservation ID from the list to delete. If invalid ID, return to reservation menu.
10.2.2.2) Prompts user to input reservation Id from the list to delete. If valid ID, proceed with deletion, show successful message and return to reservation menu.
11) If needed, user can (4) List all current reservations, which will list out every single reservation and return to Reservation Menu.
12) User can (6) Check for expired reservations
12.1) System checks and displays the number of reservatons expired and removed at that point of time.

## Print Sales Revenue Report Flow ##
1) Select (7) Print sale revenue report
2) Select (1) Generate for a day
3) Enter date in dd/MM/yyyy for the date of sale revenue report
3.1) If date is invalid (e.g. 30th day in February, 31st day in April, 29th February when it is not leap year, invalid date, month, year, etc), user will be prompted to input date again.
3.2) If input date is valid
3.2.1) Console will show the every item sold, the total number of order, total sales amount for the date user entered in.
4) User also can select (2) Generate for a period
4.1) Enter date in dd/MM/yyyy for the starting date of sale revenue report 
4.2) Enter date in dd/MM/yyyy for the ending date of sale revenue report
4.3) If date is invalid (e.g. 30th day in February, 31st day in April, 29th February when it is not leap year, invalid date, month, year, etc), user will be prompted to input date again.
4.4) If input date is valid 
4.4.1) Console will show the every item sold, the total number of order, total sales amount for the period (start date to end date) user entered in.