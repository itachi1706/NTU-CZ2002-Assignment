package sg.edu.ntu.scse.cz2002.objects.menuitem;

/**
 * Created by Kenneth on 4/4/2019.
 * for sg.edu.ntu.scse.cz2002.objects.menuitem in assignment-fsp6-grp2
 */
public class ItemNotFoundException extends Exception {

    public ItemNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.toString() + ". Is it declared properly in the database?";
    }
}
