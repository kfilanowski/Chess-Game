package Factory;

import Enums.ChessPieceType;
import Enums.GameColor;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Piece;
import Validator.*;

public class PieceFactory {

    public PieceFactory(){
    }

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
