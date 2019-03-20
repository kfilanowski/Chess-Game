package Model;

import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.PieceIF;


/**
 * A Piece class that models a chess piece that extends BlackAndWhite
 * and implements PieceIF. It also has a ChessPieceType enum and uses a
 * GameColor Enum
 *
 * @author Kevin Filanowski
 * @version March 2019
 */
public class Piece extends BlackAndWhite implements PieceIF {
    /** Instance of a ChessPieceType enum */
    ChessPieceType type;

    /**
     * Default piece constructor that initializes a new piece
     */
    public Piece() {
        type = null;
    }

    /**
     * Piece constructor that initializes a new piece based on a ChessPieceType type
     * @param type - ChessPieceType that the piece is initialized to
     */
    public Piece(ChessPieceType type) {
        this.type = type;
    }

    /**
     * Piece constructor that initializes a new piece based on a ChessPieceType type
     * and a GameColor
     * @param type - ChessPieceType that the piece is initialized to
     * @param color - GameColor that the piece is initialized to
     */
    public Piece(ChessPieceType type, GameColor color) {
        this.type = type;
        super.setColor(color);
    }

    /**
     * Method that sets the ChessPieceType to a new ChessPieceType
     * @param type - The new ChessPieceType we are setting
     */
    @Override
    public void setChessPieceType(ChessPieceType type) {
        this.type = type;
    }

    /**
     * Gets the ChessPieceType of a piece
     * @return - ChessPieceType enum
     */
    @Override
    public ChessPieceType getChessPieceType() {
        return this.type;
    }

    /**
     * Method that gets the Piece and changes it to string form
     * @return - String that represents the ChessPieceType of the piece
     */
    @Override
    public String toString(){
        String pieceType = getChessPieceType().getSymbol();
        return pieceType;
    }

    /**
     * Method that shows all of the valid movement options for a piece
     *
     * @param pos - Position of the piece for which we want to show the moves
     * @return - An array that contains the valid movement options for the piece
     */
    public Position[] showMoves(Position pos) {
        return new Position[0];
    }

    /**
     * Checks to see if the move to be attempted is a valid move by the standards of
     * Chess for this particular movement type.
     *
     * @param from - The position the piece currently has before movement.
     * @param to   - The position the piece is being asked to move to.
     * @return - True if the piece movement is valid, otherwise returns false.
     */
    public boolean validateMove(Position from, Position to){
        return this.validateMove(from,to);
    }



}
