package Enums;

public enum ChessPieceType {
    WHITE_KING("K","King", '\u2654'),
    WHITE_QUEEN("Q", "Queen",'\u2655' ),
    WHITE_ROOK("R", "Rook", '\u2656'),
    WHITE_BISHOP("B", "Bishop", '\u2657'),
    WHITE_KNIGHT("N", "Knight", '\u2658'),
    WHITE_PAWN("P", "Pawn", '\u2659'),
    BLACK_KING("K","King", '\u265A'),
    BLACK_QUEEN("Q", "Queen",'\u265B' ),
    BLACK_ROOK("R", "Rook", '\u265C'),
    BLACK_BISHOP("B", "Bishop", '\u265D'),
    BLACK_KNIGHT("N", "Knight", '\u265E'),
    BLACK_PAWN("P", "Pawn", '\u265F');

    private String symbol;
    private String name;
    private char uni;

    private ChessPieceType(String symbol, String name, char uni){
        this.symbol = symbol;
        this.name = name;
        this.uni = uni;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public char getUni(){
        return uni;
    }
}
