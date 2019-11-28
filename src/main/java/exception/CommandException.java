package exception;

/**
 * The purpose of CommandException is to turn Exceptions from the persistence
 * layer into so-called business exceptions to keep low coupling between the
 * layers and keep the encapsulation of the persistence layer
 *
 * @author carol
 */
public class CommandException extends Exception {

    /**
     * Constructor for CommandException
     *
     * @param msg The message associated with the exception
     */
    public CommandException(String msg) {
        super(msg);
    }

}
