package Driver;

import Interfaces.BoardIF;

import java.util.Scanner;
import Model.Board;
import UI_CLI.Board_Color_CLI;
import UI_CLI.Board_Mono_CLI;

/**
 * Driver for the chess program. It will ask the user if it wants to play on a black and white
 * or a colored board for the chess match.
 */
public class Driver {
    public static void main(String[] args){
        BoardIF chess_board = new Board();//Chess board that the game will be played on

        Scanner reader = new Scanner(System.in);//scanner to read the input into the file

        System.out.println("What board would you like? Color: color or Black and White: bw?");

        String in = reader.next();//reads in the next input

        switch (in){
            case "color"://if the user wants the colored board
                chess_board.setDrawStrategy(new Board_Color_CLI());
                break;
            case "bw"://if the user wants the black and white board
                chess_board.setDrawStrategy(new Board_Mono_CLI());
        }

        ((Board) chess_board).go();
    }
}
