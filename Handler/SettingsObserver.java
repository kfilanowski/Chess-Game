package Handler;

/**
 * The interface for the setting observer that observes the settings screen
 *
 * @author Jacob Ginn (100%)
 * @version 05/03/2019
 */
public interface SettingsObserver {

    /**
     * Updates the showMoves on whether to display or not
     * @param show - true to show; false to hide
     */
    public void moveUpdate(boolean show);

    /**
     * Updates whether Undos can be performed or not
     * @param undo - true to perform; false not to perform
     */
    public void undoUpdate(boolean undo);

    /**
     * Updates the max number of undos that can be performed
     * @param numUndo - the number of undos that can be performed
     */
    public void maxundoUpdate(int numUndo);

    /**
     * Updates whether the user can undo unlimited undos
     * @param unlimUndo - boolean value true for unlimited; false for limited undos
     */
    public void unlimUpdate(boolean unlimUndo);

    /**
     * updates the color of the background of the board.
     * @param white - The hex string of the color that the user wants to have the white squares be
     * @param black - The hex string of the color that the user wants to have the black squares be.
     */
    public void colorUpdate(String white, String black);
}
