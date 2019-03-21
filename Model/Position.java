package Model;

import Enums.File;
import Enums.Rank;
import Interfaces.SquareIF;

/**
 * The position class acts as a specified location on the Chess board.
 * A position holds a Rank and File object, as well as a square
 * that is located on that Rank and File.
 * 
 * @author unassigned
 * @author Kevin Filanowski
 * @version March 17, 2019
 */
public class Position {
    private Rank rank;
    private File file;
    private SquareIF square;

    /**
     * Constructs a new Postion class utilizing the Rank and File enums.
     * @param rank the rank enumeration given to this class i.e. 1,2,3,4,5,6,7,8
     * @param file the file enumeration given to this class i.e. a,b,c,d,e,f,g,h
     */
    public Position(Rank rank, File file, SquareIF square) {
        this.rank = rank;
        this.file = file;
        this.square = square;
    }

    /**
     * Gets the chess square that this position references.
     * 
     * @return - The Square object that this position class references.
     */
    public SquareIF getSquare(){
        return square;
    }

    /**
     * Gets the rank that the players see on the board, that is, 1,2,3,4,5,6,7,8.
     * 
     * @return - The rank object of this position.
     */
    public Rank getRank(){
        return rank;
    }

    /**
     * Gets the file that the players see on the board, that is, a,b,c,d,e,f,g,h.
     * 
     * @return - The file object of this position.
     */
    public File getFile(){
        return file;
    }

    /**
     * Gets our Position and changes it into string form
     * @return - The position as a string
     */
    public String toString() {
        return "rankIndex:" + rank.getIndex() + " fileIndex:" + file.getIndex();
    }

    public boolean equals(Object obj){
        if(obj instanceof Position){
            Position other = (Position) obj;
            return (other.getFile() == this.getFile() && other.getRank() == this.getRank());
        }
        return false;
    }
}
