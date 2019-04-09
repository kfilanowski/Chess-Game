package Driver;

import Interfaces.BoardIF;
import java.util.Scanner;
import Model.Board;
import UI_CLI.Board_Color_CLI;
import UI_CLI.Board_Mono_CLI;

/**
 * @author Jacob Ginn
 * @author Matt Lutz
 * @author Kevin Filanowski
 * @version April 7, 2019
 *
 * Driver for the chess program. It will ask the user if it wants to play on a black and white
 * or a colored board for the chess match.
 */
public class Driver {
    /**
     * The main driver of the program.
     * Running this class will start the program.
     * @param args - Command line arguments. These are ignored at startup.
     */
    public static void main(String[] args) {
        //Chess board that the game will be played on.
        BoardIF chess_board = new Board();
        try {
            chess_board.go();
        } catch (NumberFormatException ex) {
            System.out.println("Driver has thrown number format exception from wrong input.");
        }
    }
}