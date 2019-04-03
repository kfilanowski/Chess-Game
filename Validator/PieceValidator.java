package Validator;

import Model.Piece;
import Model.Position;
import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.BoardIF;
import Interfaces.PieceIF;

/**
 * The Decorator abstract class for each movement type in Chess.
 * @author Kevin Filanowski 100%
 * @version March 20, 2019
 */
public abstract class PieceValidator extends Piece {
    /** The current state of the board required validity checking. */
    protected BoardIF board;
    /** A PieceIf used for the PieceValidator */
    public PieceIF p;

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
        // Unwrap until we get to piece.
        while (from.getClass() != Piece.class) {
            from = ((PieceValidator)from).p;
        }
        while (to.getClass() != Piece.class) {
            to = ((PieceValidator) to).p;
        }
        if (from.getColor() == to.getColor()) { return true; }
        return false;
    }

    /**
     * Unwrappes the validators until the piece is revealed and returns that piece.
     * 
     * @return - The piece stripped of its validators.
     */
    public PieceIF getPiece() {
        PieceIF temp = p;
        while (temp.getClass() != Piece.class) {
            temp = ((PieceValidator) temp).p;
        }
        return temp;
    }

    /**
     * Checks if a piece is a King piece.
     * 
     * @param p - The piece to check.
     * @return - True if the piece is a King piece, false otherwise.
     */
    public boolean checkIfKing(PieceIF p) {
        if (p == null) { return false; }
        // Unwrap until we get to piece.
        while (p.getClass() != Piece.class) {
            p = ((PieceValidator) p).p;
        }
        if (p.getChessPieceType().getName()
                        .equals(ChessPieceType.KING.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Gets the color for a piece validator
     * @return - The color of the piece
     */
    public GameColor getColor() {
        return p.getColor();
    }

    /**
     * Gets the string form of a pice
     * @return - The string representation of a piece
     */
    @Override
    public String toString(){
        return p.toString();
    }
}