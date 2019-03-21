package Validator;

import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Position;
import Interfaces.BoardIF;

import java.util.ArrayList;

/**
 * Models the piece's ability to move like a Pawn,
 * and perform Pawn-like functions.
 * 
 * @author Jeriah Caplinger
 * @version March 18, 2019
 */
public class PawnValidator extends PieceValidator {

    /**
     * Constructor for PawnValidator.
     * 
     * @param board - The current state of the board.
     */
	public PawnValidator(BoardIF board, PieceIF p) {
		this.p = p;
		this.board = board;
	}

    /**
     * Checks to see if the move to be attempted is a valid move by the 
     * standards of Chess for this particular movement type.
     * 
     * @param from - The position the piece currently has before movement.
     * @param to   - The position the piece is being asked to move to.
     * @return - True if the piece movement is valid, otherwise returns false.
     */
	@Override
	public boolean validateMove(Position from, Position to) {
	    //TODO: Check if this move will put king into check

        int fromRank = from.getRank().getIndex();
        int fromFile = from.getFile().getIndex();

        int toRank = to.getRank().getIndex();
        int toFile = to.getFile().getIndex();

        System.out.println("from rank: " + fromRank + " to file: " + fromFile + " to rank: " + toRank + " to file: " + toFile);
        System.out.println("from rank: " + from.getRank() + " to file: " + from.getFile() + " to rank: " + to.getRank() + " to file: " + to.getFile());
        SquareIF[][] squares = board.getSquares();
        PieceIF fromPiece = squares[from.getRank().getIndex()][from.getFile().getIndex()].getPiece();
        PieceIF toPiece = squares[to.getRank().getIndex()][to.getFile().getIndex()].getPiece();

        //***********************TESTING*******************
        if(fromPiece != null) {
            System.out.println("from piece color & piece: " + fromPiece.getColor() + "  " + fromPiece.getChessPieceType());
            if(toPiece != null) {
                System.out.println("to piece color & piece: " + toPiece.getColor() + "  " + toPiece.getChessPieceType());
            }
        }
        //**********************************************************/

        // check if we are moving up one space
        if(fromFile == toFile && Math.abs(toRank - fromRank) == 1 && toPiece == null){
            return true;
        }

        // check if we are taking a piece diagonally
        if(Math.abs(toFile - fromFile) == 1 && Math.abs(toRank - fromRank) == 1){
            if(toPiece != null){
                return !checkMoveOnAlly(fromPiece, toPiece);
            }else{
                return false;
            }
        }

        // For black, we check if we can move up 2 spaces
        if(fromPiece.getColor() == GameColor.BLACK && fromRank == 6 && toRank == 4
                && toFile == fromFile){
            // check if the two spaces are empty
            if( squares[fromRank - 1][fromFile].getPiece() == null &&
                                squares[fromRank - 2][fromFile].getPiece() == null){
                return true;
            }
        }

        // For white, we check if we can move up 2 spaces
        if(fromPiece.getColor() == GameColor.WHITE && fromRank == 1 && toRank == 3
                && toFile == fromFile){
            // check if the two spaces are empty
            if(squares[fromRank + 1][fromFile].getPiece() == null &&
                                squares[fromRank + 2][fromFile].getPiece() == null){
                return true;

            }
        }

        // TODO: do En Passante
        return false;
	}

    /**
     * Returns an array of all possible positions that the piece can legally
     * move to.
     * 
     * @param pos - The current position of the piece.
     * @return - An array of Position objects, each position being a space on
     *           the board that the piece can legally move to.
     */
	@Override
	public Position[] showMoves(Position pos) {
        //TODO: Check if this move will put king into check
        //TODO: En Passante

        // tells us whether we need to add 1 to our rank or subtract 1
        final int BLACK_ADD_ONE = 1;
        final int WHITE_ADD_ONE = -1;

        ArrayList<Position> posArr = new ArrayList<>();
        SquareIF[][] squares = board.getSquares();
        PieceIF fromPiece = squares[pos.getRank().getIndex()][pos.getFile().getIndex()].getPiece();

        // we call a helper method to add the positions
        if(fromPiece.getColor() == GameColor.WHITE) {
            addPositions(pos, squares, WHITE_ADD_ONE, posArr);
        }else if(fromPiece.getColor() == GameColor.BLACK){
            addPositions(pos, squares, BLACK_ADD_ONE, posArr);
        }

       // /***********************TESTING*******************************
        for(Position found : posArr){
            System.out.println(found);
        }
         //******************************************************

		return posArr.toArray(new Position[posArr.size()]);
    }


    /**
     * Helper method that adds valid positions to the Array List of valid moves.
     * @param pos our current piece's position object
     * @param squares our 2D squares that make up the chess board
     * @param move_pos tells us whether the piece we are moving is WHITE or BLACK
     * @param posArr the array list we are adding valid positions to
     */
    private void addPositions(Position pos, SquareIF[][] squares,
                              int move_pos, ArrayList<Position> posArr){
        int fromRank = pos.getRank().getIndex();
        int fromFile = pos.getFile().getIndex();
        PieceIF fromPiece = squares[fromRank][fromFile].getPiece();

        // check if we can move up one space
        if(checkBounds(fromRank + move_pos) && squares[fromRank + move_pos][fromFile].getPiece() == null){
            // add this position to the list
            posArr.add(new Position(Rank.getRankFromIndex(fromRank + move_pos),
                    File.getFileFromIndex(fromFile), squares[fromRank + move_pos][fromFile]));
            // check if we can move up two spaces
            if(checkBounds(fromRank + move_pos + move_pos) &&
                    squares[fromRank + move_pos + move_pos][fromFile].getPiece() == null){
                // add this position to the list
                posArr.add(new Position(Rank.getRankFromIndex(fromRank + move_pos + move_pos), File.getFileFromIndex(fromFile),
                        squares[fromRank + move_pos + move_pos][fromFile]));
            }
        }

        // check if we can take diagonally
        if(checkBounds(fromRank + move_pos)) {
            // check up and to the right for white, left for black
            if(checkBounds(fromFile + 1)) {
                PieceIF toPiece = squares[fromRank + move_pos][fromFile + 1].getPiece();
                if(toPiece != null && !checkMoveOnAlly(fromPiece, toPiece)){
                    // add a valid position to the array list
                    posArr.add(new Position(Rank.getRankFromIndex(fromRank + move_pos), File.getFileFromIndex(fromFile + 1),
                            squares[fromRank + move_pos][fromFile + 1]));
                }
            }
            // check up and to the left for white, right for black
            if(checkBounds(fromFile - 1)){
                PieceIF toPiece = squares[fromRank + move_pos][fromFile - 1].getPiece();
                if(toPiece != null && !checkMoveOnAlly(fromPiece, toPiece)){
                    // add a valid position to the array list
                    posArr.add(new Position(Rank.getRankFromIndex(fromRank + move_pos), File.getFileFromIndex(fromFile - 1),
                            squares[fromRank + move_pos][fromFile - 1]));
                }
            }
        }
    }


    /**
     * Checks if the index is within the valid bounds of the board.
     * @param difference the index we are checking
     * @return true if the index is within the valid bounds
     */
    private boolean checkBounds(int difference){
        final int UPPER_BOUND = 7;
        final int LOWER_BOUND = 0;
        return difference <= UPPER_BOUND && difference >= LOWER_BOUND;
    }


	@Override
	public String toString(){
		return p.toString();
	}

	@Override
	public GameColor getColor() {
		return p.getColor();
	}
}