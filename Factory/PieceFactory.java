package Factory;

import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Piece;
import Validator.*;


/**
 * Factory class that builds chess pieces
 * @author Jeriah Caplinger (100%)
 * @versioin 5/3/2019
 */
public class PieceFactory {

    /**
     * Constructor for piece factory
     */
    public PieceFactory(){
    }

    /**
     * Sets a piece on the given board and builds a specific piece
     * @param color the color of the chess piece
     * @param type the type of the chess piece
     * @param board the board to set it on
     * @param rankIndex the rank index on the chess board
     * @param fileIndex the file index on the chess board
     */
    public void setPieceIF(GameColor color, ChessPieceType type, BoardIF board, int rankIndex, int fileIndex){
        switch (type) {
            case ROOK: {
                PieceIF piece = new Piece(type, color);
                piece = new HorizVertValidator(board, piece);
                board.getSquares()[rankIndex][fileIndex].setPiece(piece);
            }
            break;
            case KNIGHT: {
                PieceIF piece = new Piece(type, color);
                piece = new KnightValidator(board, piece);
                board.getSquares()[rankIndex][fileIndex].setPiece(piece);
            }
            break;
            case BISHOP: {
                PieceIF piece = new Piece(type, color);
                piece = new DiagonalValidator(board, piece);
                board.getSquares()[rankIndex][fileIndex].setPiece(piece);
            }
            break;
            case KING: {
                PieceIF piece = new Piece(type, color);
                piece = new KingValidator(board, piece);
                board.getSquares()[rankIndex][fileIndex].setPiece(piece);
            }
            break;
            case QUEEN: {
                PieceIF piece = new Piece(type, color);
                piece = new HorizVertValidator(board, piece);
                piece = new DiagonalValidator(board, piece);
                board.getSquares()[rankIndex][fileIndex].setPiece(piece);
            }
            break;
            case PAWN: {
                PieceIF piece = new Piece(type, color);
                piece = new PawnValidator(board, piece);
                board.getSquares()[rankIndex][fileIndex].setPiece(piece);
            }
            break;
        }
    }
}
