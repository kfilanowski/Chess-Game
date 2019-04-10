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

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public SquareIF clone();

    /**
     * toString for the color board
     * @param backColor - the background color.
     * @param foreColor - the Color of the piece letter.
     * @return - the string that holds the square
     */
    public String toString(String backColor, String foreColor);

    /**
     *
     * @param validMove - the character for the valid move.
     * @param spaceChar - the character for the mono board that holds the character for either a black or white space.
     * @param spaceChar2 - the ending character for the mono board that holds the ending character for a space
     * @param p - whether the piece is capital or not
     * @return
     */
    public String toString(char validMove, char spaceChar, char spaceChar2, char p);

    /**
     * sets the highlighted squares to true or false
     * @param b - the boolean for setting the position to.
     */
    public void setHighlighted(boolean b);

    /**
     * gets whether the square is highlighted or not.
     * @return - true if square is highlighted else false.
     */
    public boolean getHighlighted();
}