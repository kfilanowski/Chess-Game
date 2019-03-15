package Model;

import Enums.ChessPieceType;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
public class Square extends BlackAndWhite implements SquareIF {
    private ChessPieceType pieceType;
    private PieceIF piece;

    @Override
    public void clear() {
        this.piece = new Piece();
        this.pieceType = null;

    }

    @Override
    public PieceIF getPiece() {
        return this.piece;
    }

    @Override
    public void setPiece(PieceIF piece) {
        this.piece = piece;
    }

}
