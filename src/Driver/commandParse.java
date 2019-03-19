package Driver;

import Enums.File;
import Enums.Rank;
import Interfaces.BoardIF;
import Interfaces.SquareIF;
import Model.Position;

import java.util.Scanner;

public class commandParse {
    private Scanner input;

    public commandParse(){
        input = new Scanner(System.in);
    }

    public boolean parse(BoardIF board){
        boolean result = false;
        SquareIF[][] squares = board.getSquares();

        String[] command = input.nextLine().split(" ");
        if(command[0].equals("/move")){

        }
    }

    private Position getPosition(String pos, SquareIF squares){
        Position refinedPos;
        pos = pos.toLowerCase();
        String[] posArray = pos.split("");

        try {
            File file = File.getFileFromLetter(posArray[0]);
            int rank = Integer.parseInt(posArray[1]);
            refinedPos = new Position(Rank.getRankFromIndex(rank), File.getFileFromLetter(posArray[0]),
                    squares[Rank.getRankFromIndex(rank)][File.getFileFromLetter(posArray[0])]);

        }catch(NumberFormatException nfe){
            refinedPos = null;
        }

    }

}