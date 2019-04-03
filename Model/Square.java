package Model;

import Enums.GameColor;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

/**
 * Represents a square on a Chess board.
 * 
 * @author Kevin Filanowski 85%
 * @author Jeriah Caplinger 15%
 * @version March 20, 2019
 */
public class Square extends BlackAndWhite implements SquareIF {
    /** The piece on the square. */
    private PieceIF piece;
    /** The position of the object, in terms of rank and file. */
    Position pos;

    /**
     * Constructor that initializes an empty square.
     */
    public Square() {
        piece = null;
        pos = null;
    }

    /**
     * Constructor that intializies an empty square with a color.
     */
    public Square(GameColor color, Position pos) {
        super.setColor(color);
        this.pos = pos;
    }

    /**
     * Constructor that initializes a square with a piece on it.
     * 
     * @param piece - The piece to set on the square.
     */
    public Square(PieceIF piece, Position pos) {
        this.piece = piece;
        this.pos = pos;
    }

    /**
     * Clears the Chess Square by setting the piece to null.
     */
    @Override
    public void clear() {
        this.piece = null;
    }

    /**
     * Retrieves the piece on this square.
     * 
     * @return - The piece object referenced by this square, or null if there
     *           are no pieces on this square.
     */
    @Override
    public PieceIF getPiece() {
        return this.piece;
    }

    /**
     * Set a piece on this square, so square holds a reference to it.
     * 
     * @param piece - A PieceIF object to set onto this square.
     */
    @Override
    public void setPiece(PieceIF piece) {
        this.piece = piece;
    }

    /**
     * Method that gets the square in string form
     * 
     * @return - String form of a square
     */
    public String toString() {
        return " ";
    }

    /**
     * Retrieve a position of this square.
     * 
     * @return - The position representing this square.
     */
    public Position getPostion() {
        return pos;
    }
}
