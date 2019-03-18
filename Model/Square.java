package Model;

import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
public class Square extends BlackAndWhite implements SquareIF {
    private ChessPieceType pieceType;
    private PieceIF piece;

    public Square(GameColor color){
        super.setColor(color);
    }

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
    public void setPiece(PieceIF piece){
        this.piece = piece;
    }

    public String toString(){
        return " ";
    }

}
