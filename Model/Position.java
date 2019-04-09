package Model;

import Enums.File;
import Enums.Rank;

/**
 * The position class acts as a specified location on the Chess board.
 * A position holds a Rank and File object, as well as a square
 * that is located on that Rank and File.
 * 
 * @author Kevin Filanowski 70%
 * @author Jeriah Caplinger 30%
 * @version March 20, 2019
 */
public class Position {
    /** The rank of this position. */
    private Rank rank;
    /** The file of this position. */
    private File file;

    /**
     * Constructs a new Postion class utilizing the Rank and File enums.
     * @param rank the rank enumeration given to this class i.e. 1,2,3,4,5,6,7,8
     * @param file the file enumeration given to this class i.e. a,b,c,d,e,f,g,h
     */
    public Position(Rank rank, File file) {
        this.rank = rank;
        this.file = file;
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

    /**
     * Checks equality between positions by comparing the rank and the file.
     * @param obj - The positon object.
     * @return - True if the objects are equal, false otherwise.
     */
    public boolean equals(Object obj){
        if(obj instanceof Position){
            Position other = (Position) obj;
            return (other.getFile() == this.getFile() && other.getRank() == this.getRank());
        }
        return false;
    }

    /**
     * Create a deep clone of this object.
     * 
     * @return - A deep clone of this object.
     */
    public Position clone() {
        return new Position(Rank.getRankFromIndex(rank.getIndex()),
                            File.getFileFromIndex(file.getIndex()));
    }
}
