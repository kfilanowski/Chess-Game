package Model;

import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.PieceIF;


public class Piece extends BlackAndWhite implements PieceIF {
    ChessPieceType type;

    public Piece() {
        
    }

    public Piece(ChessPieceType type) {
        this.type = type;
    }

    public Piece(ChessPieceType type, GameColor color) {
        this.type = type;
        super.setColor(color);
    }

    @Override
    public void setChessPieceType(ChessPieceType type) {
        this.type = type;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return this.type;
    }

    @Override
    public String toString(){
        String pieceType = getChessPieceType().getSymbol();
        return pieceType;
    }

}