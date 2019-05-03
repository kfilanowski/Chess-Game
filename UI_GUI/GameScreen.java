package UI_GUI;

import Handler.SettingsObserver;
import Model.Board;
import com.sun.glass.ui.Screen;
import Interfaces.*;
import Controller.GameController_GUI;
import Enums.ChessPieceType;
import Enums.GameColor;
import Factory.ImageFactory;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Position;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * The game screen that the two players will play on.
 *
 * @author Kevin Filanowski
 * @version April 21, 2019
 */
public class GameScreen implements SettingsObserver {
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
    FlowPane capturedWhitePieces;

    /** Displays the black pieces that have been captured. */
    FlowPane capturedBlackPieces;

    /** The amount of squares in the board. */
    private int boardSize;

    /** ScreenChangeHandler object */
    ScreenChangeHandler handler;

    /** The undo funtion is enabled or disabled. */
    boolean undo;

    /** The undo function has unlimited times it can be implemented */
    boolean unlimUndo;

    /** The max number of undos that are allowed */
    int maxUndo;

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
        boardSize = board.getSquares().length;
        toggleShowMoves = true;
        root = new BorderPane();
        grid = new GridPane();
        capturedWhitePieces = new FlowPane();
        capturedBlackPieces = new FlowPane();
        gc = new GameController_GUI(board);
        undo = false;
        unlimUndo = false;
        maxUndo = 1;
    }

    EventHandler<ActionEvent> buttonHandlerA = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            handler.switchScreen(ScreenChangeHandler.SCREENA);
            Board_GUI.boardSettings = 0;
        }//end handle
    };

    EventHandler<ActionEvent> buttonHandlerB = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            handler.switchScreen(ScreenChangeHandler.SCREENC);
        }//end handle
    };


    /**
     * Method that sets the screenChangeHandler to a new screenChangeHandler
     * @param sch - new screenChangeHandler that we want to set
     */
    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.handler = sch;
    }

    /**
     * Retrieves the instance of this class, or creates an instance if one does not
     * already exist.
     *
     * @return - An instance of the GameScreen class.
     */
    public static GameScreen getInstance() {
        return getInstance(null);
    }

    /**
     * Retrieves the instance of this class, or creates an instance if one does not
     * already exist. When an instance is retrieved, the board is set to the
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
    public Pane getRoot() {
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
     * Creates a vertical list of labels that act as 'ranks' for the board. They are
     * numbers 1 through the number of squares on the board, and the font size and
     * padding is automatically chosen depending on the size of the board.
     * 
     * @return - A vertical list of ranks, or numbers from 1 through the number of
     *         squares on the board, added backwards.
     */
    private GridPane createRanks() {
        GridPane ranks = new GridPane();
        ranks.setAlignment(Pos.CENTER);
        Font font = new Font(36);

        for (int i = boardSize; i > 0; i--) {
            Label rank = new Label("" + i);
            rank.setFont(font);
            ranks.add(new StackPane(rank), 0, i, 1, 1);
        }
        return ranks;
    }

    /**
     * Creates a horizontal list of letters that act as 'files' for the board. They
     * are letters A through however many letters corrosponding to the number of
     * squares on the board, and the font size and padding is automatically chosen
     * depending on the size of the board.
     * 
     * @return - A horizontal list of files, or letters from A through the number of
     *         squares on the board.
     */
    private GridPane createFiles() {
        GridPane files = new GridPane();
        files.setPadding(new Insets(0, 0, 0, 36));
        files.setAlignment(Pos.CENTER);
        Font font = new Font(36);

        // set files
        for (int i = 'A'; i < 'A' + boardSize; i++) {
            Label file = new Label(Character.toString((char) i));
            file.setFont(font);
            files.add(new StackPane(file), i, 0, 1, 1);
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
        GridPane ranks = createRanks();
        GridPane files = createFiles();
        ranks.setId("ranks");
        files.setId("files");

        BorderPane.setAlignment(ranks, Pos.TOP_RIGHT);
        BorderPane.setAlignment(files, Pos.BOTTOM_CENTER);

        // Setup the board game grid.
        grid.setAlignment(Pos.CENTER);
        grid.setId("board");
        grid.setMinSize(350, 350);
        files.setMinWidth(grid.getMinWidth());
        ranks.setMinHeight(grid.getMinHeight());

        root.heightProperty().addListener(e -> {
            double min = Math.floor(Math.min(
                    root.getHeight() 
                        - ((Pane)root.getBottom()).getHeight() 
                        - ((Pane)root.getTop()).getHeight(), 
                    root.getWidth() 
                        - ((Pane)root.getLeft()).getWidth() 
                        - ((Pane)root.getRight()).getWidth()));
            center.setMaxSize(min, min);
        });
        root.widthProperty().addListener(e -> {
            double min = Math.floor(Math.min(
                    root.getHeight() 
                        - ((Pane)root.getBottom()).getHeight() 
                        - ((Pane)root.getTop()).getHeight(), 
                    root.getWidth() 
                        - ((Pane)root.getLeft()).getWidth() 
                        - ((Pane)root.getRight()).getWidth()));
            center.setMaxSize(min, min);
        });

        grid.heightProperty().addListener(squareSizeListener);
        grid.widthProperty().addListener(squareSizeListener);
        grid.heightProperty().addListener(e -> {;
            double min = Math.min(grid.getHeight(), grid.getWidth());
            double paneSize = Math.floor(min/boardSize);
            Pane temp;
            for (Node p : files.getChildren())  {
                temp = (Pane) p;
                temp.setMaxSize(paneSize, paneSize * 0.60);
                temp.setPrefSize(paneSize, paneSize * 0.60);
            }
            for (Node p : ranks.getChildren())  {
                temp = (Pane) p;
                temp.setMaxSize(paneSize * 0.60, paneSize);
                temp.setPrefSize(paneSize * 0.60, paneSize);
            }
        });
        root.widthProperty().addListener(e -> {
            double min = Math.min(grid.getHeight(), grid.getWidth());
            double paneSize = Math.floor(min/boardSize);
            Pane temp;
            for (Node p : files.getChildren())  {
                temp = (Pane) p;
                temp.setMaxSize(paneSize, paneSize * 0.60);
                temp.setPrefSize(paneSize, paneSize * 0.60);
            }
            for (Node p : ranks.getChildren())  {
                temp = (Pane) p;
                temp.setMaxSize(paneSize * 0.60, paneSize);
                temp.setPrefSize(paneSize * 0.60, paneSize);
            }
        });

        setupBoard();
        drawBoard();
        center.setLeft(ranks);
        center.setTop(files);
        center.setCenter(grid);
        root.setCenter(center);        
    }

    ChangeListener<Number> squareSizeListener = new ChangeListener<Number>() {
        /**
         * Adjusts the size of the squares given the amount of space, but
         * retains the aspect ratio of 1:1.
         *
         * @param observable - Unused, but it is the height/width property of
         *                      the grid.
         * @param oldValue - The old value.
         * @param newValue - The new changed value.
         */
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            double min = Math.min(grid.getHeight(), grid.getWidth());
            double paneSize = Math.round(min/boardSize);
            Pane temp;
            for (Node p : grid.getChildren())  {
                temp = (Pane) p;
                temp.setMaxSize(paneSize, paneSize);
                temp.setMinSize(paneSize, paneSize);
            }
        }
    };

    /**
     * Sets up the top side of the border pane. Adds buttons which include: Load,
     * Save, Undo, Redo, and Settings.
     */
    private void setupTop() {
        HBox topPanel = new HBox();
        topPanel.setAlignment(Pos.CENTER);

        // TODO: implement settings.

        // Create the row of buttons.
        Button[] buttons = new Button[5];

        buttons[0] = new Button("Load");
        buttons[0].setOnAction(e -> {
            gc.loadAction();
            drawBoard();
        });

        buttons[1] = new Button("Save");
        buttons[1].setOnAction(e -> gc.saveAction());

        buttons[2] = new Button("Undo");
        buttons[2].setOnAction(e -> {
            if(undo) {
                if(unlimUndo || maxUndo != 0) {
                    gc.undoAction();
                    maxUndo--;
                }
            }
            drawBoard();
        });

        buttons[3] = new Button("Redo");
        buttons[3].setOnAction(e -> {
            gc.redoAction();
            drawBoard();
        });

        buttons[4] = new Button("Settings");
        buttons[4].setOnAction(buttonHandlerB);


        for (Button b : buttons) {
            b.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            HBox.setHgrow(b, Priority.ALWAYS);
            b.getStyleClass().add("gameScreenTopButtons");
        }

        topPanel.getChildren().addAll(buttons);
        root.setTop(topPanel);
    }

    /**
     * Sets up the left side of the border pane. The left side of the border pane
     * will display images of the black pieces captured.
     *///    public void maxUpdate();
    private void setupLeft() {
        VBox leftPanel = new VBox();
        leftPanel.setAlignment(Pos.TOP_CENTER);

        // Setup Player labels
        Label playerOne = new Label("Player One");
        Label playerOneName = new Label(gc.getPlayerOneName());
        playerOne.getStyleClass().add("playerLabel");
        playerOneName.getStyleClass().add("playerLabel");

        // Properly scale this left side with screen resizing.

        // TODO: Scaling wont work. I need the spacing between pieces to be smaller.
        VBox.setVgrow(capturedBlackPieces, Priority.ALWAYS);
        grid.widthProperty().addListener(e -> {
            capturedBlackPieces.setMinWidth(2*grid.getWidth()/boardSize+5);
            capturedBlackPieces.setMaxWidth(((Pane)grid.getChildren().get(0)).getWidth());
        });

        // showMoves toggle button
        ToggleButton showMoves = new ToggleButton("Show Moves");
        showMoves.getStyleClass().add("toggleButton");
        showMoves.setOnAction(e -> toggleShowMoves = !toggleShowMoves);

        leftPanel.getChildren().addAll(playerOne, playerOneName, capturedBlackPieces, showMoves);
        leftPanel.setStyle("-fx-background-color: orange");
        capturedBlackPieces.setStyle("-fx-background-color: red");
        root.setLeft(leftPanel);
    }

    /**
     * Tells the root pane's left node to add the passed in node as a child. This
     * can be used, for example, to display nodes that were captured.
     * 
     * @param node - The node that was captured.
     */
    private void blackCaptured(Node node) {
        capturedBlackPieces.getChildren().add(node);
    }

    /**
     * Sets up the right side of the border pane. The right side of the border pane
     * will display images of the white pieces captured.
     */
    private void setupRight() {
        VBox rightPanel = new VBox();
        rightPanel.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(capturedWhitePieces, Priority.ALWAYS);


        // Setup Player labels
        Label playerTwo = new Label("Player Two");
        Label playerTwoName = new Label(gc.getPlayerTwoName());
        playerTwo.getStyleClass().add("playerLabel");
        playerTwoName.getStyleClass().add("playerLabel");

        // Properly scale this left side with screen resizing.
        VBox.setVgrow(capturedWhitePieces, Priority.ALWAYS);
        grid.widthProperty().addListener(e -> {
            capturedWhitePieces.setMinWidth(2*grid.getWidth()/boardSize + 5);
            capturedWhitePieces.setMaxWidth(((Pane)grid.getChildrenUnmodifiable().get(0)).getWidth());
        });

                // TODO: add functonality for exit button.

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(buttonHandlerA);

        rightPanel.getChildren().addAll(playerTwo, playerTwoName, capturedWhitePieces, exitButton);
        rightPanel.setStyle("-fx-background-color: orange");
        capturedWhitePieces.setStyle("-fx-background-color: red");
        root.setRight(rightPanel);
    }

    /**
     * Tells the root pane's right node to add the passed in node as a child. This
     * can be used, for example, to display nodes that were captured.
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
        bottomPanel.setAlignment(Pos.CENTER);

        InfoLabel info = new InfoLabel(gc.getPlayerOneName() + "'s turn!");
        info.getStyleClass().add("playerLabel");
        gc.registerAlertHandler(info);

        bottomPanel.getChildren().add(info);
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

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ((Pane) grid.getChildren().get(i + j * boardSize)).getChildren().clear();
                p = squares[i][j].getPiece();
                if (p != null) {
                    ImageView img = factory.getImage(p.getChessPieceType(), p.getColor());
                    img.getStyleClass().add("piece");
                    img.fitWidthProperty().bind(grid.widthProperty().divide(boardSize));
                    img.fitHeightProperty().bind(grid.heightProperty().divide(boardSize));
                    ((Pane) grid.getChildren().get(i + j * boardSize)).getChildren().add(img);
                }
            }
        }
    }

    /**
     * Sets up the initial squares on the board.
     */
    private void setupBoard() {
        int count = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                StackPane pane = new StackPane();
                pane.setAlignment(Pos.CENTER);

                if (count % 2 == 0)
                    pane.getStyleClass().add("whitePane");
                else
                    pane.getStyleClass().add("blackPane");
                pane.setOnMouseClicked(e -> {
                    if (!movePiece(pane) && toggleShowMoves)
                        showMoves(pane);
                    else
                        removeShowMovesColoring();
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
     * @return - True if a piece was moved, false otherwise.
     */
    private boolean movePiece(Pane pane) {
        // The rank and file index of the current selected pane.
        int toRank = GridPane.getRowIndex(pane), toFile = GridPane.getColumnIndex(pane);
        // The current selected position.
        Position curr = board.getSquares()[toRank][toFile].getPostion();
        // The function stops here if it is the first selection.
        if (last == null) { last = curr; return false; }
        // The rank and file index of the last selected position.
        int fromRank = last.getRank().getIndex(), fromFile = last.getFile().getIndex();
        // The piece on the previous selected position.
        PieceIF piece = board.getPiece(fromRank, fromFile);
        
        // Move the piece, if any.
        if (piece != null && gc.move(last, curr)) {
            // The image of the previous selected pane.
            Node fromImage = ((Pane)grid.getChildren().get(fromRank+fromFile*boardSize)).getChildren().remove(0);
            // The currently selected pane.
            Pane selectedPane = ((Pane)grid.getChildren().get(toRank+toFile*boardSize));

            // Adds the captured piece, and removes it from the board.
            if (selectedPane.getChildren().size() > 0) {
                if (board.getPiece(toRank, toFile).getColor() == GameColor.WHITE) {
                    blackCaptured(selectedPane.getChildren().remove(0));
                } else {
                    whiteCaptured(selectedPane.getChildren().remove(0));
                }
            }
            
            // Moves the selected piece.
            if (piece.getChessPieceType().equals(ChessPieceType.PAWN) && selectedPane.getChildren().size() <= 0)
                drawBoard();
            else 
                ((Pane)grid.getChildren().get(toRank+toFile*boardSize)).getChildren().add(fromImage);
            last = null;
            return true;
        } else {
            last = curr;
            return false;
        }
    }

    /**
     * Handles the onMouseClicked event for a pane on the board grid.
     * 
     * @param pane - A reference to the pane that was clicked.
     */
    private void showMoves(Pane pane) {
        if (!toggleShowMoves) {
            removeShowMovesColoring();
            return;
        }
        int rowIndex = GridPane.getRowIndex(pane);
        int colIndex = GridPane.getColumnIndex(pane);
        PieceIF piece = board.getPiece(rowIndex, colIndex);

        removeShowMovesColoring();

        // Outline selected square
        grid.getChildren().get(rowIndex+colIndex*boardSize).getStyleClass().add("selected");
        
        // Show showMoves coloring on selected piece.
        if (piece != null) {
            Position[] pos = piece.showMoves(board.getSquares()[rowIndex][colIndex].getPostion());   
            for (Position p : pos) {
                grid.getChildren().get(p.getRank().getIndex()+p.getFile().getIndex()*boardSize).getStyleClass().add("showMoves");
            }     
        }
    }

    /**
     * Removes any current visible showMovesColoring from the board.
     */
    private void removeShowMovesColoring() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grid.getChildren().get(i+j*boardSize).getStyleClass().removeAll("showMoves", "selected");
            }
        }
    }


    @Override
    public void undoUpdate(boolean undo) {
        this.undo = undo;
    }

    @Override
    public void moveUpdate(boolean show) {
        toggleShowMoves = show;
    }

    @Override
    public void maxundoUpdate(int numUndo) {
        maxUndo = numUndo;
    }

    public void unlimUpdate(boolean unlimUndo){
        this.unlimUndo = unlimUndo;
    }

    public void colorUpdate(Background white, Background black){

        int count = 0;
        for(int i = 0; i < grid.getChildren().size()-1; i++){
            System.out.println(i);

            if(i % 2 == 0)
                grid.getChildren().get(i).setStyle(/*"-fx-background-color: #ff0000;*/" -fx-border-color: #ff0000");
            else
                grid.getChildren().get(i).setStyle("-");
        }

    }
}