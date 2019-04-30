package UI_CLI;

import java.util.Scanner;

import ChessExceptions.GameOverCheckMateException;
import ChessExceptions.GameOverStaleMateException;
import Model.Position;
import History.History;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Controller.GameController_CLI;

/**
 * Parses commands for the user so that the user can play chess.
 *
 * @author Jeriah Caplinger(25%)
 * @author Matt Lutz (25%)
 * @author Jacob Ginn (25%)
 * @author Kevin Filanowski(25%)
 * @version April 14, 2019
 */
public abstract class Board_CLI implements BoardStrategy {
    /** Constant argument length for the move command. */
    private final int MOVE_ARG_LENGTH = 3;
    /** Constant argument length for the showmoves command. */
    private final int SHOWMOVES_ARG_LENGTH = 2;
    /** Scanner that gets the input from the command line */
    protected Scanner input;
    /** The main controller for the chess game. **/
    protected GameController_CLI gc;
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
     *
     * @param board - An instance of the chess board
     */
    private void parseInput(BoardIF board) {
        // Splits up input by spaces and holds them in this field.
        String command[];
        // The entire line of input from the user.
        String line;
        //
        boolean go = true;

        printMenuOptions();

        while (go) {
            line = input.nextLine();
            command = line.split(" ");

            switch (command[0].toLowerCase()) {
            case "move":      parseMoveCommand(command, board);      break;
            case "showmoves": parseShowMovesCommand(command, board); break;
            case "undo":      parseUndoCommand(board);               break;
            case "redo":      parseRedoCommand(board);               break;
            case "showmenu":  printMenuOptions();                    break;
            case "showpiecestaken": gc.printTaken();                 break;
            case "quit":      exitGame();                            break;
            default: System.out.println("Invalid Command: " + line); 
            }
        }
    }

    /**
     * Compares the command argument length with what is required for a certain
     * command to function.
     *
     * @param command - The split up command input.
     * @param length  - The minimum length needed for a specific command to
     *                function properly.
     * @return - True if the the input length was at least the amount needed,
     *         false otherwise.
     */
    private boolean checkInputLength(String[] command, int length) {
        if (command.length >= length) {
            return true;
        }
        System.out.println("Invalid Command, Please try again.");
        printMenuOptions();
        return false;
    }

    /**
     * Prints the available commands to the player.
     */
    private void printMenuOptions() {
        System.out.println("Menu Options are:\n" 
                + "move ## ##:      Move a piece from one square to another.\n"
                + "showmoves ##:    Highlights the possible moves a piece can make.\n"
                + "undo:            Takes back a previously made move.\n"
                + "redo:            Reverse the effect of an undo.\n"
                + "showpiecestaken: Print the pieces that have been taken.\n" 
                + "showmenu:        Print this menu.\n"
                + "quit:            Quit the program.\n");
    }

    /**
     * Gets the names of both the players that are playing the game.
     */
    public void getNames() {
        System.out.println("Please enter Player 1 name.");
        String name = input.nextLine();

        System.out.println("Please enter Player 2 name.");
        String name2 = input.nextLine();

        gc = new GameController_CLI(name, name2);
        System.out.println(name + "'s turn!");
    }

    /**
     * Parses the command for a movement operation.
     *
     * @param command - The command arguments given by the user.
     * @param board   - The game board itself.
     */
    private void parseMoveCommand(String[] command, BoardIF board) {
        // Ensures the correct length before proceeding.
        if (!checkInputLength(command, MOVE_ARG_LENGTH)) {
            return;
        }

        Position from = gc.getPosition(command[1], board.getSquares());
        Position to = gc.getPosition(command[2], board.getSquares());

        // If either the positions are incorrect, tell the user to input again.
        if (from == null || to == null || board.getSquare(from).getPiece() == null) {
            System.out.println("Please enter only one letter: a - h and one number: 1 - 8 i.e. a1 or E5\n");
        } else {
            try {
                gc.move(board, from, to);
            } catch (GameOverCheckMateException gocme) {
                System.out.println("GAME OVER! CHECKMATE!");
                System.out.println(gocme.getMessage());
            } catch (GameOverStaleMateException gosme) {
                System.out.println(gosme.getMessage());
            }
        }
    }

    /**
     * Parses the command for a showmovement operation.
     *
     * @param command - The command arguments given by the user.
     * @param board   - The game board itself.
     */
    private void parseShowMovesCommand(String[] command, BoardIF board) {
        // Ensures the correct length before proceeding.
        if (!checkInputLength(command, SHOWMOVES_ARG_LENGTH)) {
            return;
        }

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
            System.out.println("Invalid Piece: Please choose another piece.");
        }
    }

    /**
     * Parses the command for an undo operation.
     *
     * @param board - The game board itself.
     */
    private void parseUndoCommand(BoardIF board) {
        try {
            board.restoreState(history.undo(board));
            gc.undo(board);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.err.println("Error: Cannot undo further.");
        }
    }

    /**
     * Parses the command for a redo operation.
     *
     * @param board - The game board itself.
     */
    private void parseRedoCommand(BoardIF board) {
        try {
            board.restoreState(history.redo());
            gc.redo(board);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.err.println("Error: Cannot redo further.");
        }
    }

    /**
     * Exits the game.
     */
    private void exitGame() {
        System.out.println("Game exiting.");
        System.exit(0);
    }

    /**
     * Creates a deep clone of this object.
     *
     * @return - A deep clone of this objec.
     */
    public abstract BoardStrategy clone();
}