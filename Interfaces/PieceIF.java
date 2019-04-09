package Interfaces;
import Model.Position;
import Enums.ChessPieceType;

/**
 * @author Matt Lutz 40%
 * @author Jacob Ginn 40%
 * @author Kevin Filanowski 20%
 * PieceIF that models a piece by extending BlackAndWhiteIF
 * and using ChessPieceType.
 */
public interface PieceIF extends BlackAndWhiteIF {
    /**
     * Gets the ChessPieceType of a piece.
     * @return - The ChessPieceType of a piece.
     */
    public ChessPieceType getChessPieceType();

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    public void setChessPieceType(ChessPieceType type);

    /**
     * Method that shows all valid movement options for a piece
     * @param pos - Position of the piece we want to show the moves for
     * @return - An array that contains all the valid movement positions for this piece
     */
    default Position[] showMoves(Position pos){
        return new Position[0];
    }

    /**
     * Method that checks to see a move is valid for a given piece
     * @param from - Position you want to move from
     * @param to - Position you want to move to
     * @return - True or False depending on whether or not the move was valid
     */
    default boolean validateMove(Position from, Position to){
        return false;
    }

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public PieceIF clone();

    /**
     * Gets the boolean that tells whether or not a piece has moved
     * @return - A boolean that tells if a piece has moved
     */
    public boolean getHasMoved();

    /**
     * Sets the boolean that tells whether or not a piece has moved
     */
    public void setHasMoved(boolean hasMoved);
}
