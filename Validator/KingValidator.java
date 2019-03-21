package Validator;

import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Piece;
import Model.Position;
import Model.Board;
import UI_CLI.Board_Mono_CLI;

import java.util.ArrayList;

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
     * 
     * @param board - The current state of the board.
     */
    public KingValidator(BoardIF board, PieceIF p) {
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

        if(Math.abs(toRank - fromRank) > 1 || Math.abs(toFile - fromFile) > 1){
            return false;
        }

        // Check to see if the positions are the same
        if (fromFile == toFile && fromRank == toRank) { return false; }


        SquareIF[][] boardSquares = board.getSquares();
        PieceIF fromPiece = boardSquares[fromRank][fromFile].getPiece();
        PieceIF toPiece = boardSquares[toRank][toFile].getPiece();

        if (checkMoveOnAlly(fromPiece, toPiece) || checkIfKing(toPiece)) {
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
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = pos.getSquare().getPiece();
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
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex + 1][fileIndex]));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex][fileIndex + 1]));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex][fileIndex - 1]));
        }

        //checks the top right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex + 1][fileIndex + 1]));
        }

        //checks the top left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex + 1][fileIndex - 1]));
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
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex - 1][fileIndex]));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex][fileIndex + 1]));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex][fileIndex - 1]));
        }

        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex - 1][fileIndex - 1]));
        }

        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex - 1][fileIndex + 1]));
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
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex + 1][fileIndex]));
        }

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex - 1][fileIndex]));
        }

        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex + 1][fileIndex + 1]));
        }

        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex - 1][fileIndex + 1]));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex][fileIndex + 1]));
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
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex + 1][fileIndex]));
        }

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex - 1][fileIndex]));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex][fileIndex - 1]));
        }

        //checks the bottom left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex - 1][fileIndex - 1]));
        }

        //checks the top left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex + 1][fileIndex - 1]));
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
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex + 1][fileIndex]));
        }

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex - 1][fileIndex]));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex][fileIndex + 1]));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex][fileIndex - 1]));
        }

        //checks the top right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex + 1][fileIndex + 1]));
        }

        //checks the bottom left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex - 1][fileIndex - 1]));
        }


        //checks the top left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex + 1][fileIndex - 1]));
        }

        //checks the bottom right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex - 1][fileIndex + 1]));
        }

        return positions;
    }

    /**
     * Gets the moves for the king if its in the middle of the board
     * @param pos - current position of the king
     * @return - an arrayList of valid positions that the king can move to
     */
    private ArrayList<Position> checkBottomLeft(Position pos){
        ArrayList<Position> positions = new ArrayList<>();
        SquareIF[][] boardSquares = board.getSquares();
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex + 1][fileIndex]));
        }

        //checks the top right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex + 1][fileIndex + 1]));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex][fileIndex + 1]));
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
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex - 1][fileIndex]));
        }

        //checks the spot to the right of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex][fileIndex + 1]));
        }

        //checks the bottom right diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex + 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex + 1), boardSquares[rankIndex - 1][fileIndex + 1]));
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
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot above the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex + 1][fileIndex]));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex][fileIndex - 1]));
        }

        //checks the top left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex + 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex + 1),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex + 1][fileIndex - 1]));
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
        PieceIF piece = pos.getSquare().getPiece();
        int fileIndex = pos.getFile().getIndex();
        int rankIndex = pos.getRank().getIndex();

        //checks the spot below the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex), boardSquares[rankIndex - 1][fileIndex]));
        }

        //checks the spot to the left of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex][fileIndex - 1]));
        }

        //checks the bottom left diagonal of the king for an ally
        if (!checkMoveOnAlly(piece, boardSquares[rankIndex - 1][fileIndex - 1].getPiece())){
            positions.add(new Position(Rank.getRankFromIndex(rankIndex - 1),
                    File.getFileFromIndex(fileIndex - 1), boardSquares[rankIndex - 1][fileIndex - 1]));
        }

        return positions;


	}

    //For sprint 2
	private boolean castleValidation(){
	    return true;
    }
}