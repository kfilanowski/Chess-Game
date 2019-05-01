package Interfaces;
import javafx.scene.Scene;


import UI_GUI.Board_GUI;
import javafx.stage.Stage;

import java.util.Stack;

public interface ScreenChangeHandler {
    public static final int SCREENA = 1;
    public static final int SCREENB = 2;
    public static final int SCREENC = 3;
    /**Sub screens must call this to switch screen.
     * @param screen The screen to show**/
    public void switchScreen(int screen);
    public void switchUI(Board_GUI.Screens screens);

}
