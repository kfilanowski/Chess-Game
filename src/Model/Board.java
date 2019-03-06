package Model;

import Enums.ChessPieceType;
import Enums.File;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

public class Board implements BoardIF{
    public SquareIF [][] board;

    public static void main(String [] args){
        Board killme = new Board();
        killme.init_board();
    }


    @Override
    public void init_board() {
        board = new SquareIF[8][8];
        setup();
        draw();
    }

    @Override
    public void setup() {
        PieceIF piece = new Piece();
        piece.setChessPieceType(ChessPieceType.King);
        board[7][7].setPiece(piece);
    }

    @Override
    public void draw() {
        StringBuilder str = new StringBuilder();
        int count = 8;

        for(int i = 0; i < board.length ; i++) {
            str.append(count);
            count--;
            for (int j = 0; j < board.length; j++) {
                str.append(board[i][j]);
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
        return board.length;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public PieceIF getPiece(Rank r, File f) {
        return board[r.getNum2()][f.getNum()].getPiece();
    }

    @Override
    public PieceIF getPiece(int col, int row) {
        return board[col][row].getPiece();
    }


}
