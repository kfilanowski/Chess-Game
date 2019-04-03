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
import Validator.PawnValidator;
import org.junit.jupiter.api.*;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests if the Pawn Validator runs correctly.
 * @author Jeriah Caplinger (100%)
 * @version 3/20/2019
 */
class PawnTest {
    static Board board = null;
    static SquareIF[][] squares = null;


    @BeforeAll
    public static void setUpEverything(){
        board = new Board();
        board.init_board();
        squares = board.getSquares();

        PieceIF pawnD2W = new Piece(ChessPieceType.PAWN, GameColor.WHITE);
        pawnD2W = new PawnValidator(board, pawnD2W);
        squares[Rank.R2.getIndex()][File.D.getIndex()].setPiece(pawnD2W);

        PieceIF pawnF2B = new Piece(ChessPieceType.PAWN, GameColor.BLACK);
        pawnF2B = new PawnValidator(board, pawnF2B);
        squares[Rank.R2.getIndex()][File.F.getIndex()].setPiece(pawnF2B);

        PieceIF pawnA3B = new Piece(ChessPieceType.PAWN, GameColor.BLACK);
        pawnA3B = new PawnValidator(board, pawnA3B);
        squares[Rank.R3.getIndex()][File.A.getIndex()].setPiece(pawnA3B);

        PieceIF pawnG4B = new Piece(ChessPieceType.PAWN, GameColor.BLACK);
        pawnG4B = new PawnValidator(board, pawnG4B);
        squares[Rank.R4.getIndex()][File.G.getIndex()].setPiece(pawnG4B);
        //System.out.println(((PieceValidator)squares[Rank.R4.getIndex()][File.G.getIndex()].getPiece()).p.getChessPieceType());

        PieceIF knightH5W = new Piece(ChessPieceType.KNIGHT, GameColor.WHITE);
        knightH5W = new KnightValidator(board, knightH5W);
        squares[Rank.R5.getIndex()][File.H.getIndex()].setPiece(knightH5W);

        PieceIF pawnB4W = new Piece(ChessPieceType.PAWN, GameColor.WHITE);
        pawnB4W = new PawnValidator(board, pawnB4W);
        squares[Rank.R4.getIndex()][File.B.getIndex()].setPiece(pawnB4W);

        PieceIF knightC5B = new Piece(ChessPieceType.KNIGHT, GameColor.BLACK);
        knightC5B = new KnightValidator(board, knightC5B);
        squares[Rank.R5.getIndex()][File.C.getIndex()].setPiece(knightC5B);

    }

    @AfterEach
    public void printBoard(){
        board.setDrawStrategy(new Board_Color_CLI());
        board.draw();
    }

    @Test
    @DisplayName("Validate moves for Pawn On Openings")
    public void testValidate(){
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



        // tests random movement
        // white
        assertTrue(squares[Rank.R2.getIndex()][File.D.getIndex()].getPiece().validateMove(d2, d1));
        assertFalse(squares[Rank.R2.getIndex()][File.D.getIndex()].getPiece().validateMove(d2, d3));
        assertFalse(squares[Rank.R2.getIndex()][File.D.getIndex()].getPiece().validateMove(d2, d4));

        assertFalse(squares[Rank.R4.getIndex()][File.B.getIndex()].getPiece().validateMove(b4, c5));
        assertTrue(squares[Rank.R4.getIndex()][File.B.getIndex()].getPiece().validateMove(b4, a3));
        assertFalse(squares[Rank.R4.getIndex()][File.B.getIndex()].getPiece().validateMove(b4, a5));
        assertFalse(squares[Rank.R4.getIndex()][File.B.getIndex()].getPiece().validateMove(b4, c3));

        // black
        assertFalse(squares[Rank.R2.getIndex()][File.F.getIndex()].getPiece().validateMove(f2, f1));
        assertTrue(squares[Rank.R2.getIndex()][File.F.getIndex()].getPiece().validateMove(f2, f3));
        assertTrue(squares[Rank.R2.getIndex()][File.F.getIndex()].getPiece().validateMove(f2, f4));

        assertTrue(squares[Rank.R3.getIndex()][File.A.getIndex()].getPiece().validateMove(a3, a4));
        assertFalse(squares[Rank.R3.getIndex()][File.A.getIndex()].getPiece().validateMove(a3, a5));
        assertFalse(squares[Rank.R3.getIndex()][File.A.getIndex()].getPiece().validateMove(a3, a3));
        assertFalse(squares[Rank.R3.getIndex()][File.A.getIndex()].getPiece().validateMove(a3, a2));

        assertTrue(squares[Rank.R4.getIndex()][File.G.getIndex()].getPiece().validateMove(g4, h5));
        assertFalse(squares[Rank.R4.getIndex()][File.G.getIndex()].getPiece().validateMove(g4, h3));
        assertFalse(squares[Rank.R4.getIndex()][File.G.getIndex()].getPiece().validateMove(g4, f3));
        assertFalse(squares[Rank.R4.getIndex()][File.G.getIndex()].getPiece().validateMove(g4, f5));
    }

    @Test
    @DisplayName("Show Moves for Pawn")
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

        // tests show moves for position d2
        Position[] d2Array = squares[Rank.R2.getIndex()][File.D.getIndex()].getPiece().showMoves(d2);
        ArrayList<Position> d2List = new ArrayList<>();
        for(int i = 0; i < d2Array.length; i++){
            d2List.add(d2Array[i]);
        }

        assertTrue(d2List.contains(d1));
        assertFalse(d2List.contains(d2));
        assertFalse(d2List.contains(d3));

        //tests show moves for position f2
        Position[] f2Array = squares[Rank.R2.getIndex()][File.F.getIndex()].getPiece().showMoves(f2);
        ArrayList<Position> f2List = new ArrayList<>();
        for(int i = 0; i < f2Array.length; i++){
            f2List.add(f2Array[i]);
        }
        assertTrue(f2List.contains(f3) && f2List.contains(f4));
        assertFalse(f2List.contains(f1));

        // testing g4 moves
        Position[] g4Array = squares[Rank.R4.getIndex()][File.G.getIndex()].getPiece().showMoves(g4);
        ArrayList<Position> g4List = new ArrayList<>();
        for(int i = 0; i < g4Array.length; i++){
            g4List.add(g4Array[i]);
        }
        assertTrue(g4List.contains(h5) && g4List.contains(g5));
        assertFalse(g4List.contains(f5) && g4List.contains(g6) && g4List.contains(g3));

        // testing b4 moves
        Position[] b4Array = squares[Rank.R4.getIndex()][File.B.getIndex()].getPiece().showMoves(b4);
        ArrayList<Position> b4List = new ArrayList<>();
        for(int i = 0; i < b4Array.length; i++){
            b4List.add(b4Array[i]);
        }
        assertTrue(b4List.contains(a3) && b4List.contains(b3));
        assertFalse(b4List.contains(c5) && b4List.contains(a4) && b4List.contains(b5) && b4List.contains(b6));

        // testing a3 moves
        Position[] a3Array = squares[Rank.R3.getIndex()][File.A.getIndex()].getPiece().showMoves(a3);
        ArrayList<Position> a3List = new ArrayList<>();
        for(int i = 0; i < a3Array.length; i++){
            a3List.add(a3Array[i]);
        }
        assertTrue(a3List.contains(b4) && a3List.contains(a4));
        assertFalse(a3List.contains(a5) && a3List.contains(a2) && a3List.contains(b2) && a3List.contains(b3));
    }

}