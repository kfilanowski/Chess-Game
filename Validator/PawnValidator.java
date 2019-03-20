package Validator;

import Enums.GameColor;
import Interfaces.PieceIF;
import Model.Position;
import Interfaces.BoardIF;

/**
 * Models the piece's ability to move like a Pawn,
 * and perform Pawn-like functions.
 * 
 * @author Kevin Filanowski
 * @version March 10, 2019
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

	@Override
	public String toString(){
		return p.toString();
	}

	@Override
	public GameColor getColor() {
		return p.getColor();
	}
}