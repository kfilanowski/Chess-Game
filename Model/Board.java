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
import Validator.HorizVertValidator;
import Validator.*;

/**
 * @author - Jacob Ginn
 * @version - 3/19/2019
 * This is the board class that holds the methods to initialize , setup, and which draw method that will be used
 * when the board is printed in the command line interface.
 */
public class Board implements BoardIF{
    /** The board that holds the squares that the pieces will be placed */
    public SquareIF[][] board = new SquareIF[8][8];

    /** The strategy that the board will follow when it is drawn */
    BoardStrategy bs;

    /**
     * This class is used by the driver to initialize a board, setup the board, and to draw the board.
     */
    public void go(){
        init_board();
        setup();
        draw();
    }

    /**
     * This sets up the the board with squares that will hold pieces and the piece validators.
     */
    @Override
    public void init_board() {
        int count = 0; //helps alternate the chess board

        for(int i = 0; i < getHeight(); i++){//rows of the board
            if (i % 2 == 1) {//achieves  the checker pattern
                count = 1;
            }
            for(int j = 0; j < getWidth(); j++){//columns of the board
                if(count % 2 == 0){
                    board[i][j] = new Square(GameColor.WHITE);//makes a square of color WHITE
                    count++;

                }else{
                    board[i][j] = new Square(GameColor.BLACK);//makes a square of color BLACK
                    count++;
                }
            }
            count = 0;

        }

    }

    /**
     * This is the default setup of the board that sets black and white pieces on the board.
     */
    @Override
    public void setup() {
        setWhitePiece();
        setBlackPiece();
    }

    /**
     * This method calls the draw method in either color or mono colored.
     */
    @Override
    public void draw() {
        bs.draw(this);
    }

    /**
     * This returns the complete board that the game is being played on.
     * @return - the board that chess is being played on
     */
    @Override
    public SquareIF[][] getSquares() {
        return board;
    }

    /**
     * Sets whether the users want to play in colored ot mono-colored mode.
     * @param d - the draw strategy that is being used.
     */
    @Override
    public void setDrawStrategy(BoardStrategy d){
        this.bs = d;

    }

    /**
     * gets the width of the board
     * @return - the width of the board
     */
    @Override
    public int getWidth() {
        return board.length;
    }

    /**
     * gets the height of the board
     * @return - the height of the board
     */
    @Override
    public int getHeight() {
        return board.length;
    }

    /**
     * Gets the piece on the Square that is inputted
     * @param r - The rank of input position
     * @param f - The file of input position
     * @return - the piece in that position
     */
    @Override
    public PieceIF getPiece(Rank r, File f) {
        return board[r.getIndex()][f.getIndex()].getPiece();
    }

    /**
     * gets the piece on the Sqare that is inputted
     * @param col - The column of the input position
     * @param row - The row of the input position
     * @return - the piece in that position
     */
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

    /**
     * Sets the Black pieces on the board
     */
    private void setBlackPiece(){

        PieceIF queen = new Piece(ChessPieceType.QUEEN, GameColor.BLACK);
        queen = new DiagonalValidator(this, queen);
        queen = new HorizVertValidator(this, queen);

        board[0][3].setPiece(queen);

        PieceIF rook = new Piece(ChessPieceType.ROOK, GameColor.BLACK);
        rook = new HorizVertValidator(this, rook);
        board[0][0].setPiece(rook);

        PieceIF bishop = new Piece(ChessPieceType.BISHOP, GameColor.BLACK);
        bishop = new DiagonalValidator(this, bishop);
        board[0][2].setPiece(bishop);

        PieceIF knight = new Piece(ChessPieceType.KNIGHT, GameColor.BLACK);
        knight = new KnightValidator(this, knight);
        board[0][1].setPiece(knight);

        PieceIF bKing = new Piece(ChessPieceType.KING, GameColor.BLACK);
        bKing = new KingValidator(this, bKing);
        board[0][4].setPiece(bKing);

        PieceIF bishop2 = new Piece(ChessPieceType.BISHOP, GameColor.BLACK);
        bishop2 = new DiagonalValidator(this, bishop2);
        board[0][5].setPiece(bishop2);

        PieceIF knight2 = new Piece(ChessPieceType.KNIGHT, GameColor.BLACK);
        knight2 = new KnightValidator(this, knight2);
        board[0][6].setPiece(knight2);

        PieceIF rook2 = new Piece(ChessPieceType.ROOK, GameColor.BLACK);
        rook2 = new HorizVertValidator(this, rook2);
        board[0][7].setPiece(rook2);

        for(int i = 0; i < getWidth(); i++){
            PieceIF pawn = new Piece(ChessPieceType.PAWN, GameColor.BLACK);
            pawn = new PawnValidator(this, pawn);
            board[1][i].setPiece(pawn);
        }
    }

    /**
     * sets the White pieces on the board
     */
    private void setWhitePiece(){

        PieceIF queen = new Piece(ChessPieceType.QUEEN, GameColor.WHITE);
        queen = new HorizVertValidator(this, queen);
        queen = new DiagonalValidator(this, queen);
        board[7][3].setPiece(queen);

        PieceIF rook = new Piece(ChessPieceType.ROOK, GameColor.WHITE);
        rook = new HorizVertValidator(this, rook);
        board[7][0].setPiece(rook);

        PieceIF bishop = new Piece(ChessPieceType.BISHOP, GameColor.WHITE);
        bishop = new DiagonalValidator(this, bishop);
        board[7][2].setPiece(bishop);

        PieceIF knight = new Piece(ChessPieceType.KNIGHT, GameColor.WHITE);
        knight = new KnightValidator(this, knight);
        board[7][1].setPiece(knight);

        PieceIF bKing = new Piece(ChessPieceType.KING, GameColor.WHITE);
        bKing = new KingValidator(this, bKing);
        board[7][4].setPiece(bKing);

        PieceIF bishop2 = new Piece(ChessPieceType.BISHOP, GameColor.WHITE);
        bishop2 = new DiagonalValidator(this, bishop2);
        board[7][5].setPiece(bishop2);

        PieceIF knight2 = new Piece(ChessPieceType.KNIGHT, GameColor.WHITE);
        knight2 = new KnightValidator(this, knight2);
        board[7][6].setPiece(knight2);

        PieceIF rook2 = new Piece(ChessPieceType.ROOK, GameColor.WHITE);
        rook2 = new HorizVertValidator(this, rook2);
        board[7][7].setPiece(rook2);

        for(int i = 0; i < getWidth(); i++){
            PieceIF pawn = new Piece(ChessPieceType.PAWN, GameColor.WHITE);
            pawn = new PawnValidator(this, pawn);
            board[6][i].setPiece(pawn);
        }
    }
//    public boolean checkForCheck(GameColor color){
//        boolean finalResult = false;
//        boolean go = true;
//        int kingRank = -1;
//        int kingFile = -1;
//
//        for(int i = 0; i < Rank.getMaxIndex() && go; i++){
//            for(int j = 0; j < File.getMaxIndex() && go; j++){
//                PieceIF maybeKing = board[i][j].getPiece();
//                if(maybeKing.getChessPieceType() == ChessPieceType.KING && maybeKing.getColor() == color){
//                    go = false;
//                    kingRank = i;
//                    kingFile = j;
//                }
//            }
//        }
//
//
//
//        final int MOVE_DOWN_LEFT = -1;
//        final int MOVE_UP_RIGHT = 1;
//
//
//        // Check left, right
//        boolean cl = checkLefRight(MOVE_DOWN_LEFT, kingRank, kingFile, color);
//        boolean cr = checkLefRight(MOVE_UP_RIGHT, kingRank, kingFile, color);
//
//        // Check down, up
//        boolean cu = checkUpDown(MOVE_DOWN_LEFT, kingRank, kingFile, color);
//        boolean cd = checkUpDown(MOVE_UP_RIGHT, kingRank, kingFile, color);
//
//
//        // Check bottom-right diag
//
//        // Check Up-left diag
//
//        // Check bottom left diag
//
//        // Check Knight
//
//        finalResult = cl || cr || cu || cd;
//        return finalResult;
//    }
//
//
//    private boolean checkLefRight(int direction, int rank, int file, GameColor color){
//        boolean result = false;
//
//        boolean go = true;
//        if (direction == -1) {
//            int j = file;
//            while (j >= 0 && go) {
//                j += direction;
//                PieceIF gottenPiece = board[rank][j].getPiece();
//                if (gottenPiece != null) {
//                    if (gottenPiece.getColor() != color && (gottenPiece.getChessPieceType() == ChessPieceType.QUEEN ||
//                            gottenPiece.getChessPieceType() == ChessPieceType.ROOK)) {
//                        result = true;
//                    }
//                    go = false;
//                }
//            }
//        }else{
//            int j = file;
//            while (j < board.length && go) {
//                j += direction;
//                PieceIF gottenPiece = board[rank][j].getPiece();
//                if(gottenPiece != null){
//                    if(gottenPiece.getColor() != color && (gottenPiece.getChessPieceType() == ChessPieceType.QUEEN ||
//                            gottenPiece.getChessPieceType() == ChessPieceType.ROOK)){
//                        result = true;
//                    }
//                    go = false;
//                }
//
//            }
//        }
//        return result;
//    }
//
//    private boolean checkUpDown(int direction, int rank, int file, GameColor color){
//        boolean result = false;
//
//        boolean go = true;
//        if (direction == -1) {
//            int i = rank;
//            while (i >= 0 && go) {
//                i += direction;
//                PieceIF gottenPiece = board[i][file].getPiece();
//                if (gottenPiece != null) {
//                    if (gottenPiece.getColor() != color && (gottenPiece.getChessPieceType() == ChessPieceType.QUEEN ||
//                            gottenPiece.getChessPieceType() == ChessPieceType.ROOK)) {
//                            result = true;
//                    }
//                    go = false;
//                }
//            }
//        }else{
//            int i = rank;
//            while (i < board.length && go) {
//                i += direction;
//                PieceIF gottenPiece = board[i][file].getPiece();
//                if(gottenPiece != null){
//                    if(gottenPiece.getColor() != color && (gottenPiece.getChessPieceType() == ChessPieceType.QUEEN ||
//                            gottenPiece.getChessPieceType() == ChessPieceType.ROOK)){
//                            result = true;
//                    }
//                    go = false;
//                }
//
//
//            }
//        }
//       return result;
//    }
//
//    private boolean checkUpRightDiag(int rank, int file, GameColor color){
//        // Check squares diagonally - positive slope up - from this position.
//        boolean result = false;
//        int i = rank;
//        int j = file;
//        boolean go = true;
//        while (i > 0 &&  j < board.length && go) {
//            i--;
//            j++;
//            PieceIF gottenPiece = board[--i][++j].getPiece();
//            if(gottenPiece != null){
//                if(gottenPiece.getColor() != color && (gottenPiece.getChessPieceType() == ChessPieceType.BISHOP||
//                        gottenPiece.getChessPieceType() == ChessPieceType.QUEEN)){
//                    result = true;
//                }else if(gottenPiece.getColor() != color && gottenPiece.getChessPieceType() == ChessPieceType.PAWN){
//                    //TODO: HANDLE PAWN SOMEHOW
//                }
//                go = false;
//            }
//        }
//
//
//
//        return result;
//    }

}