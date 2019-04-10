package Validator;

import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import History.History;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Position;
import Interfaces.BoardIF;
import Model.Square;

import java.util.ArrayList;

/**
 * Models the piece's ability to move like a Pawn,
 * and perform Pawn-like functions.
 * 
 * @author Jeriah Caplinger (100%)
 * @version March 18, 2019
 */
public class PawnValidator extends PieceValidator {

    /**
     * Constructor for PawnValidator.
     * 
     * @param board - The current state of the board.
     */
	public PawnValidator(BoardIF board, PieceIF p) {
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
        int fromRank = from.getRank().getIndex();
        int fromFile = from.getFile().getIndex();

        int toRank = to.getRank().getIndex();
        int toFile = to.getFile().getIndex();

        boolean result = false;


        SquareIF[][] squares = board.getSquares();
        PieceIF fromPiece = squares[from.getRank().getIndex()][from.getFile().getIndex()].getPiece();
        int moveCorrectly = this.moveCorrectly(fromPiece.getColor());
        PieceIF toPiece = squares[to.getRank().getIndex()][to.getFile().getIndex()].getPiece();

        // check if we are moving up one space
        if(fromFile == toFile && fromRank + moveCorrectly == toRank &&
                Math.abs(toRank - fromRank) == 1 && toPiece == null){
            result = true;
        }

        // For black, we check if we can move up 2 spaces
        if(!result && fromPiece.getColor() == GameColor.BLACK && fromRank == 1 && toRank == 3
                && toFile == fromFile){
            // check if the two spaces are empty
            if( squares[fromRank + 1][fromFile].getPiece() == null &&
                    squares[fromRank + 2][fromFile].getPiece() == null){
                result = true;
            }
        }

        // For white, we check if we can move up 2 spaces
        if(!result && fromPiece.getColor() == GameColor.WHITE && fromRank == 6 && toRank == 4
                && toFile == fromFile){
            // check if the two spaces are empty
            if(squares[fromRank - 1][fromFile].getPiece() == null &&
                    squares[fromRank - 2][fromFile].getPiece() == null){
                result = true;

            }
        }

        if(!result && fromPiece.getColor() == GameColor.BLACK){
           if(this.enPassanteHelpBlack(fromRank, fromFile, toRank, toFile, squares)){
               result = true;
           }
        }else if(!result && fromPiece.getColor() == GameColor.WHITE){
            if(this.enPassanteHelpWhite(fromRank, fromFile, toRank, toFile, squares)){
                result = true;
            }
        }

        // check if we are taking a piece diagonally
        if(!result && Math.abs(toFile - fromFile) == 1 && fromRank + moveCorrectly == toRank){
            if(toPiece != null){
                result = !checkMoveOnAlly(fromPiece, toPiece);
            }else{
                result = false;
            }
        }

        if(result){
            result = !stillCheckAfterMove(from, to, fromPiece.getColor());
        }

        return result;
	}


    private boolean enPassanteHelpWhite(int fromRank, int fromFile, int toRank, int toFile,
                                        SquareIF[][] squares){
	    GameColor color = GameColor.WHITE;
        boolean result = false;
        // For black, checking EN PASSANTE
        if(fromRank ==  3 && toRank == 2
                && (toFile == fromFile - 1 || toFile == fromFile + 1)){
            if(fromFile - 1 == toFile){
                // get the piece adjacent
                PieceValidator adjacentPiece = (PieceValidator) squares[fromRank][fromFile - 1].getPiece();
                // need to check if its a pawn
                if(adjacentPiece != null && adjacentPiece.getPiece().getChessPieceType() ==
                        ChessPieceType.PAWN && adjacentPiece.getColor()
                        != color){
                    // here we have to do some history manipulating to get the previous board state
                    // in order to check if the move is valid
                    History history = History.getInstance();
                    history.add(board.saveState());
                    board.restoreState(history.undo());
                    board.restoreState(history.undo());
                    // we have to get a new set of squares because we reverted our board
                    squares = board.getSquares();
                    // we get the piece that was supposed to be a pawn
                    PieceValidator rewindPiece = (PieceValidator) squares[fromRank - 2][fromFile - 1].getPiece();
                    // perform various logical statements
                    if(rewindPiece != null && rewindPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN && rewindPiece.getColor()
                            != color && squares[fromRank - 1][fromFile - 1].getPiece() == null &&
                            squares[fromRank][fromFile - 1].getPiece() == null){
                        // if it passes we know that the en passante was legal and we can do everything
                        // accordingly
                        board.restoreState(history.redo());
                        result = true;
                        squares = board.getSquares();
                        squares[fromRank][fromFile - 1].setPiece(null);
                    }
                }
            }else if(fromFile + 1 == toFile){
                // get the piece adjacent
                PieceValidator adjacentPiece = (PieceValidator) squares[fromRank][fromFile + 1].getPiece();
                // need to check if its a pawn
                if(adjacentPiece != null && adjacentPiece.getPiece().getChessPieceType() ==
                        ChessPieceType.PAWN && adjacentPiece.getColor()
                        != color){
                    // here we have to do some history manipulating to get the previous board state
                    // in order to check if the move is valid
                    History history = History.getInstance();
                    history.add(board.saveState());
                    board.restoreState(history.undo());
                    board.restoreState(history.undo());
                    // we have to get a new set of squares because we reverted our board
                    squares = board.getSquares();
                    // we get the piece that was supposed to be a pawn
                    PieceValidator rewindPiece = (PieceValidator) squares[fromRank - 2][fromFile + 1].getPiece();
                    // perform various logical statements
                    if(rewindPiece != null && rewindPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN && rewindPiece.getColor()
                            != color && squares[fromRank - 1][fromFile + 1].getPiece() == null &&
                            squares[fromRank][fromFile + 1].getPiece() == null){
                        // if it passes we know that the en passante was legal and we can do everything
                        // accordingly
                        board.restoreState(history.redo());
                        result = true;
                        squares = board.getSquares();
                        squares[fromRank][fromFile + 1].setPiece(null);
                    }
                }
            }
        }
        return result;
    }



	private boolean enPassanteHelpBlack(int fromRank, int fromFile, int toRank, int toFile,
                                        SquareIF[][] squares){
	    GameColor color = GameColor.BLACK;
	    boolean result = false;
        // For black, checking EN PASSANTE
        if(fromRank ==  4 && toRank == 5
                && (toFile == fromFile - 1 || toFile == fromFile + 1)){
            if(fromFile - 1 == toFile){
                // get the piece adjacent
                PieceValidator adjacentPiece = (PieceValidator) squares[fromRank][fromFile - 1].getPiece();
                // need to check if its a pawn
                if(adjacentPiece != null && adjacentPiece.getPiece().getChessPieceType() ==
                        ChessPieceType.PAWN && adjacentPiece.getColor()
                        != color){
                    // here we have to do some history manipulating to get the previous board state
                    // in order to check if the move is valid
                    History history = History.getInstance();
                    //history.add(board.saveState());
                    board.restoreState(history.undo());
                    //board.restoreState(history.undo());
                    // we have to get a new set of squares because we reverted our board
                    squares = board.getSquares();
                    // we get the piece that was supposed to be a pawn
                    PieceValidator rewindPiece = (PieceValidator) squares[fromRank + 2][fromFile - 1].getPiece();
                    // perform various logical statements
                    if(rewindPiece != null && rewindPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN && rewindPiece.getColor()
                            != color && squares[fromRank + 1][fromFile - 1].getPiece() == null &&
                            squares[fromRank][fromFile - 1].getPiece() == null){
                        // if it passes we know that the en passante was legal and we can do everything
                        // accordingly
                        board.restoreState(history.redo());
                        result = true;
                        squares = board.getSquares();
                        squares[fromRank][fromFile - 1].setPiece(null);
                    }
                }
            }else if(fromFile + 1 == toFile){
                // get the piece adjacent
                PieceValidator adjacentPiece = (PieceValidator) squares[fromRank][fromFile + 1].getPiece();
                // need to check if its a pawn
                if(adjacentPiece != null && adjacentPiece.getPiece().getChessPieceType() ==
                        ChessPieceType.PAWN && adjacentPiece.getColor()
                        != color){
                    // here we have to do some history manipulating to get the previous board state
                    // in order to check if the move is valid
                    History history = History.getInstance();
                    history.add(board.saveState());
                    board.restoreState(history.undo());
                    board.restoreState(history.undo());
                    // we have to get a new set of squares because we reverted our board
                    squares = board.getSquares();
                    // we get the piece that was supposed to be a pawn
                    PieceValidator rewindPiece = (PieceValidator) squares[fromRank + 2][fromFile + 1].getPiece();
                    // perform various logical statements
                    if(rewindPiece != null && rewindPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN && rewindPiece.getColor()
                            != color && squares[fromRank + 1][fromFile + 1].getPiece() == null &&
                            squares[fromRank][fromFile + 1].getPiece() == null){
                        // if it passes we know that the en passante was legal and we can do everything
                        // accordingly
                        board.restoreState(history.redo());
                        result = true;
                        squares = board.getSquares();
                        squares[fromRank][fromFile + 1].setPiece(null);
                    }
                }
            }
        }
        return result;
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
        // tells us whether we need to add 1 to our rank or subtract 1
        final int BLACK_ADD_ONE = 1;
        final int WHITE_ADD_ONE = -1;

        ArrayList<Position> posArr = new ArrayList<>();
        SquareIF[][] squares = board.getSquares();
        PieceIF fromPiece = squares[pos.getRank().getIndex()][pos.getFile().getIndex()].getPiece();

        // we call a helper method to add the positions
        if(fromPiece.getColor() == GameColor.WHITE) {
            addPositions(pos, squares, WHITE_ADD_ONE, posArr);
        }else if(fromPiece.getColor() == GameColor.BLACK){
            addPositions(pos, squares, BLACK_ADD_ONE, posArr);
        }

		return posArr.toArray(new Position[posArr.size()]);
    }



    private void showEnPassanteWhite(int fromRank, int fromFile, SquareIF[][] squares,
                                        ArrayList<Position> posArray) {
        GameColor color = GameColor.WHITE;
        // For black, checking EN PASSANTE
        if (fromRank == 3) {
            // get the piece adjacent
            PieceValidator adjacentPiece = (PieceValidator) squares[fromRank][fromFile - 1].getPiece();
            // need to check if its a pawn
            if (adjacentPiece != null && adjacentPiece.getPiece().getChessPieceType() ==
                    ChessPieceType.PAWN && adjacentPiece.getColor()
                    != color) {
                // here we have to do some history manipulating to get the previous board state
                // in order to check if the move is valid
                History history = History.getInstance();
                history.add(board.saveState());
                board.restoreState(history.undo());
                board.restoreState(history.undo());
                // we have to get a new set of squares because we reverted our board
                squares = board.getSquares();
                // we get the piece that was supposed to be a pawn
                PieceValidator rewindPiece = (PieceValidator) squares[fromRank - 2][fromFile - 1].getPiece();
                // perform various logical statements
                if (rewindPiece != null && rewindPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN && rewindPiece.getColor()
                        != color && squares[fromRank - 1][fromFile - 1].getPiece() == null &&
                        squares[fromRank][fromFile - 1].getPiece() == null) {
                    // if it passes we know that the en passante was legal and we can do everything
                    // accordingly
                    board.restoreState(history.redo());
                    posArray.add(new Position(Rank.getRankFromIndex(fromRank -1), File.getFileFromIndex(fromFile - 1)));
                }

                // get the piece adjacent
                adjacentPiece = (PieceValidator) squares[fromRank][fromFile + 1].getPiece();
                // need to check if its a pawn
                if (adjacentPiece != null && adjacentPiece.getPiece().getChessPieceType() ==
                        ChessPieceType.PAWN && adjacentPiece.getColor()
                        != color) {
                    // here we have to do some history manipulating to get the previous board state
                    // in order to check if the move is valid
                    history.add(board.saveState());
                    board.restoreState(history.undo());
                    board.restoreState(history.undo());
                    // we have to get a new set of squares because we reverted our board
                    squares = board.getSquares();
                    // we get the piece that was supposed to be a pawn
                    rewindPiece = (PieceValidator) squares[fromRank - 2][fromFile + 1].getPiece();
                    // perform various logical statements
                    if (rewindPiece != null && rewindPiece.getPiece().getChessPieceType() == ChessPieceType.PAWN && rewindPiece.getColor()
                            != color && squares[fromRank - 1][fromFile + 1].getPiece() == null &&
                            squares[fromRank][fromFile + 1].getPiece() == null) {
                        // if it passes we know that the en passante was legal and we can do everything
                        // accordingly
                        board.restoreState(history.redo());
                        posArray.add(new Position(Rank.getRankFromIndex(fromRank -1), File.getFileFromIndex(fromFile + 1)));
                    }
                }
            }
        }
    }



    private void showEnPassanteBlack(int fromRank, int fromFile, SquareIF[][] squares, ArrayList<Position> posArray){
        GameColor color = GameColor.BLACK;
        // For black, checking EN PASSANTE
        if(fromRank ==  4){
            // get the piece adjacent
            PieceValidator leftPiece = (PieceValidator) squares[fromRank][fromFile - 1].getPiece();
            PieceValidator rightPiece = (PieceValidator) squares[fromRank][fromFile + 1].getPiece();
            // need to check if its a pawn
            if(leftPiece != null || rightPiece != null && leftPiece.getPiece().getChessPieceType() ==
                    ChessPieceType.PAWN || rightPiece.getPiece().getChessPieceType()== ChessPieceType.PAWN
                    && leftPiece.getColor() != color || rightPiece.getColor() != color){
                // here we have to do some history manipulating to get the previous board state
                // in order to check if the move is valid
                History history = History.getInstance();
                history.add(board.saveState());
                board.restoreState(history.undo());
                board.restoreState(history.undo());
                // we have to get a new set of squares because we reverted our board
                squares = board.getSquares();
                // we get the piece that was supposed to be a pawn
                PieceValidator leftRewindPiece = (PieceValidator) squares[fromRank + 2][fromFile - 1].getPiece();
                PieceValidator rightRewindPiece = (PieceValidator) squares[fromRank + 2][fromFile - 1].getPiece();
                // perform various logical statements
                if(leftRewindPiece != null && leftRewindPiece.getPiece().getChessPieceType() ==
                        ChessPieceType.PAWN && leftRewindPiece.getColor()
                        != color && squares[fromRank + 1][fromFile - 1].getPiece() == null &&
                        squares[fromRank][fromFile - 1].getPiece() == null){
                    // if it passes we know that the en passante was legal and we can do everything
                    // accordingly
                    posArray.add(new Position(Rank.getRankFromIndex(fromRank+1), File.getFileFromIndex(fromFile - 1)));
                }else if(rightRewindPiece != null && rightRewindPiece.getPiece().getChessPieceType() ==
                        ChessPieceType.PAWN && rightRewindPiece.getColor()
                        != color && squares[fromRank + 1][fromFile + 1].getPiece() == null &&
                        squares[fromRank][fromFile + 1].getPiece() == null){
                    posArray.add(new Position(Rank.getRankFromIndex(fromRank+1), File.getFileFromIndex(fromFile + 1)));
                }
                board.restoreState(history.redo());
            }
        }
    }





    /**
     * Ensures we are moving the pawn correctly.
     * @param color the color of the piece
     * @return -1 if it is white or 1 if it is black
     */
    private int moveCorrectly(GameColor color){
	    if(color == GameColor.WHITE){
	        return -1;
        }else{
	        return 1;
        }
    }


    /**
     * Helper method that adds valid positions to the Array List of valid moves.
     * @param pos our current piece's position object
     * @param squares our 2D squares that make up the chess board
     * @param move_pos tells us whether the piece we are moving is WHITE or BLACK
     * @param posArr the array list we are adding valid positions to
     */
    private void addPositions(Position pos, SquareIF[][] squares,
                              int move_pos, ArrayList<Position> posArr){
        int fromRank = pos.getRank().getIndex();
        int fromFile = pos.getFile().getIndex();
        PieceIF fromPiece = squares[fromRank][fromFile].getPiece();

        // check if we can move up one space
        if(checkBounds(fromRank + move_pos) && squares[fromRank + move_pos][fromFile].getPiece() == null
                && !stillCheckAfterMove(pos, new Position(Rank.getRankFromIndex(fromRank + move_pos),
                File.getFileFromIndex(fromFile)), fromPiece.getColor())){
            // add this position to the list
            posArr.add(new Position(Rank.getRankFromIndex(fromRank + move_pos),
                    File.getFileFromIndex(fromFile)));
        }


        // For black, we check if we can move up 2 spaces
        if(fromPiece.getColor() == GameColor.BLACK && fromRank == 1){
            // check if the two spaces are empty
            if( squares[fromRank + move_pos][fromFile].getPiece() == null &&
                    squares[fromRank + move_pos + move_pos][fromFile].getPiece() == null &&
                    !stillCheckAfterMove(pos, new Position(Rank.getRankFromIndex(fromRank + move_pos),
                            File.getFileFromIndex(fromFile)), fromPiece.getColor())){
                posArr.add(new Position(Rank.getRankFromIndex(fromRank+move_pos+move_pos), File.getFileFromIndex(fromFile)));
            }
        }

        // For white, we check if we can move up 2 spaces
        if(fromPiece.getColor() == GameColor.WHITE && fromRank == 6){
            // check if the two spaces are empty
            if(squares[fromRank + move_pos][fromFile].getPiece() == null &&
                    squares[fromRank + move_pos + move_pos][fromFile].getPiece() == null
                    && !stillCheckAfterMove(pos, new Position(Rank.getRankFromIndex(fromRank + move_pos),
                    File.getFileFromIndex(fromFile)), fromPiece.getColor())){
                posArr.add(new Position(Rank.getRankFromIndex(fromRank + move_pos+move_pos), File.getFileFromIndex(fromFile)));
            }
        }



        // check if we can take diagonally
        if(checkBounds(fromRank + move_pos)) {
            // check up and to the right for white, left for black
            if(checkBounds(fromFile + 1)) {
                PieceIF rightPiece = squares[fromRank + move_pos][fromFile + 1].getPiece();
                if(rightPiece != null && !checkMoveOnAlly(fromPiece, rightPiece) &&
                        !stillCheckAfterMove(pos, new Position(Rank.getRankFromIndex(fromRank + move_pos),
                                File.getFileFromIndex(fromFile)), fromPiece.getColor())){
                    // add a valid position to the array list
                    posArr.add(new Position(Rank.getRankFromIndex(fromRank + move_pos), File.getFileFromIndex(fromFile + 1)));
                }
            }
            // check up and to the left for white, right for black
            if(checkBounds(fromFile - 1)){
                PieceIF toPiece = squares[fromRank + move_pos][fromFile - 1].getPiece();
                if(toPiece != null && !checkMoveOnAlly(fromPiece, toPiece) &&
                        !stillCheckAfterMove(pos, new Position(Rank.getRankFromIndex(fromRank + move_pos),
                                File.getFileFromIndex(fromFile)), fromPiece.getColor())){
                    // add a valid position to the array list
                    posArr.add(new Position(Rank.getRankFromIndex(fromRank + move_pos), File.getFileFromIndex(fromFile - 1)));
                }
            }

            //TODO: check for stillCheckAfterMove() with en passante
            //TODO: fix en passante for showmoves, validate moves works.
//            if(fromPiece.getColor() == GameColor.BLACK) {
//                showEnPassanteBlack(fromRank, fromFile, squares, posArr);
//            }else if(fromPiece.getColor() == GameColor.WHITE){
//                showEnPassanteWhite(fromRank, fromFile, squares, posArr);
//            }

        }
    }


    /**
     * Checks if the index is within the valid bounds of the board.
     * @param difference the index we are checking
     * @return true if the index is within the valid bounds
     */
    private boolean checkBounds(int difference){
        final int UPPER_BOUND = 7;
        final int LOWER_BOUND = 0;
        return difference <= UPPER_BOUND && difference >= LOWER_BOUND;
    }

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    @Override
    public PieceValidator clone() {
        PieceIF newPiece = p.clone();
        return new PawnValidator(board, newPiece);
    }

    /**
     * Compares an object with this Validator object.
     * 
     * @param obj - An object to compare with this Validator object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof PawnValidator) {
            PawnValidator v = (PawnValidator) obj;
            return v.p.equals(p);
        }
        return false;
    }
}