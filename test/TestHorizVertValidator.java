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
import Validator.HorizVertValidator;

/**
 * Tester class tests the HorizVertValidator methods using JUnit 5.
 * 
 * @author Kevin Filanowski
 * @version March 11, 2019
 */
public class TestHorizVertValidator {
    /** The Object to test. */
    static HorizVertValidator valid = null;
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
        squares[5][5].setPiece(new Piece(ChessPieceType.Pawn,   GameColor.Black));
        squares[7][5].setPiece(new Piece(ChessPieceType.Queen,  GameColor.White));
        squares[0][3].setPiece(new Piece(ChessPieceType.Rook,   GameColor.Black));
        squares[2][6].setPiece(new Piece(ChessPieceType.Pawn,   GameColor.White));
        squares[4][7].setPiece(new Piece(ChessPieceType.Bishop, GameColor.White));
        squares[7][1].setPiece(new Piece(ChessPieceType.Bishop, GameColor.White));
        squares[7][3].setPiece(new Piece(ChessPieceType.King,   GameColor.Black));

        // Instantiate the validator.
        valid = new HorizVertValidator(board);

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
     * Test vertical movement with no obstructions.
     */
    @Test
    @DisplayName("Vertical Movement - No obstructions")
    public void testValidateMove1() {
        Position from  = new Position(Rank.R4, File.A, null);
        Position to    = new Position(Rank.R1, File.A, null);
        Position from2 = new Position(Rank.R1, File.A, null);
        Position to2   = new Position(Rank.R4, File.A, null);
        assertAll("A4 -> A1, A1 -> A4", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test vertical movement with the case where
     * the positions from and to are the same.
     */
    @Test
    @DisplayName("Same Boundry Case")
    public void testValidateMove2() {
        Position from = new Position(Rank.R2, File.A, null);
        Position to   = new Position(Rank.R2, File.A, null);
        assertFalse(valid.validateMove(from, to));
    }

    /**
     * Test vertical movement with the case where the positions from and to are
     * one space apart.
     */
    @Test
    @DisplayName("Vertical Movement - Closest Boundry Case")
    public void testValidateMove3() {
        Position from  = new Position(Rank.R2, File.C, null);
        Position to    = new Position(Rank.R3, File.C, null);
        Position from2 = new Position(Rank.R3, File.C, null);
        Position to2   = new Position(Rank.R2, File.C, null);
        assertAll("C2 -> C3, C3 -> C2", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test vertical movement with the case where the positions from and to
     * are the furthest distance apart.
     */
    @Test
    @DisplayName("Vertical Movement - Furthest Boundry Case")
    public void testValidateMove4() {
        Position from  = new Position(Rank.R8, File.A, null);
        Position to    = new Position(Rank.R1, File.A, null);
        Position from2 = new Position(Rank.R1, File.A, null);
        Position to2   = new Position(Rank.R8, File.A, null);
        assertAll("A8 -> A1, A1 -> A8", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test vertical movement with the case where a piece is obstructing the path.
     */
    @Test
    @DisplayName("Vertical Movement - Obstructed Case")
    public void testValidateMove5() {
        Position from  = new Position(Rank.R8, File.F, null);
        Position to    = new Position(Rank.R1, File.F, null);
        Position from2 = new Position(Rank.R1, File.F, null);
        Position to2   = new Position(Rank.R8, File.F, null);
        assertAll("F8 -> F1, F1 -> F8", 
            () -> assertFalse(valid.validateMove(from, to)),
            () -> assertFalse(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test Horizontal movement with no obstructions.
     */
    @Test
    @DisplayName("Horizontal Movement - No obstructions")
    public void testValidateMove6() {
        Position from  = new Position(Rank.R2, File.A, null);
        Position to    = new Position(Rank.R2, File.D, null);
        Position from2 = new Position(Rank.R2, File.D, null);
        Position to2   = new Position(Rank.R2, File.A, null);
        assertAll("A2 -> D2, D2 -> A2", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test Horizontal movement with the case where the positions from and to are one
     * space apart.
     */
    @Test
    @DisplayName("Horizontal Movement - Closest Boundry Case")
    public void testValidateMove7() {
        Position from  = new Position(Rank.R7, File.A, null);
        Position to    = new Position(Rank.R7, File.B, null);
        Position from2 = new Position(Rank.R7, File.B, null);
        Position to2   = new Position(Rank.R7, File.A, null);
        assertAll("A7 -> B7, B7 -> A7", 
            () -> assertTrue(valid.validateMove(from, to)),
            () -> assertTrue(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test horizontal movement with the case where the positions from and to are the
     * furthest distance apart.
     */
    @Test
    @DisplayName("Horionztal Movement - Furthest Boundry Case")
    public void testValidateMove8() {
        Position from = new Position(Rank.R7, File.A, null);
        Position to   = new Position(Rank.R7, File.H, null);
        assertTrue(valid.validateMove(from, to));
    }

    /**
     * Test horizontal movement with the case where a piece is obstructing the path.
     */
    @Test
    @DisplayName("Horizontal Movement - Obstructed Case")
    public void testValidateMove9() {
        Position from  = new Position(Rank.R1, File.H, null);
        Position to    = new Position(Rank.R1, File.A, null);
        Position from2 = new Position(Rank.R1, File.A, null);
        Position to2   = new Position(Rank.R1, File.H, null);
        assertAll("H1 -> A1, A1 -> H1", 
            () -> assertFalse(valid.validateMove(from, to)),
            () -> assertFalse(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test to ensure that diagonal movement is not validated.
     */
    @Test
    @DisplayName("Diagonal Movement Case")
    public void testValidateMove10() {
        Position from  = new Position(Rank.R1, File.A, null);
        Position to    = new Position(Rank.R7, File.H, null);
        Position from2 = new Position(Rank.R2, File.B, null);
        Position to2   = new Position(Rank.R1, File.A, null);
        assertAll("A1 -> H7, B2 -> A1", 
            () -> assertFalse(valid.validateMove(from, to)),
            () -> assertFalse(valid.validateMove(from2, to2))
        );
    }

    /**
     * Test to ensure that a piece can be placed onto an opponent piece.
     */
    @Test
    @DisplayName("Moving onto an Opponent piece")
    public void testValidateMove11() {
        Position from = new Position(Rank.R8, File.F, null);
        Position to   = new Position(Rank.R6, File.F, null);
        assertTrue(valid.validateMove(from, to));
    }

    /**
     * Test to ensure that a piece cannot be placed onto a piece of the same color.
     */
    @Test
    @DisplayName("Moving onto an ally piece")
    public void testValidateMove12() {
        Position from = new Position(Rank.R8, File.B, null);
        Position to   = new Position(Rank.R8, File.F, null);
        assertFalse(valid.validateMove(from, to));
    }

    /**
     * Test to ensure that a piece cannot be placed onto a King piece.
     */
    @Test
    @DisplayName("Moving onto a King piece")
    public void testValidateMove13() {
        Position from  = new Position(Rank.R8, File.B, null);
        Position to    = new Position(Rank.R8, File.D, null);
        Position from2 = new Position(Rank.R8, File.F, null);
        assertAll("B8 -> D8, F8 -> D8",
            () -> assertFalse(valid.validateMove(from, to)),
            () -> assertFalse(valid.validateMove(from2, to)));
    }

    /**
     * Test to ensure that all possible moves horizontally and vertically
     * for a particular piece are returned by the method ShowMoves.
     * Note: I could not simply use AssertArrayEquals because the order matters
     *       and it is a deep equal, which means checking the square object too.
     *       I wanted to keep it more simple.
     */
    @Test
    @DisplayName("What moves are available for a piece.")
    public void testShowMoves1() {
        Position pos = new Position(Rank.R8, File.D, 
                new Square(new Piece(ChessPieceType.King, GameColor.Black)));

        Position[] actual = valid.showMoves(pos);
        Position[] expected = {new Position(Rank.R7, File.D, null),
                               new Position(Rank.R6, File.D, null),
                               new Position(Rank.R5, File.D, null),
                               new Position(Rank.R4, File.D, null),
                               new Position(Rank.R3, File.D, null),
                               new Position(Rank.R2, File.D, null),
                               new Position(Rank.R8, File.B, null),
                               new Position(Rank.R8, File.C, null),
                               new Position(Rank.R8, File.E, null),
                               new Position(Rank.R8, File.F, null)};
        // Check to ensure that the size of both arrays are the same.
        if (expected.length != actual.length) { assertFalse(true); }

        // Check if all positions in expected array are in actual array.
        int actualCount = 0;
        for (Position i : expected) {
            for (Position j : actual) {
                if (i.getRank() == j.getRank() && i.getFile() == j.getFile()) {
                    actualCount++;
                }
            }
        }

        if (expected.length == actualCount) {
            assertTrue(true);
        } else {
            assertFalse(true);
        }
    }

    /**
     * Test to ensure that all possible moves horizontally and vertically
     * for a particular piece are returned by the method ShowMoves.
     * Note: I could not simply use AssertArrayEquals because the order matters
     *       and it is a deep equal, which means checking the square object too.
     *       I wanted to keep it more simple.
     */
    @Test
    @DisplayName("What moves are available for a piece.")
    public void testShowMoves2() {
        Position pos = new Position(Rank.R3, File.G, 
                new Square(new Piece(ChessPieceType.Pawn, GameColor.Black)));

        Position[] actual = valid.showMoves(pos);
        Position[] expected = {new Position(Rank.R1, File.G, null),
                               new Position(Rank.R2, File.G, null),
                               new Position(Rank.R3, File.A, null),
                               new Position(Rank.R3, File.B, null),
                               new Position(Rank.R3, File.C, null),
                               new Position(Rank.R3, File.D, null),
                               new Position(Rank.R3, File.E, null),
                               new Position(Rank.R3, File.F, null),
                               new Position(Rank.R3, File.H, null),
                               new Position(Rank.R4, File.G, null),
                               new Position(Rank.R5, File.G, null),
                               new Position(Rank.R6, File.G, null),
                               new Position(Rank.R7, File.G, null),
                               new Position(Rank.R8, File.G, null)};        
        // Check to ensure that the size of both arrays are the same.
        if (expected.length != actual.length) { assertFalse(true, "Size does not match"); }

        // Check if all positions in expected array are in actual array.
        int actualCount = 0;
        for (Position i : expected) {
            for (Position j : actual) {
                if (i.getRank() == j.getRank() && i.getFile() == j.getFile()) {
                    actualCount++;
                }
            }
        }

        if (expected.length == actualCount) {
            assertTrue(true);
        } else {
            assertFalse(true, "Contents do not match.");
        }
    }

    /**
     * Test to ensure that all possible moves horizontally and vertically
     * for a particular piece are returned by the method ShowMoves.
     * Note: I could not simply use AssertArrayEquals because the order matters
     *       and it is a deep equal, which means checking the square object too.
     *       I wanted to keep it more simple.
     */
    @Test
    @DisplayName("What moves are available for a piece.")
    public void testShowMoves3() {
        Position pos = new Position(Rank.R8, File.F, 
                new Square(new Piece(ChessPieceType.Queen, GameColor.White)));

        Position[] actual = valid.showMoves(pos);
        Position[] expected = {new Position(Rank.R7, File.F, null),
                               new Position(Rank.R6, File.F, null),
                               new Position(Rank.R8, File.H, null),
                               new Position(Rank.R8, File.G, null),
                               new Position(Rank.R8, File.E, null)};
        // Check to ensure that the size of both arrays are the same.
        if (expected.length != actual.length) { assertFalse(true, "Size does not match"); }

        // Check if all positions in expected array are in actual array.
        int actualCount = 0;
        for (Position i : expected) {
            for (Position j : actual) {
                if (i.getRank() == j.getRank() && i.getFile() == j.getFile()) {
                    actualCount++;
                }
            }
        }

        if (expected.length == actualCount) {
            assertTrue(true);
        } else {
            assertFalse(true, "Contents do not match.");
        }
    }
    
    /**
     * Test to ensure that all possible moves horizontally and vertically
     * for a particular piece are returned by the method ShowMoves.
     * Note: I could not simply use AssertArrayEquals because the order matters
     *       and it is a deep equal, which means checking the square object too.
     *       I wanted to keep it more simple.
     */
    @Test
    @DisplayName("What moves are available for a piece.")
    public void testShowMoves4() {
        Position pos = new Position(Rank.R1, File.D, 
                new Square(new Piece(ChessPieceType.Rook, GameColor.Black)));

        Position[] actual = valid.showMoves(pos);
        Position[] expected = {new Position(Rank.R1, File.A, null),
                               new Position(Rank.R1, File.B, null),
                               new Position(Rank.R1, File.C, null),
                               new Position(Rank.R1, File.E, null),
                               new Position(Rank.R1, File.F, null),
                               new Position(Rank.R1, File.G, null),
                               new Position(Rank.R1, File.H, null),
                               new Position(Rank.R2, File.D, null),
                               new Position(Rank.R3, File.D, null),
                               new Position(Rank.R4, File.D, null),
                               new Position(Rank.R5, File.D, null),
                               new Position(Rank.R6, File.D, null),
                               new Position(Rank.R7, File.D, null)};
        // Check to ensure that the size of both arrays are the same.
        if (expected.length != actual.length) { assertFalse(true, "Size does not match"); }

        // Check if all positions in expected array are in actual array.
        int actualCount = 0;
        for (Position i : expected) {
            for (Position j : actual) {
                if (i.getRank() == j.getRank() && i.getFile() == j.getFile()) {
                    actualCount++;
                }
            }
        }

        if (expected.length == actualCount) {
            assertTrue(true);
        } else {
            assertFalse(true, "Contents do not match.");
        }
    }

    /**
     * Test to ensure that all possible moves horizontally and vertically
     * for a particular piece are returned by the method ShowMoves.
     * Note: I could not simply use AssertArrayEquals because the order matters
     *       and it is a deep equal, which means checking the square object too.
     *       I wanted to keep it more simple.
     */
    @Test
    @DisplayName("What moves are available for a piece.")
    public void testShowMoves5() {
        Position pos = new Position(Rank.R5, File.H, 
                new Square(new Piece(ChessPieceType.Bishop, GameColor.White)));

        Position[] actual = valid.showMoves(pos);
        Position[] expected = {new Position(Rank.R5, File.A, null),
                               new Position(Rank.R5, File.B, null),
                               new Position(Rank.R5, File.C, null),
                               new Position(Rank.R5, File.D, null),
                               new Position(Rank.R5, File.E, null),
                               new Position(Rank.R5, File.F, null),
                               new Position(Rank.R5, File.G, null),
                               new Position(Rank.R6, File.H, null),
                               new Position(Rank.R7, File.H, null),
                               new Position(Rank.R8, File.H, null),
                               new Position(Rank.R1, File.H, null),
                               new Position(Rank.R2, File.H, null),
                               new Position(Rank.R3, File.H, null),
                               new Position(Rank.R4, File.H, null)};
        // Check to ensure that the size of both arrays are the same.
        if (expected.length != actual.length) { assertFalse(true, "Size does not match"); }

        // Check if all positions in expected array are in actual array.
        int actualCount = 0;
        for (Position i : expected) {
            for (Position j : actual) {
                if (i.getRank() == j.getRank() && i.getFile() == j.getFile()) {
                    actualCount++;
                }
            }
        }

        if (expected.length == actualCount) {
            assertTrue(true);
        } else {
            assertFalse(true, "Contents do not match.");
        }
    }

    @AfterAll
    public static void AfterAll() {
        board = null;
        valid = null;
    }
}