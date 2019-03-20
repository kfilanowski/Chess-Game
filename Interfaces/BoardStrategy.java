package Interfaces;

/**
 * sets the draw strategy of the board which determines whether it will be printed in mono or color
 */
public interface BoardStrategy {

    /**
     * This method calls the draw method in either color or mono colored.
     */
    public void draw(BoardIF board);
}
