package Model;

import Enums.ChessPieceType;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

public class Square extends BlackAndWhite implements SquareIF {
    private ChessPieceType pieceType;

    @Override
    public void clear() {

    }

    @Override
    public PieceIF getPiece() {
        return null;
    }

    @Override
    public void setPiece(PieceIF piece) {

    }
}
