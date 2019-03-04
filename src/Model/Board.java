package Model;

import Enums.File;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

public class Board implements BoardIF{
    public char [][] board = new char [8][8];

    public static void main(String [] args){
        Board killme = new Board();
        killme.init_board();
    }


    @Override
    public void init_board() {
        setup();
        draw();
    }

    @Override
    public void setup() {
        board [0][0] = 'R';
        board [0][7] = 'R';
        board [7][0] = 'R';
        board [7][7] = 'R';

        board [0][1] = 'N';
        board [7][1] = 'N';
        board [0][6] = 'N';
        board [7][6] = 'N';

        board [0][2] = 'B';
        board [0][5] = 'B';
        board [7][2] = 'B';
        board [7][5] = 'B';

        board [0][4] = 'K';
        board [7][4] = 'K';

        board [0][3] = 'Q';
        board [7][3] = 'Q';


    }

    @Override
    public void draw() {
        for(int i = 0; i < board.length ; i++) {
            System.out.println();
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
        }
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
