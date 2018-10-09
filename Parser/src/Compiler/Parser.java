package Compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Parser {

    static LinkedList<String> errorList = new LinkedList<>();
    static LinkedList<Token> tokens;
    static Token lookahead;
    static LinkedList<ParseTree> nodeList = new LinkedList<>();
    static int b = 0;
    static ParseTree node = null;
//-------------------------------------------------------START----------------------------------------------------------

    public static LinkedList<String> parse(LinkedList<Token> tokens) throws FileNotFoundException {
        Parser.tokens = (LinkedList<Token>) tokens.clone();
        lookahead = Parser.tokens.getFirst();
        
        Function();

        if (lookahead.key != 0)
            Errors.unexpectedSymbol();

        int a = errorList.size();

        if (a == 0) {
            System.out.println("Program compiled with '0' errors");
            System.out.println("Parse Tree Created with '" + b + "' calls");
        }
        else {
            System.out.println("Program compiled with '" + a + "' errors");
        }

        save2(errorList);
        save3(nodeList);
        return errorList;
    }
//---------------------------------------------------FUNCTION-----------------------------------------------------------
    private static void Function(){
        node = new ParseTree(b,"Function");
        nodeList.addLast(node);
        b++;
        Type_();
        Identifier();
        OpenBracket();
        ArgList();
        CloseBracket();
        CompoundStmt();
    }

    private static void CompoundStmt() {
        node = new ParseTree(b,"CompoundStmt");
        nodeList.add(node);
        b++;
        CurlyOpen();
        StmtList();
        CurlyClose();
    }

    private static void CurlyClose() {
        if (lookahead.key == 7){
            node = new ParseTree(b,"Close Bracket");
            nodeList.add(node);
            b++;
            nextToken();
        }
        else {
            Errors.missingClose_Bracket();
        }
    }

    private static void StmtList() {
        node = new ParseTree(b,"StmtList");
        nodeList.add(node);
        b++;
        Stmt();
        StmtList_();
    }

    private static void StmtList_() {
        node = new ParseTree(b,"StmtList_");
        nodeList.add(node);
        b++;
        if (lookahead.key == 8 ||
                lookahead.key == 2 ||
                lookahead.key == 6 ||
                lookahead.key == 1 ||
                lookahead.key == 9){
            Stmt();
            StmtList_();
        }
    }

    private static void Stmt() {
        node = new ParseTree(b,"Stmt");
        nodeList.add(node);
        b++;
        if (lookahead.key == 8){
            WhileStmt();
        }
        else if (lookahead.key == 2){
            Expr();
            SemiColon();
        }
        else if (lookahead.key == 6){
            CompoundStmt();
        }
        else if (lookahead.key == 1){
            Declaration();
        }
        else if (lookahead.key == 9){
            SemiColon();
        }
    }

    private static void Expr() {
        node = new ParseTree(b,"Expr");
        nodeList.add(node);
        b++;
        if (lookahead.key == 2){
            Identifier();

            if (lookahead.key == 16){
                nextToken();
                Mag();
            }
            else {
                Errors.assignmentOperator();
            }
        }
        else {
            Errors.missingIdentifier();
        }
    }

    private static void WhileStmt() {
        node = new ParseTree(b,"WhileStmt");
        nodeList.add(node);
        b++;
        if (lookahead.key == 8){
            nextToken();
            OpenBracket();
            BoolExpr();
            CloseBracket();
            Stmt();
        }
        else{
            Errors.unexpectedSymbol();
        }
    }

    private static void BoolExpr() {
        node = new ParseTree(b,"BoolExpr");
        nodeList.add(node);
        b++;
        Identifier();
        Compare();
        Mag();
    }

    private static void Mag() {
        node = new ParseTree(b,"Mag");
        nodeList.add(node);
        b++;
        Term();
        Mag_();
    }

    private static void Mag_() {
        node = new ParseTree(b,"Mag_");
        nodeList.add(node);
        b++;
        if (lookahead.key == 14){
            nextToken();
            Term();
            Mag_();
            }
        else if (lookahead.key == 15){
            nextToken();
            Term();
            Mag_();
        }
    }

    private static void Term() {
        node = new ParseTree(b,"Term");
        nodeList.add(node);
        b++;
        Factor();
        Term_();
    }

    private static void Term_() {
        node = new ParseTree(b,"Term_");
        nodeList.add(node);
        b++;
        if (lookahead.key == 12){
            nextToken();
            Factor();
            Term_();
        }
        else if (lookahead.key == 13){
            nextToken();
            Factor();
            Term_();
        }
    }

    private static void Factor() {
        node = new ParseTree(b,"Factor");
        nodeList.add(node);
        b++;
        if (lookahead.key == 3){
            OpenBracket();
            Expr();
            CloseBracket();
        }
        else if (lookahead.key == 2){
            Identifier();
        }
        else if (lookahead.key == 11){
            nextToken();
        }
    }

    private static void Compare() {
        node = new ParseTree(b,"Compare");
        nodeList.add(node);
        b++;

        if (lookahead.key == 10){
            nextToken();
        }
        else {
            Errors.logicalOperator();
        }
    }

    private static void Declaration() {
        node = new ParseTree(b,"Declaration");
        nodeList.add(node);
        b++;
        Type_();
        IdentList();
        SemiColon();
    }

    private static void IdentList() {
        node = new ParseTree(b,"IdentList");
        nodeList.add(node);
        b++;

        nextToken();
    }

    private static void SemiColon() {
        if (lookahead.key == 9){
            nextToken();
        }
        else {
            Errors.missingPunctuation();
        }
    }

    private static void CurlyOpen() {
        node = new ParseTree(b,"OpenBracket");
        nodeList.add(node);
        b++;

        if (lookahead.key == 6){
            nextToken();
        }
        else {
            Errors.missingOpen_Bracket();
        }
    }

    private static void CloseBracket() {
        node = new ParseTree(b,"CloseBracket");
        nodeList.add(node);
        b++;

        if (lookahead.key == 4){
            nextToken();
        }
        else {
            Errors.missingOpen_Bracket();
        }
    }

    private static void ArgList() {
        node = new ParseTree(b,"ArgList");
        nodeList.add(node);
        b++;
        Arg();
        ArgList_();
    }

    private static void ArgList_() {
        node = new ParseTree(b,"ArgList_");
        nodeList.add(node);
        b++;
        if (lookahead.key == 5){
            Comma();
            Arg();
            ArgList_();
        }
    }

    private static void Comma() {
        if (lookahead.key == 5){
            nextToken();
        }
        else {
            Errors.missingPunctuation();
        }
    }

    private static void Arg() {
        node = new ParseTree(b,"Arg");
        nodeList.add(node);
        b++;
        Type_();
        Identifier();
    }

    private static void OpenBracket() {
        node = new ParseTree(b,"OpenBracket");
        nodeList.add(node);
        b++;

        if (lookahead.key == 3){
            nextToken();
        }
        else {
            Errors.missingOpen_Bracket();
        }
    }

    private static void Identifier() {
        node = new ParseTree(b,"Identifier");
        nodeList.add(node);
        b++;
        if (lookahead.key == 2){
            nextToken();
        }
        else {
            Errors.missingIdentifier();
        }
    }

    private static void Type_() {
        node = new ParseTree(b,"Type");
        nodeList.add(node);
        b++;

        if (lookahead.key == 1){
            nextToken();
        }
        else {
            Errors.missingData_Type();
        }
    }

    private static void nextToken() {
        tokens.pop();
        if (tokens.isEmpty())
            lookahead = new Token(0,"");
        else
            lookahead = tokens.getFirst();
    }
    public static void save2(LinkedList<String> haha) throws FileNotFoundException {
        String tmp = haha.toString();
        PrintWriter a = new PrintWriter(new FileOutputStream("Errors.txt"));
        a.write(tmp);
        a.close();
    }

    public static void save3(LinkedList<ParseTree> haha) throws FileNotFoundException {
        String tmp = haha.toString();
        PrintWriter a = new PrintWriter(new FileOutputStream("ParseTree.txt"));
        a.write(tmp);
        a.close();
    }
}
