package UI_GUI;

import Interfaces.BoardIF;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * The game screen that the two players will play on.
 * 
 * @author Kevin Filanowski
 * @version April 18, 2019
 */
public class GameScreen {
    /** A static reference to this class for the Singleton pattern. */
    private static GameScreen instance;

    /** The root of the screen. */
    private BorderPane root;

    /** A reference to the game board. */
    private BoardIF board;

    /**
     * Private Constructor a GameScreen instance using the Singleton pattern.
     */
    private GameScreen() {
        root = new BorderPane();
        setupCenter();
    }

    /**
     * Retrieves the instance of this class, or creates an instance if one does
     * not already exist.
     *
     * @return - An instance of the GameScreen class.
     */
    public static GameScreen getInstance() {
        if (instance == null) { instance = new GameScreen(); }
        return instance;
    }

    /**
     * Returns the root pane of the screen.
     * 
     * @return - The root pane of the screen.
     */
    public BorderPane getRoot() {
        return root;
    }

    /**
     * Sets the board, copies a reference of the board object.
     * 
     * @param board - A reference to the game board.
     */
    public void setBoard(BoardIF board) {
        this.board = board;
    }

    /**
     * Sets up the screen.
     */
    public final void setupCenter() {
        GridPane grid = new GridPane();
        root.setCenter(grid);
        
        ImageView pawn = new ImageView("images/chessPieces/BP.png");
        grid.add(pawn, 0, 0, 1, 1);

        grid.setMinSize(100, 100);
        grid.setPrefSize(300, 300);
        grid.setMaxSize(500, 500);
        grid.setStyle("-fx-background-color: #555555");
    }
}