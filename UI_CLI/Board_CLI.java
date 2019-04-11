package UI_CLI;

import java.util.Scanner;

import ChessExceptions.GameOverCheckMateException;
import ChessExceptions.GameOverStaleMateException;
import Enums.ChessPieceType;
import Enums.File;
import Enums.Rank;
import Model.Piece;
import Model.Position;
import History.History;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.SquareIF;

/**
 * Parses commands for the user so that the user can play chess.
 * 
 * @author Jeriah Caplinger(25%)
 * @author Matt Lutz (25%)
 * @author Jacob Ginn (25%)
 * @author Kevin Filanowski(25%)
 * @version April 7, 2019
 */
public abstract class Board_CLI implements BoardStrategy {
    /** Scanner that gets the input from the command line */
    protected Scanner input;
    /** This object contains a list of snapshots of the entire board game. */
    protected GameController gc;
    /** The history of the board */
    protected History<BoardIF> history;

    /**
     * The default constructor for Board_CLI.
     */
    public Board_CLI() {
        input = new Scanner(System.in);
        history = History.getInstance();
    }

    /**
     * Begins the parsing operations
     */
    public void go(BoardIF board) {
        parseInput(board);
    }

    /**
     * Method that parses the command line arguments and calls the move helper
     * method.
     * @param board - An instance of the chess board
     */
    private void parseInput(BoardIF board) {
        //
        String command[];
        //
        String line;
        //
        boolean go = true;
        //


        printMenuOptions();

        while (go) {
            line = input.nextLine();
            command = line.split(" ");
            // if it is a move command i.e. /move b4 c8 (specifies we are moving the piece
            // at b4 to c8
            if (command[0].toLowerCase().equals("/move") && command.length == 3) {
                Position from = gc.getPosition(command[1], board.getSquares());
                Position to = gc.getPosition(command[2], board.getSquares());

                // if either the to position or the from position is incorrect it tells the
                // user to input a correct square
                if (from == null || to == null || board.getSquare(from).getPiece() == null) {
                    System.out.println("Please enter only one letter: a - h and one number: 1 - 8 i.e. a1 or E5\n");
                } else {

                    try {
                        gc.move(board, from, to);
                    }catch (GameOverCheckMateException gocme){
                        board.draw();
                        System.out.println("GAME OVER! CHECKMATE!");
                        System.out.println(gocme.getMessage());
                    }catch (GameOverStaleMateException gosme){
                        board.draw();
                        System.out.println(gosme.getMessage());
                    }
                }

                // we can stop our while loop now that we have correct input
                // go = false;
            } else if (command[0].toLowerCase().equals("/showmoves") && command.length == 2) {
                try {
                    Position from = gc.getPosition(command[1], board.getSquares());
                    Position[] temp = board.getSquares()[from.getRank().getIndex()][from.getFile().getIndex()].getPiece()
                            .showMoves(from);
                    if (gc.getplayerTurn()) {
                        board.draw(board, temp);
                    } else {
                        board.revDraw(board, temp);
                    }
                } catch (NullPointerException ex) {
                    System.out.println("Invalid Piece: Please Choose another piece.");
                }
            } else if (command[0].toLowerCase().equals("/undo")) {
                try {
                    board.restoreState(history.undo());
                    gc.undo(board);
                } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                    System.err.println("Error: Can not undo further.");
                }
            } else if (command[0].toLowerCase().equals("/redo")) {
                try {
                    board.restoreState(history.redo());
                    gc.redo(board);
                } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                    System.err.println("Error: Can not redo further.");
                }
            } else if (command[0].toLowerCase().equals("/showmenu")) {
                printMenuOptions();
            } else if (command[0].toLowerCase().equals("/showpiecestaken")){
                gc.printTaken();
            }else if (command[0].toLowerCase().equals("/quit")) {
                System.out.println("System exiting.");
                System.exit(0);
            }
        }
    }


    /**
     * Prints the available commands to the player.
     */
    public void printMenuOptions() {
        System.out.println("Menu Options are:\n"
        + "1) /move ## ##:   Move a piece from one square to another.\n"
        + "2) /showmoves ##: Highlights the possible moves a piece can make.\n"
        + "3) /undo:         Takes back a previously made move.\n"
        + "4) /redo:         Reverse the effect of an undo.\n"
        + "5) /showmenu:     Print this menu.\n"
        + "6) /showpiecestaken: Print the pieces that have been taken.\n"
        + "7) /quit:         Quit the program.\n");
    }

    /**
     * gets the names of both the players that are playing the game of Chess.
     */
    public void getNames(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter Player 1 name.");
        String name = reader.next();
        System.out.println("Please enter Player 2 name.");
        String name2 =reader.next();
        gc = new GameController(name, name2);
        System.out.println(name + "'s turn!");

    }


    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public abstract BoardStrategy clone();

}