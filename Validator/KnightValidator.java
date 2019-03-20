package Validator;

import Enums.File;
import Enums.Rank;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Position;
import Interfaces.BoardIF;

import java.util.ArrayList;

/**
 * Models the piece's ability to move like a Knight,
 * and perform Knight-like functions.
 * 
 * @author Jeriah Caplinger
 * @version March 18, 2019
 */
public class KnightValidator extends PieceValidator {

    /**
     * Constructor for KnightValidator.
     * 
     * @param board - The current state of the board.
     */
    public KnightValidator(BoardIF board) {
        super(board);
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
	    int fromRank = from.getRank().getIndex();
	    int fromFile = from.getFile().getIndex();

	    int toRank = to.getRank().getIndex();
	    int toFile = to.getFile().getIndex();

	    int rankDiff;
	    int fileDiff;

	    // we do this to check if its a move on an ally piece
	    SquareIF[][] squares =  board.getSquares();
        PieceIF fromPiece = squares[from.getRank().getIndex()][from.getFile().getIndex()].getPiece();
        PieceIF toPiece = squares[to.getRank().getIndex()][to.getFile().getIndex()].getPiece();


	    // if they are on the same file or rank, then it is not a valid move
        // and we can safely perform our validation checks
        // TODO: add check for our own king's check here
	    if(fromRank == toRank || fromFile == toFile || checkMoveOnAlly(fromPiece, toPiece)){
	        return false;
	    }

        // here we subtract to see if it is a valid move
        rankDiff = Math.abs(toRank - fromRank);
	    fileDiff = Math.abs(toFile - fromFile);

	    // we return a boolean stating whether the knight is moving 2 to the left or right
        // and 1 up or down; or whether the knight is moving 1 to the left or right and 2 up
        // or down
	    return ((fileDiff == 2 && rankDiff == 1 ) || (fileDiff == 1 && rankDiff == 2));
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
        ArrayList<Position> posArr = new ArrayList<>();
        SquareIF[][] squares = board.getSquares();
        PieceIF fromPiece = squares[pos.getRank().getIndex()][pos.getFile().getIndex()].getPiece();
        //TODO: First thing is to check whether if we move this piece it will
        // put our king into check
        final int MOVE_ONE = 1;
        final int MOVE_TWO = 2;
        int fromRank = pos.getRank().getIndex();
        int fromFile = pos.getFile().getIndex();
        int toRank;
        int toFile;

        // There are 8 cases:
        // Case 1
        toRank = fromRank + MOVE_ONE;
        toFile = fromFile + MOVE_TWO;
        addPosition(toRank, toFile, fromPiece, squares, posArr);

        // Case 2
        toRank = fromRank + MOVE_TWO;
        toFile = fromFile + MOVE_ONE;
        addPosition(toRank, toFile, fromPiece, squares, posArr);

        // Case 3
        toRank = fromRank + MOVE_ONE;
        toFile = fromFile - MOVE_TWO;
        addPosition(toRank, toFile, fromPiece, squares, posArr);

        // Case 4
        toRank = fromRank + MOVE_TWO;
        toFile = fromFile - MOVE_ONE;
        addPosition(toRank, toFile, fromPiece, squares, posArr);

        // Case 5
        toRank = fromRank - MOVE_ONE;
        toFile = fromFile - MOVE_TWO;
        addPosition(toRank, toFile, fromPiece, squares, posArr);

        // Case 6
        toRank = fromRank - MOVE_TWO;
        toFile = fromFile - MOVE_ONE;
        addPosition(toRank, toFile, fromPiece, squares, posArr);

        // Case 7
        toRank = fromRank - MOVE_ONE;
        toFile = fromFile + MOVE_TWO;
        addPosition(toRank, toFile, fromPiece, squares, posArr);

        // Case 8
        toRank = fromRank - MOVE_TWO;
        toFile = fromFile + MOVE_ONE;
        addPosition(toRank, toFile, fromPiece, squares, posArr);

        // Convert to Position[] array and return.
        return posArr.toArray(new Position[posArr.size()]);

    }

    /**
     * Helper method that adds a valid position move to the ArrayList of valid moves.
     * @param toRank the rank we want to move to
     * @param toFile the file we want to move to
     * @param fromPiece the piece we are currently at
     * @param squares a 2D array of all the squares on the board
     * @param posArr the ArrayList that holds all possible moves
     */
    private void addPosition(int toRank, int toFile, PieceIF fromPiece, SquareIF[][] squares, ArrayList<Position> posArr){
        if(checkBounds(toRank) && checkBounds(toFile)){
            // we get the piece at the square we are trying to go to
            PieceIF toPiece = squares[toRank][toFile].getPiece();
            // if it is not an ally piece, we add the move to our ArrayList
            if(!checkMoveOnAlly(fromPiece, toPiece)){
                posArr.add(new Position(Rank.getRankFromIndex(toRank),
                        File.getFileFromIndex(toFile), squares[toRank][toFile]));
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

}