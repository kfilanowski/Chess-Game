package UI_CLI;

import Enums.GameColor;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.SquareIF;

/**
 * @author - Jacob Ginn 100%
 * @version March 20, 2019
 * This is the class that will draw the board in Black and White
 */
public class Board_Mono_CLI implements BoardStrategy {
    /**
     * Draws the board in black and white with the squares and the pieces are in color.
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

                        if( squares[i][j].getPiece().getColor() == GameColor.BLACK ){
                            str.append( "( " + squares[i][j].getPiece().toString().toUpperCase() + " )");//black squares
                        }else{
                            str.append( "( " + squares[i][j].getPiece().toString().toLowerCase() + " )" );//Black piece
                            // is UpperCase
                        }

                    } else {

                        if(squares[i][j].getPiece().getColor() == GameColor.BLACK){
                            str.append("[ " + squares[i][j].getPiece().toString().toUpperCase() + " ]");//white squares
                        }else{
                            str.append("[ " + squares[i][j].getPiece().toString().toLowerCase() + " ]");//white piece is
                            // lowercase
                        }
                    }

                } else {

                    if (squares[i][j].isBlack()) {
                        str.append("(   )");//prints a black space for the board
                    } else {
                        str.append("[   ]");//prints a white space for the board
                    }
                }
            }

            str.append("\n");
            count--;//decrements the rank of the chess board
        }
        str.append("    A    B    C    D    E    F    G    H");//Shows the File of the Chess pieces
        System.out.println(str);//draws the Chess board
    }//end Draw()
}//end Class()
