package Model;

import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.PieceIF;


/**
 * Class that models a piece by extending BlackAndWhite
 * and implementing a PieceIF. It has a ChessPieceType enum
 * and uses a GameColor enum.
 * @author Kevin Filanowski
 * @version March 20, 2019
 */
public class Piece extends BlackAndWhite implements PieceIF {
    ChessPieceType type;

    /**
     * Default piece constructor
     */
    public Piece() {
        type = null;
    }

    /**
     * Piece constructor that makes a new piece from a ChessPieceType enum
     * @param type - ChessPieceType enum that the new piece is being made from
     */
    public Piece(ChessPieceType type) {
        this.type = type;
    }

    /**
     * Piece constructor that makes a new piece from a ChessPieceType enum
     * @param type - ChessPieceType enum that the new piece is being made from
     * @param color - GameColor enum that the new piece is being made from
     */
    public Piece(ChessPieceType type, GameColor color) {
        this.type = type;
        super.setColor(color);
    }

    /**
     * Sets a piece to a new ChessPieceType
     * @param type - The ChessPieceType that the piece is being set to
     */
    @Override
    public void setChessPieceType(ChessPieceType type) {
        this.type = type;
    }

    /**
     * Gets the ChessPieceType of a piece
     * @return - The ChessPieceType of a piece
     */
    @Override
    public ChessPieceType getChessPieceType() {
        return this.type;
    }

    /**
     * Gets the String representation of the piece
     * @return - The piece in string form
     */
    @Override
    public String toString(){
        String pieceType = getChessPieceType().getSymbol();
        return pieceType;
    }
}
