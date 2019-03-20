package Driver;

import Enums.File;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.SquareIF;
import Model.Position;

import java.util.Scanner;

/**
 * Parses commands for the user so that the user can play chess.
 * @author Jeriah Caplinger && Matt Lutz && Jacob Ginn
 * @version 3/19/2019
 */
public class commandParse {
    private Scanner input;

    public commandParse(){
        input = new Scanner(System.in);
    }


    public void parse(BoardIF board, String[] command){
        boolean go = true;
        SquareIF[][] squares = board.getSquares();

        while(go) {
            command = input.nextLine().split(" ");
            // if it is a move command i.e. /move b4 c8 (specifies we are moving the piece at b4 to c8
            if (command[0].toLowerCase().equals("/move") && command.length == 3) {
                Position from = getPosition(command[1], squares);
                Position to = getPosition(command[2], squares);
                move(board, from, to);



                // we can stop our while loop now that we have correct input
//                go = false;
            }else if(command[0].toLowerCase().equals("/show moves") && command.length == 2){
                Position from = getPosition(command[1], squares);
                //TODO: pass in Position to a validator to show the moves
                go = false;
            }else if(command[0].toLowerCase().equals("/undo") && command.length == 1){
                //TODO: handle undo somehow
                System.out.println("undo");
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

        // we attempt to get the position
        try {
            File file = File.getFileFromLetter(posArray[0]);
            int parseRank = Integer.parseInt(posArray[1]);
            Rank rank = Rank.getRankFromNum(parseRank);

            // if we have a valid file and rank
            if(file != null && rank != null){
                refinedPos = new Position(rank, file,
                        squares[rank.getIndex()][file.getIndex()]);

            // otherwise it is not a valid file and/or rank and we return null
            }else{
                refinedPos = null;
            }
         // if we were not supplied a number
        }catch(NumberFormatException nfe){
            refinedPos = null;
        }
        return refinedPos;
    }

    private void move(BoardIF board, Position from, Position to){
        int fromFile = from.getFile().getIndex();
        int fromRank = from.getRank().getIndex();
        //System.out.println("This is the file: " + fromFile);
        //System.out.println("This is the rank: " + fromRank);


        int toFile = to.getFile().getIndex();
        int toRank = to.getRank().getIndex();
        //System.out.println("This is the file: " + toFile);
        //System.out.println("This is the rank: " + toRank);
        boolean test = board.getSquare(from.getRank(), from.getFile()).getPiece().validateMove(from,to);
        //System.out.println(test);
        if(test){

            board.getSquares()[toRank][toFile].setPiece(board.getSquares()[fromRank][fromFile].getPiece());
            board.getSquares()[fromRank][fromFile].setPiece(null);
            board.draw();

        }else{
            System.out.println("Cannot move piece");
        }


    }
}