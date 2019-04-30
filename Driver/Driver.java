package Driver;

import Interfaces.BoardIF;
import Model.Board;

/**
 * Driver for the chess program. It will ask the user if it wants to play on a
 * black and white or a colored board for the chess match.
 * 
 * @author Kevin Filanowski 100%
 * @version April 7, 2019
 */
public class Driver {
    /**
     * The main driver of the program. Running this class will start the
     * program.
     *
     * @param args - Command line arguments. These are ignored at startup.
     */
    public static void main(String[] args) {
        // Chess board that the game will be played on.
        BoardIF chess_board = new Board();
        try {
            chess_board.go();
        } catch (NumberFormatException ex) {
            // TODO: This is thrown when we choose a wrong menu option. lets
            // have it loop or something.
            System.out.println("Driver has thrown number format exception from wrong input."); 
        }
    }
}