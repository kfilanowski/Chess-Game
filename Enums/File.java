package Enums;

public enum File {
        A("a", 0),
        B("b", 1),
        C("c", 2),
        D("d", 3),
        E("e", 4),
        F("f", 5),
        G("g", 6),
        H("h", 7);

        private String file;
        private int index;

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
