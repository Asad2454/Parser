package Compiler;

public class Errors {

        public static void missingOpen_Bracket() {
            Parser.errorList.addLast("Open brackets expected and '" + Parser.lookahead.value + "' found instead" + "\n");
        }

        public static void missingClose_Bracket() {
            Parser.errorList.addLast("Closing brackets expected and '" + Parser.lookahead.value + "' found instead" + "\n");
        }

        public static void missingIdentifier() {
            Parser.errorList.addLast("Missing 'Identifier'" + Parser.lookahead.value + " found instead" + "\n");
        }

        public static void unexpectedSymbol() {
            Parser.errorList.addLast("Unexpected symbol "+ Parser.lookahead.value + " found" + "\n");
        }

        public static void missingPunctuation() {
            Parser.errorList.addLast("Missing punctuation, "+ Parser.lookahead.value + " found" + "\n");
        }

        public static void missingData_Type() {
            Parser.errorList.addLast("Missing Data Type '" + Parser.lookahead.value + "' found instead" + "\n");
        }

        public static void assignmentOperator() {
            Parser.errorList.addLast("Missing assignment operator, "+ Parser.lookahead.value + " found" + "\n");
        }

        public static void logicalOperator() {
            Parser.errorList.addLast("Missing logical operator, "+ Parser.lookahead.value + " found" + "\n");
        }
}
