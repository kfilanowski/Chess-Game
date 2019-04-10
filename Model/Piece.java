package Model;

import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.PieceIF;

/**
 * Class that models a piece by extending BlackAndWhite
 * and implementing a PieceIF. It has a ChessPieceType enum
 * and uses a GameColor enum.
 * @author Kevin Filanowski 100%
 * @version March 20, 2019
 */
public class Piece extends BlackAndWhite implements PieceIF {
    /** Defines the type of chess piece, I.E rook, bishop, pawn, etc. */
    private ChessPieceType type;

    /** This boolean will be set to false if a piece has moved, required for castle functionality */
    private boolean hasMoved;

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
        this.hasMoved = false;
        this.type = type;
    }

    /**
     * Piece constructor that makes a new piece from a ChessPieceType enum
     * @param type - ChessPieceType enum that the new piece is being made from
     * @param color - GameColor enum that the new piece is being made from
     */
    public Piece(ChessPieceType type, GameColor color) {
        this.hasMoved = false;
        this.type = type;
        super.setColor(color);
    }

    /**
     * Gets the boolean that tells whether or not a piece has moved
     * @return - The boolean that chcks if a piece has moved
     */
    @Override
    public boolean getHasMoved(){
        return this.hasMoved;
    }

    /**
     * Sets the boolean that tells whether or not a piece has moved
     * @param hasMoved - Our hasMoved boolean
     */
    @Override
    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
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

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public PieceIF clone() {
        Piece newPiece = new Piece();
        newPiece.setChessPieceType(type);
        newPiece.setColor(getColor());
        return newPiece;
    }

    /**
     * Compares an object with this Piece object.
     * 
     * @param obj - An object to compare with this Piece object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Piece) {
            Piece p = (Piece) obj;
            if (type.equals(p.type)) {
                return getColor().equals(p.getColor());
            } 
        }
        return false;
    }
}
