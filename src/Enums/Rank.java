package Enums;

public enum Rank {
    R1(1, 0),
    R2(2, 1),
    R3(3, 2),
    R4(4, 3),
    R5(5, 4),
    R6(6, 5),
    R7(7, 6),
    R8(8, 7);

    private int num;
    private int index;

    private Rank(int num, int index){
        this.num = num;
        this.index = index;
    }

    public int getNum() {
        return num;
    }

    public int getIndex() {
        return index;
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
}
