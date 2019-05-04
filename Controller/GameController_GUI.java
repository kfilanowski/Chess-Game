package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import Model.*;

import ChessExceptions.GameOverStaleMateException;
import Enums.ChessPieceType;
import Enums.GameColor;
import Enums.Rank;
import Handler.AlertHandler;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.ScreenChangeHandler;
import Model.Position;
import UI_GUI.Board_GUI;
import UI_GUI.InputNameScreen;
import Validator.PawnValidator;
import Validator.HorizVertValidator;
import Validator.KingValidator;
import Validator.PieceValidator;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import History.History;
import History.State;


/**
 * Game controller conducts chess game logic for a chess game and certain
 * detections necessary to ensure a proper game of chess is played.
 * 
 * @author Kevin Filanowski (33%)
 * @author Jeriah Caplinger (33%)
 * @author Matt Lutz (33%)
 * @version April 28, 2019
 */
public class GameController_GUI {
    /** A reference to the game board. */
    BoardIF board;
    /** The boolean for which players turn it is. **/
    boolean playerTurn;
    /** The First Player Name */
    String playerOneName;
    /** The Second Player Name */
    String playerTwoName;
    /** Counter for detecting 50-move-rule draw */
    private int counter;
    /** Upper bound for the 50-move-rule draw */
    private final int FIFTY_MOVES = 50;
    /** ArrayList that keeps track of three fold repetition */
    //ArrayList<State<BoardIF>> threeFold;
    /** ArrayList that contains a copy of the history list */
    //ArrayList<State<BoardIF>> copyOfHistory;
    /** The white pieces that have been taken */
    ArrayList<PieceIF> whiteTakenPiece;
    /** The Black pieces that have been taken */
    ArrayList<PieceIF> blackTakenPiece;
    /** A list of alertHandlers to notify if there is some kind of alert. */
    ArrayList<AlertHandler> ahList;
    /** The screen change handler that handles changing the screens. */
    ScreenChangeHandler handler;
    /** ArrayList that contains a copy of the history list */
    ArrayList<State<BoardIF>> copyOfHistory;
    /** ArrayList that keeps track of three fold repetition */
    ArrayList<State<BoardIF>> threeFold;
    /** file chooser object for opening a save and load dialog box */
    private FileChooser fileChooser;
    /** boolean for determiniing if the 50 move draw has been reached */
    private boolean continueGame;
    /** boolean for determining if the game can continue using the three fold repitition */
    private boolean continueThreeFold;

    /**
     * Constructor for a GUI GameController. This controller is for a chess
     * game, and requires a chess board.
     * 
     * @param board - A reference to the game board.
     */
    public GameController_GUI(BoardIF board) {
        this.board = board;
        playerTurn = true;
        whiteTakenPiece = new ArrayList<>();
        blackTakenPiece = new ArrayList<>();
        copyOfHistory = new ArrayList<>();
        threeFold = new ArrayList<>();
        ahList = new ArrayList<>();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        continueGame = true;
        continueThreeFold = true;
    }

    /**
     * Method that moves a chess piece and provides all backend detections to
     * ensure a proper game of chess is played.
     *
     * @param from - The position to move from.
     * @param to   - The position to move to.
     * @return - if the move moved successfully
     */
    public boolean move(Position from, Position to) {
        int fromFile = from.getFile().getIndex(); 
        int fromRank = from.getRank().getIndex();
        int toFile = to.getFile().getIndex();
        int toRank = to.getRank().getIndex();

        boolean validMove = board.getSquare(from.getRank(), from.getFile())
                                .getPiece().validateMove(from, to);
        
        boolean whiteTurn = (board.getSquare(from.getRank(), from.getFile())
                                .getPiece().getColor() == GameColor.WHITE
                                && playerTurn);
        boolean blackTurn = (board.getSquare(from.getRank(), from.getFile())
                                .getPiece().getColor() == GameColor.BLACK
                                && !playerTurn);

        if (validMove && (whiteTurn || blackTurn) && continueGame && continueThreeFold) {
            // For en passante checking
            boolean deleteEnPassPiece = false;
            Position enPassPos = null;
            PieceValidator beforeSave = (PieceValidator) board.getSquares()[fromRank][fromFile].getPiece();
            // for en passante so board state is saved correctly
            if(beforeSave.getPiece().getChessPieceType() == ChessPieceType.PAWN){
                PawnValidator pawn = (PawnValidator) board.getSquares()[fromRank][fromFile].getPiece();
                enPassPos = pawn.isItEnPassante(new
                                Position(Rank.getRankFromIndex(fromRank), Enums.File.getFileFromIndex(fromFile)),
                        beforeSave.getPiece().getColor());
                if(enPassPos != null){
                    deleteEnPassPiece = true;
                }
            }
            // end en passante movement

            History.getInstance().add(board.saveState());
            getTakenPiece(to);
            // the following is the implementation for checking for the fifty
            // move rule
            PieceValidator toPiece = (PieceValidator) board.getSquares()[toRank][toFile].getPiece();
            PieceValidator fromPiece = (PieceValidator) board.getSquares()[fromRank][fromFile].getPiece();

            if (fromPiece.getPiece().getChessPieceType() != ChessPieceType.PAWN && toPiece == null) {
                this.counter++;
                // if counter == 50 then it is a draw
                if (counter == FIFTY_MOVES) {
                    // we throw the game over exception
                    if (!playerTurn) {
                        playerTurn = true;
                    } else {
                        playerTurn = false;
                    }
                    continueGame = false;
                }
            } else {
                counter = 0;
            }


            // moves the piece
            board.getSquares()[toRank][toFile].setPiece(board.getSquares()[fromRank][fromFile].getPiece());
            PieceValidator toPiece2 = (PieceValidator) board.getSquares()[toRank][toFile].getPiece();


            toPiece2.getPiece().setHasMoved(true);
            board.getSquares()[fromRank][fromFile].setPiece(null);

            //This block of code is for the King's castle movement
            if(toPiece2.getPiece().getChessPieceType() == ChessPieceType.KING
                    && (fromFile - toFile) == 2){
                HorizVertValidator rook = (HorizVertValidator)board.getSquares()[fromRank][fromFile - 4].getPiece();
                board.getSquares()[fromRank][toFile + 1].setPiece(rook);
                board.getSquares()[fromRank][fromFile - 4].setPiece(null);
            }else if(toPiece2.getPiece().getChessPieceType() == ChessPieceType.KING
                    && (toFile - fromFile) == 2){
                HorizVertValidator rook = (HorizVertValidator)board.getSquares()[fromRank][fromFile  + 3].getPiece();
                board.getSquares()[fromRank][toFile - 1].setPiece(rook);
                board.getSquares()[fromRank][fromFile + 3].setPiece(null);
            }


            // deletes the en passante movement
            if(deleteEnPassPiece){
                board.getSquares()[enPassPos.getRank().getIndex()][enPassPos.getFile().getIndex()].setPiece(null);
            }

            if (!playerTurn) {
                // board.draw();
                playerTurn = true;
                //detects check mate and stale mate
                boolean isItCheckMate = this.endGameHelpCheckMate(board, GameColor.WHITE);
                boolean isItStaleMate = this.endGameHelpStaleMate(board, GameColor.WHITE);
                boolean isItThreeFold = this.threeFoldRep(board);
                if(isItCheckMate){
                    alert("CHECKMATE! " + InputNameScreen.getInstance().getPlayer2Name() + " wins!");
                }else if(isItStaleMate){
                    alert("STALEMATE! " + InputNameScreen.getInstance().getPlayer1Name() +
                            " draws with " + InputNameScreen.getInstance().getPlayer2Name());
                }else if (!continueGame){
                    this.alert("DRAW! 50 moves draw rule has been reached!");
                }else if(isItThreeFold){
                    this.continueThreeFold = false;
                    alert("DRAW! Three fold repetition has been reached!");
                }
                else {
                    alert(InputNameScreen.getInstance().getPlayer1Name() + "'s turn!");
                }
                board.draw();
                playerTurn = true;
            } else {
                // board.revDraw(board);
                playerTurn = false;
                // detects check mate and stale mate
                boolean isItCheckMate = this.endGameHelpCheckMate(board, GameColor.BLACK);
                boolean isItStaleMate = this.endGameHelpStaleMate(board, GameColor.BLACK);
                boolean isItThreeFold = this.threeFoldRep(board);
                if(isItCheckMate){
                    alert("CHECKMATE! " + InputNameScreen.getInstance().getPlayer1Name() + " wins!");
                }else if(isItStaleMate){
                    alert("STALEMATE! " + InputNameScreen.getInstance().getPlayer1Name() +
                            " draws with " + InputNameScreen.getInstance().getPlayer2Name());
                }else if (!continueGame){
                    this.alert("DRAW! 50 moves draw rule has been reached!");
                }else if(isItThreeFold){
                    this.continueThreeFold = false;
                    alert("DRAW! Three fold repetition has been reached!");
                }
                else {
                    alert(InputNameScreen.getInstance().getPlayer2Name() + "'s turn!");
                }
                board.revDraw(board);
                playerTurn = false;
            }
            return true;
        }
        return false;
    }


    /**
     * Detects if the game is over by either a checkmate or a stalemate
     *
     * @param board the chess board
     * @param color the color we are checking for checkmate or stalemate
     */
    private boolean endGameHelpCheckMate(BoardIF board, GameColor color) {
        boolean result = false;
        if (((Board) board).checkForCheck(color)) {
            if(GameColor.WHITE == color){
                alert(InputNameScreen.getInstance().getPlayer1Name() + " is in CHECK!");
            }else if(color == GameColor.BLACK){
                alert(InputNameScreen.getInstance().getPlayer2Name() + " is in CHECK!");
            }
            if ((((Board) board).checkForCheckMate(color))) {
                result = true;
            }
        }
        return result;
    }


    /**
     * Helper method that determines if the game has ended in stalemate
     * @param board the chess board
     * @param color the color to check for
     * @return true if it is in stalemate
     */
    private boolean endGameHelpStaleMate(BoardIF board, GameColor color){
        boolean result = false;
        if ( !((Board) board).checkForCheck(color)) {
            if ((((Board) board).checkForStaleMate(color))) {
                result = true;
            }
        }
        return result;
    }



    /**
     * Detects three fold repetition for the game to end in a draw
     *
     * @param board the board we are using
     * @return returns true if there is a three fold repetition.
     */
    private boolean threeFoldRep(BoardIF board) {
        boolean result = false;
        int counter = 0;
        History<BoardIF> history = History.getInstance();
        State<BoardIF> state = board.saveState();
        this.copyOfHistory.addAll(history.getList());

        // this is for after three fold repetition occurs, if the players decide
        // to continue we need to take out the board states so we can count a
        // new set of 3 fold repetitions.
        for (State<BoardIF> st : this.threeFold) {
            for (int i = 0; i < 3; i++) {
                this.copyOfHistory.remove(st);
            }
        }

        // detects equality and if we find three occurrences its 3 fold
        // repetition
        for (State<BoardIF> s : this.copyOfHistory) {
            if (s.getState().equals(state.getState())) {
                counter++;
                if (counter == 3) {
                    this.threeFold.add(s);
                    result = true;
                }
            }
        }
        this.copyOfHistory.clear();
        return result;
    }


    /**
     * Adds the piece that was taken to the Arraylist of the player that took it.
     *
     * @param to - The position that the move will go to.
     */
    private void getTakenPiece(Position to) {
        if (board.getSquare(to.getRank(), to.getFile()).getPiece() != null) {
            if (playerTurn) {
                whiteTakenPiece.add(board.getSquare(to.getRank(), to.getFile()).getPiece());
            } else {
                blackTakenPiece.add(board.getSquare(to.getRank(), to.getFile()).getPiece());
            }
        }
    }

    /**
     * Retrieves the name of the first player.
     * 
     * @return - The name of player one.
     */
    public String getPlayerOneName() {
        return InputNameScreen.getInstance().getPlayer1Name();
    }

    /**
     * Sets the player's name
     * @param name the player name to be set
     */
    public void setPlayerOneName(String name) {
        playerOneName = InputNameScreen.getInstance().getPlayer1Name();
    }

    /**
     * Sets the players name
     * @param name the players name to be set
     */
    public void setPlayerTwoName(String name) {
        playerTwoName = InputNameScreen.getInstance().getPlayer2Name();
    }

    /**
     * Retrieves the name of the second player.
     * 
     * @return - The name of player two.
     */
    public String getPlayerTwoName() {
        return InputNameScreen.getInstance().getPlayer2Name();
    }

    /**
     * Handles an undo action triggered by some event such as a button press.
     */
    public void undoAction() {
        State<BoardIF> state = History.getInstance().undo(board);
        if (state == null) {
            alert("Undo could not occur");
        } else {
            board.restoreState(state);
            playerTurn = !playerTurn;
            alert("Undo occured");
            this.continueThreeFold = true;
            counter--;
            if(!continueGame) {
                this.continueGame = true;
                playerTurn = !playerTurn;
            }
        }
    }


    /**
     * Handles a redo action triggered by some event such as a button press.
     */
    public void redoAction() {
        State<BoardIF> state = History.getInstance().redo();
        if (state == null) {
            alert("Redo could not occur");
        } else {
            board.restoreState(state);
            playerTurn = !playerTurn;
            alert("Redo occured");
        }
    }

    /**
     * Method that opens a FileChooser save dialog window and allows the user to save
     * the chess game as an xml file.
     */
    public void saveAction() {
        // create and add file extension filters to the dialog window
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                // we get our history as an xml string and write it to the designated file
                FileWriter fileWriter = new FileWriter(file);
                PrintWriter writer = new PrintWriter(fileWriter);
                writer.print(History.getInstance().toXML(board));
                writer.close();
                // we alert the user that the file was successfully saved
                alert("File was saved as >>  " + file.toString());
            } catch (IOException ioe) {
                // we alert the user that the file was not saved
                alert("ERROR! Could not save file.");
            }
        }else{
            // alert user that the file was not saved
            alert("ERROR! Could not save file");
        }
    }

    /**
     * Method that opens a open dialog window and allows the user to load a chess game
     */
    public void loadAction() {
        // create and add file extension filters to the dialog window
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            board.restoreState(History.getInstance().loadHistory(file).getState().clone().saveState());
        }
    }

    /**
     * The settings screen action that can switch the screen
     */
    public void settingsAction(){
        handler.switchScreen(ScreenChangeHandler.SETTINGSSCREEN);
    }

    /**
     * Adds an alertHandler to the list of alert handlers.
     * 
     * @param ah - An instance of an alertHandler.
     */
	public void registerAlertHandler(AlertHandler ah) {
        ahList.add(ah);
    }

    /**
     * Return the boolean value determining the player's turn.
     * 
     * @return - True if it is the white player's turn, false otherwise.
     */
    public boolean getPlayerTurn() {
        return playerTurn;
    }
    
    /**
     * Notifies all alertHandlers of an alert.
     * 
     * @param alert - A message related to the alert.
     */
    public void alert(String alert) {
        for (AlertHandler a : ahList) {
            a.handle(alert);
        }
    }
}