package Model;

import Enums.File;
import Enums.Rank;

public class Position {
    private Rank rank;
    private File file;
    private Square square;

    /**
     * Constructs a new Postion class utilizing the Rank and File enums.
     * @param rank the rank enumeration given to this class i.e. 1,2,3,4,5,6,7,8
     * @param file the file enumeration given to this class i.e. a,b,c,d,e,f,g,h
     */
    public Position(Rank rank, File file, Square square){
        this.rank = rank;
        this.file = file;
        this.square = square;
    }


    /**
     * Gets the chess square that this position references.
     * @return the Square object that this position class references.
     */
    public Square getSquare(){
        return this.square;
    }


    /**
     * Gets the rank that the players see on the board, that is, 1,2,3,4,5,6,7,8.
     * @return The rank value of this position that the players see on the board
     */
    public int getRank(){
        return this.rank.getNum();
    }

    /**
     * Gets the rank index, that is, the index that the players do not see that is used to
     * access squares on the board.
     * @return the rank index that the backend game logic uses to run the game
     */
    public int getRankIndex(){
        return this.rank.getIndex();
    }


    /**
     * Gets the file that the players see on the board, that is, a,b,c,d,e,f,g,h.
     * @return the file value of this position that the players see on the board
     */
    public String getFile(){
        return this.file.getFile();
    }

    /**
     * Gets the index value of the file (a,b,c,d,e,f,g,h) that the players see on the board
     * that is used to access squares on the board.
     * @return the file index that the backend game logic uses to run the game.
     */
    public int getFileIndex(){
        return this.file.getIndex();
    }

}
