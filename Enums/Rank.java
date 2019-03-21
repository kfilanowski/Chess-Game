package Enums;

/**
 * @author Matt Lutz 33% && Jeriah Caplinger 33% && Kevin Filanowski 33%
 * Enum that represents the rank of a chess piece
 */
public enum Rank {
    /** Enum for Rank 1*/
    R1(8, 0),

    /** Enum for Rank 2*/
    R2(7, 1),

    /** Enum for Rank 3*/
    R3(6, 2),

    /** Enum for Rank 4*/
    R4(5, 3),

    /** Enum for Rank 5*/
    R5(4, 4),

    /** Enum for Rank 6*/
    R6(3, 5),

    /** Enum for Rank 7*/
    R7(2, 6),

    /** Enum for Rank 8*/
    R8(1, 7);

    /** Holds the number of the rank*/
    private int rankNum;

    /** Holds the index for the rank*/
    private int index;

    /**
     * Constructor for the rank enum
     * @param rankNum - Holds the number of the rank
     * @param index - Holds the index of the rank
     */
    private Rank(int rankNum, int index){
        this.rankNum = rankNum;
        this.index = index;
    }

    /**
     * Gets the rank number
     *
     * @return - An int that contains the rank number
     */
    public int getRankNum() {
        return this.rankNum;
    }

    /**
     * Gets the index of the rank
     *
     * @return - An int that contains the index of the rank
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Retrieve a Rank enumeration based on the index.
     * 
     * @param index - The index of the enumeration.
     * @return - A Rank enumeration corrosponding to this index.
     */
    public static Rank getRankFromIndex(int index) {
        for (Rank r: Rank.values()) {
            if (r.getIndex() == index) { return r; }
        }
        return null;
    }

    /**
     * Retrieve a Rank enumeration based on the rank number.
     *
     * @param rankNum - The index of the enumeration.
     * @return - A Rank enumeration corrosponding to this index.
     */
    public static Rank getRankFromNum(int rankNum){
        for (Rank r: Rank.values()) {
            if (r.getRankNum() == rankNum) { return r; }
        }
        return null;
    }

    /**
     * Gets the max index for a rank
     *
     * @return - The max index for the rank
     */
    public static int getMaxIndex(){
        int max = -1;
        for (Rank r: Rank.values()){
            if(r.getIndex() > max){
                max = r.getIndex();
            }
        }
        return max;
    }
}
