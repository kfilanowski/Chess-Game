package Validator;

import Model.Position;
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
		int fromFile = from.getFileIndex(), fromRank = from.getRankIndex();
        int toFile   = to.getFileIndex(),   toRank   = to.getRankIndex();

        // Check to see if the movement is not diagonal.
        if (Math.abs(fromFile - toFile) != Math.abs(fromRank - toRank))
            return false;
        
        // Check to see if the positions are the same.
        if (fromFile == toFile && fromRank == toRank) return false;

        // Ensure the final spot is not an ally or an opposing team's King.
        SquareIF[][] squares = board.getSquares();
        PieceIF fromPiece = squares[fromRank][fromFile].getPiece();
        PieceIF toPiece   = squares[toRank][toFile].getPiece();
        if (checkMoveOnAllyOrKing(fromPiece, toPiece)) return false;

        // Ensure that the Piece is not moving past other units.
        int i = fromRank, j = fromFile;
        while (i != toRank && j != toFile) {
            i = (i < toRank) ? i + 1 : i - 1;
            j = (j < toFile) ? j + 1 : j - 1;
            if (i != toRank && j != toFile)
                if (squares[i][j].getPiece() != null) 
                    return false;
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
		return null;
    }
}