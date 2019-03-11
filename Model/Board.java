package Model;

import Enums.ChessPieceType;
import Enums.File;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

public class Board implements BoardIF{
    public SquareIF [][] board = new SquareIF[8][8];;

    public static void main(String [] args){
        Board killme = new Board();
        killme.init_board();
    }


    @Override
    public void init_board() {
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = new Square();
            }
        }

    }

    @Override
    public void setup() {

    }


    @Override
    public void draw() {
        StringBuilder str = new StringBuilder();
        int count = 8;

        for(int i = 0; i < board.length ; i++) {
            str.append("\n" + count);
            count--;
            for (int j = 0; j < board.length; j++) {
                str.append(board[i][j].getPiece());
            }
        }
        System.out.println(str);
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
        return board.length;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public PieceIF getPiece(Rank r, File f) {
        return board[r.getIndex()][f.getIndex()].getPiece();
    }

    @Override
    public PieceIF getPiece(int col, int row) {
        return board[col][row].getPiece();
    }


}
