package UI_CLI;

import ChessExceptions.GameOverCheckMateException;
import ChessExceptions.GameOverStaleMateException;
import Enums.ChessPieceType;
import Enums.File;
import Enums.GameColor;
import Enums.Rank;
import History.History;
import Interfaces.BoardIF;
import Interfaces.PieceIF;
import Interfaces.SquareIF;
import Model.Board;
import Model.Position;
import Validator.PieceValidator;
import History.State;
import java.util.ArrayList;


/**
 * Game controller conducts basic game logic for a chess game and certain detections necessary
 * to ensure a proper game of chess is played
 * @author Jeriah Caplinger (33.6%)
 * @author Matt Lutz (33.2%)
 * @author Jacob Ginn (33.2%)
 */
public class GameController {
    /** The boolean for which players turn it is. **/
    boolean playerTurn;
    /** The First Player Name */
    String player1Name;
    /** The Second Player Name */
    String player2Name;
    /** counter for detecting 50-move-rule draw*/
    private int counter;
    /** Upper bound for the 50-move-rule draw */
    private final int fiftyMoveRule = 50;
    /** ArrayList that keeps track of three fold repetition*/
    ArrayList<State<BoardIF>> threeFold;
    /** ArrayList that contains a copy of the history list */
    ArrayList<State<BoardIF>> copyOfHistory;
    /** The white pieces that have been taken */
    ArrayList<PieceIF> WhiteTakenPiece = new ArrayList<PieceIF>();
    /** The Black pieces that have been taken */
    ArrayList<PieceIF> BlackTakenPiece = new ArrayList<PieceIF>();


    /**
     * Constructor for the gamecontroller
     * @param player1Name - First Player name
     * @param player2Name - Second Player name
     */
    public GameController(String player1Name, String player2Name){
        this.playerTurn = true;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.counter = 0;
        this.threeFold = new ArrayList<>();
        this.copyOfHistory = new ArrayList<>();
    }

    /**
     * Gets whose players turn it is
     * @return the player's turn
     */
    public boolean getplayerTurn(){
        return this.playerTurn;
    }

    /**
     * Sets the players turn
     * @param playerTurn boolean value that sets a players turn
     */
    public void setPlayerTurn(boolean playerTurn){
        this.playerTurn = playerTurn;
    }

    /**
     * Method that moves a chess piece and provides all backend detections to ensure
     * a proper game of chess is played
     * @param board the chess board
     * @param from the position to move from
     * @param to the position to move to
     * @throws GameOverCheckMateException if the game is over by CheckMate
     * @throws GameOverStaleMateException if the game is over by StaleMate
     */
    public void move(BoardIF board, Position from, Position to) throws
            GameOverCheckMateException, GameOverStaleMateException {
        ArrayList<PieceIF> piece = new ArrayList<PieceIF>();


        int fromFile = from.getFile().getIndex(); // from square file
        int fromRank = from.getRank().getIndex(); // from square rank

        int toFile = to.getFile().getIndex(); // to square file
        int toRank = to.getRank().getIndex(); // to square rank

        boolean validMove = board.getSquare(from.getRank(), from.getFile()).getPiece().validateMove(from, to);

        boolean whiteTurn = (board.getSquare(from.getRank(), from.getFile()).getPiece().getColor() == GameColor.WHITE && playerTurn);

        boolean blackTurn = (board.getSquare(from.getRank(), from.getFile()).getPiece().getColor() == GameColor.BLACK && !playerTurn);

        // if the move is valid
        if (validMove && (whiteTurn || blackTurn)) {
            History.getInstance().add(board.saveState());
            getTakenPiece(board, to);
            // the following is the implementation for checking for the fifty move rule
            PieceValidator toPiece = (PieceValidator) board.getSquares()[toRank][toFile].getPiece();
            PieceValidator fromPiece = (PieceValidator) board.getSquares()[fromRank][fromFile].getPiece();
            if(fromPiece.getPiece().getChessPieceType() != ChessPieceType.PAWN && toPiece == null ){
                this.counter++;
                // if counter == 50 then it is a draw
                if(counter == this.fiftyMoveRule){
                    // we throw the game over exception
                    if(!playerTurn){
                        playerTurn = true;
                        board.draw();
                        throw new GameOverStaleMateException("THE GAME HAS ENDED IN A DRAW\n-50 moves have been made" +
                                " without a pawn moving or a piece being taken.\nif you would like to" +
                                " continue, use /undo otherwise, to end the game in a draw use /quit");
                    }else{
                        playerTurn = false;
                        board.draw();
                        throw new GameOverStaleMateException("THE GAME HAS ENDED IN A DRAW\n-50 moves have been made" +
                                " without a pawn moving or a piece being taken.\nif you would like to" +
                                " continue, use /undo otherwise, to end the game in a draw use /quit");
                    }
                }
            }else{
                counter = 0;
            }

            // moves the piece
            board.getSquares()[toRank][toFile].setPiece(board.getSquares()[fromRank][fromFile].getPiece());
            board.getSquares()[fromRank][fromFile].setPiece(null);
            if (!playerTurn) {
                board.draw();
                playerTurn = true;
                // detects check mate and stale mate
                this.endGameHelp(board, GameColor.WHITE);
                this.threeFoldRep(board);
                System.out.println(player1Name + "'s turn!");
                System.out.print("White Has Taken" + WhiteTakenPiece + " \n");
                board.draw();
                playerTurn = true;
            }else{
                board.revDraw(board);
                playerTurn = false;
                // detects check mate and stale mate
                this.endGameHelp(board, GameColor.BLACK);
                this.threeFoldRep(board);
                System.out.println(player2Name + "'s turn!");
                System.out.print("Black Has Taken" + BlackTakenPiece + "\n");
                board.revDraw(board);
                playerTurn = false;
            }
        } else {
            System.out.println("Cannot move piece");
        }
    }


    /**
     * Detects three fold repetition for the game to end in a draw
     * @param board the board we are using
     */
    private void threeFoldRep(BoardIF board){
        int counter = 0;
        History history = History.getInstance();
        State<BoardIF> state = board.saveState();
        this.copyOfHistory.addAll(history.getList());

        // this is for after three fold repetition occurs, if the players decide to
        // continue we need to take out the board states so we can count a new set of
        // 3 fold repetitions.
        for(State<BoardIF> st : this.threeFold){
            for(int i = 0; i < 3; i++){
                this.copyOfHistory.remove(st);
            }
        }

        // detects equality and if we find three occurrences its 3 fold repetition
        for (State<BoardIF> s : this.copyOfHistory) {
            if (s.getState().equals(state.getState())) {
                counter++;
                System.out.println("Counter:" + counter);
                if(counter == 3){
                    this.threeFold.add(s);
                    System.out.println("THREE FOLD REPITITION!\nif you would like to" +
                            " continue, use /undo otherwise, to end the game in a draw use /quit");
                }
            }
        }
        this.copyOfHistory.clear();
    }


    /**
     * Detects if the game is over by either a checkmate or a stalemate
     * @param board the chess board
     * @param color the color we are checking for checkmate or stalemate
     * @throws GameOverStaleMateException if a player is in stalemate
     * @throws GameOverCheckMateException if a player is in checkmate
     */
    private void endGameHelp(BoardIF board, GameColor color) throws GameOverStaleMateException,
            GameOverCheckMateException{
        if(((Board) board).checkForCheck(color)){
            if(( ((Board) board).checkForCheckMate(color))){
                throw new GameOverCheckMateException(color.toString().toUpperCase() + " LOSES!");
            }
        }else{
            if(( ((Board) board).checkForStaleMate(color))){
                throw new GameOverStaleMateException("DRAW! STALEMATE!");
            }
        }
    }


    /**
     * Helper method that gets a position based on provided user input
     *
     * @param pos     the requested position i.e. b6 or B6 or f5 or F5
     * @param squares the squares on the chess board
     * @return a Position object representing the requested position from the user
     *         or null if the requested position is invalid
     */
    public Position getPosition(String pos, SquareIF[][] squares) {
        Position refinedPos;
        pos = pos.toLowerCase();
        String[] posArray = pos.split("");

        if (posArray.length > 2) {
            return null;
        }

        // we attempt to get the position
        try {
            File file = File.getFileFromLetter(posArray[0]);
            int parseRank = Integer.parseInt(posArray[1]);
            Rank rank = Rank.getRankFromNum(parseRank);

            // if we have a valid file and rank
            if (file != null && rank != null) {
                refinedPos = new Position(rank, file);

                // otherwise it is not a valid file and/or rank and we return null
            } else {
                refinedPos = null;
            }
            // if we were not supplied a number
        } catch (NumberFormatException nfe) {
            refinedPos = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            refinedPos = null;
        }
        return refinedPos;
    }

    /**
     * prints the players turn and flips the whose turn it is for the undo method.
     * @param board - The board being played on.
     */
    public void undo(BoardIF board) {
        if (!playerTurn) {
            System.out.println(player1Name + "'s turn!");
            board.draw();
            playerTurn = true;

            // ensures the 50 move rule is consistent with undos
            if(counter > 0) {
                this.counter--;
            }
        }else{
            System.out.println(player2Name + "'s turn!");
            board.revDraw(board);
            playerTurn = false;

            // ensures the 50 move rule is consistent with undos
            if(counter > 0) {
                this.counter--;
            }
        }
    }

    /**
     * prints the players turn and flips whose turn it is for the redo method.
     * @param board - The board being played on.
     */
    public void redo(BoardIF board) {
        if (!playerTurn) {
            System.out.println(player1Name + "'s turn!");
            board.draw();
            playerTurn = true;
            // ensures the 50 move rule is consistent with redos
            this.counter++;
        }else{
            System.out.println(player2Name + "'s turn!");
            board.revDraw(board);
            playerTurn = false;
            // ensures the 50 move rule is consistent with redos
            this.counter++;
        }
    }

    /**
     * adds the piece that was taken to the Arraylist of th player that took it.
     * @param board - The board that the game is being played on.
     * @param to - The position that the move will go to.
     */
    public void getTakenPiece(BoardIF board, Position to){
        if(board.getSquare(to.getRank(), to.getFile()).getPiece() != null) {
            if (playerTurn) {
                WhiteTakenPiece.add(board.getSquare(to.getRank(), to.getFile()).getPiece());
            } else {
                BlackTakenPiece.add(board.getSquare(to.getRank(), to.getFile()).getPiece());
            }
        }
    }

    /**
     * Prints each players pieces that they have taken from their opponent.
     */
    public void printTaken(){
        System.out.println("White has taken " + WhiteTakenPiece + "\nBlack has taken " + BlackTakenPiece);
    }
}
