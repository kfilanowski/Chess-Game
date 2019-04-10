package Enums;

/**
 * Enum that represents the file of a position on the board
 * @author Matt Lutz 33%
 * @author Jeriah Caplinger 33%
 * @author Kevin Filanowski 33%
 * @version March 20, 2019
 */
public enum File {
    /** Enum for file A Index 0 */
    A("a", 0),

    /** Enum for file B Index 1 */
    B("b", 1),

    /** Enum for file C Index 2 */
    C("c", 2),

    /** Enum for file D Index 3 */
    D("d", 3),

    /** Enum for file E Index 4 */
    E("e", 4),

    /** Enum for file F. Index 5 */
    F("f", 5),

    /** Enum for file G. Index 6 */
    G("g", 6),

    /** Enum for file H Index 7 */
    H("h", 7);

    /** String that holds the file */
    private String file;

    /** int that holds the index for the file */
    private int index;

    /**
     * Constructor for the File enum
     * @param file - String holds the file
     * @param index - int that holds the index for the file
     */
    private File(String file, int index){
        this.file = file;
        this.index = index;
    }

    /**
     * Returns the index of the file.
     * @return - Index of the file.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the String file.
     * @return - The string file.
     */
    public String getFile() {
        return file;
    }

    /**
     * Retrieve a File enumeration based on the index.
     * 
     * @param index - The index of the enumeration.
     * @return - A File enumeration corrosponding to this index.
     */
    public static File getFileFromIndex(int index) {
        for (File f : File.values()) {
            if (f.getIndex() == index) {
                return f;
            }
        }
        return null;
    }

    /**
     * Retrieve a File enumeration based on the index.
     *
     * @param letter - The index of the enumeration.
     * @return - A File enumeration corrosponding to this index.
     */
    public static File getFileFromLetter(String letter) {
        letter = letter.toLowerCase();
        for (File f : File.values()) {
            if (f.getFile().equals(letter)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Gets the max index for the file
     * @return - The max index for the file
     */
    public static int getMaxIndex(){
        int max = -1;
        for (File f: File.values()){
            if(f.getIndex() > max){
                max = f.getIndex();
            }
        }
        return max;
    }
}
