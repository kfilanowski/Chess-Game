package Validator;

import Model.Piece;
import Model.Position;
import Enums.ChessPieceType;
import Interfaces.BoardIF;
import Interfaces.PieceIF;

/**
 * The Decorator abstract class for each movement type in Chess.
 * 
 * @author Kevin Filanowski
 * @version March 10, 2019
 */
public abstract class PieceValidator extends Piece {
    /** The current state of the board required validity checking. */
    protected BoardIF board;

    /**
     * Constructor for PieceValidator.
     * 
     * @param board - The current state of the board.
     */
    public PieceValidator(BoardIF board) {
        this.board = board;
    }

    /**
     * Checks to see if the move to be attempted is a valid move by the standards of
     * Chess for this particular movement type.
     * 
     * @param from - The position the piece currently has before movement.
     * @param to   - The position the piece is being asked to move to.
     * @return - True if the piece movement is valid, otherwise returns false.
     */
    public abstract boolean validateMove(Position from, Position to);

    /**
     * Returns an array of all possible positions that the piece can legally move
     * to.
     * 
     * @param pos - The current position of the piece.
     * @return - An array of Position objects, each position being a space on the
     *         board that the piece can legally move to.
     */
    public abstract Position[] showMoves(Position pos);

    /**
     * Checks if a piece is moving onto a piece of the same color.
     * 
     * @param from - The piece that is initiating the movement.
     * @param to   - The piece that is being contested by the from piece.
     * @return     - True if the from piece attempts to move on the same square
     *               where a piece of the same color resides.
     */
    public boolean checkMoveOnAlly(PieceIF from, PieceIF to) {
        if (from == null || to == null) { return false; }
        if (from.getColor() == to.getColor()) { return true; }
        return false;
    }

    /**
     * Checks if a piece is a King piece.
     * 
     * @param p - The piece to check.
     * @return - True if the piece is a King piece, false otherwise.
     */
    public boolean checkIfKing(PieceIF p) {
        if (p == null) { return false; }
        if (p.getChessPieceType().getName().equals(ChessPieceType.King.getName())) {
            return true;
        }
        return false;
    }
}