package UI_GUI;

import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Model.Position;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import Interfaces.*;

public class Board_GUI extends Application implements BoardStrategy, ScreenChangeHandler {

    Stage primaryStage;

    /**The scene of the stage**/
    Scene scene;

    /**The root of scene A and scene B**/
    Pane rootA, rootB;

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
}