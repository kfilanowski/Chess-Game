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
 * @author Kevin Filanowski 100%
 * @version March 20, 2019
 */
public class DiagonalValidator extends PieceValidator {
    /**
     * Constructor for DiagonalValidator.
     * 
     * @param board - The current state of the board.
     * @param p - The PieceIF decorator.
     */
    public DiagonalValidator(BoardIF board, PieceIF p) {
        this.p = p;
        this.board = board;
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

        // Allows for unwrapping, this is the reuslt of validateMove.
        boolean result = true;

        // Check to see if the movement is not diagonal.
        if (Math.abs(fromFile - toFile) != Math.abs(fromRank - toRank))
            result = false;
        
        // Check to see if the positions are the same.
        if (fromFile == toFile && fromRank == toRank) { result = false; }

        // Ensure the final spot is not an ally or an opposing team's King.
        SquareIF[][] squares = board.getSquares();
        PieceIF fromPiece = squares[fromRank][fromFile].getPiece();
        PieceIF toPiece   = squares[toRank][toFile].getPiece();
        if (checkMoveOnAlly(fromPiece, toPiece) || checkIfKing(toPiece)) {
            result = false;
        }

        // Ensure that the Piece is not moving past other units.
        int i = fromRank, j = fromFile;
        while (i != toRank && j != toFile) {
            i = (i < toRank) ? i + 1 : i - 1;
            j = (j < toFile) ? j + 1 : j - 1;
            if (i != toRank && j != toFile) {
                if (squares[i][j].getPiece() != null) { result = false; }
            }
        }
        return p.validateMove(from, to) || result;
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
        Position newPosition;

        // For readability and brevity.
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();
        PieceIF piece = board.getSquare(pos).getPiece();
        int size = squares.length - 1;
        
        // Check squares diagonally - positive slope up - from this position.
        int i = rankIndex;
        int j = fileIndex;
        while (i > 0 &&  j < size && squares[--i][++j].getPiece() == null) {
            posArr.add(new Position(Rank.getRankFromIndex(i),
                                    File.getFileFromIndex(j)));
        }
        // Check the last piece.
        if (i >= 0 && j <= size) {
            if (!checkMoveOnAlly(piece, squares[i][j].getPiece())
                    && !checkIfKing(squares[i][j].getPiece())) {
                newPosition = new Position(Rank.getRankFromIndex(i),
                                        File.getFileFromIndex(j));
                if (!posArr.contains(newPosition)) {
                    posArr.add(newPosition);
                }
            }
        }

        // Check squares diagonally - negative slope up - from this position.
        i = rankIndex;
        j = fileIndex;
        while (i > 0 && j > 0 && squares[--i][--j].getPiece() == null) {
            posArr.add(new Position(Rank.getRankFromIndex(i),
                                    File.getFileFromIndex(j)));
        }
        // Check the last piece.
        if (i >= 0 && j >= 0) {
            if (!checkMoveOnAlly(piece, squares[i][j].getPiece())
                    && !checkIfKing(squares[i][j].getPiece())) {
                newPosition = new Position(Rank.getRankFromIndex(i),
                                        File.getFileFromIndex(j));
                if (!posArr.contains(newPosition)) {
                    posArr.add(newPosition);
                }
            }
        }

        // Check squares diagonally - positive slope down - from this position.
        i = rankIndex;
        j = fileIndex;
        while (i < size && j > 0 && squares[++i][--j].getPiece() == null) {
            posArr.add(new Position(Rank.getRankFromIndex(i),
                                    File.getFileFromIndex(j)));
        }
        // Check the last piece.
        if (i <= size && j >= 0) {
            if (!checkMoveOnAlly(piece, squares[i][j].getPiece())
                    && !checkIfKing(squares[i][j].getPiece())) {
                newPosition = new Position(Rank.getRankFromIndex(i),
                                        File.getFileFromIndex(j));
                if (!posArr.contains(newPosition)) {
                    posArr.add(newPosition);
                }
            }
        }

        // Check squares diagonally - negative slope down - from this position.
        i = rankIndex;
        j = fileIndex;
        while (i < size && j < size && squares[++i][++j].getPiece() == null) {
            posArr.add(new Position(Rank.getRankFromIndex(i),
                                    File.getFileFromIndex(j)));
        }
        // Check the last piece.
        if (i <= size && j <= size) {
            if (!checkMoveOnAlly(piece, squares[i][j].getPiece())
                    && !checkIfKing(squares[i][j].getPiece())) {
                newPosition = new Position(Rank.getRankFromIndex(i),
                                        File.getFileFromIndex(j));
                if (!posArr.contains(newPosition)) {
                    posArr.add(newPosition);
                }
            }
        }

        // Combine the decorated moves.
        Position[] first = p.showMoves(pos);
        Position[] second = posArr.toArray(new Position[posArr.size()]);
        Position[] both = new Position[first.length + second.length];
        // Copy the inner wrapped positions first.
        for (int k = 0; k < first.length; k++) {
            both[k] = first[k];
        }
        // Copy the new moves from this method.
        for (int k = first.length; k < both.length; k++) {
            both[k] = second[k - first.length];
        }
        return both;
    }

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    @Override
    public PieceValidator clone() {
        PieceIF newPiece = p.clone();
        return new DiagonalValidator(board, newPiece);
    }

    /**
     * Compares an object with this Validator object.
     * 
     * @param obj - An object to compare with this Validator object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof DiagonalValidator) {
            DiagonalValidator v = (DiagonalValidator) obj;
            return v.p.equals(p);
        }
        return false;
    }
}