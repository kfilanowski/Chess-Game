package Interfaces;

import Model.Position;

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

    /**
     * calls the beginning functions of the board.
     * @param board - The board that the game is being played on.
     */
    public void go(BoardIF board);

    /**
     * Draws the board with the highlighted positions.
     * @param board - The board that the game is being played on.
     * @param pos - The position array that holds the valid position.
     */
    public void draw(BoardIF board, Position[] pos);

    /**
     * Draws the board for the player playing the black pieces.
     * @param board - The board that the game is being played on.
     */
    public void revDraw(BoardIF board);

    /**
     * Gets the names of the players that are playing the game.
     */
    public void getNames();

    /**
     * Draws the board for the player playing the black pieces with the highlighted positions.
     * @param board - The board that the game is being played on.
     * @param pos- The position array that holds the valid position.
     */
    public void revDraw(BoardIF board, Position[] pos);

}
