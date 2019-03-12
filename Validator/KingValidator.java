package Validator;

import Interfaces.BoardIF;
import Model.Position;

/**
 * Models the piece's ability to move like a King,
 * and perform King-like functions.
 * 
 * @author Kevin Filanowski
 * @version March 10, 2019
 */
public class KingValidator extends PieceValidator {

    /**
     * Constructor for KingValidator.
     * 
     * @param board - The current state of the board.
     */
    public KingValidator(BoardIF board) {
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
		return null;
    }
}