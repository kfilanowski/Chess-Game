package UI_CLI;

import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import History.History;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Piece;
import Model.Position;

import java.util.ArrayList;

/**
 * This class is the controller for the board CLI that handles the functionality on the board
 *
 * @author - Jacob Ginn (33%)
 * @author - Matt Lutz (33%)
 * @author  - Jeriah Capplinger (33%)
 */
public class GameController {
    /** The boolean for which players turn it is. **/
    boolean playerTurn;
    /** The First Player Name */
    String player1Name;
    /** The Second Player Name */
    String player2Name;
    /** The white pieces that have been taken */
    ArrayList<PieceIF> WhiteTakenPiece = new ArrayList<PieceIF>();
    /** The Black pieces that have been taken */
    ArrayList<PieceIF> BlackTakenPiece = new ArrayList<PieceIF>();


    /**
     * Constructor for the gamecontroller
     * @param player1Name - First Player name
     * @param player2Name - Second Player name
     */
    public GameController(String player1Name, String player2Name){
        playerTurn = true;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public boolean getplayerTurn(){
        return this.playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn){
        this.playerTurn = playerTurn;
    }

    /**
     * Helper method that is used to move a piece on the board
     *
     * @param board - An instance of the chess board
     * @param from  - The starting position that we want to move from
     * @param to    - The final position that we want to move to
     */
    public void move(BoardIF board, Position from, Position to) {
        int fromFile = from.getFile().getIndex(); // from square file
        int fromRank = from.getRank().getIndex(); // from square rank

        int toFile = to.getFile().getIndex(); // to square file
        int toRank = to.getRank().getIndex(); // to square rank

        boolean validMove = board.getSquare(from.getRank(), from.getFile()).getPiece().validateMove(from, to);

        boolean whiteTurn = (board.getSquare(from.getRank(), from.getFile()).getPiece().getColor() == GameColor.WHITE && playerTurn);

        boolean blackTurn = (board.getSquare(from.getRank(), from.getFile()).getPiece().getColor() == GameColor.BLACK && !playerTurn);

        if (validMove && whiteTurn || blackTurn ) {
            History.getInstance().add(board.saveState());
            getTakenPiece(board, to);
            board.getSquares()[toRank][toFile].setPiece(board.getSquares()[fromRank][fromFile].getPiece());
            board.getSquares()[fromRank][fromFile].setPiece(null);
            if (!playerTurn) {
                System.out.println(player1Name + "'s turn!");
                System.out.print("White Has Taken" + WhiteTakenPiece + " \n");
                board.draw();
                playerTurn = true;
            }else{
                System.out.println(player2Name + "'s turn!");
                System.out.print("Black Has Taken" + BlackTakenPiece + "\n");
                board.revDraw(board);
                playerTurn = false;
            }
        } else {
            System.out.println("Cannot move piece");
        }
    }

    /**
     * Helper method that gets a position based on provided user input
     *
     * @param pos     the requested position i.e. b6 or B6 or f5 or F5
     * @param squares the squares on the chess board
     * @return a Position object representing the requested position from the user
     *         or null if the requested position is invalid
     */
    public Position getPosition(String pos, SquareIF[][] squares) {
        Position refinedPos;
        pos = pos.toLowerCase();
        String[] posArray = pos.split("");

        if (posArray.length > 2) {
            return null;
        }

        // we attempt to get the position
        try {
            File file = File.getFileFromLetter(posArray[0]);
            int parseRank = Integer.parseInt(posArray[1]);
            Rank rank = Rank.getRankFromNum(parseRank);

            // if we have a valid file and rank
            if (file != null && rank != null) {
                refinedPos = new Position(rank, file);

                // otherwise it is not a valid file and/or rank and we return null
            } else {
                refinedPos = null;
            }
            // if we were not supplied a number
        } catch (NumberFormatException nfe) {
            refinedPos = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            refinedPos = null;
        }
        return refinedPos;
    }

    /**
     * prints the players turn and flips the whose turn it is for the undo method.
     * @param board - The board being played on.
     */
    public void undo(BoardIF board) {
        if (!playerTurn) {
            System.out.println(player1Name + "'s turn!");
            board.draw();
            playerTurn = true;
        }else{
            System.out.println(player2Name + "'s turn!");
            board.revDraw(board);
            playerTurn = false;
        }
    }

    /**
     * prints the players turn and flips whose turn it is for the redo method.
     * @param board - The board being played on.
     */
    public void redo(BoardIF board) {
        if (!playerTurn) {
            System.out.println(player1Name + "'s turn!");
            board.draw();
            playerTurn = true;
        }else{
            System.out.println(player2Name + "'s turn!");
            board.revDraw(board);
            playerTurn = false;
        }
    }

    /**
     * adds the piece that was taken to the Arraylist of th player that took it.
     * @param board - The board that the game is being played on.
     * @param to - The position that the move will go to.
     */
    public void getTakenPiece(BoardIF board, Position to){
        if(board.getSquare(to.getRank(), to.getFile()).getPiece() != null) {
            if (playerTurn) {
                WhiteTakenPiece.add(board.getSquare(to.getRank(), to.getFile()).getPiece());
            } else {
                BlackTakenPiece.add(board.getSquare(to.getRank(), to.getFile()).getPiece());
            }
        }
    }

    /**
     * Prints each players pieces that they have taken from their opponent.
     */
    public void printTaken(){
        System.out.println("White has taken " + WhiteTakenPiece + "\nBlack has taken " + BlackTakenPiece);
    }
}
