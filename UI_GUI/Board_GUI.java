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

    @Override
    public void go(BoardIF board) {
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameScreen game = GameScreen.getInstance();

        Scene scene = new Scene(game.getRoot(), 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Board_GUI clone() {
        return new Board_GUI();
    }

    ///////////// delete later, this is for testing purposes. /////////////
    public static void main(String[] args) {
        launch(args);
    }
}