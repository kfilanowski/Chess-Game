package Model;

import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.PieceIF;
import Validator.PieceValidator;
import javafx.geometry.Pos;


public class Piece extends BlackAndWhite implements PieceIF {
    ChessPieceType type;

    public Piece() {
        type = null;
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

    public Position[] showMoves(Position pos) {
        return new Position[0];
    }

    public boolean validateMove(Position from, Position to){
        return this.validateMove(from,to);
    }



}
