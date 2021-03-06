package Validator;


import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Position;
import Model.Board;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Models the piece's ability to move like a King,
 * and perform King-like functions.
 * 
 * @author Matt Lutz 100%
 * @version March 10, 2019
 */
public class KingValidator extends PieceValidator {

    /**
     * Constructor for KingValidator.
     * @param board - The current state of the board.
     */
    public KingValidator(BoardIF board, PieceIF p) {
        this.p = p;
        this.board = board;
    }

    /**
     * Checks to see if the move to be attempted is a valid move by the 
     * standards of Chess for this particular movement type.
     * @param from - The position the piece currently has before movement.
     * @param to   - The position the piece is being asked to move to.
     * @return - True if the piece movement is valid, otherwise returns false.
     */
	@Override
	public boolean validateMove(Position from, Position to) {
        Board board = (Board)this.board;
	    // For readability and brevity.
        int fromFile = from.getFile().getIndex();
        int fromRank = from.getRank().getIndex();
        int toFile = to.getFile().getIndex();
        int toRank = to.getRank().getIndex();

        SquareIF[][] boardSquares = board.getSquares();
        PieceIF fromPiece = boardSquares[fromRank][fromFile].getPiece();
        PieceIF toPiece = boardSquares[toRank][toFile].getPiece();
        ArrayList<Position> validMoves = new ArrayList<>(Arrays.asList(showMoves(from)));


        if((Math.abs(toRank - fromRank) > 1 || Math.abs(toFile - fromFile) > 1) &&
                !castleValidation(fromPiece.getColor(), from, validMoves)){
            return false;
        }else if(castleValidation(fromPiece.getColor(), from, validMoves) && Math.abs(toRank - fromRank) == 1 &&
        validMoves.contains(to)){
            return true;
        }

        // Check to see if the positions are the same
        if (fromFile == toFile && fromRank == toRank) { return false; }





        //checks to see if the position we want to move to is a valid position
        if (!validMoves.contains(to)){
            return false;
        }
        return true;
	}

    /**
     * Returns an array of all possible positions that the piece can legally
     * move to.
     * @param pos - The current position of the piece.
     * @return - An array of Position objects, each position being a space on
     *           the board that the piece can legally move to.
     */
	@Override
	public Position[] showMoves(Position pos) {
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        if(fileIndex == 0 && rankIndex == 0){
            positions = checkBottomLeft(pos);
        }else if(fileIndex == 0 && rankIndex == 7){
            positions = checkTopLeft(pos);
        }else if(fileIndex == 7 && rankIndex == 0){
            positions = checkBottomRight(pos);
        }else if(fileIndex == 7 && rankIndex == 7){
            positions = checkTopRight(pos);
        }else if(rankIndex == 0){
            positions = checkKingBottom(pos);
        }else if(rankIndex == 7) {
            positions = checkKingTop(pos);
        }else if(fileIndex == 0){
            positions = checkKingLeft(pos);
        }else if(fileIndex == 7){
            positions = checkKingRight(pos);
        }else{
            positions = checkKingMiddle(pos);
        }


            Boolean[] valids = checkMoveInCheck(positions, piece.getColor());
            positions = showMovesInCheck(valids, positions);

            //if(piece.getChessPieceType() == ChessPieceType.KING) {
                castleValidation(piece.getColor(), pos, positions);
            //}




        // Convert to Position[] array and return.
        return positions.toArray(new Position[positions.size()]);

    }

    /**
     * Gets the moves for the king if its on the bottom edge of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
    private ArrayList<Position> checkKingBottom(Position pos){
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex].getPostion(), piece.getColor())
                && !checkIfKing(boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        //checks the top right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        //checks the top left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex - 1)));
        }
        return positions;
    }

    /**
     * Gets the moves for the king if its on the top edge of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
    private ArrayList<Position> checkKingTop(Position pos) {
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        return positions;
    }

    /**
     * Gets the moves for the king if its on the left edge of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
    public ArrayList<Position> checkKingLeft(Position pos){
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex)));
        }

        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        return positions;
	}

    /**
     * Gets the moves for the king if its on the right edge of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
	public ArrayList<Position> checkKingRight(Position pos){

        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        //checks the bottom left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        //checks the top left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex - 1)));
        }


        return positions;
	}

    /**
     * Gets the moves for the king if its in the middle of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
	public ArrayList<Position> checkKingMiddle(Position pos){
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        //checks the top right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex + 1].getPostion(), piece.getColor())
         && !checkIfKing(boardSquares[rankIndex + 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        //checks the bottom left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex - 1)));
        }


        //checks the top left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        //checks the bottom right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex + 1].getPiece()) ){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        return positions;
    }

    /**
     * Gets the moves for the king if its on the bottom left corner of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
    private ArrayList<Position> checkBottomLeft(Position pos){
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the top right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        return positions;
    }

    /**
     * Gets the moves for the king if its in the top left corner of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
    private ArrayList<Position> checkTopLeft(Position pos){
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        //checks the bottom right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex + 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex + 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex + 1)));
        }

        return positions;
    }

    /**
     * Gets the moves for the king if its in the bottom right corner of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
    private ArrayList<Position> checkBottomRight(Position pos){

        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        //checks the top left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex - 1].getPiece())  &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex + 1][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex + 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        return positions;
    }

    /**
     * Gets the moves for the king if its in the top right corner of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
    private ArrayList<Position> checkTopRight(Position pos){
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = board.getSquare(pos).getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex)));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        //checks the bottom left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex - 1].getPiece()) &&
                !stillCheckAfterMove(pos, boardSquares[rankIndex - 1][fileIndex - 1].getPostion(), piece.getColor())
        && !checkIfKing(boardSquares[rankIndex - 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex - 1)));
        }

        return positions;
	}

    /**
     * Adds a boolean to a boolean array that tells whether or not we will move into check
     * @param validMoves - Array of valid movement options for the king
     * @param color - Color of the king we want to check
     * @return - A boolean array of values for each
     */
	private Boolean[] checkMoveInCheck(ArrayList<Position> validMoves, GameColor color){
        Boolean[] valids = new Boolean[validMoves.size()];

        for(int i = 0; i < validMoves.size(); i++){
            boolean cantMove = true;
            if(((Board)this.board).checkUpRightDiag(validMoves.get(i).getRank().getIndex(),
                    validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkUpLeftDiag(validMoves.get(i).getRank().getIndex(),
                    validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkDownLeftDiag(validMoves.get(i).getRank().getIndex(),
                    validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkDownRightDiag(validMoves.get(i).getRank().getIndex(),
                    validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkDownVert(validMoves.get(i).getRank().getIndex(),
                    validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkUpVert(validMoves.get(i).getRank().getIndex(),
                    validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkLeftHoriz(validMoves.get(i).getRank().getIndex(),
                    validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkRightHoriz(validMoves.get(i).getRank().getIndex(),
                    validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkPawn(validMoves.get(i).getRank().getIndex(),
                            validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkKnight(validMoves.get(i).getRank().getIndex(),
                            validMoves.get(i).getFile().getIndex(),color) ||
                    ((Board)this.board).checkKing(validMoves.get(i).getRank().getIndex(),
                            validMoves.get(i).getFile().getIndex(),color)) {
                cantMove = false;
                valids[i] = cantMove;
            }else {
                valids[i] = cantMove;
            }

        }
        return valids;
    }

    /**
     * Helper method that updates the showmoves for the king if it can move itself into check
     * @param valids - Array of booleans where each index corresponds to our array of valid moves
     * @param positions - Array of valid movement options for the king
     * @return - An updated array of valid moves if the king can castle
     */
    private ArrayList<Position> showMovesInCheck(Boolean[] valids, ArrayList<Position> positions){
        ArrayList<Position> moves = new ArrayList<>();
        moves.addAll(positions);
        ArrayList<Boolean> validPositions = new ArrayList<>(Arrays.asList(valids));

        for(int i = 0; i < validPositions.size(); i++){
            if(!validPositions.get(i)){
               moves.remove(i);
               validPositions.remove(i);
               i--;
            }
        }
        return moves;
    }


    /**
     * Helper method that calls the checkCastleRight and checkCastleLeft methods
     * @param color - Color of the king we want to check
     * @param kingPos - Position of the king we want to check
     * @param validMoves - Array of valid movement options for the king
     * @return - An updated array of valid moves if the king can castle
     */
	public boolean castleValidation(GameColor color, Position kingPos, ArrayList<Position> validMoves){
	    boolean result = false;

	    if (checkLeftKingCastle(color, kingPos, validMoves) && checkRightKingCastle(color, kingPos, validMoves)){
	        result = true;
        }else if (checkLeftKingCastle(color, kingPos, validMoves) || checkRightKingCastle(color, kingPos, validMoves)){
	        result = true;
        }
        return result;

    }

    /**
     * Helper method that checks the left of the king for castling
     * @param color - Color of the king we want to check
     * @param kingPos - Position of the king we want to check
     * @param validMoves - Array of valid movement options for the king
     * @return - True if the king can castle left, false if not
     */
    public boolean checkLeftKingCastle(GameColor color, Position kingPos, ArrayList<Position> validMoves){
	    int kingRank = kingPos.getRank().getIndex();
	    int kingFile = kingPos.getFile().getIndex();
	    SquareIF[][] squares = board.getSquares();
        PieceValidator king = (PieceValidator) squares[kingRank][kingFile].getPiece();
	    //check to the left of the king
        int i = kingFile - 1;
        while (i >= 0) {
            PieceValidator gottenPiece = (PieceValidator) squares[kingRank][i].getPiece();
            if(gottenPiece != null) {
                if (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK
                      && gottenPiece.getColor() == color && (!king.getPiece().getHasMoved() &&
                        !gottenPiece.getPiece().getHasMoved()) && !((Board)board).checkForCheck(color)) {
                    validMoves.add(new Position(kingPos.getRank(), File.getFileFromIndex(kingFile - 2)));
                    return true;
                }else if(gottenPiece.getPiece().getChessPieceType() != ChessPieceType.ROOK){
                    return false;
                }
            }
            i--;
        }
        return false;
    }

    /**
     * Helper method that checks the right of the king for castling
     * @param color - Color of the king we want to check
     * @param kingPos - Position of the king we want to check
     * @param validMoves - Array of valid movement options for the king
     * @return - True if the king can castle right, false if not
     */
    public boolean checkRightKingCastle(GameColor color, Position kingPos, ArrayList<Position> validMoves){
        int kingRank = kingPos.getRank().getIndex();
        int kingFile = kingPos.getFile().getIndex();
        SquareIF[][] squares = board.getSquares();
        PieceValidator king = (PieceValidator) squares[kingRank][kingFile].getPiece();
        //check to the left of the king
        int i = kingFile + 1;
        while (i <= File.getMaxIndex()) {
            PieceValidator gottenPiece = (PieceValidator) squares[kingRank][i].getPiece();
            if(gottenPiece != null) {
                if (gottenPiece.getPiece().getChessPieceType() == ChessPieceType.ROOK
                        && gottenPiece.getColor() == color && (!king.getPiece().getHasMoved() &&
                        !gottenPiece.getPiece().getHasMoved()) && !((Board)board).checkForCheck(color)) {
                    validMoves.add(new Position(kingPos.getRank(), File.getFileFromIndex(kingFile + 2)));
                    return true;
                }else if(gottenPiece.getPiece().getChessPieceType() != ChessPieceType.ROOK){
                    return false;
                }
            }
            i++;
        }
        return false;
    }



    /**
     * Create a deep clone of this object.
     *
     * @return - A deep clone of this object.
     */
    @Override
    public PieceValidator clone() {
        PieceIF newPiece = p.clone();
        return new KingValidator(board, newPiece);
    }

    /**
     * Compares an object with this Validator object.
     *
     * @param obj - An object to compare with this Validator object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof KingValidator) {
            KingValidator v = (KingValidator) obj;
            return v.p.equals(p);
        }
        return false;
    }


}