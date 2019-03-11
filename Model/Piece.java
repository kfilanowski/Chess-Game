package Model;

import Enums.ChessPieceType;
import Interfaces.PieceIF;


public class Piece extends BlackAndWhite implements PieceIF {
    ChessPieceType type;

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
