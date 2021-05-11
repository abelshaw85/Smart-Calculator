package calculator;

public class StringValidator {

    public static boolean isNumber(String number) {
        String validNumberRegex = "(\\+|-)?\\d+";
        if (number.matches(validNumberRegex)) {
            return true;
        }
        return false;
    }

    public static boolean isOperator(String input) {
        String operatorsRegex = "[\\+\\-]*";
        String operatorsRegex2 = "[\\*\\/\\^]";
        if (input.matches(operatorsRegex) || input.matches(operatorsRegex2)) {
            return true;
        }
        return false;
    }

    // Contains matching parenthesis, and at least one operator
    public static boolean isValidExpression(String[] parts) {
        int leftBrackets = 0;
        int rightBrackets = 0;
        boolean operatorFound = false;
        for (String part : parts) {
            if (StringValidator.isOperator(part)) {
                operatorFound = true;
            } else if (part.equals("(")) {
                leftBrackets++;
            } else if (part.equals(")")) {
                rightBrackets++;
            }
        }
        return operatorFound && leftBrackets == rightBrackets;
    }
    
    // Check input contains only valid characters (letters, numbers or operators).
    public static boolean hasValidCharacters(String input) {
        String validCharsRegex = "[()\\w\\s+-=*^]+";
        return input.matches(validCharsRegex);
    }
}
