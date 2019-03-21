package Enums;

/**
 * @author Matt Lutz 33% && Jeriah Caplinger 33% && Kevin Filanowski 33%
 * Enum that represents the file of a position on the board
 *
 */
public enum File {
    /** Enum for file A*/
    A("a", 0),

    /** Enum for file B*/
    B("b", 1),

    /** Enum for file C*/
    C("c", 2),

    /** Enum for file D*/
    D("d", 3),

    /** Enum for file E*/
    E("e", 4),

    /** Enum for file F*/
    F("f", 5),

    /** Enum for file G*/
    G("g", 6),

    /** Enum for file H*/
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


    public int getIndex() {
        return index;
    }

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
