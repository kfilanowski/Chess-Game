package Enums;

public enum ChessPieceType {
    King("K","King"),
    Queen("Q", "Queen"),
    Rook("R", "Rook"),
    Bishop("B", "Bishop"),
    Knight("N", "Knight"),
    Pawn("P", "Pawn");

    private String symbol;
    private String name;

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
}
