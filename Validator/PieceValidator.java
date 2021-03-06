package Validator;

import Interfaces.SquareIF;
import Model.Board;
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
    protected PieceIF p;

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
     * Gets the underlying chess piece type that this validator is wrapping.
     * 
     * @return - An enumeration of type ChessPieceType.
     */
    public ChessPieceType getChessPieceType() {
        return getPiece().getChessPieceType();
    }

    /**
     * Gets the underlying color that this validator is wrapping.
     * 
     * @return - An enumeration of type GameColor.
     */
    public GameColor getColor() {
        return getPiece().getColor();
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
     * Checks if our piece takes our opponents piece if we are still in check
     * @param from the position we are moving from
     * @param to the position we are taking
     * @param color the color of our king
     * @return true if after a move for this piece, it is still in check
     */
    public boolean stillCheckAfterMove(Position from, Position to, GameColor color){
        boolean result = false;
        SquareIF[][] squares = board.getSquares();
        int toRank = to.getRank().getIndex();
        int toFile = to.getFile().getIndex();

        // we get whatever is at the toRank and toFile square, could be null or an actual piece
        PieceIF theirPiece = squares[toRank][toFile].getPiece();
        squares[toRank][toFile].setPiece(null);

        int fromRank = from.getRank().getIndex();
        int fromFile = from.getFile().getIndex();

        // we set
        PieceIF ourPiece = squares[fromRank][fromFile].getPiece();
        squares[fromRank][fromFile].setPiece(null);
        squares[toRank][toFile].setPiece(ourPiece);

        Board castedBoard = (Board) board;
        if(castedBoard.checkForCheck(color)){
            result = true;
        }

        squares[fromRank][fromFile].setPiece(ourPiece);
        squares[toRank][toFile].setPiece(theirPiece);
        return result;
    }

    /**
     * Gets the string form of a pice
     * @return - The string representation of a piece
     */
    @Override
    public String toString(){
        return p.toString();
    }

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    @Override
    public abstract PieceValidator clone();

    /**
     * Compares an object with this PieceValidator object.
     * 
     * @param obj - An object to compare with this PieceValidator object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public abstract boolean equals(Object obj);
}