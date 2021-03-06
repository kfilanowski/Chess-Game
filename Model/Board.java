package Model;

import Enums.*;
import History.State;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import UI_CLI.Board_Color_CLI;
import UI_CLI.Board_Mono_CLI;
import UI_GUI.Board_GUI;
import Validator.*;
import java.util.Scanner;

/**
 * * This is the board class that holds the methods to initialize , setup, and
 *   which draw method that will be used when the board is printed in the
 *   command line interface.
 *
 * @author - Jacob Ginn 25%
 * @author - Kevin Filanowski 25%
 * @author - Matt Lutz 25%
 * @author - Jeriah Caplinger 25%
 * @version - April 7, 2019
 *
 */
public class Board implements BoardIF {
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
     * This method sets up picking a UI for presenting the board, initializes
     * the board, sets the pieces on the board, draws the board, and finally
     * accepts input from the user.
     *
     * @throws NumberFormatException - Thrown when the input is invalid.
     */
    public void go() throws NumberFormatException {
        setupDrawing();
        bs.getNames();
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
        String input = "0";

        while (!chosen) {
            System.out.println("Menu Options: \n" 
                             + "1) Colored Command Line Interface.\n"
                             + "2) Monochrome Command Line Interface.\n" 
                             + "3) Graphical User Interface.\n" 
                             + "4) Exit.\n");

            System.out.print("Please enter an integer: ");
            input = reader.nextLine();

            switch (input) {
            case "1": { // If the user wants the colored board.
                setDrawStrategy(new Board_Color_CLI());
                chosen = true;
            }
                break;
            case "2": { // If the user wants the monochrome board.
                setDrawStrategy(new Board_Mono_CLI());
                chosen = true;
            }
                break;
            case "3": { // If the user wants the graphical user interface.
                setDrawStrategy(new Board_GUI());
                chosen = true;
            }
                break;
            case "4": { // If the user wants to exit the program.
                reader.close();
                System.exit(0);
            }
                break;
            default:
                System.out.println("Please enter an integer between 1 and 4.\n");
            }
        }
        //reader.close();
    }

    /**
     * This sets up the the board with squares that will hold pieces and the
     * piece validators.
     */
    @Override
    public void init_board() {
        int count = 0; // helps alternate the chess board

        for (int i = 0; i < getHeight(); i++) {// rows of the board
            if (i % 2 == 1) {// achieves the checker pattern
                count = 1;
            }
            for (int j = 0; j < getWidth(); j++) {// columns of the board
                if (count % 2 == 0) {
                    board[i][j] = new Square(GameColor.WHITE,
                            new Position(Rank.getRankFromIndex(i),
                            File.getFileFromIndex(j)));
                    count++;

                } else {
                    board[i][j] = new Square(GameColor.BLACK,
                            new Position(Rank.getRankFromIndex(i), 
                            File.getFileFromIndex(j)));
                    count++;
                }
            }
            count = 0;
        }
    }

    /**
     * This is the default setup of the board that sets black and white pieces
     * on the board.
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
     * Draws the board in black and white with the squares and the pieces are in
     * color.
     *
     * @param board - the board object that is printed on the command line
     *              interface.
     */
    public void revDraw(BoardIF board) {
        bs.revDraw(board);
    }

    /**
     * Draws the board with the highlighted squares that are valid moves for the
     * selected piece
     *
     * @param board - The board that the game is being played on.
     * @param pos   - The position array that holds all of the valid moves.
     */
    public void draw(BoardIF board, Position[] pos) {
        bs.draw(board, pos);
    }

    /**
     * Draws the Board for the black piece player with the valid moves of the
     * piece Highlighted on the board.
     *
     * @param board - The board that the game is being played on.
     * @param pos   - the position array that holds the valid positions a piece
     *              can move
     */
    public void revDraw(BoardIF board, Position[] pos) {
        bs.revDraw(board, pos);
    }

    /**
     * This returns the complete board that the game is being played on.
     *
     * @return - the board that chess is being played on
     */
    @Override
    public SquareIF[][] getSquares() {
        return board;
    }

    /**
     * Sets whether the users want to play in colored ot mono-colored mode.
     *
     * @param d - the draw strategy that is being used.
     */
    @Override
    public void setDrawStrategy(BoardStrategy d) {
        this.bs = d;
    }

    /**
     * gets the width of the board
     *
     * @return - the width of the board
     */
    @Override
    public int getWidth() {
        return board.length;
    }

    /**
     * gets the height of the board
     *
     * @return - the height of the board
     */
    @Override
    public int getHeight() {
        return board.length;
    }

    /**
     * Gets the piece on the Square that is inputted
     *
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
     *
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
     * @return - A SquareIF from the board that falls on the specified rank and
     *         file.
     */
    public SquareIF getSquare(Rank rank, File file) {
        return board[rank.getIndex()][file.getIndex()];
    }

    /**
     * Retrieve a square at a specified position.
     *
     * @param pos - The position of the square.
     * @return - A SquareIF from the board that falls on the specified position.
     */
    public SquareIF getSquare(Position pos) {
        return board[pos.getRank().getIndex()][pos.getFile().getIndex()];
    }

    /**
     * Return a state object ensapsulating a clone of this board object in its
     * current state.
     *
     * @return - A state encapsulating the current state of this board object as
     *         a clone.
     */
    public State<BoardIF> saveState() {
        return new State<BoardIF>(this.clone());
    }

    /**
     * Restores the state of this object from a state object.
     *
     * @param state - The state from which to get the state of the board.
     */
    public void restoreState(State<BoardIF> state) {
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
    private void setBlackPiece() {
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

        for (int i = 0; i < getWidth(); i++) {
            PieceIF pawn = new Piece(ChessPieceType.PAWN, GameColor.BLACK);
            pawn = new PawnValidator(this, pawn);
            board[1][i].setPiece(pawn);
        }
    }

    /**
     * sets the White pieces on the board
     */
    private void setWhitePiece() {
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

        for (int i = 0; i < getWidth(); i++) {
            PieceIF pawn = new Piece(ChessPieceType.PAWN, GameColor.WHITE);
            pawn = new PawnValidator(this, pawn);
            board[6][i].setPiece(pawn);
        }
    }

    /**
     * Method that checks to see if the king is in check
     *
     * @param color - Gamecolor for the piece that we want to check
     * @return - True if the king is in check, false if not
     */
    public boolean checkForCheck(GameColor color) {
        boolean finalResult = false;
        PieceValidator maybeKing = getKingFromBoard(color);

        if (maybeKing.getPiece().getChessPieceType() == ChessPieceType.KING) {
            finalResult = checkHelp(getKingRankIndex(color),
                                    getKingFileIndex(color), color);
        }
        return finalResult;
    }

    /**
     * Determines whether a player is in checkmate
     *
     * @param color - the color of the king in which to determine it is in check
     *              mate
     * @return - true if the king is in check mate, false otherwise
     */
    public boolean checkForCheckMate(GameColor color) {
        boolean result = false;
        PieceValidator king = getKingFromBoard(color);
        Position pos = new Position(Rank.getRankFromIndex(this.getKingRankIndex(color)),
                File.getFileFromIndex(this.getKingFileIndex(color)));
        boolean check = checkForCheck(color);
        if (check && king.getPiece().showMoves(pos).length == 0 
                  && !canKingGetOutOfCheckMate(color)) {
            result = true;
        }
        return result;
    }

    /**
     * Determines whether a player is in stalemate
     *
     * @param color - the color of the king in which to determine it is in check
     *              mate
     * @return - True if the king is in stalemate, false otherwise
     */
    public boolean checkForStaleMate(GameColor color) {
        boolean result = false;
        PieceValidator king = getKingFromBoard(color);
        Position pos = new Position(Rank.getRankFromIndex(this.getKingRankIndex(color)),
                File.getFileFromIndex(this.getKingFileIndex(color)));
        boolean check = checkForCheck(color);
        if (!check && king.getPiece().showMoves(pos).length == 0 
                   && !canKingGetOutOfCheckMate(color)) {
            result = true;
        }
        return result;
    }

    /**
     * Iterates through every allied piece for the king to see if any piece can
     * get the king out of check.. If not, then the king is checkmated
     *
     * @param color the color of the king in potential check mate
     * @return true if the king can get out i.e. the king is not in checkmate.
     *         False if the king is in checkmate
     */
    private boolean canKingGetOutOfCheckMate(GameColor color) {
        boolean result = false;
        // iterate through the board to find our allied pieces. We continue
        // until we run out of pieces or there is a piece that can get us out of
        // check (potential checkmate)
        for (int i = 0; i <= Rank.getMaxIndex() && !result; i++) {
            for (int j = 0; j <= File.getMaxIndex() && !result; j++) {
                PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
                if (gottenPiece != null && gottenPiece.getColor() == color) {
                    if (gottenPiece
                            .showMoves(new Position(Rank.getRankFromIndex(i), 
                            File.getFileFromIndex(j))).length != 0) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Helper method that gets the king from the board
     *
     * @param color - Color of the king we want to get from the board
     * @return - The king that we got from the board
     */
    private PieceValidator getKingFromBoard(GameColor color) {
        boolean go = true;
        int kingRank = -1;
        int kingFile = -1;
        PieceValidator maybeKing = null;
        PieceValidator king = null;
        // loops through and finds the king for which we are checking check on
        for (int i = 0; i <= Rank.getMaxIndex() && go; i++) {
            for (int j = 0; j <= File.getMaxIndex() && go; j++) {
                maybeKing = (PieceValidator) board[i][j].getPiece();
                if (maybeKing != null && maybeKing.getPiece().getChessPieceType() == ChessPieceType.KING
                        && maybeKing.getPiece().getColor() == color) {
                    go = false;
                    kingRank = i;
                    kingFile = j;
                }
            }
        }

        if (kingRank != -1 && kingFile != -1) {
            king = maybeKing;
        }

        return king;
    }

    /**
     * Gets the rank index of the king that we get from the board
     *
     * @param color - Color of the king we want to get from the board
     * @return - The rank index of the king we got from the board
     */
    private int getKingRankIndex(GameColor color) {
        boolean go = true;
        int kingRank = -1;
        for (int i = 0; i <= Rank.getMaxIndex() && go; i++) {
            for (int j = 0; j <= File.getMaxIndex() && go; j++) {
                PieceValidator maybeKing = (PieceValidator) board[i][j].getPiece();
                if (maybeKing != null && maybeKing.getPiece().getChessPieceType() == ChessPieceType.KING
                        && maybeKing.getColor() == color) {
                    go = false;
                    kingRank = i;

                }
            }
        }
        return kingRank;
    }

    /**
     * Gets the file index of the king that we get from the board
     *
     * @param color - Color of the king we want to get from the board
     * @return - The file index of the king we got from the board
     */
    private int getKingFileIndex(GameColor color) {
        boolean go = true;
        int kingFile = -1;
        for (int i = 0; i <= Rank.getMaxIndex() && go; i++) {
            for (int j = 0; j <= File.getMaxIndex() && go; j++) {
                PieceValidator maybeKing = (PieceValidator) board[i][j].getPiece();
                if (maybeKing != null && maybeKing.getPiece().getChessPieceType() == ChessPieceType.KING
                        && maybeKing.getColor() == color) {
                    go = false;
                    kingFile = j;

                }
            }
        }
        return kingFile;
    }

    /**
     * Helper method that manages checking in all different directions for check
     *
     * @param kingRank the rank the king is on
     * @param kingFile the file the king is on
     * @param color    the color of the king
     * @return true if the king is in check, false otherwise
     */
    private boolean checkHelp(int kingRank, int kingFile, GameColor color) {
        return checkLeftHoriz(kingRank, kingFile, color) || checkRightHoriz(kingRank, kingFile, color)
                || checkDownVert(kingRank, kingFile, color) || checkUpVert(kingRank, kingFile, color)
                || checkUpRightDiag(kingRank, kingFile, color) || checkDownRightDiag(kingRank, kingFile, color)
                || checkDownLeftDiag(kingRank, kingFile, color) || checkUpLeftDiag(kingRank, kingFile, color)
                || checkKnight(kingRank, kingFile, color) || checkPawn(kingRank, kingFile, color)
                || checkKing(kingRank, kingFile, color);
    }

    /**
     * Checks horizontally left if the king is in check
     *
     * @param rank  the rank of the king
     * @param file  the file of the king
     * @param color the color of the king
     * @return true if the king is in check
     */
    public boolean checkLeftHoriz(int rank, int file, GameColor color) {
        boolean result = false;
        int leftFile = file - 1;

        boolean go = true;
        while (leftFile >= 0 && go) {
            PieceValidator gottenPiece = (PieceValidator) board[rank][leftFile].getPiece();
            if (gottenPiece != null) {
                go = false;
                if (gottenPiece.getPiece().getColor() == color
                        && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING) {
                    go = true;
                }
                if (gottenPiece.getPiece().getColor() != color
                        && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN
                                || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK)) {
                    result = true;
                }
            }
            leftFile--;
        }
        return result;
    }

    /**
     * Checks vertically down if the king is in check
     *
     * @param rank  the rank of the king
     * @param file  the file of the king
     * @param color the color of the king
     * @return true if the king is in check
     */
    public boolean checkDownVert(int rank, int file, GameColor color) {
        boolean result = false;
        boolean go = true;

        int downRank = rank - 1;

        while (downRank >= 0 && go) {
            PieceValidator gottenPiece = (PieceValidator) board[downRank][file].getPiece();

            if (gottenPiece != null) {
                go = false;
                if (gottenPiece.getPiece().getColor() == color
                        && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING) {
                    go = true;
                }
                if (gottenPiece.getPiece().getColor() != color
                        && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN
                                || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK)) {
                    result = true;
                }
            }
            downRank--;
        }

        return result;
    }

    /**
     * Checks horizontally right if the king is in check
     *
     * @param rank  the rank of the king
     * @param file  the file of the king
     * @param color the color of the king
     * @return true if the king is in check
     */
    public boolean checkRightHoriz(int rank, int file, GameColor color) {
        boolean result = false;

        int rightFile = file + 1;
        int max_file_index = File.getMaxIndex();

        boolean go = true;
        while (rightFile <= max_file_index && go) {
            PieceValidator gottenPiece = (PieceValidator) board[rank][rightFile].getPiece();
            if (gottenPiece != null) {
                go = false;
                if (gottenPiece.getPiece().getColor() == color
                        && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING) {
                    go = true;
                }
                if (gottenPiece.getPiece().getColor() != color
                        && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN
                                || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK)) {
                    result = true;
                }
            }
            rightFile++;
        }
        return result;
    }

    /**
     * Checks vertically up if the king is in check
     *
     * @param rank  the rank of the king
     * @param file  the file of the king
     * @param color the color of the king
     * @return true if the king is in check
     */
    public boolean checkUpVert(int rank, int file, GameColor color) {
        boolean result = false;
        boolean go = true;

        int upRank = rank + 1;
        int max_rank_index = Rank.getMaxIndex();

        while (upRank <= max_rank_index && go) {
            PieceValidator gottenPiece = (PieceValidator) board[upRank][file].getPiece();
            if (gottenPiece != null) {
                go = false;
                if (gottenPiece.getPiece().getColor() == color
                        && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING) {
                    go = true;
                }
                if (gottenPiece.getPiece().getColor() != color
                        && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN
                                || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK)) {
                    result = true;
                }
            }
            upRank++;
        }
        return result;
    }

    /**
     * Checks the up right diagonal for piece that could put the king in check
     *
     * @param rank  - rank index we want to start from
     * @param file  - file index we want to start from
     * @param color - color of the piece we are starting from
     * @return - True if we find the Queen or Bishop, false otherwise
     */
    public boolean checkUpRightDiag(int rank, int file, GameColor color) {
        // Check squares diagonally - positive slope up - from this position.
        boolean result = false;
        int i = rank - 1;
        int j = file + 1;
        boolean go = true;
        while (i > 0 && j <= File.getMaxIndex() && go) {

            PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
            if (gottenPiece != null) {
                if (gottenPiece.getPiece().getColor() != color
                        && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.BISHOP
                                || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN)) {
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
     * Checks the up left diagonal for piece that could put the king in check
     *
     * @param rank  - rank index we want to start from
     * @param file  - file index we want to start from
     * @param color - color of the piece we are starting from
     * @return - True if we find the Queen or Bishop, false otherwise
     */
    public boolean checkUpLeftDiag(int rank, int file, GameColor color) {
        // Check squares diagonally - positive slope down - from this position.
        boolean result = false;
        int i = rank - 1;
        int j = file - 1;
        boolean go = true;
        while (i >= 0 && j >= 0 && go) {

            PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
            if (gottenPiece != null) {
                if (gottenPiece.getPiece().getColor() != color
                        && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.BISHOP
                                || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN)) {
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
     * Checks the down right diagonal for piece that could put the king in check
     *
     * @param rank  - rank index we want to start from
     * @param file  - file index we want to start from
     * @param color - color of the piece we are starting from
     * @return - True if we find the Queen or Bishop, false otherwise
     */
    public boolean checkDownRightDiag(int rank, int file, GameColor color) {
        // Check squares diagonally - positive slope up - from this position.
        boolean result = false;
        int i = rank + 1;
        int j = file + 1;
        boolean go = true;
        while (i <= Rank.getMaxIndex() && j <= File.getMaxIndex() && go) {
            PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
            if (gottenPiece != null) {
                if (gottenPiece.getPiece().getColor() != color
                        && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.BISHOP
                                || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN)) {
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
     * Checks the down left diagonal for a piece that could put the king in
     * check
     *
     * @param rank  - rank index we want to start from
     * @param file  - file index we want to start from
     * @param color - color of the piece we are starting from
     * @return - True if we find the Queen or Bishop, false otherwise
     */
    public boolean checkDownLeftDiag(int rank, int file, GameColor color) {
        // Check squares diagonally - positive slope up - from this position.
        boolean result = false;
        int i = rank + 1;
        int j = file - 1;
        boolean go = true;
        while (i <= Rank.getMaxIndex() && j >= 0 && go) {
            PieceValidator gottenPiece = (PieceValidator) board[i][j].getPiece();
            if (gottenPiece != null) {
                if (gottenPiece.getPiece().getColor() != color
                        && (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.BISHOP
                                || gottenPiece.getPiece().getChessPieceType() == ChessPieceType.QUEEN)) {
                    result = true;
                }
                go = false;
            }
            i++;
            j--;
        }
        return result;
    }

    /**
     * Helper method that determines if an opposite colored pawn is putting the
     * king into check
     *
     * @param rank  the rank of the king
     * @param file  the file of the king
     * @param color the color of the king
     * @return true if an opposite color pawn is putting the king into check
     */
    public boolean checkPawn(int rank, int file, GameColor color) {
        boolean result;
        if (color == GameColor.WHITE) {
            result = this.checkBlackPawn(rank, file, color);
        } else {
            result = this.checkWhitePawn(rank, file, color);
        }
        return result;
    }

    /**
     * Helper method that determines if the black pawn is putting the white king
     * into check
     *
     * @param rank  the rank of the king
     * @param file  the file of the king
     * @param color the color of the king
     * @return true if the pawn is putting the king into check
     */
    private boolean checkBlackPawn(int rank, int file, GameColor color) {
        boolean result = false;
        int leftRank = rank - 1;
        int leftFile = file - 1;
        int rightRank = rank - 1;
        int rightFile = file + 1;

        // checks left diagnol square for pawn
        if (checkHelpBounds(leftRank) && checkHelpBounds(leftFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[leftRank][leftFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checks right diagnol square for pawn
        if (!result && checkHelpBounds(rightRank) && checkHelpBounds(rightFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[rightRank][rightFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Helper method that determines if the white pawn is putting the black king
     * into check
     *
     * @param rank  the rank of the king
     * @param file  the file of the king
     * @param color the color of the king
     * @return true if the pawn is putting the king into check
     */
    private boolean checkWhitePawn(int rank, int file, GameColor color) {
        boolean result = false;
        int leftRank = rank + 1;
        int leftFile = file - 1;
        int rightRank = rank + 1;
        int rightFile = file + 1;

        // checks left diagnol for pawn
        if (checkHelpBounds(leftRank) && checkHelpBounds(leftFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[leftRank][leftFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checks right diagnol for pawn
        if (!result && checkHelpBounds(rightRank) && checkHelpBounds(rightFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[rightRank][rightFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Helper method that checks to see if a king would put the other king in
     * check
     *
     * @param rank  the rank of the king
     * @param file  the file of the king
     * @param color the color of the king
     * @return true if a king is putting the opponent king into check
     */
    public boolean checkKing(int rank, int file, GameColor color) {
        boolean result = false;
        int upRank = rank - 1;
        int downRank = rank + 1;
        int rightFile = file + 1;
        int leftFile = file - 1;

        // checking the down left diagonal for the king
        if (checkHelpBounds(downRank) && checkHelpBounds(leftFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[downRank][leftFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checking the up right diagonal for the king
        if (checkHelpBounds(upRank) && checkHelpBounds(rightFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[upRank][rightFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checking the down right diagonal for the king
        if (checkHelpBounds(downRank) && checkHelpBounds(rightFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[downRank][rightFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checking the up left diagonal for the king
        if (checkHelpBounds(upRank) && checkHelpBounds(leftFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[upRank][leftFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checking the spot above for the king
        if (checkHelpBounds(upRank) && checkHelpBounds(file)) {
            PieceValidator gottenPiece = (PieceValidator) board[upRank][file].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checking the spot below for the king
        if (checkHelpBounds(downRank) && checkHelpBounds(file)) {
            PieceValidator gottenPiece = (PieceValidator) board[downRank][file].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checking the spot to the right for the king
        if (checkHelpBounds(rank) && checkHelpBounds(rightFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[rank][rightFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        // checking the spot to the left for the king
        if (checkHelpBounds(rank) && checkHelpBounds(leftFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[rank][leftFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KING
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Helper method that checks if our king is being put into check by an
     * opposite colored knight
     *
     * @param rank  the rank our king is at
     * @param file  the file our king is at
     * @param color the color of our king
     * @return true if there is an opposite colored knight putting our king into
     *         check
     */
    public boolean checkKnight(int rank, int file, GameColor color) {
        final int MOVE_ONE = 1;
        final int MOVE_TWO = 2;
        int toRank;
        int toFile;

        // There are 8 cases: Case 1
        toRank = rank + MOVE_ONE;
        toFile = file + MOVE_TWO;
        boolean result = this.knightCheckHelp(rank, file, toRank, toFile, color);
        if (!result) {
            // Case 2
            toRank = rank + MOVE_TWO;
            toFile = file + MOVE_ONE;
            result = this.knightCheckHelp(rank, file, toRank, toFile, color);
            if (!result) {
                // Case 3
                toRank = rank + MOVE_ONE;
                toFile = file - MOVE_TWO;
                result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                if (!result) {
                    // Case 4
                    toRank = rank + MOVE_TWO;
                    toFile = file - MOVE_ONE;
                    result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                    if (!result) {
                        // Case 5
                        toRank = rank - MOVE_ONE;
                        toFile = file - MOVE_TWO;
                        result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                        if (!result) {
                            // Case 6
                            toRank = rank - MOVE_TWO;
                            toFile = file - MOVE_ONE;
                            result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                            if (!result) {
                                // Case 7
                                toRank = rank - MOVE_ONE;
                                toFile = file + MOVE_TWO;
                                result = this.knightCheckHelp(rank, file, toRank, toFile, color);
                                if (!result) {
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
     * Checks if the position we are getting to is valid, and contains an
     * opposite colored knight that is putting our king into check
     *
     * @param fromRank the rank the king is at
     * @param fromFile the file the king is at
     * @param toRank   the rank the opposite colored knight could be at to put
     *                 our king into check
     * @param toFile   the file the opposite colored knight could be at to put
     *                 our king into check
     * @param color    the color of our king
     * @return true if there is an opposite colored knight putting our king into
     *         check
     */
    private boolean knightCheckHelp(int fromRank, int fromFile, int toRank, int toFile, GameColor color) {
        boolean result = false;
        if (this.checkHelpBounds(toRank) && this.checkHelpBounds(toFile)) {
            PieceValidator gottenPiece = (PieceValidator) board[toRank][toFile].getPiece();
            if (gottenPiece != null && gottenPiece.getPiece().getChessPieceType() == ChessPieceType.KNIGHT
                    && gottenPiece.getPiece().getColor() != color) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Checks if the index is within the valid bounds of the board.
     *
     * @param difference the index we are checking
     * @return true if the index is within the valid bounds
     */
    private boolean checkHelpBounds(int difference) {
        final int UPPER_BOUND = board.length - 1;
        final int LOWER_BOUND = 0;
        return difference <= UPPER_BOUND && difference >= LOWER_BOUND;
    }


    /**
     * Converts this board into an XML object
     * @return an XML string that represents this board
     */
    public String toXML(){
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t<board>\n");
        // we loop through the whole board and get the pieces
        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                PieceValidator piece = (PieceValidator) board[i][j].getPiece();
                if(piece != null){
                    // starting tag to signify a piece
                    builder.append("\t\t\t<piece rankIndex=\"" + i + "\" fileIndex = \""+ j +"\"  >\n");

                    // gets chess piece type
                    builder.append("\t\t\t\t<pieceType> ");
                    builder.append(piece.getPiece().getChessPieceType());
                    builder.append(" </pieceType>\n");

                    // gets color type
                    builder.append("\t\t\t\t<color> ");
                    builder.append(piece.getPiece().getColor());
                    builder.append(" </color>\n");

//                    // gets the rank index for the piece
//                    builder.append("\t\t\t\t<rankIndex> ");
//                    builder.append(i);
//                    builder.append(" </rankIndex>\n");
//
//                    // gets the file index for the piece
//                    builder.append("\t\t\t\t<fileIndex> ");
//                    builder.append(j);
//                    builder.append(" </fileIndex>\n");

                    // end tag for the piece
                    builder.append("\t\t\t</piece>\n");
                }
            }
        }
        builder.append("\t\t</board>\n");
        return builder.toString();
    }

}
