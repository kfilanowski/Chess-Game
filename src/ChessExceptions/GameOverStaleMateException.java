package ChessExceptions;

/**
 * This exception is specifically thrown when a game ends because a player is in
 * stale mate.
 *
 * @author Jeriah Caplinger (100%)
 * @version 4/10/2019
 */
public class GameOverStaleMateException extends Exception {

    /**
     * Default constructor for the exception with no parameters
     */
    public GameOverStaleMateException() {
        super("Stale Mate");
    }

    /**
     * constructor for the exception with a string message
     *
     * @param message a specified message for the exception
     */
    public GameOverStaleMateException(String message) {
        super(message);
    }

    /**
     * constructor for the exception with string message and throwable object
     *
     * @param message A specified message for the exception
     * @param cause   the cause of the exception
     */
    public GameOverStaleMateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor for the exception with a throwable object parameter
     *
     * @param cause the cause of the exception
     */
    public GameOverStaleMateException(Throwable cause) {
        super(cause);
    }

}
