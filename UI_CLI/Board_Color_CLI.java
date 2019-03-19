package UI_CLI;

import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.SquareIF;

public class Board_Color_CLI implements BoardStrategy {
    @Override
    public void draw(BoardIF board) {
        SquareIF[][] squares = board.getSquares();

        StringBuilder str = new StringBuilder();
        int count = 8;


        for(int i = 0; i < squares.length ; i++) {
            str.append("\n" + count + "  ");
            count--;
            for (int j = 0; j < squares.length; j++) {
                if(squares[i][j].isWhite()){
                    str.append("\u001b[0m" + "\u001b[46m");
                }
                if (squares[i][j].getPiece() != null)
                    if(squares[i][j].getPiece().isWhite()){
                        str.append(" " + "\u001b[30m" + squares[i][j].getPiece() + "\u001b[0m" + " ");
                    }else{
                        str.append(" " + "\u001b[32m" +  squares[i][j].getPiece() + "\u001b[0m" + " ");
                    }
                else
                    str.append(" . " );
            }
        }
        str.append("\n\n    A  B  C  D  E  F  G  H");
        System.out.println(str);
    }
}
