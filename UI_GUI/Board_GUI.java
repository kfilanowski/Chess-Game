package UI_GUI;

import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import colorama.VolumeControl;
import Model.Position;
import colorama.ColorChooser;
import colorama.ColorScene;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import Interfaces.*;

import java.awt.*;

public class Board_GUI extends Application implements BoardStrategy, ScreenChangeHandler {

    Stage primaryStage;

    /**The scene of the stage**/
    Scene scene;

    /**The root of scene A and scene B**/
    Pane rootA, rootB;

    /**Get the instance of this application**/
    private static Board_GUI instance;

    /**The screens that can be used by this application**/
    public enum Screens {ColorScreen, ColorChooser, Volume};

    /**The root pane that all screens fit in to**/
    private Pane root;


    boolean  runOnce = true;

    @Override
    public void draw(BoardIF board) {

    }

    /**
     * 
     */
    @Override
    public void go(BoardIF board) {
        GameScreen.getInstance(board);
        launch();
    }

    @Override
    public void draw(BoardIF board, Position[] pos) {

    }

    @Override
    public void revDraw(BoardIF board) {

    }

    @Override
    public void getNames() {

    }

    @Override
    public void revDraw(BoardIF board, Position[] pos) {

    }

    /**
     * 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainMenu menu = new MainMenu();
        menu.setScreenChangeHandler(this);
        GameScreen game = GameScreen.getInstance();
        game.setScreenChangeHandler(this);
        game.setup();

        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(900);
        rootA = menu.getRoot();
        rootB = game.getRoot();
        this.primaryStage = primaryStage;
        scene = new Scene(menu.getRoot());
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a deep clone of this object.
     *
     * @return - A deep clone of this objec.
     */
    public Board_GUI clone() {
        return new Board_GUI();
    }

    /**
     * A method that switches the screen to a new one
     * @param screen The screen to show
     */
    @Override
    public void switchScreen(int screen){
        if(screen == ScreenChangeHandler.SCREENA){
            scene.setRoot(rootA);
        }else{
            scene.setRoot(rootB);
        }
    }

    public void switchUI(Screens screen){
        /**Switch the root pane of this screen to change scenes
         * @param screen The choise of screen*/

            switch(screen){
                case ColorScreen:
                    root = ColorScene.getInstance();
                    break;
                case ColorChooser:
                    ColorChooser ch = ColorChooser.getInstance();
                    ch.setScreenChangeHandler(this);


                    root = ch;
                    break;
                case Volume:
                    root = new VolumeControl(0);
                    break;
            }//end switch

            //Change the screen
            if(scene == null)
                scene = new Scene(root,400,600);
            else
                scene.setRoot(root);

            runOnce = false;
        }//end switchUI

    public static Board_GUI getInstance(){
        return instance;
    }//end getInstance


}