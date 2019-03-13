package Validator;

import Model.Position;
import java.util.ArrayList;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

/**
 * Models the piece's ability to move horizontally and vertically.
 * 
 * @author Kevin Filanowski
 * @version March 10, 2019
 */
public class HorizVertValidator extends PieceValidator {

    /**
     * Constructor for HorizVertValidator.
     * 
     * @param board - The current state of the board.
     */
    public HorizVertValidator(BoardIF board) {
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
        int fromFile = from.getFileIndex(), fromRank = from.getRankIndex();
        int toFile   = to.getFileIndex(),   toRank   = to.getRankIndex();

        // Check to see if the movement is not horizontal or vertical.
        if (fromFile != toFile && fromRank != toRank) return false;

        // Check to see if the positions are the same
        if (fromFile == toFile && fromRank == toRank) return false;

        // Ensure that the Piece is not moving past other units
        SquareIF[][] squares = board.getSquares();
        if (fromFile == toFile) {
            for (int i = Math.min(fromRank, toRank) + 1;
                     i < Math.max(fromRank, toRank) - 1;
                     i++) {
                if (squares[i][fromFile].getPiece() != null) return false;
            }
        } else {
            for (int j = Math.min(fromFile, toFile) + 1;
                     j < Math.max(fromFile, toFile) - 1;
                     j++) {
                if (squares[fromRank][j].getPiece() != null) return false;
            }
        }
        
        // Ensure the final spot is not an ally or an opposing team's King.
        PieceIF fromPiece = squares[fromRank][fromFile].getPiece();
        PieceIF toPiece = squares[toRank][toFile].getPiece();
        if (checkMoveOnAllyOrKing(fromPiece, toPiece)) return false;

		return true;
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
        ArrayList<Position> posArray = new ArrayList<>();
        /*SquareIF[][] squares = board.getSquares();
        int file = pos.getFileIndex(), rank = pos.getRankIndex();

        
        // Check Up.
        int i = pos.getRankIndex();
        while (i > 0 && squares[--i][file].getPiece() == null) {
            //posArray.add(new Position(Rank., file, squares[i][file]));
        }
        // TODO: Helper method to check if the position at I is an opponent piece.

        // Check Down.
        for (int i = pos.getRankIndex(); i < squares.length; i++) {
            if (squares[i][file].getPiece() == null) {
                // posArray.add(new Position(, file, squares[i][file]));
            }
        }

        // Check to the left.
        for (int i = pos.getFileIndex(); i > 0; i--) {
            if (squares[rank][i].getPiece() == null) {
                // posArray.add(new Position(, file, squares[rank][i]));
            }
        }

        // Check to the right.
        for (int i = pos.getFileIndex(); i < squares.length; i++) {
            if (squares[rank][i].getPiece() == null) {
                // posArray.add(new Position(, file, squares[i][file]));
            }
        }
        */
        return null;
		//return (Position[]) posArray.toArray();
    }
}