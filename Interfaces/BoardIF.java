package Interfaces;

import Enums.File;
import Enums.Rank;

public interface BoardIF {

    /**
     *
     */

    public void init_board();

    public void setup();

    public void draw();

    public SquareIF[][] getSquares();

    public void setDrawStrategy(BoardStrategy d);

    public int getWidth();

    public int getHeight();

    public PieceIF getPiece(Rank r, File f);

    public PieceIF getPiece(int col, int row);

}
