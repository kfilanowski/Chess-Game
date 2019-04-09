package Model;

import Enums.GameColor;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

/**
 * Represents a square on a Chess board.
 * 
 * @author Kevin Filanowski 85%
 * @author Jeriah Caplinger 15%
 * @version March 20, 2019
 */
public class Square extends BlackAndWhite implements SquareIF {
    /** The piece on the square. */
    private PieceIF piece;
    /** The position of the object, in terms of rank and file. */
    private Position pos;
    /** The square is highlighted*/
    private Boolean highlighted;

    /**
     * Constructor that initializes an empty square.
     */
    public Square() {
        piece = null;
        pos = null;
        highlighted = false;
    }

    /**
     * Constructor that intializies an empty square with a color.
     */
    public Square(GameColor color, Position pos) {
        super.setColor(color);
        this.pos = pos;
        highlighted = false;
    }

    /**
     * Constructor that initializes a square with a piece on it.
     * 
     * @param piece - The piece to set on the square.
     */
    public Square(PieceIF piece, Position pos) {
        this.piece = piece;
        this.pos = pos;
        highlighted = false;
    }

    /**
     * Clears the Chess Square by setting the piece to null.
     */
    @Override
    public void clear() {
        this.piece = null;
    }

    /**
     * Retrieves the piece on this square.
     * 
     * @return - The piece object referenced by this square, or null if there
     *           are no pieces on this square.
     */
    @Override
    public PieceIF getPiece() {
        return this.piece;
    }

    /**
     * Set a piece on this square, so square holds a reference to it.
     * 
     * @param piece - A PieceIF object to set onto this square.
     */
    @Override
    public void setPiece(PieceIF piece) {
        this.piece = piece;
    }

    /**
     * Method that gets the square in string form
     * 
     * @return - String form of a square
     */
    public String toString(String backColor, String foreColor) {
        String str;
        if(this.getPiece() != null){
            str = (backColor + " " + backColor + foreColor + this.getPiece().toString() + " " + "\u001b[0m");
        }else{
            str = (backColor + "   " + "\u001b[0m");
        }
        return str;
    }

    /**
     * Retrieve a position of this square.
     * 
     * @return - The position representing this square.
     */
    public Position getPostion() {
        return pos;
    }

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public SquareIF clone() {
        Square newSquare = new Square();
        if (piece != null) {
            newSquare.setPiece(piece.clone());
        }
        newSquare.pos = pos.clone();
        newSquare.setColor(getColor());
        newSquare.highlighted = highlighted;
        return newSquare;
    }

    /**
     * Compares an object with this Square object.
     * 
     * @param obj - An object to compare with this Square object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Square) {
            Square s = (Square) obj;
            if (pos.equals(s.pos) && s.highlighted == highlighted) {
                if (s.piece == null && piece == null) {
                    return true;
                }
                if (piece == null || s.piece == null) {
                    return false;
                }
                return piece.equals(s.getPiece());
            }
        }
        return false;
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    public boolean getHighlighted(){
        return highlighted;
    }
    
    /**
     * 
     * 
     * 
     *
     * 
     * 
     * 
     * 
     */
    public void setHighlighted(boolean b){
        this.highlighted = b;
    }
}
