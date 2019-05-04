package Interfaces;
import javafx.scene.Scene;


import UI_GUI.Board_GUI;
import javafx.stage.Stage;

import java.util.Stack;

public interface ScreenChangeHandler {
    public static final int MAINSCREEN = 1;
    public static final int PLAYERNAMESCREEN = 2;
    public static final int SETTINGSSCREEN = 3;
    public static final int CPU = 4;
    public static final int ONLINE = 5;
    public static final int RULES= 6;
    public static final int TUTORIAL = 7;
    public static final int GAMESCREEN= 8;

    /**Sub screens must call this to switch screen.
     * @param screen The screen to show**/
    public void switchScreen(int screen);
}
