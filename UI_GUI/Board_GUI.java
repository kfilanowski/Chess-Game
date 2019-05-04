package UI_GUI;

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
 * Class that sets up the Graphical User Interface for the whole project
 */
public class Board_GUI extends Application implements BoardStrategy, ScreenChangeHandler {

    public static int boardSettings;

    private static Stage primaryStage;

    /**The scene of the stage**/
    private Scene scene;

    /**The root of scene A and scene H**/
    private Pane rootA, rootB, rootC, rootD, rootE, rootF, rootG, rootH;

    /**Get the instance of this application**/
    private static Board_GUI instance;

    /**The screens that can be used by this application**/
    public enum Screens {ColorScreen, ColorChooser, Volume};

    /**The root pane that all screens fit in to**/
    private Pane root;


    boolean  runOnce = true;

    /**
     * Not used
     * @param board - The board to display.
     */
    @Override
    public void draw(BoardIF board) {

    }

    /**
     * Go method that calls launch
     */
    @Override
    public void go(BoardIF board) {
        GameScreen.getInstance(board);
        launch();
    }

    /**
     * Not used
     * @param board - The board that the game is being played on.
     * @param pos   - The position array that holds the valid position.
     */
    @Override
    public void draw(BoardIF board, Position[] pos) {

    }

    /**
     * Not used
     * @param board - The board that the game is being played on.
     */
    @Override
    public void revDraw(BoardIF board) {

    }

    /**
     * Not used
     */
    @Override
    public void getNames() {

    }

    /**
     * Not used
     * @param board - The board that the game is being played on.
     * @param pos-  The position array that holds the valid position.
     */
    @Override
    public void revDraw(BoardIF board, Position[] pos) {

    }

    /**
     * The start method that sets up all the Screens for the gui
     * @param primaryStage - The primary stage for the Board_GUI
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        boardSettings = 0;
        MainMenu menu = new MainMenu();
        menu.setScreenChangeHandler(this);
        GameScreen game = GameScreen.getInstance();
        game.setup();
        InputNameScreen nameScreen = InputNameScreen.getInstance();
        nameScreen.setScreenChangeHandler(this);
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


    /**
     * Switch the root pane of this screen to change scenes
     * @param screen The choise of screen
     */
    public void switchUI(Screens screen){
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

    /**
     * Gets an instance of the Board_GUI since its a singleton
     * @return - Board_GUI instance
     */
    public static Board_GUI getInstance() {
        return instance;
    }//end getInstance

    /**
     * Gets the primary stage
     * @return - The stage
     */
    public static Stage getPrimaryStage(){
        return primaryStage;
    }


}