package Enums;

/**
 * Enum that represents a specific type of chess piece
 * 
 * @author Matt Lutz 95%
 * @author Jacob Ginn 5% 
 */
public enum ChessPieceType {
    /** Piece enum for the King */
    KING("K", "King"),

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
     *
     * @param symbol - String that holds the symbol for a chess piece type
     * @param name   - String that holds the name of a chess piece type
     */
    private ChessPieceType(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    /**
     * Gets the symbol for the chess piece type
     *
     * @return - The string that holds the symbol for the chess piece type
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the name of the chess piece type
     *
     * @return - The string that holds the name of the chess piece type
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unicode representation of the chess piece type
     *
     * @return - The string that holds the unicode representation of the chess
     *           piece type
     */
    public char getUni() {
        return uni;
    }


    /**
     * Gets a specific chess piece type based on the given string
     * @param str the given string that should match to a chesss piece type
     * @return the chess piece type that matches the string or null if none were matched
     */
    public static ChessPieceType getChessPieceTypeFromString(String str){
        ChessPieceType type = null;
        str = str.trim();
        if(str.equalsIgnoreCase("PAWN")){
            type = PAWN;
        }else if(str.equalsIgnoreCase("ROOK")){
            type = ROOK;
        }else if(str.equalsIgnoreCase("KNIGHT")){
            type = KNIGHT;
        }else if(str.equalsIgnoreCase("BISHOP")){
            type = BISHOP;
        }else if(str.equalsIgnoreCase("QUEEN")){
            type = QUEEN;
        }else if(str.equalsIgnoreCase("KING")){
            type = KING;
        }
        return  type;
    }
}
