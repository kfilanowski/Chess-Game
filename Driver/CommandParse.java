package Driver;

import Enums.File;
import Enums.Rank;
import History.History;
import Interfaces.BoardIF;
import Interfaces.SquareIF;
import Model.Position;
import java.util.Scanner;

/**
 * Parses commands for the user so that the user can play chess.
 * @author Jeriah Caplinger 45% 
 * @author Matt Lutz 45% 
 * @author Jacob Ginn 10%
 * @version 3/20/2019
 */
public class CommandParse {
    /** Scanner that gets the input from the command line */
    private Scanner input;

    History history = History.getInstance();

    /**
     * The default constructor for CommandParse.
     */
    public CommandParse() {
        input = new Scanner(System.in);
    }

    /**
     * Method that parses the command line arguments and calls the move helper method
     * @param board - An instance of the chess board
     * @param command - The array that holds the command line arguments
     */
    public void parse(BoardIF board, String[] command) {
        boolean go = true;
        SquareIF[][] squares = board.getSquares();

        while(go) {
            command = input.nextLine().split(" ");
            // if it is a move command i.e. /move b4 c8 (specifies we are moving the piece at b4 to c8
            if (command[0].toLowerCase().equals("/move") && command.length == 3) {
                
                Position from = getPosition(command[1], squares);
                Position to = getPosition(command[2], squares);

                //if either the to position or the from position is incorrect it tells the
                //user to input a correct square
                if(from == null || to == null || board.getSquare(from).getPiece() == null) {
                    System.out.println("Please enter only one letter: a - h and one number: 1 - 8 i.e. a1 or E5\n");
                } else {           
                    move(board, from, to);
                }

                // we can stop our while loop now that we have correct input
//                go = false;
            } else if(command[0].toLowerCase().equals("/showmoves") && command.length == 2) {
                Position from = getPosition(command[1], squares);
                Position[] temp = squares[from.getRank().getIndex()][from.getFile().getIndex()]
                        .getPiece().showMoves(from);
                board.draw(board,temp);
                //TODO: pass in Position to a validator to show the moves
               // go = false;
            } else if(command[0].toLowerCase().equals("/undo") && command.length == 1) {
                try {
                    board.restoreState(history.undo());
                    board.draw();
                } catch (NullPointerException ex) {
                    System.err.println("reached null: Could not undo further.");
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    System.err.println("reached index out of bounds: Could not redo further.");
                }
            } else if (command[0].toLowerCase().equals("/redo") && command.length == 1) {
                try {
                    board.restoreState(history.redo());
                    board.draw();
                } catch (NullPointerException ex) {
                    System.err.println("reached null: Could not redo further.");
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    System.err.println("reached index out of bounds: Could not redo further.");
                }

            } else if (command[0].toLowerCase().equals("/quit") && command.length == 1) {
                System.exit(0);
            }
        }
    }

    /**
     * Helper method that gets a position based on provided user input
     * @param pos the requested position i.e. b6 or B6 or f5 or F5
     * @param squares the squares on the chess board
     * @return a Position object representing the requested position from the user
     *          or null if the requested position is invalid
     */
    private Position getPosition(String pos, SquareIF[][] squares){
        Position refinedPos;
        pos = pos.toLowerCase();
        String[] posArray = pos.split("");

        if(posArray.length > 2){
           return null;
        }

        // we attempt to get the position
        try {
            File file = File.getFileFromLetter(posArray[0]);
            int parseRank = Integer.parseInt(posArray[1]);
            Rank rank = Rank.getRankFromNum(parseRank);

            // if we have a valid file and rank
            if(file != null && rank != null){
                refinedPos = new Position(rank, file);

            // otherwise it is not a valid file and/or rank and we return null
            }else{
                refinedPos = null;
            }
         // if we were not supplied a number
        }catch(NumberFormatException nfe){
            refinedPos = null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            refinedPos = null;
        }
        return refinedPos;
    }

    /**
     * Helper method that is used to move a piece on the board
     * @param board - An instance of the chess board
     * @param from - The starting position that we want to move from
     * @param to - The final position that we want to move to
     */
    private void move(BoardIF board, Position from, Position to){
        int fromFile = from.getFile().getIndex(); //from square file
        int fromRank = from.getRank().getIndex(); //from square rank

        int toFile = to.getFile().getIndex();   // to square file
        int toRank = to.getRank().getIndex();   // to square rank

        boolean validMove = board.getSquare(from.getRank(), from.getFile()).getPiece().validateMove(from,to);

        if(validMove){
            history.add(board.saveState());
            board.getSquares()[toRank][toFile].setPiece(board.getSquares()[fromRank][fromFile].getPiece());
            board.getSquares()[fromRank][fromFile].setPiece(null);
            board.draw();
        }else{
            System.out.println("Cannot move piece");
        }
    }
}