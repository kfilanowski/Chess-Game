package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;

import Enums.*;
import Interfaces.BoardIF;
import Interfaces.SquareIF;
import Model.*;
import Validator.DiagonalValidator;

/**
 * Tester class tests the HorizVertValidator methods using JUnit 5.
 * 
 * @author Kevin Filanowski
 * @version March 12, 2019
 */
public class TestDiagonalValidator {
    /** The Object to test. */
    static DiagonalValidator valid = null;
    /** A board is required to instantiate HorizVertValidator. */
    static BoardIF board = null;

    /**
     * This method runs before all tests begin.
     */
    @BeforeAll
    public static void BeforeAll() {
        // Initialize a new board
        board = new Board();
        board.init_board();

        // Add pieces to the board.
        SquareIF[][] squares = board.getSquares();
        squares[4][6].setPiece(new Piece(ChessPieceType.Pawn,   GameColor.White));
        squares[7][5].setPiece(new Piece(ChessPieceType.Queen,  GameColor.White));
        squares[0][3].setPiece(new Piece(ChessPieceType.Rook,   GameColor.Black));
        squares[2][6].setPiece(new Piece(ChessPieceType.Pawn,   GameColor.Black));
        squares[4][7].setPiece(new Piece(ChessPieceType.Bishop, GameColor.Black));
        squares[7][1].setPiece(new Piece(ChessPieceType.Bishop, GameColor.Black));
        squares[7][3].setPiece(new Piece(ChessPieceType.King,   GameColor.Black));

        // Instantiate the validator.
        valid = new DiagonalValidator(board);

        System.out.println("\n=====New Test====");
        // Draw the board.
        board.draw();
    }

    /**
     * This method is called before each test is run.
     */
    @BeforeEach
    public void BeforeEach() {
        //
    }

    /**
     * Test with the case where the positions from and to are the same.
     */
    @Test
    @DisplayName("Same Boundry Case")
    public void testValidateMove1() {
        Position from = new Position(Rank.R2, File.A, null);
        Position to   = new Position(Rank.R2, File.A, null);
        assertFalse(valid.validateMove(from, to));
    }

    /**
     * Test positive diagonal movement in the positive slope 
     * direction with no obstructions.
     */
    @Test
    @DisplayName("Positive Diagonal Movement (/) - No obstructions")
    public void testValidateMove2() {
        Position from  = new Position(Rank.R7, File.A, null);
        Position to    = new Position(Rank.R1, File.G, null);
        Position from2 = new Position(Rank.R1, File.G, null);
        Position to2   = new Position(Rank.R7, File.A, null);
        assertAll("A7 -> G1, G1 -> A7", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test negative diagonal movement in the negative slope direction
     * with no obstructions.
     */
    @Test
    @DisplayName("Negative Diagonal Movement (\\) - No obstructions")
    public void testValidateMove3() {
        Position from  = new Position(Rank.R1, File.B, null);
        Position to    = new Position(Rank.R7, File.H, null);
        Position from2 = new Position(Rank.R7, File.H, null);
        Position to2   = new Position(Rank.R1, File.B, null);
        assertAll("B1 -> H7, H7 -> B1", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2)));
    }

    

    /**
     * Test positive diagonal movement with the case 
     * where the positions from and to are one space apart.
     */
    @Test
    @DisplayName("Positive Diagonal Movement (/) - Closest Boundry Case")
    public void testValidateMove4() {
        Position from  = new Position(Rank.R2, File.A, null);
        Position to    = new Position(Rank.R1, File.B, null);
        Position from2 = new Position(Rank.R1, File.B, null);
        Position to2   = new Position(Rank.R2, File.A, null);
        assertAll("A2 -> B1, B1 -> A2", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test negative diagonal movement with the case where 
     * the positions from and to are one space apart.
     */
    @Test
    @DisplayName("Negative Diagonal Movement (\\) - Closest Boundry Case")
    public void testValidateMove5() {
        Position from  = new Position(Rank.R1, File.A, null);
        Position to    = new Position(Rank.R2, File.B, null);
        Position from2 = new Position(Rank.R2, File.B, null);
        Position to2   = new Position(Rank.R1, File.A, null);
        assertAll("A1 -> B2, B2 -> A1", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2)));
    }

    /**
     * Test positive diagonal movement with the case 
     * where the positions from and to are the furthest distance apart.
     */
    @Test
    @DisplayName("Positive Diagonal Movement (/) - Furthest Boundry Case")
    public void testValidateMove6() {
        Position from  = new Position(Rank.R8, File.A, null);
        Position to    = new Position(Rank.R1, File.H, null);
        Position from2 = new Position(Rank.R1, File.H, null);
        Position to2   = new Position(Rank.R8, File.A, null);
        assertAll("A8 -> H1, H1 -> A8", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test negative diagonal movement with the case where the 
     * positions from and to are the furthest distance apart.
     */
    @Test
    @DisplayName("Negative Diagonal Movement (\\) - Furthest Boundry Case")
    public void testValidateMove7() {
        Position from  = new Position(Rank.R1, File.A, null);
        Position to    = new Position(Rank.R8, File.H, null);
        Position from2 = new Position(Rank.R8, File.H, null);
        Position to2   = new Position(Rank.R1, File.A, null);
        assertAll("A1 -> H8, H8 -> A1", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2)));
    }

    /**
     * Test positive diagonal movement with the case 
     * where a piece is obstructing the path.
     */
    @Test
    @DisplayName("Positive Diagonal Movement (/) - Obstructed Case")
    public void testValidateMove8() {
        Position from  = new Position(Rank.R4, File.F, null);
        Position to    = new Position(Rank.R2, File.H, null);
        Position from2 = new Position(Rank.R2, File.H, null);
        Position to2   = new Position(Rank.R4, File.F, null);
        assertAll("F4 -> H2, H2 -> F4", 
            () -> assertFalse(valid.validateMove(from, to)),
            () -> assertFalse(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test negative diagonal movement with the case where a piece is obstructing
     * the path.
     */
    @Test
    @DisplayName("Negative Diagonal Movement (\\) - Obstructed Case")
    public void testValidateMove9() {
        Position from  = new Position(Rank.R1, File.C, null);
        Position to    = new Position(Rank.R6, File.H, null);
        Position from2 = new Position(Rank.R6, File.H, null);
        Position to2   = new Position(Rank.R1, File.C, null);
        assertAll("C1 -> H6, H6 -> C1", 
            () -> assertFalse(valid.validateMove(from, to)),
            () -> assertFalse(valid.validateMove(from2, to2)));
    }

    /**
     * Test to ensure that non-diagonal movement is not validated.
     */
    @Test
    @DisplayName("Non-Diagonal Movement Case")
    public void testValidateMove10() {
        Position from  = new Position(Rank.R4, File.D, null);
        Position to    = new Position(Rank.R6, File.D, null);
        Position from2 = new Position(Rank.R4, File.D, null);
        Position to2   = new Position(Rank.R4, File.B, null);
        Position from3 = new Position(Rank.R1, File.A, null);
        Position to3   = new Position(Rank.R7, File.A, null);
        Position from4 = new Position(Rank.R1, File.A, null);
        Position to4   = new Position(Rank.R7, File.H, null);
        Position from5 = new Position(Rank.R5, File.H, null);
        Position to5   = new Position(Rank.R5, File.G, null);
        assertAll("D4 -> D6, D4 -> B4, A1 -> A7, A1 -> H7, H5 -> G5", 
            () -> assertFalse(valid.validateMove(from, to)),
            () -> assertFalse(valid.validateMove(from2, to2)),
            () -> assertFalse(valid.validateMove(from3, to3)),
            () -> assertFalse(valid.validateMove(from4, to4)),
            () -> assertFalse(valid.validateMove(from5, to5))
        );
    }

    /**
     * Test to ensure that a piece can be placed onto an opponent piece.
     */
    @Test
    @DisplayName("Moving onto an Opponent piece")
    public void testValidateMove11() {
        Position from = new Position(Rank.R8, File.D, null);
        Position to   = new Position(Rank.R5, File.G, null);
        assertTrue(valid.validateMove(from, to));
    }

    /**
     * Test to ensure that a piece cannot be placed onto a piece of the same color.
     */
    @Test
    @DisplayName("Moving onto an ally piece")
    public void testValidateMove12() {
        Position from = new Position(Rank.R8, File.B, null);
        Position to   = new Position(Rank.R3, File.G, null);
        assertFalse(valid.validateMove(from, to));
    }

    /**
     * Test to ensure that a piece cannot be placed onto a King piece.
     */
    @Test
    @DisplayName("Moving onto a King piece")
    public void testValidateMove13() {
        Position from  = new Position(Rank.R5, File.G, null);
        Position to    = new Position(Rank.R8, File.D, null);
        assertFalse(valid.validateMove(from, to));
    }

    @AfterAll
    public static void AfterAll() {
        board = null;
        valid = null;
    }
}