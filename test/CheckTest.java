import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Board;
import Model.Piece;
import UI_CLI.Board_Color_CLI;
import Validator.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests if the functionality for Checking the King runs correctly.
 * @author Jeriah Caplinger
 * @version 4/2/2019
 */
class CheckTest {
    static Board board = null;
    static SquareIF[][] squares = null;


    @BeforeAll
    public static void setUpEverything(){
        board = new Board();
        board.init_board();
        squares = board.getSquares();

        PieceIF kingD2W = new Piece(ChessPieceType.KING, GameColor.WHITE);
        kingD2W = new KingValidator(board, kingD2W);
        squares[Rank.R2.getIndex()][File.D.getIndex()].setPiece(kingD2W);

    }

    @AfterEach
    public void printBoard(){
        board.setDrawStrategy(new Board_Color_CLI());
        board.draw();
    }


    @Test
    @DisplayName("Queen/Rook Check Vertical/Horizontal Test")
    public void testQueenRookCheck() {
        //testing all different check attacks from black for ROOK
        for(int i = 0; i <= Rank.getMaxIndex(); i++){
            if(i != Rank.R2.getIndex()){
                assertTrue(this.rookBlackSetClear(Rank.getRankFromIndex(i), File.D));
            }
        }

        for(int i = 0; i <= File.getMaxIndex(); i++){
            if(i != File.D.getIndex()){
                assertTrue(this.rookBlackSetClear(Rank.R2, File.getFileFromIndex(i)));
            }
        }

        //testing all different check attacks from black for QUEEN
        for(int i = 0; i <= Rank.getMaxIndex(); i++){
            if(i != Rank.R2.getIndex()){
                assertTrue(this.queenBlackSetClear(Rank.getRankFromIndex(i), File.D));
            }
        }

        for(int i = 0; i <= File.getMaxIndex(); i++){
            if(i != File.D.getIndex()){
                assertTrue(this.queenBlackSetClear(Rank.R2, File.getFileFromIndex(i)));
            }
        }

    }

    /**
     * Helper method that sets a queen to test for check then immediately takes the piece off
     * the board. It prints the board before the queen is taken off the board
     * @param rank rank to set queen at
     * @param file file to set queen at
     * @return true if the black queen put the white king into check
     */
    private boolean queenBlackSetClear(Rank rank, File file){
        PieceIF queen = new Piece(ChessPieceType.QUEEN, GameColor.BLACK);
        queen = new HorizVertValidator(board, queen);
        queen = new DiagonalValidator(board, queen);
        squares[rank.getIndex()][file.getIndex()].setPiece(queen);

        boolean result = board.checkForCheck(GameColor.WHITE);

        printBoard();

        squares[rank.getIndex()][file.getIndex()].setPiece(null);

        return result;
    }


    /**
     * Helper method that sets a black queen to test for check and leaves it on the board.
     * Note: no board is printed after use. To do so, call printBoard()
     * @param rank rank to set queen at
     * @param file file to set queen at
     * @return true if the black queen put the white king into check
     */
    private boolean queenBlackSetStay(Rank rank, File file){
        PieceIF queen = new Piece(ChessPieceType.QUEEN, GameColor.BLACK);
        queen = new HorizVertValidator(board, queen);
        queen = new DiagonalValidator(board, queen);
        squares[rank.getIndex()][file.getIndex()].setPiece(queen);

        return board.checkForCheck(GameColor.WHITE);
    }


    /**
     * Helper method that sets a black rook to test for check and leaves it on the board.
     * Note: no board is printed after use. To do so, call printBoard()
     * @param rank rank to set rook at
     * @param file file to set rook at
     * @return true if the black rook put the white king into check
     */
    private boolean rookBlackSetStay(Rank rank, File file){
        PieceIF rookD1B = new Piece(ChessPieceType.ROOK, GameColor.BLACK);
        rookD1B = new HorizVertValidator(board, rookD1B);
        squares[rank.getIndex()][file.getIndex()].setPiece(rookD1B);

        return board.checkForCheck(GameColor.WHITE);
    }

    /**
     * Helper method that sets a black rook to test for check then immediately takes the piece off
     * the board and prints the board before the piece is taken off
     * @param rank rank to set rook at
     * @param file file to set rook at
     * @return true if the black rook put the white king into check
     */
    private boolean rookBlackSetClear(Rank rank, File file){
        PieceIF rookD1B = new Piece(ChessPieceType.ROOK, GameColor.BLACK);
        rookD1B = new HorizVertValidator(board, rookD1B);
        squares[rank.getIndex()][file.getIndex()].setPiece(rookD1B);

        boolean result = board.checkForCheck(GameColor.WHITE);

        printBoard();

        squares[rank.getIndex()][file.getIndex()].setPiece(null);


        return result;
    }




}