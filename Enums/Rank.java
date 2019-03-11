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
}
