package UI_CLI;

import Enums.GameColor;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.SquareIF;

/**
 * @author - Jacob Ginn 100%
 * This is the Command line interface color drawing strategy. This will be used to draw the board in color instead of
 * just mono colors.
 */
public class Board_Color_CLI implements BoardStrategy {

    /**
     * Draws the board in black and white with the squares and the pieces are black and white
     * @param board - the board object that is printed on the command line interface.
     */
    @Override
    public void draw(BoardIF board) {
        SquareIF[][] squares = board.getSquares();

        StringBuilder str = new StringBuilder();

        int count = 8;

        for (int i = 0; i < squares.length; i++) {//rows of the chess board

            str.append("\u001b[0m" + count + " ");//shows the rank of the chess board

            for (int j = 0; j < squares.length; j++) {//columns of the chessboard

                if (squares[i][j].getPiece() != null) {

                    if (squares[i][j].isBlack()) {

                        if(squares[i][j].getPiece().getColor() == GameColor.BLACK){
                            str.append("\u001b[47m" + " " + "\u001b[30m\u001b[47m" + squares[i][j].getPiece() + " " + "\u001b[0m");
                        }else{
                            str.append("\u001b[47m" + " " + "\u001b[31m\u001b[47m" + squares[i][j].getPiece() + " " + "\u001b[0m");
                        }

                    } else {

                        if(squares[i][j].getPiece().getColor() == GameColor.BLACK){
                            str.append("\u001b[107m" + " " + "\u001b[30m\u001b[107m" + squares[i][j].getPiece() +
                                    " " + "\u001b[0m");
                        }else{
                            str.append("\u001b[107m" + " " + "\u001b[31m\u001b[107m" + squares[i][j].getPiece() +
                                    " " + "\u001b[0m");
                        }
                    }

                } else {

                    if (squares[i][j].isBlack()) {
                        str.append("\u001b[47m" + "   " + "\u001b[0m");//prints a black space for the board
                    } else {
                        str.append("\u001b[107m" + "   " + "\u001b[0m");//prints a white space for the board
                    }
                }
            }

            str.append("\n");
            count--;//decrements the rank of the chess board
        }
        str.append("   A  B  C  D  E  F  G  H");//Shows the File of the Chess pieces
        System.out.println(str);//draws the Chess board

    }
}
