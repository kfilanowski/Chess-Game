package UI_GUI;

import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Model.Position;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Board_GUI extends Application implements BoardStrategy {

    @Override
    public void draw(BoardIF board) {

    }

    @Override
    public BoardStrategy clone() {
        return null;
    }

    @Override
    public void go(BoardIF board) {
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
        BorderPane root = new BorderPane();
        root.setCenter(createBoardGrid());
        //root.setTop();
        //root.setLeft();
        //root.setRight();
        //root.setBottom();

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public GridPane createBoardGrid() {
        GridPane grid = new GridPane();
        grid.setMinSize(100, 100);
        grid.setPrefSize(300, 300);
        grid.setMaxSize(500, 500);
        grid.setStyle("-fx-background-color: #555555");

        
        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }

}