package Interfaces;

import Enums.ChessPieceType;

public interface PieceIF extends BlackAndWhiteIF {
    public ChessPieceType getChessPieceType();
    public void setChessPieceType(ChessPieceType type);
}
