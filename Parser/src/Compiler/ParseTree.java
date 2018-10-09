package Compiler;

public class ParseTree {
        int key;
        String value;

        public ParseTree(int key, String value) {
            this.key = key;
            this.value = value;

        }
        @Override
        public String toString() {
            return   key + ")  " + value + "\n" ;
        }
    }
