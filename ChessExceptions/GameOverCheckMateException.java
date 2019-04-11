package ChessExceptions;

/**
 * This exception is specifically thrown when a game ends because
 * a player is in check mate.
 * @author Jeriah Caplinger (100%)
 * @version 4/10/2019
 */
public class GameOverCheckMateException extends Exception {

    /**
     * Default constructor for the exception with no parameters
     */
    public GameOverCheckMateException(){
        super("Check Mate");
    }

    /**
     * constructor for the exception with a string message
     * @param message a specified message for the exception
     */
    public GameOverCheckMateException(String message){
        super(message);
    }

    /**
     * constructor for the exception with string message and throwable object
     * @param message A specified message for the exception
     * @param cause the cause of the exception
     */
    public GameOverCheckMateException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * constructor for the exception with a throwable object parameter
     * @param cause the cause of the exception
     */
    public GameOverCheckMateException(Throwable cause){
        super(cause);
    }

}
