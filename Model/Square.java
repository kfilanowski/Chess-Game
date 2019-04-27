package Model;

import Enums.GameColor;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

import java.sql.SQLOutput;

/**
 * Represents a square on a Chess board.
 * 
 * @author Kevin Filanowski 65%
 * @author Jeriah Caplinger 15%
 * @author Jacob Ginn 20%
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
     * @param pos - The position array that holds the positions a piece can move
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
     * toString for square that is used for the color board when showing the possible moves
     * @param backColor - The background color of the square
     * @param foreColor - The Color of the piece that is on a square
     * @return the string containing the board.
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
     * toString for square that is used for the mono board when showing the possible moves
     * @param validMove - the character for if it is a valid move for the piece
     * @param spaceChar - the Character that is used for a white or black space
     * @param spaceChar2 - the Ending character of the space
     * @param p - whether the piece is white or black
     * @return - the string containing the board.
     */
    public String toString(char validMove, char spaceChar, char spaceChar2, char p){
        String str;
        if(this.getPiece() != null){
            if(p == 'u')
                str = (spaceChar + "" + validMove + this.getPiece().toString().toUpperCase() + validMove + spaceChar2);
            else
                str = (spaceChar + "" + validMove + this.getPiece().toString().toLowerCase() + validMove + spaceChar2);
        }else{
            str = (spaceChar + "" + validMove + " " + validMove + spaceChar2);
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
     * returns true if the square is a valid move for the piece and
     * returns false if the square is not a valid move.
     * @return -true if the move is valid, false otherwise
     */
    public boolean getHighlighted(){
        return highlighted;
    }
    
    /**
     * Sets the highlighted field to true if the square is a valid move and flase if the square is not a
     * valid move.
     * @param b - True or false depending on the chess piece selected.
     */
    public void setHighlighted(boolean b){
        this.highlighted = b;
    }
}
