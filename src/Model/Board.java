package Model;

import Enums.File;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

public class Board implements BoardIF{
    @Override
    public void init_board() {

    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {

    }

    @Override
    public SquareIF[][] getSquares() {
        return new SquareIF[0][];
    }

    @Override
    public void setDrawStrategy(BoardStrategy d) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public PieceIF getPiece(Rank r, File f) {
        return null;
    }

    @Override
    public PieceIF getPiece(int col, int row) {
        return null;
    }


}
