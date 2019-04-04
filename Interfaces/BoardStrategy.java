package Interfaces;

/**
 * Sets the draw strategy of the board which determines whether it will be
 * printed in mono or color.
 * 
 * @author Matt Lutz 75%
 * @author Kevin Filanowski 25%
 * @version March 20, 2019
 */
public interface BoardStrategy {

    /**
     * This method calls the draw method in either color or mono colored.
     * 
     * @param board - The board to display.
     */
    public void draw(BoardIF board);

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public BoardStrategy clone();
}
