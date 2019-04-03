package Interfaces;

import Model.Position;

/**
 * This gives the basic outline of a square that a piece will be placed
 * 
 * @author Matt Lutz 90%
 * @author Kevin Filanowski 10%
 */
public interface SquareIF {
    /**
     * Clears the Chess Square by setting the piece to null.
     */
    public void clear();

    /**
     * Set a piece on this square, so square holds a reference to it.
     *
     * @param piece - A PieceIF object to set onto this square.
     */
    public void setPiece(PieceIF piece);

    /**
     * Retrieves the piece on this square.
     *
     * @return - The piece object referenced by this square, or null if there
     * are no pieces on this square.
     */
    public PieceIF getPiece();

    /**
     * Determine if the color is white.
     *
     * @return - True if it is white, false if it is black.
     */
    public boolean isWhite();

    /**
     * Determine if the color is black.
     *
     * @return - True if it is black, false if it is white.
     */
    public boolean isBlack();

    /**
     * Retrieve the position at this square.
     *
     * @return - The position at this object.
     */
    public Position getPostion();


    public String toString(String backColor, String foreColor);

    public void setHighlighted(boolean b);

    public boolean getHighlighted();
}