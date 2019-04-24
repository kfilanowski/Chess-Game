package UI_GUI;

import com.sun.glass.ui.Screen;

import Controller.GameController_GUI;
import Enums.GameColor;
import Factory.ImageFactory;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Position;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * The game screen that the two players will play on.
 *
 * @author Kevin Filanowski
 * @version April 21, 2019
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

    /** The previously clicked position on the board. */
    private Position last;

    /** Handles game logic that is detached from a GUI. */
    private GameController_GUI gc;

    /** Toggle for whether or not ShowMoves should occur on mouse clicks. */
    private boolean toggleShowMoves;

    /** Displays the white pieces that have been captured. */
    TilePane capturedWhitePieces;

    /** Displays the black pieces that have been captured. */
    TilePane capturedBlackPieces;

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
        this.board = board;
        toggleShowMoves = true;
        root = new BorderPane();
        grid = new GridPane();
        capturedWhitePieces = new TilePane();
        capturedBlackPieces = new TilePane();
        gc = new GameController_GUI(board);
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
     * Creates a vertical list of labels that act as 'ranks' for the board. They
     * are numbers 1 through the number of squares on the board, and the font
     * size and padding is automatically chosen depending on the size of the board.
     * 
     * @return - A vertical list of ranks, or numbers from 1 through the number
     * of squares on the board, added backwards.
     */
    private VBox createRanks() {
        // TODO: Have these paddings and sizes dynamically adjust with resizing.
        VBox ranks = new VBox(11.8);
        ranks.setPadding(new Insets(5, 10, 0, 10));
        Font font = new Font(42);

        for (int i = board.getSquares().length; i > 0; i--) {
            Label rank = new Label("" + i);
            rank.setFont(font);
            ranks.getChildren().add(rank);
        }
        return ranks;
    }

    /**
     * Creates a horizontal list of letters that act as 'files' for the board. They
     * are letters A through however many letters corrosponding to the number of
     * squares on the board, and the font size and padding is automatically chosen 
     * depending on the size of the board.
     * 
     * @return - A horizontal list of files, or letters from A through the number
     * of squares on the board.
     */
    private HBox createFiles() {
        // TODO: Have these paddings and sizes dynamically adjust with resizing.
        HBox files = new HBox(11.8*3);
        files.setPadding(new Insets(0, 0, 0, 62));
        Font font = new Font(42);

        // set files
        for (int i = 'A'; i < 'A' + board.getSquares().length; i++) {
            Label file = new Label(Character.toString((char)i));
            file.setFont(font);
            files.getChildren().add(file);
        }
        return files;
    }


    /**
     * Sets up the board in the center of the screen.
     */
    private void setupCenter() {
        BorderPane center = new BorderPane();
        BorderPane.setAlignment(grid, Pos.TOP_LEFT);

        // Create the rank and files.
        VBox ranks = createRanks();
        ranks.setId("ranks");
        HBox files = createFiles();
        files.setId("files");

        // Setup the board game grid.
        grid.setMaxSize(500, 500);
        grid.setAlignment(Pos.CENTER);
        grid.setId("board");
        setupBoard();
        drawBoard();

        center.setLeft(ranks);
        center.setTop(files);
        center.setCenter(grid);
        root.setCenter(center);
    }

    /**
     * Sets up the top side of the border pane. Adds buttons which include:
     * Load, Save, Undo, Redo, and Settings.
     */
    private void setupTop() {
        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(0, 100, 0, 100));
        topPanel.setAlignment(Pos.CENTER);
        
        // Create the row of buttons.
        Button[] buttons = new Button[5];
        buttons[0] = new Button("Load");
        buttons[1] = new Button("Save");
        buttons[2] = new Button("Undo");
        buttons[3] = new Button("Redo");
        buttons[4] = new Button("Settings");

        for (Button b : buttons) {
            b.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            HBox.setHgrow(b, Priority.ALWAYS);
            b.getStyleClass().add("topButtons");
        }

        //TODO: Add button functionality
        //TODO: Make buttons look prettier.

        topPanel.getChildren().addAll(buttons);
        root.setTop(topPanel);
    }

    /**
     * Sets up the left side of the border pane. The left side of the border
     * pane will display images of the black pieces captured.
     */
    private void setupLeft() {
        VBox leftPanel = new VBox();
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(0, 0, 20, 0));

        // Setup Player labels
        Label playerOne = new Label("Player One");
        Label playerOneName = new Label(gc.getPlayerOneName());
        playerOne.getStyleClass().add("playerLabel");
        playerOneName.getStyleClass().add("playerLabel");

        // TODO: add functonality for button.
        // showMoves toggle button
        ToggleButton showMoves = new ToggleButton("Show Moves");

        // Spacer between captured pieces and button.
        Pane spacer = new Pane();
        spacer.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(1, 10);
        
        // TODO: Properly scale this with screen resizing.
        // Sets up the pane that displays the captured pieces.
        capturedBlackPieces.setMaxSize(grid.getMaxWidth()/4, grid.getMaxWidth()/4);
        capturedBlackPieces.setMinSize(grid.getMaxWidth()/4, grid.getMaxWidth()/4);
        int height = Screen.getMainScreen().getHeight();
        capturedBlackPieces.setPrefTileHeight((height/12));

        leftPanel.getChildren().addAll(playerOne, playerOneName, capturedBlackPieces, spacer, showMoves);
        root.setLeft(leftPanel);
    }

    /**
     * Tells the root pane's left node to add the passed in node as a child.
     * This can be used, for example, to display nodes that were captured.
     * 
     * @param node - The node that was captured.
     */
    private void blackCaptured(Node node) {
        capturedBlackPieces.getChildren().add(node);
    }

    /**
     * Sets up the right side of the border pane. The right side of the border
     * pane will display images of the white pieces captured.
     */
    private void setupRight() {
        VBox rightPanel = new VBox();
        rightPanel.setPadding(new Insets(0, 0, 20, 0));
        rightPanel.setAlignment(Pos.TOP_CENTER);

        // Setup Player labels
        Label playerTwo = new Label("Player Two");
        Label playerTwoName = new Label(gc.getPlayerTwoName());
        playerTwo.getStyleClass().add("playerLabel");
        playerTwoName.getStyleClass().add("playerLabel");

        // TODO: add functonality for button.
        // Exit button
        Button exitButton = new Button("Exit");

        // Spacer between captured pieces and button.
        Pane spacer = new Pane();
        spacer.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(1, 10);

        // TODO: Properly scale this with screen resizing.
        // Sets up the pane that displays the captured pieces.
        capturedWhitePieces.setMinSize(grid.getMaxWidth()/4, grid.getMaxWidth()/4);
        capturedWhitePieces.setMaxSize(grid.getMaxWidth()/4, grid.getMaxWidth()/4);
        int height = Screen.getMainScreen().getHeight();
        capturedWhitePieces.setPrefTileHeight((height/12));
        
        rightPanel.getChildren().addAll(playerTwo, playerTwoName, capturedWhitePieces, spacer, exitButton);
        root.setRight(rightPanel);
    }

    /**
     * Tells the root pane's right node to add the passed in node as a child.
     * This can be used, for example, to display nodes that were captured.
     * 
     * @param node - The node that was captured.
     */
    private void whiteCaptured(Node node) {
        capturedWhitePieces.getChildren().add(node);
    }

    /**
     * Sets up the bottom side of the border pane. The bottom side of the border
     * pane will display the player's currently selected piece, and potentially
     * other types of information.
     */
    private void setupBottom() {
        HBox bottomPanel = new HBox();
        //TODO: fill in the bottom side.
        root.setBottom(bottomPanel);
    }

    /**
     * Sets up each side of the screen.
     */
    public void setup() {
        setupTop();
        setupCenter();
        setupLeft();
        setupRight();
        setupBottom();
    }

    /**
     * Draws the pieces on the grid.
     */
    private void drawBoard() {
        // The squares on the board.
        SquareIF[][] squares = board.getSquares();
        // A factory method to retrieve piece images.
        ImageFactory factory = new ImageFactory();
        // Holds a temporary reference to a piece.
        PieceIF p;
        // Size of the square in height or length.
        int size = squares.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                p = squares[i][j].getPiece();
                if (p != null) {
                    ((Pane)grid.getChildren().get(i+j*size)).getChildren().add(
                        factory.getImage(p.getChessPieceType(), p.getColor(),
                            (int) grid.getMaxWidth() / size));
                }
            }
        }
    }

    /**
     * Sets up the initial squares on the board.
     */
    private void setupBoard() {
        int size = board.getSquares().length;
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pane pane = new Pane();
                pane.setMinSize(grid.getMaxWidth() / size, grid.getMaxWidth() / size);
                if (count % 2 == 0)
                    pane.getStyleClass().add("whitePane");
                else
                    pane.getStyleClass().add("blackPane");
                pane.setOnMouseClicked(e -> {
                    movePiece(pane);
                    showMoves(pane);
                });
                grid.add(pane, i, j, 1, 1);
                count++;
            }
            count = i % 2 + 1;
        }
    }

    /**
     * Moves the piece visually on the board, and calls upon the controller to
     * move the piece in it's underlying data structure.
     * 
     * @param pane - A reference to the specific pane that was clicked.
     */
    private void movePiece(Pane pane) {
        // The rank and file index of the current selected pane.
        int toRank = GridPane.getRowIndex(pane), toFile = GridPane.getColumnIndex(pane);
        // The current selected position.
        Position curr = board.getSquares()[toRank][toFile].getPostion();
        // The function stops here if it is the first selection.
        if (last == null) { last = curr; return; }
        // The rank and file index of the last selected position.
        int fromRank = last.getRank().getIndex(), fromFile = last.getFile().getIndex();
        // The size of the board.
        int size = board.getSquares().length;
        // The piece on the previous selected position.
        PieceIF piece = board.getPiece(fromRank, fromFile);
        
        // Move the piece, if any.
        if (piece != null && gc.move(last, curr)) {
            // The image of the previous selected pane.
            Node fromImage = ((Pane)grid.getChildren().get(fromRank+fromFile*size)).getChildren().remove(0);
            // The currently selected pane.
            Pane selectedPane = ((Pane)grid.getChildren().get(toRank+toFile*size));

            // Adds the captured piece, and removes it from the board.
            if (selectedPane.getChildren().size() > 0) {
                if (board.getPiece(toRank, toFile).getColor() == GameColor.WHITE) {
                    blackCaptured(selectedPane.getChildren().remove(0));
                } else {
                    whiteCaptured(selectedPane.getChildren().remove(0));
                }
            }

            // Moves the selected piece.
            ((Pane)grid.getChildren().get(toRank+toFile*size)).getChildren().add(fromImage);
            last = null;
        } else {
            last = curr;
        }
        
    }

    /**
     * Handles the onMouseClicked event for a pane on the board grid.
     * 
     * @param pane - A reference to the pane that was clicked.
     */
    private void showMoves(Pane pane) {
        if (!toggleShowMoves) { return; }
        int rowIndex = GridPane.getRowIndex(pane);
        int colIndex = GridPane.getColumnIndex(pane);
        int size = board.getSquares().length;
        PieceIF piece = board.getPiece(rowIndex, colIndex);

        // Remove showMoves coloring from the board.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid.getChildren().get(i+j*size).getStyleClass().removeAll("showMoves", "selected");
            }
        }

        // Outline selected square
        grid.getChildren().get(rowIndex+colIndex*size).getStyleClass().add("selected");
        
        // Show showMoves coloring on selected piece.
        if (piece != null) {
            Position[] pos = piece.showMoves(board.getSquares()[rowIndex][colIndex].getPostion());   
            for (Position p : pos) {
                grid.getChildren().get(p.getRank().getIndex()+p.getFile().getIndex()*size).getStyleClass().add("showMoves");
            }     
        }
    }
}