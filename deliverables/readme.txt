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