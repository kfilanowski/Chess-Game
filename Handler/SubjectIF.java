package Handler;

/**
 * The interface for the subject that is being observed. holds the needed methods to notify the observer
 *
 * @author Jacob Ginn(100%)
 * @version 05/03/2019
 */
public interface SubjectIF {
    /**
     * adds an observer to the setting screen.
     * @param observer - the Observer to the setting screen that must implement SettingsObserver
     */
    public void addObserver(SettingsObserver observer);

    /**
     * removes an observer from the setting screen
     * @param observer - the Observer to the setting screen that must implement SettingsObserver
     */
    public void removeObserver(SettingsObserver observer);


    /**
     * Show moves notify that notifies the observer
     * @param show - true to show; false to hide
     */
    public void showNotify(boolean show);


    /**
     * undo notify that notifies the observer to enable undo or not
     * @param undo - true to enable; false to disable
     */
    public void undoNotify(boolean undo);

    /**
     * Notifies the max undo that the users are able to do
     * @param numUndo - the number of undo commands that the users can achieve
     */
    public void maxundoNotify(int numUndo);


    /**
     * Sets the number of undos that the users are allowed to unlimited
     * @param unlimUndo - true to enable; false to disable;
     */
    public void unlimNotify(boolean unlimUndo);

    /**
     * notifies the color of the board to be changed to the selected color
     */
    public void colorNotify();
}