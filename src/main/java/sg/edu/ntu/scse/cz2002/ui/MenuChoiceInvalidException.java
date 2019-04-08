package sg.edu.ntu.scse.cz2002.ui;

import org.jetbrains.annotations.NotNull;

/**
 * Exception for invalid menu choices
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-08
 */
public class MenuChoiceInvalidException extends IllegalStateException {

    /**
     * Required Constructor to throw this exception
     * @param tag A tag to differentiate where this is thrown from. Compulsory
     */
    public MenuChoiceInvalidException(@NotNull String tag) {
        super("Invalid Choice (" + tag + ")");
    }
}
