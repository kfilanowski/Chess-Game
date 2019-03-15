package Model;

import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

import java.util.ArrayList;

public class Board implements BoardIF{
    public SquareIF[][] board = new SquareIF[8][8];;

    public static void main(String [] args){
        Board killme = new Board();
        killme.init_board();
    }


    @Override
    public void init_board() {
        int count = 0;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(count % 2 == 0){
                    Square temp = new Square();
                    temp.setColor(GameColor.White);
                    board[i][j] = temp;
                    count++;
                }else{
                    Square temp = new Square();
                    temp.setColor(GameColor.Black);
                    board[i][j] = temp;
                    count++;
                }
            }
        }

    }

    @Override
    public void setup() {
        PieceIF wrook1 = new Piece(ChessPieceType.Rook);
        ((Piece) wrook1).setColor(GameColor.White);
        board[0][0].setPiece(wrook1);
    }


    @Override
    public void draw() {
        StringBuilder str = new StringBuilder();
        int count = 8;

        for(int i = 0; i < board.length ; i++) {
            str.append("\n" + count);
            count--;
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getPiece() != null)
                    str.append(" " + board[i][j].getPiece() + " ");
                else
                    str.append(" . ");
            }
        }
        System.out.println(str);
    }

    @Override
    public SquareIF[][] getSquares() {
        return board;
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
