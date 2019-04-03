import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Board;
import Model.Piece;
import Model.Position;
import UI_CLI.Board_Color_CLI;
import Validator.KnightValidator;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests if the Knight Validator runs correctly.
 * @author Jeriah Caplinger (100%)
 * @version 3/20/2019
 */
class KnightTest {
    static Board board = null;
    static SquareIF[][] squares = null;


    @BeforeAll
    public static void setUpEverything(){
        board = new Board();
        board.init_board();
        squares = board.getSquares();

        PieceIF knight = new Piece(ChessPieceType.KNIGHT, GameColor.WHITE);
        knight = new KnightValidator(board, knight);
        squares[Rank.R1.getIndex()][File.B.getIndex()].setPiece(knight);

        PieceIF knight2 = new Piece(ChessPieceType.KNIGHT, GameColor.WHITE);
        knight2 = new KnightValidator(board, knight2);
        squares[Rank.R1.getIndex()][File.G.getIndex()].setPiece(knight2);

        PieceIF pawnD2W = new Piece(ChessPieceType.PAWN, GameColor.WHITE);
        pawnD2W = new KnightValidator(board, pawnD2W);
        squares[Rank.R2.getIndex()][File.D.getIndex()].setPiece(pawnD2W);

        PieceIF pawnA3B = new Piece(ChessPieceType.PAWN, GameColor.BLACK);
        pawnA3B = new KnightValidator(board, pawnA3B);
        squares[Rank.R3.getIndex()][File.A.getIndex()].setPiece(pawnA3B);

    }

    @AfterEach
    public void printBoard(){
        board.setDrawStrategy(new Board_Color_CLI());
        board.draw();
    }

    @Test
    @DisplayName("Validate moves for Knight On Openings")
    public void testValidate(){
        Position b1 = new Position(Rank.R1, File.B);
        Position g1 = new Position(Rank.R1, File.G);
        Position c3 = new Position(Rank.R3, File.C);
        Position d2 = new Position(Rank.R2, File.D);
        Position a3 = new Position(Rank.R3, File.A);

        // tests random movement
        assertTrue(squares[Rank.R1.getIndex()][File.B.getIndex()].getPiece().validateMove(b1, c3));
        assertFalse(squares[Rank.R1.getIndex()][File.G.getIndex()].getPiece().validateMove(g1, c3));
        assertFalse(squares[Rank.R1.getIndex()][File.G.getIndex()].getPiece().validateMove(b1, g1));

        // tests movement on ally
        assertFalse(squares[Rank.R1.getIndex()][File.B.getIndex()].getPiece().validateMove(b1, d2));
        //tests movement on opponent
        assertTrue(squares[Rank.R1.getIndex()][File.G.getIndex()].getPiece().validateMove(b1, a3));

    }

    @Test
    @DisplayName("Show Moves for Knight")
    public void testShowMoves(){
        // positions
        Position a2 = new Position(Rank.R3, File.A);
        Position a3 = new Position(Rank.R3, File.A);
        Position a4 = new Position(Rank.R4, File.A);
        Position a5 = new Position(Rank.R5, File.A);

        Position b1 = new Position(Rank.R1, File.B);
        Position b2 = new Position(Rank.R2, File.B);
        Position b3 = new Position(Rank.R3, File.B);
        Position b4 = new Position(Rank.R4, File.B);
        Position b5 = new Position(Rank.R5, File.B);
        Position b6 = new Position(Rank.R6, File.B);

        Position c2 = new Position(Rank.R2, File.C);
        Position c3 = new Position(Rank.R3, File.C);
        Position c4 = new Position(Rank.R4, File.C);
        Position c5 = new Position(Rank.R5, File.C);
        Position c6 = new Position(Rank.R6, File.C);

        Position d1 = new Position(Rank.R1, File.D);
        Position d2 = new Position(Rank.R2, File.D);
        Position d3 = new Position(Rank.R3, File.D);
        Position d4 = new Position(Rank.R4, File.D);

        Position f1 = new Position(Rank.R1, File.F);
        Position f2 = new Position(Rank.R2, File.F);

        Position g3 = new Position(Rank.R3, File.G);
        Position g1 = new Position(Rank.R1, File.G);

        Position e2 = new Position(Rank.R2, File.E);
        Position e6 = new Position(Rank.R6, File.E);

        Position f3 = new Position(Rank.R3, File.F);
        Position f4 = new Position(Rank.R4, File.F);
        Position f5 = new Position(Rank.R5, File.F);

        Position g4 = new Position(Rank.R4, File.G);
        Position g5 = new Position(Rank.R5, File.G);
        Position g6 = new Position(Rank.R6, File.G);

        Position h3 = new Position(Rank.R3, File.H);
        Position h5 = new Position(Rank.R5, File.H);

        // setting pieces





        // tests show moves for position b1
        Position[] b1Array = squares[Rank.R1.getIndex()][File.B.getIndex()].getPiece().showMoves(b1);
        ArrayList<Position> b1List = new ArrayList<>();
        for(int i = 0; i < b1Array.length; i++){
            b1List.add(b1Array[i]);
        }

        assertTrue(b1List.contains(a3) && b1List.contains(c3));
        assertFalse(b1List.contains(d2));

        //tests show moves for position h1
        Position h1 = new Position(Rank.R1, File.H);
        Position[] h1Array = squares[Rank.R1.getIndex()][File.B.getIndex()].getPiece().showMoves(h1);
        ArrayList<Position> h1List = new ArrayList<>();
        for(int i = 0; i < h1Array.length; i++){
            h1List.add(h1Array[i]);
        }
        assertTrue(h1List.contains(f2) && h1List.contains(g3));

        // testing h3 moves
        Position[] h3Array = squares[Rank.R1.getIndex()][File.B.getIndex()].getPiece().showMoves(h3);
        ArrayList<Position> h3List = new ArrayList<>();
        for(int i = 0; i < h3Array.length; i++){
            h3List.add(h3Array[i]);
        }
        assertTrue(h3List.contains(g1) && h3List.contains(g5) && h3List.contains(f4) && h3List.contains(f2));

        // testing d4 moves
        Position[] d4Array = squares[Rank.R1.getIndex()][File.B.getIndex()].getPiece().showMoves(d4);
        ArrayList<Position> d4List = new ArrayList<>();
        for(int i = 0; i < d4Array.length; i++){
            d4List.add(d4Array[i]);
        }
        assertTrue(d4List.contains(b3));
        assertTrue(d4List.contains(c2));
        assertTrue(d4List.contains(e2));
        assertTrue(d4List.contains(b5));
        assertTrue(d4List.contains(c6));
        assertTrue(d4List.contains(e6));
        assertTrue(d4List.contains(f5));
        assertTrue(d4List.contains(f3));
    }

}