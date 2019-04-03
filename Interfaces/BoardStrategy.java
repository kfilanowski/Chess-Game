package Interfaces;

import Model.Position;

/**
 * Sets the draw strategy of the board which determines whether it will be
 * printed in mono or color.
 * 
 * @author Matt Lutz 100%
 * @version March 20, 2019
 */
public interface BoardStrategy {

    /**
     * This method calls the draw method in either color or mono colored.
     */
    public void draw(BoardIF board);

    public void draw(BoardIF board, Position[] pos);
}
