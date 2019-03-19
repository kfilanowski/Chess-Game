package UI_CLI;

import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.SquareIF;

/**
 * This is the class that will draw the board in Black and White
 */
public class Board_Mono_CLI implements BoardStrategy {

    /**
     * Draws the board in black and white with the squares and the pieces are black and white
     * @param board - the board object that is printed on the command line interface.
     */
    @Override
    public void draw(BoardIF board) {
        SquareIF[][] squares = board.getSquares();

        StringBuilder str = new StringBuilder();

        int count = 8;

        for (int i = 0; i < squares.length; i++) {

            str.append("\u001b[0m" + count + " ");

            for (int j = 0; j < squares.length; j++) {

                if (squares[i][j].getPiece() != null) {

                    if (squares[i][j].isBlack()) {

                        if(squares[i][j].getPiece().isBlack()){
                            str.append("\u001b[47m" + " " + "\u001b[30m\u001b[47m" + squares[i][j].getPiece() + " " + "\u001b[0m");
                        }else{
                            str.append("\u001b[47m" + " " + "\u001b[31m\u001b[47m" + squares[i][j].getPiece() + " " + "\u001b[0m");
                        }

                    } else {

                        if(squares[i][j].getPiece().isBlack()){
                            str.append("\u001b[107m" + " " + "\u001b[30m\u001b[107m" + squares[i][j].getPiece() + " " + "\u001b[0m");
                        }else{
                            str.append("\u001b[107m" + " " + "\u001b[31m\u001b[107m" + squares[i][j].getPiece() + " " + "\u001b[0m");
                        }
                    }

                } else {

                    if (squares[i][j].isBlack()) {
                        str.append("\u001b[47m" + "   " + "\u001b[0m");
                    } else {
                        str.append("\u001b[107m" + "   " + "\u001b[0m");
                    }
                }
            }

            str.append("\n");
            count--;
        }
            System.out.println(str);

    }

}
