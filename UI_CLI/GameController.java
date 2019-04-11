package UI_CLI;

import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import History.History;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Board;
import Model.Piece;
import Model.Position;
import Validator.PieceValidator;

import java.util.ArrayList;


public class GameController {
    /** The boolean for which players turn it is. **/
    boolean playerTurn;
    /** The First Player Name */
    String player1Name;
    /** The Second Player Name */
    String player2Name;

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

    /**
     * Helper method that is used to move a piece on the board
     *
     * @param board - An instance of the chess board
     * @param from  - The starting position that we want to move from
     * @param to    - The final position that we want to move to
     */
    public void move(BoardIF board, Position from, Position to) {
        //ArrayList<PieceIF> piece = new ArrayList<PieceIF>();


        int fromFile = from.getFile().getIndex(); // from square file
        int fromRank = from.getRank().getIndex(); // from square rank

        int toFile = to.getFile().getIndex(); // to square file
        int toRank = to.getRank().getIndex(); // to square rank

        boolean validMove = board.getSquare(from.getRank(), from.getFile()).getPiece().validateMove(from, to);

        boolean whiteTurn = (board.getSquare(from.getRank(), from.getFile()).getPiece().getColor() == GameColor.WHITE && playerTurn);

        boolean blackTurn = (board.getSquare(from.getRank(), from.getFile()).getPiece().getColor() == GameColor.BLACK && !playerTurn);



        if (validMove && whiteTurn || blackTurn ) {
            History.getInstance().add(board.saveState());
            //board.getSquares()[fromRank][fromFile].getPiece().setHasMoved(true);
            board.getSquares()[toRank][toFile].setPiece(board.getSquares()[fromRank][fromFile].getPiece());
            //board.getSquares()[toRank][toFile].getPiece().setHasMoved(true);
//            PieceValidator piece = (PieceValidator) board.getSquares()[toRank][toFile].getPiece();
//            if(piece.getPiece().getChessPieceType() == ChessPieceType.KING &&
//                    ((Board)board).checkForCheckMate(from, piece.getColor())){
//                if(piece.getColor() == GameColor.WHITE){
//                    System.out.println("Black Wins!");
//                }else {
//                    System.out.println("White Wins!");
//                }
//
//            }

            board.getSquares()[fromRank][fromFile].setPiece(null);
            if (!playerTurn) {
                System.out.println(player1Name + "'s turn!");
                board.draw();
                playerTurn = true;
            }else{
                System.out.println(player2Name + "'s turn!");
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
}
