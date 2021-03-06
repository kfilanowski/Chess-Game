package Validator;

import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Board;
import Model.Position;
import Interfaces.BoardIF;

import java.util.ArrayList;

/**
 * Models the piece's ability to move like a Knight,
 * and perform Knight-like functions.
 * 
 * @author Jeriah Caplinger (100%)
 * @version March 18, 2019
 */
public class KnightValidator extends PieceValidator {

    /**
     * Constructor for KnightValidator.
     * 
     * @param board - The current state of the board.
     */
    public KnightValidator(BoardIF board, PieceIF valid_piece) {
        this.board = board;
        this.p = valid_piece;
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
        //System.out.println(!stillCheckAfterMove(from, to, fromPiece.getColor()));
        //System.out.println(((fileDiff == 2 && rankDiff == 1 ) || (fileDiff == 1 && rankDiff == 2)));
	    return ((fileDiff == 2 && rankDiff == 1 ) || (fileDiff == 1 && rankDiff == 2)) && !checkIfKing(toPiece) &&
                !stillCheckAfterMove(from, to, fromPiece.getColor());
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
        addPosition(pos, toRank, toFile, fromPiece, squares, posArr);

        // Case 2
        toRank = fromRank + MOVE_TWO;
        toFile = fromFile + MOVE_ONE;
        addPosition(pos, toRank, toFile, fromPiece, squares, posArr);

        // Case 3
        toRank = fromRank + MOVE_ONE;
        toFile = fromFile - MOVE_TWO;
        addPosition(pos, toRank, toFile, fromPiece, squares, posArr);

        // Case 4
        toRank = fromRank + MOVE_TWO;
        toFile = fromFile - MOVE_ONE;
        addPosition(pos, toRank, toFile, fromPiece, squares, posArr);

        // Case 5
        toRank = fromRank - MOVE_ONE;
        toFile = fromFile - MOVE_TWO;
        addPosition(pos, toRank, toFile, fromPiece, squares, posArr);

        // Case 6
        toRank = fromRank - MOVE_TWO;
        toFile = fromFile - MOVE_ONE;
        addPosition(pos, toRank, toFile, fromPiece, squares, posArr);

        // Case 7
        toRank = fromRank - MOVE_ONE;
        toFile = fromFile + MOVE_TWO;
        addPosition(pos, toRank, toFile, fromPiece, squares, posArr);

        // Case 8
        toRank = fromRank - MOVE_TWO;
        toFile = fromFile + MOVE_ONE;
        addPosition(pos, toRank, toFile, fromPiece, squares, posArr);

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
    private void addPosition(Position from, int toRank, int toFile, PieceIF fromPiece,
                             SquareIF[][] squares, ArrayList<Position> posArr){
        if(checkBounds(toRank) && checkBounds(toFile)){
            // we get the piece at the square we are trying to go to
            PieceIF toPiece = squares[toRank][toFile].getPiece();
            // if it is not an ally piece, we add the move to our ArrayList
            Position to = new Position(Rank.getRankFromIndex(toRank), File.getFileFromIndex(toFile));
            if(!checkMoveOnAlly(fromPiece, toPiece) && !stillCheckAfterMove(from, to, fromPiece.getColor())
                && !checkIfKing(toPiece)){
                posArr.add(new Position(Rank.getRankFromIndex(toRank),
                        File.getFileFromIndex(toFile)));
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


    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    @Override
    public PieceValidator clone() {
        PieceIF newPiece = p.clone();
        return new KnightValidator(board, newPiece);
    }

    /**
     * Compares an object with this Validator object.
     * 
     * @param obj - An object to compare with this Validator object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof KnightValidator) {
            KnightValidator v = (KnightValidator) obj;
            return v.p.equals(p);
        }
        return false;
    }
}