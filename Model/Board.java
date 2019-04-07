package Model;

import java.util.Scanner;

import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import History.State;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import UI_CLI.Board_Color_CLI;
import UI_CLI.Board_Mono_CLI;
import Validator.HorizVertValidator;
import Validator.*;

/**
 * * This is the board class that holds the methods to initialize , setup, and
 * which draw method that will be used when the board is printed in the command
 * line interface.
 * 
 * @author - Jacob Ginn
 * @author - Kevin Filanowski
 * @version - April 7, 2019
 * 
 */
public class Board implements BoardIF{
    /** The board that holds the squares that the pieces will be placed */
    private SquareIF[][] board;
    /** The strategy that the board will follow when it is drawn */
    private BoardStrategy bs;

    /**
     * Constructs a board object and populates the squares.
     */
    public Board() {
        board = new SquareIF[8][8];
    }

    /**
     * This class is used by the driver to initialize a board, setup the board,
     * and to draw the board.
     * 
     * @throws NumberFormatException - Thrown when the input is invalid.
     */
    public void go() throws NumberFormatException {
        setupDrawing();
        init_board();
        setup();
        draw();
        bs.go(this);
    }

    /**
     * Prompts the user for the type of board they would like to be drawn.
     * 
     * @throws NumberFormatException - Thrown when the input is invalid.
     */
    private void setupDrawing() throws NumberFormatException {
        // Scanner to read the input into the file.
        Scanner reader = new Scanner(System.in);
        // Boolean flag to ensure a viable option is chosen.
        Boolean chosen = false;

        while (!chosen) {
            System.out.println("Menu Options: \n" 
                             + "1) Colored Command Line Interface.\n"
                             + "2) Monochrome Command Line Interface.\n" 
                             + "3) Graphical User Interface.\n" 
                             + "4) Exit.\n");
            System.out.print("Please enter an integer: ");

            // Reads in the input.
            int input = Integer.parseInt(reader.next());

            switch (input) {
            case 1: { // If the user wants the colored board.
                setDrawStrategy(new Board_Color_CLI());
                chosen = true;
            } break;
            case 2: { // If the user wants the monochrome board.
                setDrawStrategy(new Board_Mono_CLI());
                chosen = true;
            } break;
            case 3: { // If the user wants the graphical user interface.
                setDrawStrategy(new Board_Color_CLI());
                chosen = true;
            } break;
            case 4: { // If the user wants to exit the program.
                reader.close();
                System.exit(0);
            } break;
            default:
            }
        }
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
                    board[i][j] = new Square(GameColor.WHITE, new Position(
                        Rank.getRankFromIndex(i), File.getFileFromIndex(j)));//makes a square of color WHITE
                    count++;

                }else{
                    board[i][j] = new Square(GameColor.BLACK, new Position(
                        Rank.getRankFromIndex(i), File.getFileFromIndex(j)));//makes a square of color BLACK
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
     * 
     * 
     * 
     * 
     * 
     */
    public void draw(BoardIF board, Position[] pos){
        bs.draw(board, pos);
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
    public void setDrawStrategy(BoardStrategy d) {
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
     *
     * @param rank - The rank the square falls on.
     * @param file - The file the square falls on.
     * @return     - A SquareIF from the board that falls on the specified
     *               rank and file.
     */
    public SquareIF getSquare(Rank rank, File file) {
        return board[rank.getIndex()][file.getIndex()];
    }

    /**
     * Retrieve a square at a specified position.
     *
     * @param pos - The position of the square.
     * @return    - A SquareIF from the board that falls on the specified
     *              position.
     */
    public SquareIF getSquare(Position pos) {
        return board[pos.getRank().getIndex()][pos.getFile().getIndex()];
    }

    /**
     * Return a state object ensapsulating a clone of this board object 
     * in its current state.
     * 
     * @return - A state encapsulating the current state of 
     *          this board object as a clone.
     */
    public State<Board> saveState() {
        return new State<Board>(this.clone());
    }

    /**
     * Restores the state of this object from a state object.
     * 
     * @param state - The state from which to get the state of the board.
     */
    public void restoreState(State<Board> state) {
        Board newState = (Board) state.getState();
        bs = newState.bs;
        board = newState.board;
    }

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public Board clone() {
        Board newBoard = new Board();
        newBoard.bs = bs.clone();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                newBoard.board[i][j] = board[i][j].clone();
            }
        }
        return newBoard;
    }

    /**
     * Compares an object with this board object.
     * 
     * @param obj - An object to compare with this board object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Board) {
            Board b = (Board) obj;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j].equals(b.board[i][j]) == false) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets the Black pieces on the board
     */
    private void setBlackPiece(){
        PieceIF queen = new Piece(ChessPieceType.QUEEN, GameColor.BLACK);
        queen = new HorizVertValidator(this, queen);
        queen = new DiagonalValidator(this, queen);
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
    
    /**
     * 
     * 
     * 
     * 
     * 
     */
    public boolean checkForCheck(GameColor color){
        boolean finalResult = false;
        boolean go = true;
        int kingRank = -1;
        int kingFile = -1;

        // loops through and finds the king for which we are checking check on
        for(int i = 0; i <= Rank.getMaxIndex() && go; i++){
            for(int j = 0; j <= File.getMaxIndex() && go; j++){
                PieceValidator maybeKing = (PieceValidator) board[i][j].getPiece();
                if(maybeKing != null && maybeKing.getPiece().getChessPieceType() == ChessPieceType.KING
                        && maybeKing.getColor() == color){
                    go = false;
                    kingRank = i;
                    kingFile = j;
                }
            }
        }

        if(kingRank != -1 && kingFile != -1){
            finalResult = this.checkHelp(kingRank, kingFile, color);
        }
        

        return finalResult;
    }


    /**
     * Helper method that manages checking in all different directions for check
     * @param kingRank the rank the king is on
     * @param kingFile the file the king is on
     * @param color the color of the king
     * @return true if the king is in check, false otherwise
     */
    private boolean checkHelp(int kingRank, int kingFile, GameColor color){
        // following short circuits checking if the king is in check
        boolean result = checkLeftHoriz(kingRank, kingFile, color);
        if(!result){
            result = checkRightHoriz(kingRank, kingFile, color);
            if(!result){
                result = checkDownVert(kingRank, kingFile, color);
                if(!result){
                    result = checkUpVert(kingRank, kingFile, color);
                    if(!result){
                        result = checkUpRightDiag(kingRank, kingFile, color);
                        if(!result){
                            result = checkDownRightDiag(kingRank, kingFile, color);
                            if(!result){
                                result = checkDownLeftDiag(kingRank, kingFile, color);
                                if(!result){
                                    result = checkUpLeftDiag(kingRank, kingFile, color);
                                    if(!result){
                                        result = checkKnight(kingRank, kingFile, color);
                                        if(!result){
                                            result = checkPawn(kingRank, kingFile, color);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }


    /**
     * Helper method that determines if an opposite colored pawn is putting the king
     * into check
     * @param rank the rank of the king
     * @param file the file of the king
     * @param color the color of the king
     * @return true if an opposite color pawn is putting the king into check
     */
    private boolean checkPawn(int rank, int file, GameColor color){
        boolean result;
        if(color == GameColor.WHITE){
            result = this.checkBlackPawn(rank, file, color);
        }else{
            result = this.checkWhitePawn(rank, file, color);
        }
        return result;
    }


    /**
     * Helper method that determines if the black pawn is putting the white
     * king into check
     * @param rank the rank of the king
     * @param file the file of the king
     * @param color the color of the king
     * @return true if the pawn is putting the king into check
     */
    private boolean checkBlackPawn(int rank, int file, GameColor color){
        boolean result = false;
        int leftRank = rank - 1;
        int leftFile = file - 1;
        int rightRank = rank - 1;
        int rightFile = file + 1;

        // checks left diagnol square for pawn
        if(checkHelpBounds(leftRank) && checkHelpBounds(leftFile)){
            PieceValidator gottenPiece = (PieceValidator) board[leftRank][leftFile].getPiece();
            if(gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN
                    && gottenPiece.getPiece().getColor() != color){
                result = true;
            }
        }

        // checks right diagnol square for pawn
        if(!result && checkHelpBounds(rightRank) && checkHelpBounds(rightFile)){
            PieceValidator gottenPiece = (PieceValidator) board[rightRank][rightFile].getPiece();
            if(gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN
                    && gottenPiece.getPiece().getColor() != color){
                result = true;
            }
        }
        return result;
    }

    /**
     * Helper method that determines if the white pawn is putting the black
     * king into check
     * @param rank the rank of the king
     * @param file the file of the king
     * @param color the color of the king
     * @return true if the pawn is putting the king into check
     */
    private boolean checkWhitePawn(int rank, int file, GameColor color){
        boolean result = false;
        int leftRank = rank + 1;
        int leftFile = file - 1;
        int rightRank = rank + 1;
        int rightFile = file + 1;

        // checks left diagnol for pawn
        if(checkHelpBounds(leftRank) && checkHelpBounds(leftFile)){
            PieceValidator gottenPiece = (PieceValidator) board[leftRank][leftFile].getPiece();
            if(gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN
                    && gottenPiece.getPiece().getColor() != color){
                result = true;
            }
        }

        // checks right diagnol for pawn
        if(!result && checkHelpBounds(rightRank) && checkHelpBounds(rightFile)){
            PieceValidator gottenPiece = (PieceValidator) board[rightRank][rightFile].getPiece();
            if(gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN
                    && gottenPiece.getPiece().getColor() != color){
                result = true;
            }
        }

        return result;
    }


    /**
     * Helper method that checks if our king is being put into check by an opposite
     * colored knight
     * @param rank the rank our king is at
     * @param file the file our king is at
     * @param color the color of our king
     * @return true if there is an opposite colored knight putting our king into check
     */
    private boolean checkKnight(int rank, int file, GameColor color){
        final int MOVE_ONE = 1;
        final int MOVE_TWO = 2;
        int toRank;
        int toFile;

        // There are 8 cases:
        // Case 1
        toRank = rank + MOVE_ONE;
        toFile = file + MOVE_TWO;
        boolean result = this.knightCheckHelp(rank, file, toRank, toFile, color);
        if(!result) {
            // Case 2
            toRank = rank + MOVE_TWO;
            toFile = file + MOVE_ONE;
            result = this.knightCheckHelp(rank, file, toRank, toFile, color);
            if(!result){
                // Case 3
                toRank = rank + MOVE_ONE;
                toFile = file - MOVE_TWO;
                result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                if(!result){
                    // Case 4
                    toRank = rank + MOVE_TWO;
                    toFile = file - MOVE_ONE;
                    result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                    if(!result){
                        // Case 5
                        toRank = rank - MOVE_ONE;
                        toFile = file - MOVE_TWO;
                        result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                        if(!result){
                            // Case 6
                            toRank = rank - MOVE_TWO;
                            toFile = file - MOVE_ONE;
                            result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                            if(!result){
                                // Case 7
                                toRank = rank - MOVE_ONE;
                                toFile = file + MOVE_TWO;
                                result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                                if(!result){
                                    // Case 8
                                    toRank = rank - MOVE_TWO;
                                    toFile = file + MOVE_ONE;
                                    result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Checks if the position we are getting to is valid, and contains an opposite colored
     * knight that is putting our king into check
     * @param fromRank the rank the king is at
     * @param fromFile the file the king is at
     * @param toRank the rank the opposite colored knight could be at to put our king into check
     * @param toFile the file the opposite colored knight could be at to put our king into check
     * @param color the color of our king
     * @return true if there is an opposite colored knight putting our king into check
     */
    private boolean knightCheckHelp(int fromRank, int fromFile, int toRank, int toFile, GameColor color){
        boolean result = false;
        if(this.checkHelpBounds(toRank) && this.checkHelpBounds(toFile)){
            PieceValidator gottenPiece = (PieceValidator) board[toRank][toFile].getPiece();
            if(gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KNIGHT &&
                    gottenPiece.getPiece().getColor() != color){
                result = true;
            }
        }
        return result;
    }

    /**
     * Checks if the index is within the valid bounds of the board.
     * @param difference the index we are checking
     * @return true if the index is within the valid bounds
     */
    private boolean checkHelpBounds(int difference){
        final int UPPER_BOUND = 7;
        final int LOWER_BOUND = 0;
        return difference <= UPPER_BOUND && difference >= LOWER_BOUND;
    }

    /**
     * Checks horizontally left if the king is in check
     * @param rank the rank of the king
     * @param file the file of the king
     * @param color the color of the king
     * @return true if the king is in check
     */
    private boolean checkLeftHoriz(int rank, int file, GameColor color){
        boolean result = false;
        int leftFile = file;

        boolean go = true;
        while(leftFile >= 0 && go){
            PieceValidator gottenPiece = (PieceValidator) board[rank][leftFile].getPiece();
            if(gottenPiece != null ){
                go = false;
                if(gottenPiece.getPiece().getColor() == color && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING){
                    go = true;
                }
                if(gottenPiece.getPiece().getColor() != color && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN
                        || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK)){
                    result = true;
                }
            }
            leftFile--;
        }
        return result;
    }

    /**
     * Checks vertically down if the king is in check
     * @param rank the rank of the king
     * @param file the file of the king
     * @param color the color of the king
     * @return true if the king is in check
     */
    private boolean checkDownVert(int rank, int file, GameColor color){
        boolean result = false;
        boolean go = true;

        int downRank = rank;

        while(downRank >= 0 && go){
            PieceValidator gottenPiece = (PieceValidator) board[downRank][file].getPiece();

            if(gottenPiece != null){
                go = false;
                if(gottenPiece.getPiece().getColor() == color && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING){
                    go = true;
                }
                if(gottenPiece.getPiece().getColor() != color && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN
                        || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK)){
                    result = true;
                    System.out.println("entered");
                }
            }
            downRank--;
        }

        return result;
    }

    /**
     * Checks horizontally right if the king is in check
     * @param rank the rank of the king
     * @param file the file of the king
     * @param color the color of the king
     * @return true if the king is in check
     */
    private boolean checkRightHoriz(int rank, int file, GameColor color){
        boolean result = false;

        int rightFile = file;
        int max_file_index = File.getMaxIndex();

        boolean go = true;
        while(rightFile <= max_file_index && go){
            PieceValidator gottenPiece = (PieceValidator) board[rank][rightFile].getPiece();
            if(gottenPiece != null){
                go = false;
                if(gottenPiece.getPiece().getColor() == color && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING){
                    go = true;
                }
                if(gottenPiece.getPiece().getColor() != color && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN
                        || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK)){
                    result = true;
                }
            }
            rightFile++;
        }
        return result;
    }

    /**
     * Checks vertically up if the king is in check
     * @param rank the rank of the king
     * @param file the file of the king
     * @param color the color of the king
     * @return true if the king is in check
     */
    private boolean checkUpVert(int rank, int file, GameColor color){
        boolean result = false;
        boolean go = true;


        int upRank = rank;
        int max_rank_index = Rank.getMaxIndex();

        while(upRank <= max_rank_index && go){
            PieceValidator gottenPiece = (PieceValidator) board[upRank][file].getPiece();
            if(gottenPiece != null){
                go = false;
                if(gottenPiece.getPiece().getColor() == color && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING){
                    go = true;
                }
                if(gottenPiece.getPiece().getColor() != color && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN
                        || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK)){
                    result = true;
                }
            }
            upRank++;
        }
        return result;
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * @param rank
     * @param file
     * @param color
     * @return
     */
    private boolean checkUpRightDiag(int rank, int file, GameColor color){
        // Check squares diagonally - positive slope up - from this position.
        boolean result = false;
        int i = rank - 1;
        int j = file + 1;
        boolean go = true;
        while (i > 0 &&  j <= File.getMaxIndex() && go) {

            PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
            if(gottenPiece != null){
                if(gottenPiece.getPiece().getColor() != color && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.BISHOP ||
                        gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN)){
                    result = true;
                }
                go = false;
            }

            i--;
            j++;
        }
        return result;
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * @param rank
     * @param file
     * @param color
     * @return
     */
    private boolean checkUpLeftDiag(int rank, int file, GameColor color){
        // Check squares diagonally - positive slope up - from this position.
        boolean result = false;
        int i = rank - 1;
        int j = file - 1;
        boolean go = true;
        while (i >= 0 &&  j >= 0  && go) {

            PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
            if(gottenPiece != null){
                if(gottenPiece.getPiece().getColor() != color && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.BISHOP ||
                        gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN)){
                    result = true;
                }
                go = false;
            }

            i--;
            j--;
        }
        return result;
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    private boolean checkDownRightDiag(int rank, int file, GameColor color){
        // Check squares diagonally - positive slope up - from this position.
        boolean result = false;
        int i = rank + 1;
        int j = file + 1;
        boolean go = true;
        while (i <= Rank.getMaxIndex() &&  j <= File.getMaxIndex() && go) {
            PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
            if(gottenPiece != null){
                if(gottenPiece.getPiece().getColor() != color && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.BISHOP ||
                        gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN)){
                    result = true;
                }
                go = false;
            }
            i++;
            j++;
        }
        return result;
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * @param rank
     * @param file
     * @param color
     * @return
     */
    private boolean checkDownLeftDiag(int rank, int file, GameColor color){
        // Check squares diagonally - positive slope up - from this position.
        boolean result = false;
        int i = rank + 1;
        int j = file - 1;
        boolean go = true;
        while (i <= Rank.getMaxIndex() &&  j >= 0 && go) {
            PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
            if(gottenPiece != null){
                if(gottenPiece.getPiece().getColor() != color && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.BISHOP ||
                        gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN)){
                    result = true;
                }
                go = false;
            }
            i++;
            j--;
        }
        return result;
    }



}