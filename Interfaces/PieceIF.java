package Interfaces;
import Model.Position;

import Enums.ChessPieceType;

public interface PieceIF extends BlackAndWhiteIF {
    public ChessPieceType getChessPieceType();
    public void setChessPieceType(ChessPieceType type);

    default Position[] showMoves(Position pos){
        return new Position[0];
    }

    default boolean validateMove(Position from, Position to){
        return true;
    }
}
