package sg.edu.ntu.scse.cz2002.objects.menuitem;

/**
 * Exception when an Order Item cannot be found in the order
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-04
 */
public class ItemNotFoundException extends Exception {

    /**
     * Constructor for the exception
     * @param message Message you wish to send as part of the exception
     */
    public ItemNotFoundException(String message) {
        super(message);
    }

    /**
     * Prints the exception out
     * @return Exception String
     */
    @Override
    public String toString() {
        return super.toString() + ". Is it declared properly in the database?";
    }
}
