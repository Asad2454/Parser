package Compiler;

import java.io.IOException;
import java.util.LinkedList;

public class Home {

    static LinkedList<Token> tokenList = new LinkedList<Token>();

    public static void main(String args[]) throws IOException {
        String stringArray1;
        stringArray1 = Lexical.ReadFileToCharArray("C:\\SourceCode.txt");
        String stringArray2 = stringArray1 + " ";
        char[] stringArray = stringArray2.toCharArray();
        tokenList = Lexical.checkToken(stringArray);
        Lexical.Output();
        Parser.parse(tokenList);
    }
}