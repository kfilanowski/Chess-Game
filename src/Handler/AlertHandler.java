package Handler;

/**
 * AlertHandler class is essentially the listener that responds to any alert
 * message from some class registered with it.
 * 
 * @author Kevin Filanowski
 * @version April 28, 2019
 */
public interface AlertHandler {

    /**
     * This method may be called by anything connected to this listener, and
     * notifies the class that an alert occured, with a message about the alert.
     * 
     * @param alert - A message related to the alert.
     */
    public void handle(String alert);
}