package Validator;

import Model.Position;

import java.util.ArrayList;

import Enums.Rank;
import Enums.File;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;

/**
 * Models the piece's ability to move diagonally.
 * 
 * @author Kevin Filanowski
 * @version March 14, 2019
 */
public class DiagonalValidator extends PieceValidator {

    /**
     * Constructor for DiagonalValidator.
     * 
     * @param board - The current state of the board.
     */
    public DiagonalValidator(BoardIF board) {
        super(board);
    }

    /**
     * Checks to see if the move to be attempted is a valid move by the 
     * standards of Chess for this particular movement type.
     * 
     * @param from - The position the piece currently has before movement.
     * @param to   - The position the piece is being asked to move to.
     * @return - True if the piece movement is valid, otherwise returns false.
     */
	@Override
	public boolean validateMove(Position from, Position to) {
        // For readability and brevity.
        int fromFile = from.getFile().getIndex();
        int fromRank = from.getRank().getIndex();
        int toFile = to.getFile().getIndex();
        int toRank = to.getRank().getIndex();

        // Check to see if the movement is not diagonal.
        if (Math.abs(fromFile - toFile) != Math.abs(fromRank - toRank))
            return false;
        
        // Check to see if the positions are the same.
        if (fromFile == toFile && fromRank == toRank) { return false; }

        // Ensure the final spot is not an ally or an opposing team's King.
        SquareIF[][] squares = board.getSquares();
        PieceIF fromPiece = squares[fromRank][fromFile].getPiece();
        PieceIF toPiece   = squares[toRank][toFile].getPiece();
        if (checkMoveOnAlly(fromPiece, toPiece) || checkIfKing(toPiece)) {
            return false;
        }

        // Ensure that the Piece is not moving past other units.
        int i = fromRank, j = fromFile;
        while (i != toRank && j != toFile) {
            i = (i < toRank) ? i + 1 : i - 1;
            j = (j < toFile) ? j + 1 : j - 1;
            if (i != toRank && j != toFile) {
                if (squares[i][j].getPiece() != null) { return false; }
            }
        }
        return true;
	}

    /**
     * Returns an array of all possible positions that the piece can legally
     * move to.
     * 
     * @param pos - The current position of the piece.
     * @return - An array of Position objects, each position being a space on
     *           the board that the piece can legally move to.
     */
	@Override
	public Position[] showMoves(Position pos) {
        // To store all the positions.
        ArrayList<Position> posArr = new ArrayList<>();
        SquareIF[][] squares = board.getSquares();

        // For readability and brevity.
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();
        PieceIF piece = pos.getSquare().getPiece();
        int size = squares.length - 1;

        // Check squares diagonally - positive slope up - from this position.
        int i = rankIndex;
        int j = fileIndex;
        while (i > 0 &&  j < size && squares[--i][++j].getPiece() == null) {
            posArr.add(new Position(Rank.getRankFromIndex(i),
                                File.getFileFromIndex(j), squares[i][j]));
        }
        // Check the last piece.
        if (i > 0 && j < size) {
            if (!checkMoveOnAlly(piece, squares[i][j].getPiece())
                    && !checkIfKing(squares[i][j].getPiece())) {
                posArr.add(new Position(Rank.getRankFromIndex(i),
                                    File.getFileFromIndex(j), squares[i][j]));
            }
        }

        // Check squares diagonally - negative slope up - from this position.
        i = rankIndex;
        j = fileIndex;
        while (i > 0 && j > 0 && squares[--i][--j].getPiece() == null) {
            posArr.add(new Position(pos.getRank(), File.getFileFromIndex(i), squares[rankIndex][i]));
        }
        // Check the last piece.
        if (i < size) {
            if (!checkMoveOnAlly(piece, squares[rankIndex][i].getPiece())
                    && !checkIfKing(squares[rankIndex][i].getPiece())) {
                posArr.add(new Position(pos.getRank(), File.getFileFromIndex(i), squares[rankIndex][i]));
            }
        }

        // Check squares down from this position.
        i = rankIndex;
        while (i < size && squares[++i][fileIndex].getPiece() == null) {
            posArr.add(new Position(Rank.getRankFromIndex(i), pos.getFile(), squares[i][fileIndex]));
        }
        // Check the last piece.
        if (i < size) {
            if (!checkMoveOnAlly(piece, squares[i][fileIndex].getPiece())
                    && !checkIfKing(squares[i][fileIndex].getPiece())) {
                posArr.add(new Position(Rank.getRankFromIndex(i), pos.getFile(), squares[i][fileIndex]));
            }
        }

        // Check squares left of this position.
        i = fileIndex;
        while (i > 0 && squares[rankIndex][--i].getPiece() == null) {
            posArr.add(new Position(pos.getRank(), File.getFileFromIndex(i), squares[rankIndex][i]));
        }
        // Check the last piece.
        if (i > 0) {
            if (!checkMoveOnAlly(piece, squares[rankIndex][i].getPiece())
                    && !checkIfKing(squares[rankIndex][i].getPiece())) {
                posArr.add(new Position(pos.getRank(), File.getFileFromIndex(i), squares[rankIndex][i]));
            }
        }
        // Convert to Position[] array and return.
        return posArr.toArray(new Position[posArr.size()]);
    }
}