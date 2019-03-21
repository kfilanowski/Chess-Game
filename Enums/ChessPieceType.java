package Enums;

/**
 * @author Matt Lutz 95% && Jacob Ginn 5%
 * Enum that represents a specific type of chess piece
 */
public enum ChessPieceType {

    /** Piece enum for the King */
    KING("K","King"),

    /** Piece enum for the Queen */
    QUEEN("Q", "Queen"),

    /** Piece enum for the Rook */
    ROOK("R", "Rook"),

    /** Piece enum for the Bishop */
    BISHOP("B", "Bishop"),

    /** Piece enum for the Knight */
    KNIGHT("N", "Knight"),

    /** Piece enum for the Pawn */
    PAWN("P", "Pawn");

    /** String that holds the symbol of a specific chess piece type */
    private String symbol;
    /** String that holds the name of a specific chess piece type */
    private String name;
    /** String that holds the unicode char for a specific chess piece type */
    private char uni;

    /**
     * constructor for the ChessPieceType Enum
     * @param symbol - String that holds the symbol for a chess piece type
     * @param name - String that holds the name of a chess piece type
     */
    private ChessPieceType(String symbol, String name){
        this.symbol = symbol;
        this.name = name;
    }

    /**
     * Gets the symbol for the chess piece type
     * @return - The string that holds the symbol for the chess piece type
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the name of the chess piece type
     * @return - The string that holds the name of the chess piece type
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unicode representation of the chess piece type
     * @return - The string that holds the unicode representation of the chess piece type
     */
    public char getUni(){
        return uni;
    }
}
