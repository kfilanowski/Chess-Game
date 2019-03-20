package Enums;

public enum ChessPieceType {
    KING("K","King"),
    QUEEN("Q", "Queen"),
    ROOK("R", "Rook"),
    BISHOP("B", "Bishop"),
    KNIGHT("N", "Knight"),
    PAWN("P", "Pawn");

    private String symbol;
    private String name;
    private char uni;

    private ChessPieceType(String symbol, String name){
        this.symbol = symbol;
        this.name = name;
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
