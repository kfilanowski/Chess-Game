package Model;

import Enums.GameColor;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

/**
 * Represents a square on a Chess board.
 * 
 * @author unassigned
 * @author Kevin Filanowski
 * @version March 17, 2019
 */
public class Square extends BlackAndWhite implements SquareIF {
    private PieceIF piece;

    public Square(GameColor color){
        super.setColor(color);
    }

    /**
     * Constructor that initializes an empty square.
     */
    public Square() {
        piece = null;
    }

    /**
     * Constructor that initializes a square with a piece on it.
     * 
     * @param piece - The piece to set on the square.
     */
    public Square(PieceIF piece) {
        this.piece = piece;
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
    public void setPiece(PieceIF piece){
        this.piece = piece;
    }

    /**
     * Method that gets the square in string form
     * @return - String form of a square
     */
    public String toString(){
        return piece.getChessPieceType().toString();
    }

}
