package Validator;

import Model.Position;
import java.util.ArrayList;

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
    public HorizVertValidator(SquareIF[][] board) {
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

        // Ensure that the Piece is not moving past other units
        if (fromRank == toRank) {
            for (int i = fromRank + 1; i < toRank - 1; i++)
                if (board[fromFile][i].getPiece() != null) return false;
        } else {
            for (int i = fromFile + 1; i < toFile - 1; i++)
                if (board[i][fromRank].getPiece() != null) return false;
        }

        // Ensure the final spot is not an ally or an opposing team's King.
        PieceIF fromPiece = board[fromFile][fromRank].getPiece();
        PieceIF toPiece = board[toFile][toRank].getPiece();
        if (toPiece != null) {
            if (fromPiece.getColor() == toPiece.getColor()
                || toPiece.getChessPieceType().getName() == "King") {
                return false;
            }
        }
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
		return null;
    }
}