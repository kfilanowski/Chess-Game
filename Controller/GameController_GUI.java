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
 * @author Kevin Filanowski
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


    Stage stage;
    FileChooser fileChooser;

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
        ahList = new ArrayList<>();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
    }

    /**
     * Method that moves a chess piece and provides all backend detections to
     * ensure a proper game of chess is played.
     *
     * @param from - The position to move from.
     * @param to   - The position to move to.
     * @throws GameOverCheckMateException - If the game is over by CheckMate.
     * @throws GameOverStaleMateException - If the game is over by StaleMate.
     * @return - //TODO: 
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

        if (validMove && (whiteTurn || blackTurn)) {
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
                // detects check mate and stale mate
                //this.endGameHelp(board, GameColor.WHITE);
                //this.threeFoldRep(board);
                alert(playerTwoName + "'s turn!");
                board.draw();
                playerTurn = true;
            } else {
                // board.revDraw(board);
                playerTurn = false;
                // detects check mate and stale mate
                //this.endGameHelp(board, GameColor.BLACK);
                //this.threeFoldRep(board);
                alert(playerTwoName + "'s turn!");
                board.revDraw(board);
                playerTurn = false;
            }
            return true;
        }
        return false;
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
     * 
     * 
     * 
     */
    public void setPlayerOneName(String name) {
        playerOneName = InputNameScreen.getInstance().getPlayer1Name();
    }

    /**
     * 
     * 
     * 
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