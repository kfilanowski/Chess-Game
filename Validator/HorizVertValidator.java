package Validator;

import Enums.GameColor;
import Model.Position;
import java.util.ArrayList;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Enums.File;
import Enums.Rank;
/**
 * Models the piece's ability to move horizontally and vertically.
 * 
 * @author Kevin Filanowski
 * @version March 10, 2019
 */
public class HorizVertValidator extends PieceValidator {
    PieceIF p;

//    /**
//     * Constructor for HorizVertValidator.
//     *
//     * @param board - The current state of the board.
//     */
//    public HorizVertValidator(BoardIF board, PieceIF valid_piece) {
//        super(board, valid_piece);
//
//    }

    public HorizVertValidator(BoardIF board, PieceIF p) {
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

        // Check to see if the movement is not horizontal or vertical.
        if (fromFile != toFile && fromRank != toRank) { return false; }

        // Check to see if the positions are the same
        if (fromFile == toFile && fromRank == toRank) { return false; }

        // Ensure that the Piece is not moving past other units
        SquareIF[][] squares = board.getSquares();
        int min, max;
        if (fromFile == toFile) {
            min = Math.min(fromRank, toRank) + 1;
            max = Math.max(fromRank, toRank) - 1;
            for (int i = min; i < max; i++) {
                if (squares[i][fromFile].getPiece() != null) { return false; }
            }
        } else {
            min = Math.min(fromFile, toFile) + 1;
            max = Math.max(fromFile, toFile) - 1;
            for (int i = min; i < max; i++) {
                if (squares[fromRank][i].getPiece() != null) { return false; }
            }
        }
        
        // Ensure the final spot is not an ally or an opposing team's King.
        PieceIF fromPiece = squares[fromRank][fromFile].getPiece();
        PieceIF toPiece = squares[toRank][toFile].getPiece();
        if (checkMoveOnAlly(fromPiece, toPiece) || checkIfKing(toPiece)) {
            return false; 
        }
		return true;
	}

    /**
     * Returns an array of all possible positions that the piece can legally
     * move to, including capturing opponents.
     * 
     * @param pos - The current position of the piece.
     * @return    - An array of Position objects, each position being a space on
     *              the board that the piece can legally move to.
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
        int size      = squares.length - 1;

        // Check squares up from this position.
        int i = rankIndex;
        while (i > 0 && squares[--i][fileIndex].getPiece() == null) {
            posArr.add(new Position(Rank.getRankFromIndex(i),
                    pos.getFile(), squares[i][fileIndex]));
        }
        // Check the last piece.
        if (i > 0) {
            if (!checkMoveOnAlly(piece, squares[i][fileIndex].getPiece())
                    && !checkIfKing(squares[i][fileIndex].getPiece())) {
                posArr.add(new Position(Rank.getRankFromIndex(i),
                        pos.getFile(), squares[i][fileIndex]));
            }
        }

        // Check squares right of this position.
        i = fileIndex;
        while (i < size && squares[rankIndex][++i].getPiece() == null) {
            posArr.add(new Position(pos.getRank(), File.getFileFromIndex(i),
                    squares[rankIndex][i]));
        }
        // Check the last piece.
        if (i < size) {
            if (!checkMoveOnAlly(piece, squares[rankIndex][i].getPiece())
                    && !checkIfKing(squares[rankIndex][i].getPiece())) {
                posArr.add(new Position(pos.getRank(), File.getFileFromIndex(i),
                        squares[rankIndex][i]));
            }
        }

        // Check squares down from this position.
        i = rankIndex;
        while (i < size && squares[++i][fileIndex].getPiece() == null) {
            posArr.add(new Position(Rank.getRankFromIndex(i),
                    pos.getFile(), squares[i][fileIndex]));
        }
        // Check the last piece.
        if (i < size) {
            if (!checkMoveOnAlly(piece, squares[i][fileIndex].getPiece())
                    && !checkIfKing(squares[i][fileIndex].getPiece())) {
                posArr.add(new Position(Rank.getRankFromIndex(i),
                        pos.getFile(), squares[i][fileIndex]));
            }
        }

        // Check squares left of this position.
        i = fileIndex;
        while (i > 0 && squares[rankIndex][--i].getPiece() == null) {
            posArr.add(new Position(pos.getRank(), File.getFileFromIndex(i),
                    squares[rankIndex][i]));
        }
        // Check the last piece.
        if (i > 0) {
            if (!checkMoveOnAlly(piece, squares[rankIndex][i].getPiece())
                    && !checkIfKing(squares[rankIndex][i].getPiece())) {
                posArr.add(new Position(pos.getRank(),
                        File.getFileFromIndex(i), squares[rankIndex][i]));
            }
        }

        Position[] first = p.showMoves(pos);
        Position[] second = posArr.toArray(new Position[posArr.size()]);
        Position[] both = new Position[first.length + second.length];

        for (int j = 0; j < first.length; j++) {
            both[j] = first[j];
        }
        for (int j = first.length; j < both.length; j++) {
            both[j] = second[j - first.length];
        }

        // Convert to Position[] array and return.
        return both;
    }
}