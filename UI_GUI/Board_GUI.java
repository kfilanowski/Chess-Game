package UI_GUI;

import Controller.GameController_GUI;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.ScreenChangeHandler;
import Model.Position;
import colorama.ColorChooser;
import colorama.ColorScene;
import colorama.VolumeControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Board strategy that is called upon for the board.
 * 
 * @author Kevin Filanowski 30%
 */
public class Board_GUI extends Application implements BoardStrategy, ScreenChangeHandler {

    public static int boardSettings;

    /** The main window of the program. */
    private static Stage primaryStage;

    /** The scene of the stage **/
    private Scene scene;

    /** The root of scene A and scene H **/
    private Pane rootA, rootB, rootC, rootD, rootE, rootF, rootG, rootH;

    /** Get the instance of this application **/
    private static Board_GUI instance;

    /** The screens that can be used by this application **/
    public enum Screens {
        ColorScreen, ColorChooser, Volume
    };

    /** The root pane that all screens fit in to **/
    private Pane root;

    /** An instance of the GameController class. */
    private GameController_GUI gc;


    boolean  runOnce = true;

    @Override
    public void draw(BoardIF board) {

    }

    /**
     * Begins the drawing of the GUI.
     * 
     * @param board - A reference to the board.
     */
    @Override
    public void go(BoardIF board) {
        gc = new GameController_GUI(board);
        GameScreen.getInstance(board, gc);
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
        boardSettings = 0;
        MainMenu menu = new MainMenu();
        menu.setScreenChangeHandler(this);
        InputNameScreen nameScreen = InputNameScreen.getInstance(gc);
        nameScreen.setScreenChangeHandler(this);
        GameScreen game = GameScreen.getInstance();
        game.setup();
        game.setScreenChangeHandler(this);
        SettingsScreen settings = SettingsScreen.getInstance();
        settings.settingScene();
        settings.setScreenChangeHandler(this);
        menu.setup();
        OnlinePlay online = new OnlinePlay();
        online.setScreenChangeHandler(this);
        PlayerVsCpu cpu = new PlayerVsCpu();
        cpu.setScreenChangeHandler(this);
        ChessRules rules = new ChessRules();
        rules.setScreenChangeHandler(this);
        Tutorial tutorial = new Tutorial();
        tutorial.setScreenChangeHandler(this);
        
        rootA = menu.getRoot();
        rootB = nameScreen.getRoot();
        rootC = settings.getRoot();
        rootD = cpu.getRoot();
        rootE = online.getRoot();
        rootF = rules.getRoot();
        rootG = tutorial.getRoot();
        rootH = game.getRoot();
        this.primaryStage = primaryStage;
        scene = new Scene(menu.getRoot());
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(675);
        primaryStage.setMinHeight(500);

        primaryStage.show();

        System.out.println("stage height: " + primaryStage.getHeight());
        System.out.println("stage width: " + primaryStage.getWidth());
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
        if(screen == ScreenChangeHandler.MAINSCREEN){
            scene.setRoot(rootA);
        }else if(screen == ScreenChangeHandler.PLAYERNAMESCREEN){
            scene.setRoot(rootB);
        }else if(screen == ScreenChangeHandler.SETTINGSSCREEN){
            scene.setRoot(rootC);
        }else if(screen == ScreenChangeHandler.CPU){
            scene.setRoot(rootD);
        }else if(screen == ScreenChangeHandler.ONLINE){
            scene.setRoot(rootE);
        }else if(screen == ScreenChangeHandler.RULES){
            scene.setRoot(rootF);
        }else if(screen == ScreenChangeHandler.TUTORIAL){
            scene.setRoot(rootG);
        }else if(screen == ScreenChangeHandler.GAMESCREEN){
            scene.setRoot(rootH);
            boardSettings = 1;
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
                scene = new Scene(root, 675, 500);
            else
                scene.setRoot(root);

            runOnce = false;
        }//end switchUI

    public static Board_GUI getInstance() {
        return instance;
    }//end getInstance

    public static Stage getPrimaryStage(){
        return primaryStage;
    }


}