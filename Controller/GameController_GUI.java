package Controller;

import java.util.ArrayList;

import ChessExceptions.GameOverStaleMateException;
import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Model.Position;
import Validator.PieceValidator;
import History.History;


/**
 * Game controller conducts chess game logic for a chess game and certain
 * detections necessary to ensure a proper game of chess is played.
 * 
 * @author Kevin Filanowski
 * @version April 22, 2019
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

    public GameController_GUI(BoardIF board) {
        this.board = board;
        playerTurn = true;
        whiteTakenPiece = new ArrayList<>();
        blackTakenPiece = new ArrayList<>();
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
            board.getSquares()[fromRank][fromFile].setPiece(null);
            if (!playerTurn) {
                // board.draw();
                playerTurn = true;
                // detects check mate and stale mate
                //this.endGameHelp(board, GameColor.WHITE);
                //this.threeFoldRep(board);
                System.out.println(playerOneName + "'s turn!");
                board.draw();
                playerTurn = true;
            } else {
                // board.revDraw(board);
                playerTurn = false;
                // detects check mate and stale mate
                //this.endGameHelp(board, GameColor.BLACK);
                //this.threeFoldRep(board);
                System.out.println(playerTwoName + "'s turn!");
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
        return playerOneName;
    }

    /**
     * Retrieves the name of the second player.
     * 
     * @return - The name of player two.
     */
    public String getPlayerTwoName() {
        return playerTwoName;
    }
}