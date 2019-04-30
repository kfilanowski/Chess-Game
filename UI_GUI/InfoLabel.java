package UI_GUI;

import Handler.AlertHandler;
import javafx.scene.control.Label;

/**
 * InfoLabel class is a label that implements the AlertHandler interface.
 * This label will display any alerts or information through the label when 
 * called upon. It is effectively a listener class.
 * 
 * @author Kevin Filanowski
 * @version April 28, 2019
 */
public class InfoLabel extends Label implements AlertHandler {

    /**
     * Default constructor for an InfoLabel, which just calls upon the default
     * constructor for a Label class.
     */
    public InfoLabel() {
        super();
    }

    /**
     * Constructor for an InfoLabel, which calls upon the constructor for a
     * label. Adds text to the label on initialization.
     * 
     * @param text - The text to set the label to contain.
     */
    public InfoLabel(String text) {
        super(text);
    }

    /**
     * This method may be called by anything connected to this listener, and
     * notifies the class that an alert occured, with a message about the alert.
     * For this particular case, we set the label text to the alert message.
     * 
     * @param alert - A message related to the alert.
     */
    @Override
    public void handle(String alert) {
        this.setText(alert);
    }
}