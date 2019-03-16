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
        killme.setBlackPiece();
        killme.setWhitePiece();
        killme.draw();
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
        str.append("\n  A  B  C  D  E  F  G  H");
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

    private void setBlackPiece(){
        board[0][0].setPiece(new Piece(ChessPieceType.Rook, GameColor.Black));
        board[0][1].setPiece(new Piece(ChessPieceType.Knight, GameColor.Black));
        board[0][2].setPiece(new Piece(ChessPieceType.Bishop, GameColor.Black));
        board[0][3].setPiece(new Piece(ChessPieceType.Queen, GameColor.Black));
        board[0][4].setPiece(new Piece(ChessPieceType.King, GameColor.Black));
        board[0][5].setPiece(new Piece(ChessPieceType.Bishop, GameColor.Black));
        board[0][6].setPiece(new Piece(ChessPieceType.Knight, GameColor.Black));
        board[0][7].setPiece(new Piece(ChessPieceType.Rook, GameColor.Black));
        board[1][0].setPiece(new Piece(ChessPieceType.Pawn, GameColor.Black));
        board[1][1].setPiece(new Piece(ChessPieceType.Pawn, GameColor.Black));
        board[1][2].setPiece(new Piece(ChessPieceType.Pawn, GameColor.Black));
        board[1][3].setPiece(new Piece(ChessPieceType.Pawn, GameColor.Black));
        board[1][4].setPiece(new Piece(ChessPieceType.Pawn, GameColor.Black));
        board[1][5].setPiece(new Piece(ChessPieceType.Pawn, GameColor.Black));
        board[1][6].setPiece(new Piece(ChessPieceType.Pawn, GameColor.Black));
        board[1][7].setPiece(new Piece(ChessPieceType.Pawn, GameColor.Black));
    }

    private void setWhitePiece(){
        board[7][0].setPiece(new Piece(ChessPieceType.Rook, GameColor.White));
        board[7][1].setPiece(new Piece(ChessPieceType.Knight, GameColor.White));
        board[7][2].setPiece(new Piece(ChessPieceType.Bishop, GameColor.White));
        board[7][3].setPiece(new Piece(ChessPieceType.Queen, GameColor.White));
        board[7][4].setPiece(new Piece(ChessPieceType.King, GameColor.White));
        board[7][5].setPiece(new Piece(ChessPieceType.Bishop, GameColor.White));
        board[7][6].setPiece(new Piece(ChessPieceType.Knight, GameColor.White));
        board[7][7].setPiece(new Piece(ChessPieceType.Rook, GameColor.White));
        board[6][0].setPiece(new Piece(ChessPieceType.Pawn, GameColor.White));
        board[6][1].setPiece(new Piece(ChessPieceType.Pawn, GameColor.White));
        board[6][2].setPiece(new Piece(ChessPieceType.Pawn, GameColor.White));
        board[6][3].setPiece(new Piece(ChessPieceType.Pawn, GameColor.White));
        board[6][4].setPiece(new Piece(ChessPieceType.Pawn, GameColor.White));
        board[6][5].setPiece(new Piece(ChessPieceType.Pawn, GameColor.White));
        board[6][6].setPiece(new Piece(ChessPieceType.Pawn, GameColor.White));
        board[6][7].setPiece(new Piece(ChessPieceType.Pawn, GameColor.White));

    }


}
