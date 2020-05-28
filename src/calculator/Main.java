package calculator;

import java.math.BigInteger;
import java.util.*;

public class Main {
    private static Map<String, BigInteger> variables = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input == null || input.isEmpty()) {
                continue;
            }

            if (input.charAt(0) == '/') {
                if (manageCommand(input)) {
                    continue;
                } else { //if exit command is triggered, this is false
                    break;
                }
            }

            //input = formatLine(input);
            String[] parts = expressionToArray(input);

            if (checkChars(input)) {
                //Attempt to add variable if "=" is present
                if (Arrays.asList(parts).contains("=")) {
                    addVariable(input);
                    continue;
                }

                if (parts.length == 1) { //user only entered 1 value
                    BigInteger value = getVariableOrValue(parts[0]);

                    //print the variable's value, or the entered number, if it exists
                    if (value != null) {
                        System.out.println(value);
                    } else {
                        System.out.println("Unknown variable");
                    }
                }
                //if the array contains more than 2 words and includes a + or -
                else if (StringValidator.isValidExpression(parts)) {
                    parts = convertVariablesToValues(parts);
                    String[] postfixInput = PostfixConverter.infixToPostfix(parts);

                    Deque<String> stack = new ArrayDeque<>();
                    for (String value : postfixInput) {
                        if (StringValidator.isNumber(value)) {
                            stack.push(value);
                            continue;
                        }
                        if (StringValidator.isOperator(value)) {
                            BigInteger secondValue = new BigInteger(stack.pop());
                            BigInteger firstValue = new BigInteger(stack.pop());

                            // no matter how many + signs, its only a plus
                            // positive number of - signs means treat as + sign
                            if (value.contains("+") || (value.contains("-") && value.length() % 2 == 0)) {
                                stack.push(Calculator.add(firstValue, secondValue).toString());
                            } else if (value.contains("-")) {
                                stack.push(Calculator.subtract(firstValue, secondValue).toString());
                            } else if (value.contains("*")) {
                                stack.push(Calculator.multiply(firstValue, secondValue).toString());
                            } else if (value.contains("/")) {
                                stack.push(Calculator.divide(firstValue, secondValue).toString());
                            } else if (value.contains("^")) { //exponent must be int
                                stack.push(Calculator.power(firstValue, secondValue.intValue()).toString());
                            }
                        }
                    }
                    if (!stack.isEmpty()) {
                        System.out.println(stack.pop());
                    }
                } else {
                    System.out.println("Invalid expression");
                }
            }
        }
        System.out.println("Bye!");
    }

    private static BigInteger getVariableOrValue(String str) {
        if (variables.containsKey(str)) {
            return new BigInteger(variables.get(str).toString());
        }
        if (StringValidator.isNumber(str)) {
            return new BigInteger(str);
        }
        return null; //if the string is not a variable or a valid number
    }

    //Check input contains only valid characters (letters, numbers or operators).
    private static boolean checkChars(String input) {
        String validCharsRegex = "[()\\w\\s+-=*^]+";
        return input.matches(validCharsRegex);
    }

    //add spacing around =, ( and ), and remove excess whitespace
    private static String[] expressionToArray(String line) {
        StringBuilder formattedLine = new StringBuilder();
        String charactersToPad = "=()*/";
        String charactersToBlockPad = "+-";
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (charactersToPad.indexOf(c) >= 0) {
                formattedLine.append(" ");
                formattedLine.append(c);
                formattedLine.append(" ");
            } else if (charactersToBlockPad.indexOf(c) >= 0) {
                formattedLine.append(" ");
                formattedLine.append(c);
                while (line.charAt(i + 1) == c) {
                    formattedLine.append(c);
                    i++;
                }
                formattedLine.append(" ");
            } else {
                formattedLine.append(line.charAt(i));
            }
        }
        String whitespaceRegex = "(\\s+)";
        line = formattedLine.toString().replaceAll(whitespaceRegex, " ");
        line = line.trim();

        return line.split(" ");
    }

    private static String[] convertVariablesToValues(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            if (variables.containsKey(parts[i])) {
                //convert variables to their numerical values
                if (getVariableOrValue(parts[i]) != null) {
                    parts[i] = getVariableOrValue(parts[i]).toString();
                }
            }
        }
        return parts;
    }

    private static void addVariable(String input) {
        //as splitting at the =, need to remove whitespace
        String whitespaceRegex = "(\\s+)";
        input = input.replaceAll(whitespaceRegex, "");
        //split string into before/after the = sign
        String[] parts = input.split("=");
        String variableName = parts[0];
        BigInteger value = getVariableOrValue(parts[1]);
        String charactersRegex = "[a-zA-Z]+";
        if (!variableName.matches(charactersRegex)) {
            System.out.println("Invalid identifier");
            return;
        }
        if (value == null || parts.length > 2) {
            System.out.println("Invalid assignment");
            return;
        }
        variables.put(variableName, value); //either converts to int, or retrieves variable value
    }

    //return true if program should resume
    private static boolean manageCommand(String command) {
        switch (command) {
            case "/help":
                System.out.println("The program calculates the answer to add or subtract operations");
                return true;
            case "/exit":
                return false;
            default:
                System.out.println("Unknown command");
                return true;
        }
    }
}
