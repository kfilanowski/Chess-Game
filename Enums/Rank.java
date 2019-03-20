package Enums;

public enum Rank {
    R1(8, 0),
    R2(7, 1),
    R3(6, 2),
    R4(5, 3),
    R5(4, 4),
    R6(3, 5),
    R7(2, 6),
    R8(1, 7);

    private int rankNum;
    private int index;

    private Rank(int rankNum, int index){
        this.rankNum = rankNum;
        this.index = index;
    }

    public int getRankNum() {
        return this.rankNum;
    }

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

    public static Rank getRankFromNum(int rankNum){
        for (Rank r: Rank.values()) {
            if (r.getRankNum() == rankNum) { return r; }
        }
        return null;
    }

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
