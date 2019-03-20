package Model;

import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import UI_CLI.Board_Color_CLI;
import UI_CLI.Board_Mono_CLI;
import Validator.DiagonalValidator;
import Validator.HorizVertValidator;
import Validator.KnightValidator;
import Validator.PieceValidator;
import java.util.Arrays;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Board implements BoardIF{
    public SquareIF[][] board = new SquareIF[8][8];
    BoardStrategy bs;

    public void go(){
        init_board();
        setup();
        draw();
    }
    @Override
    public void init_board() {
        int count = 0; //helps alternate the chess board

        for(int i = 0; i < board.length; i++){
            if (i % 2 == 1) {
                count = 1;
            }
            for(int j = 0; j < board.length; j++){
                if(count % 2 == 0){
                    board[i][j] = new Square(GameColor.WHITE);
                    count++;

                }else{
                    board[i][j] = new Square(GameColor.BLACK);
                    count++;
                }
            }
            count = 0;

        }

    }

    @Override
    public void setup() {
        setWhitePiece();
        setBlackPiece();
    }


    @Override
    public void draw() {
    bs.draw(this);
    }

    @Override
    public SquareIF[][] getSquares() {
        return board;
    }

    @Override
    public void setDrawStrategy(BoardStrategy d){
        this.bs = d;

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

    /**
     * Retrieve a square at a specified rank and file.
     * @param rank - The rank the square falls on.
     * @param file - The file the square falls on.
     * @return - A SquareIF from the board that falls on the specified
     *           rank and file.
     */
    public SquareIF getSquare(Rank rank, File file) {
        return board[rank.getIndex()][file.getIndex()];
    }
    private void setBlackPiece(){
//        //board[0][0].setPiece(new Piece(ChessPieceType.ROOK, GameColor.Black));
//        board[0][0].setPiece(new HorizVertValidator(this, new Piece(ChessPieceType.ROOK, GameColor.BLACK)));
//        board[0][1].setPiece(new KnightValidator(this, new Piece(ChessPieceType.KNIGHT, GameColor.BLACK)));
//        board[0][2].setPiece(new DiagonalValidator(this, new Piece(ChessPieceType.BISHOP, GameColor.BLACK)));
//        board[0][3].setPiece(new HorizVertValidator(new DiagonalValidator(this, new Piece(ChessPieceType.QUEEN, GameColor.BLACK))));
//
//
//        board[0][0].setPiece(new HorizVertValidator(this, new Piece(ChessPieceType.ROOK, GameColor.BLACK)));
//        board[0][0].setPiece(new HorizVertValidator(this, new Piece(ChessPieceType.ROOK, GameColor.BLACK)));
//        board[0][0].setPiece(new HorizVertValidator(this, new Piece(ChessPieceType.ROOK, GameColor.BLACK)));
//        board[0][0].setPiece(new HorizVertValidator(this, new Piece(ChessPieceType.ROOK, GameColor.BLACK)));
//        board[0][0].setPiece(new HorizVertValidator(this, new Piece(ChessPieceType.ROOK, GameColor.BLACK)));
//
//        System.out.println(Arrays.toString(((PieceValidator) board[0][2].getPiece()).showMoves(new Position(Rank.R1, File.C, board[0][2]))));
//
//        //for(int i = 0; i < board.length; i++)
//        //    board[1][i].setPiece(new Piece(ChessPieceType.PAWN, GameColor.BLACK));
//        System.out.println(Arrays.toString(((PieceValidator) board[0][0].getPiece()).showMoves(new Position(Rank.R1, File.A, board[0][0]))));
        PieceIF piece = new Piece(ChessPieceType.QUEEN, GameColor.BLACK);
        piece = new HorizVertValidator(this, piece);
        piece = new DiagonalValidator(this, piece);
        System.out.println(Arrays.toString(((PieceValidator) piece).showMoves(new Position(Rank.R1, File.D, board[0][3]))));
    }

    private void setWhitePiece(){
        board[7][0].setPiece(new Piece(ChessPieceType.ROOK, GameColor.WHITE));
        board[7][1].setPiece(new Piece(ChessPieceType.KNIGHT, GameColor.WHITE));
        board[7][2].setPiece(new Piece(ChessPieceType.BISHOP, GameColor.WHITE));
        board[7][3].setPiece(new Piece(ChessPieceType.QUEEN, GameColor.WHITE));
        board[7][4].setPiece(new Piece(ChessPieceType.KING, GameColor.WHITE));
        board[7][5].setPiece(new Piece(ChessPieceType.BISHOP, GameColor.WHITE));
        board[7][6].setPiece(new Piece(ChessPieceType.KNIGHT, GameColor.WHITE));
        board[7][7].setPiece(new Piece(ChessPieceType.ROOK, GameColor.WHITE));
        for(int i = 0; i < board.length; i++)
            board[6][i].setPiece(new Piece(ChessPieceType.PAWN, GameColor.WHITE));
    }


}
