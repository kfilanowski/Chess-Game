package Interfaces;

import Enums.File;
import Enums.Rank;
import History.State;
import Model.Board;
import Model.Position;

/**
 * @author Matt Lutz 90%
 * @author Kevin Filanowski 10%
 * This interface outlines the basic functionality of a board
 */
public interface BoardIF {

    /**
     * This sets up the the board with squares that will hold pieces and the piece validators.
     */
    public void init_board();

    /**
     * This is the default setup of the board that sets black and white pieces on the board.
     */
    public void setup();

    /**
     * This method calls the draw method in either color or mono colored.
     */
    public void draw();

    /**
     * This returns the complete board that the game is being played on.
     * @return - the board that chess is being played on
     */
    public SquareIF[][] getSquares();

    /**
     * Sets whether the users want to play in colored ot mono-colored mode.
     * @param d - the draw strategy that is being used.
     */
    public void setDrawStrategy(BoardStrategy d);

    /**
     * gets the width of the board
     * @return - the width of the board
     */
    public int getWidth();

    /**
     * gets the height of the board
     * @return - the height of the board
     */
    public int getHeight();

    /**
     * Gets the piece on the Square that is inputted
     * @param r - The rank of input position
     * @param f - The file of input position
     * @return - the piece in that position
     */
    public PieceIF getPiece(Rank r, File f);

    /**
     * gets the piece on the Sqare that is inputted
     * @param col - The column of the input position
     * @param row - The row of the input position
     * @return - the piece in that position
     */
    public PieceIF getPiece(int col, int row);

    /**
     * Retrieve a square at a specified rank and file.
     * @param rank - The rank the square falls on.
     * @param file - The file the square falls on.
     * @return - A SquareIF from the board that falls on the specified
     *           rank and file.
     */
    public SquareIF getSquare(Rank rank, File file);

    /**
     * Retrieve a square at a specified position.
     * 
     * @param pos - The position of the square.
     * @return    - A SquareIF from the board that falls on the specified
     *              position.
     */
    public SquareIF getSquare(Position pos);

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public BoardIF clone();
    
    /**
     * Return a state object ensapsulating a clone of this board object 
     * in its current state.
     * 
     * @return - A state encapsulating the current state of 
     *          this board object as a clone.
     */
    public State<Board> saveState();

    /**
     * Restores the state of this object from a state object.
     * 
     * @param state - The state from which to get the state of the board.
     */
    public void restoreState(State<Board> state);

    /**
     * 
     * 
     * 
     */
    public void draw(BoardIF board, Position[] pos);


    public void revDraw(BoardIF board);
}
