package Enums;

public enum ChessPieceType {
    King('K',"King"),
    Queen('Q', "Queen"),
    Rook('R', "Rook"),
    Bishop('B', "Bishop"),
    Knight('N', "Knight"),
    Pawn('P', "Pawn");

    private char symbol;
    private String name;

    private ChessPieceType(char symbol, String name){
        this.symbol = symbol;
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }
}
