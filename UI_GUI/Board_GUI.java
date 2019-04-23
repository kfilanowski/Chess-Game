package UI_GUI;

import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Model.Position;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Board_GUI extends Application implements BoardStrategy {

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
        GameScreen game = GameScreen.getInstance();
        game.setup();

        Scene scene = new Scene(game.getRoot(), 1200, 650);
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
}