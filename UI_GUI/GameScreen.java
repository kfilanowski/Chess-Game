package UI_GUI;

import Factory.ImageFactory;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * The game screen that the two players will play on.
 *
 * @author Kevin Filanowski
 * @version April 19, 2019
 */
public class GameScreen {
    /** A static reference to this class for the Singleton pattern. */
    private static GameScreen instance;

    /** The root of the screen. */
    private BorderPane root;

    /** A reference to the game board. */
    private BoardIF board;

    /** A GridPane for the board. */
    private GridPane grid;

    /**
     * Private Constructor a GameScreen instance using the Singleton pattern.
     */
    private GameScreen() {
        this(null);
    }

    /**
     * Private Constructor a GameScreen instance using the Singleton pattern.
     *
     * @param board - A reference to the game board.
     */
    private GameScreen(BoardIF board) {
        root = new BorderPane();
        grid = new GridPane();
        this.board = board;
        setupBoard();
    }

    /**
     * Retrieves the instance of this class, or creates an instance if one does
     * not already exist.
     *
     * @return - An instance of the GameScreen class.
     */
    public static GameScreen getInstance() {
        return getInstance(null);
    }

    /**
     * Retrieves the instance of this class, or creates an instance if one does
     * not already exist. When an instance is retrieved, the board is set to the
     * parameter.
     *
     * @param board - A reference to the game board.
     * @return - An instance of the GameScreen class.
     */
    public static GameScreen getInstance(BoardIF board) {
        if (instance == null) {
            instance = new GameScreen(board);
        }
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
     * Sets up the board in the center of the screen.
     */
    public final void setupBoard() {
        grid.setMinSize(400, 400);
        grid.setMaxSize(600, 600);
        grid.setAlignment(Pos.CENTER);
        root.setCenter(grid);
        setupSquares();
        // grid.requestFocus();
    }

    /**
     * Draws the pieces on the grid.
     */
    public void drawBoard() {
        // The squares on the board.
        SquareIF[][] squares = board.getSquares();
        // A factory method to retrieve piece images.
        ImageFactory factory = new ImageFactory();
        // Holds a temporary reference to a piece.
        PieceIF p;

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                p = squares[i][j].getPiece();
                if (p != null) {
                    grid.add(factory.getImage(p.getChessPieceType(), p.getColor(),
                            (int) grid.getMaxWidth() / squares.length), j, i, 1, 1);
                }
            }
        }
    }

    /**
     * Sets up the initial squares on the board.
     */
    private final void setupSquares() {
        int size = board.getSquares().length;
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pane pane = new Pane();
                pane.setMinSize(grid.getMaxWidth() / size, grid.getMaxWidth() / size);
                if (count % 2 == 0)
                    pane.setStyle("-fx-background-color: #ffe1b4");
                else
                    pane.setStyle("-fx-background-color: #7d3c0e");
                grid.add(pane, i, j, 1, 1);
                count++;
            }
            count = i % 2 + 1;
        }
    }

    // public final void setupWhitePieces(GridPane grid) {int size =
    // board.getSquares().length; ImageView[] whitePawns = new ImageView[size];
    // ImageView[] whitePieces = new ImageView[size];

    // // Add the row of white pawns. for (int i = 0; i < size; i++)
    // {whitePawns[i] = new ImageView("images/chessPieces/WP.png");
    // whitePawns[i].setPreserveRatio(true);
    // whitePawns[i].setFitWidth(grid.getMaxWidth()/size);
    // whitePawns[i].setSmooth(true); whitePawns[i].setCache(true);
    // grid.add(whitePawns[i], i, 6, 1, 1);
    // }

    // whitePieces[0] = new ImageView("images/chessPieces/WR.png");
    // whitePieces[1] = new ImageView("images/chessPieces/WN.png");
    // whitePieces[2] = new ImageView("images/chessPieces/WB.png");
    // whitePieces[3] = new ImageView("images/chessPieces/WK.png");
    // whitePieces[4] = new ImageView("images/chessPieces/WQ.png");
    // whitePieces[5] = new ImageView("images/chessPieces/WB.png");
    // whitePieces[6] = new ImageView("images/chessPieces/WN.png");
    // whitePieces[7] = new ImageView("images/chessPieces/WR.png");

    // for (int i = 0; i < size; i++) {whitePieces[i].setPreserveRatio(true);
    // whitePieces[i].setFitWidth(grid.getMaxWidth()/size);
    // whitePieces[i].setSmooth(true); whitePieces[i].setCache(true);
    // grid.add(whitePieces[i], i, 7, 1, 1);
    // }

    // }

    // public final void setupBlackPieces(GridPane grid) {int size =
    // board.getSquares().length; ImageView[] blackPawns = new ImageView[size];
    // ImageView[] blackPieces = new ImageView[size];

    // // Add the row of black pawns. for (int i = 0; i < size; i++)
    // {blackPawns[i] = new ImageView("images/chessPieces/BP.png");
    // blackPawns[i].setPreserveRatio(true);
    // blackPawns[i].setFitWidth(grid.getMaxWidth()/size);
    // blackPawns[i].setSmooth(true); blackPawns[i].setCache(true);
    // grid.add(blackPawns[i], i, 1, 1, 1);
    // }

    // blackPieces[0] = new ImageView("images/chessPieces/BR.png");
    // blackPieces[1] = new ImageView("images/chessPieces/BN.png");
    // blackPieces[2] = new ImageView("images/chessPieces/BB.png");
    // blackPieces[3] = new ImageView("images/chessPieces/BK.png");
    // blackPieces[4] = new ImageView("images/chessPieces/BQ.png");
    // blackPieces[5] = new ImageView("images/chessPieces/BB.png");
    // blackPieces[6] = new ImageView("images/chessPieces/BN.png");
    // blackPieces[7] = new ImageView("images/chessPieces/BR.png");

    // for (int i = 0; i < size; i++) {blackPieces[i].setPreserveRatio(true);
    // blackPieces[i].setFitWidth(grid.getMaxWidth()/size);
    // blackPieces[i].setSmooth(true); blackPieces[i].setCache(true);
    // grid.add(blackPieces[i], i, 0, 1, 1);
    // }
    // }
}