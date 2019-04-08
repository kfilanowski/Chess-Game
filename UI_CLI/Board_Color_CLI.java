package UI_CLI;

import Enums.GameColor;
import Interfaces.BoardIF;
import Interfaces.BoardStrategy;
import Interfaces.SquareIF;
import Model.Position;

/**
 * This is the Command line interface color drawing strategy. This will be used
 * to draw the board in color instead of just mono colors.
 * 
 * @author - Jacob Ginn 100%
 * @version April 7, 2019
 */
public class Board_Color_CLI extends Board_CLI implements BoardStrategy {

    /**
     * 
     * 
     * 
     */
    public Board_Color_CLI() {
        super();
    }

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
                            str.append(squares[i][j].toString("\u001b[47m","\u001b[30m"));
                        }else{
                            str.append(squares[i][j].toString("\u001b[47m","\u001b[31m"));
                        }

                    } else {

                        if(squares[i][j].getPiece().getColor() == GameColor.BLACK){
                            str.append(squares[i][j].toString("\u001b[107m" ,"\u001b[30m"));
                        }else{
                            str.append(squares[i][j].toString("\u001b[107m" ,"\u001b[31m"));
                        }
                    }

                } else {

                    if (squares[i][j].isBlack()) {
                        str.append(squares[i][j].toString("\u001b[47m" ,"\u001b[30m"));

                    } else {
                        str.append(squares[i][j].toString("\u001b[107m" ,"\u001b[30m"));

                    }
                }
            }

            str.append("\n");
            count--;//decrements the rank of the chess board
        }
        str.append("   A  B  C  D  E  F  G  H");//Shows the File of the Chess pieces
        System.out.println(str);//draws the Chess board
    }

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public Board_Color_CLI clone() {
        return new Board_Color_CLI();
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    @Override
    public void draw(BoardIF board, Position [] pos) {
        SquareIF[][] squares = board.getSquares();
        squares = setPosHighlight(squares,pos);
        StringBuilder str = new StringBuilder();
        boolean done = false;
        int count = 8;

        for(Position p: pos){
            System.out.println(p);
        }

        for (int i = 0; i < squares.length; i++) {//rows of the chess board

            str.append("\u001b[0m" + count + " ");//shows the rank of the chess board

            for (int j = 0; j < squares.length; j++) {//columns of the chessboard
                done = false;

                if(squares[i][j].getHighlighted()){
                    str = printsHighlight(squares, i, j, str);
                    done = true;
                }

                if (squares[i][j].getPiece() != null && !done) {

                    if (squares[i][j].isBlack() && !done) {

                        if(squares[i][j].getPiece().getColor() == GameColor.BLACK){
                            str.append(squares[i][j].toString("\u001b[47m","\u001b[30m"));
                        }else{
                            str.append(squares[i][j].toString("\u001b[47m","\u001b[31m"));
                        }

                    } else if(!done) {

                        if(squares[i][j].getPiece().getColor() == GameColor.BLACK){
                            str.append(squares[i][j].toString("\u001b[107m" ,"\u001b[30m"));
                        }else{
                            str.append(squares[i][j].toString("\u001b[107m" ,"\u001b[31m"));
                        }
                    }

                } else if(!done) {

                    if (squares[i][j].isBlack()) {
                        str.append(squares[i][j].toString("\u001b[47m" ,"\u001b[30m"));

                    } else {
                        str.append(squares[i][j].toString("\u001b[107m" ,"\u001b[30m"));

                    }
                }
            }

            str.append("\n");
            count--;//decrements the rank of the chess board
        }
        str.append("   A  B  C  D  E  F  G  H");//Shows the File of the Chess pieces
        System.out.println(str);//draws the Chess board
    }


    /**
     * 
     * 
     * 
     * 
     * 
     * 
     */
    private SquareIF[][] setPosHighlight(SquareIF[][] squares, Position [] pos){
        for(Position p: pos){
            squares[p.getRank().getIndex()][p.getFile().getIndex()].setHighlighted(true);

        }
        return squares;
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    private StringBuilder printsHighlight(SquareIF[][] squares, int i, int j, StringBuilder str){
        if(squares[i][j].getPiece() != null) {
            if (squares[i][j].getPiece().getColor() == GameColor.BLACK) {
                str.append(squares[i][j].toString("\u001b[42m", "\u001b[30m"));
                squares[i][j].setHighlighted(false);
            } else {
                str.append(squares[i][j].toString("\u001b[42m", "\u001b[31m"));
                squares[i][j].setHighlighted(false);
            }
        }else{
            str.append(squares[i][j].toString("\u001b[42m", "\u001b[30m"));
            squares[i][j].setHighlighted(false);
        }
        return str;
    }
    
}
