package Compiler;

public class Token {
    int key;
    String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Token(int key, String value) {
        this.key = key;
        this.value = value;

    }
    @Override
    public String toString() {
        return   key + " | " + value + "\n" ;
    }
}
