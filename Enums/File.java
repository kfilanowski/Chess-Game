package Enums;

public enum File {
        A('a', 0),
        B('b', 1),
        C('c', 2),
        D('d', 3),
        E('e', 4),
        F('f', 5),
        G('g', 6),
        H('h', 7);

        private char file;
        private int num;

        private File(char file, int num){
            this.file = file;
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public int getFile() {
            return file;
        }
}
