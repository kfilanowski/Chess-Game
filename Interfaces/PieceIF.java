package Interfaces;
import Model.Position;

import Enums.ChessPieceType;

public interface PieceIF extends BlackAndWhiteIF {
    public ChessPieceType getChessPieceType();
    public void setChessPieceType(ChessPieceType type);
    public Position[] showMoves(Position pos);
    public boolean validateMove(Position from, Position to);
}
