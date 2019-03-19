package UI_CLI;

import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.SquareIF;

public class Board_Mono_CLI implements BoardStrategy {

/**    public void draw(BoardIF board) {


        SquareIF[][] squares = board.getSquares();

        StringBuilder str = new StringBuilder();
        int count = 8;


        for (int i = 0; i < squares.length; i++) {
           str.append("\n" + count + "  ");
            count--;
            for (int j = 0; j < squares.length; j++) {
                if (squares[i][j].isWhite()) {
                    str.append();
                }
                if (squares[i][j].getPiece() != null)
                    if (squares[i][j].getPiece().isWhite()) {
                        str.append(" " + squares[i][j].getPiece() + " ");
                    } else {
                        str.append(" " + squares[i][j].getPiece() + " ");
                    }
                else
                    str.append(" . ");
            }
        }
        str.append("\n\n    A  B  C  D  E  F  G  H");
        System.out.println(str);

    }
*/
    @Override
    public void draw(BoardIF board){
        SquareIF[][] squares = board.getSquares();

        StringBuilder str = new StringBuilder();

        int count = 8;

        for(int i = 0; i < squares.length; i++){
            str.append("\u001b[0m" + count + " ");
            for(int j = 0; j < squares.length; j++){
                if(squares[i][j].getPiece() != null) {
                    if (squares[i][j].isBlack()) {
                        str.append("\u001b[47m" + squares[i][j].getPiece() + "\u001b[0m");
                    } else {
                        str.append("\u001b[107m" + squares[i][j].getPiece() + "\u001b[0m");
                    }
                }else{
                    if (squares[i][j].isBlack()) {
                        str.append("\u001b[47m" + "\u2001"+ "\u001b[0m");
                    } else {
                        str.append("\u001b[107m" + "\u2001" + "\u001b[0m");
                    }
                }
            }
            str.append("\n");
            count--;
        }

        System.out.println(str);

    }

}
