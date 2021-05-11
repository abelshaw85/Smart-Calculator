package calculator;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

public class UI {
	private static VariableManager variableManager;
	
	static {
		variableManager = VariableManager.getInstance();
	}
	
	private UI() {
	}
	
	public static void start() {
		System.out.println("====SMART CALCULATOR====");
    	System.out.println("Welcome to Smart Calculator!");
    	System.out.println("First time using this application? Type /help for more details!");
        try (Scanner scanner = new Scanner(System.in)) {
        	
	        while (true) {
	            String input = scanner.nextLine();
	
	            if (input == null || input.isEmpty()) {
	            	System.out.println("Invalid command. Type /help for tips on how to use this application.");
	                continue;
	            }
	
	            // If input begins with /, then it is a command.
	            if (input.charAt(0) == '/') {
	                if (manageCommand(input)) {
	                    continue;
	                } else { // If exit command is triggered, this is false
	                    break;
	                }
	            }
	
	            String[] parts = SmartCalculatorStringUtils.expressionToArray(input);
	
	            if (StringValidator.hasValidCharacters(input)) {
	                // Attempt to add variable if "=" is present
	                if (Arrays.asList(parts).contains("=")) {
	                    variableManager.addVariable(input);
	                    continue;
	                }
	
	                if (parts.length == 1) { // User only entered 1 value
	                    BigInteger value = variableManager.getVariableOrValue(parts[0]);
	
	                    // Print the variable's value, or the entered number, if it exists
	                    if (value != null) {
	                        System.out.println(value);
	                    } else {
	                        System.out.println("Unknown variable");
	                    }
	                }
	                // If the array contains more than 2 words and includes a + or -
	                else if (StringValidator.isValidExpression(parts)) {
	                    parts = variableManager.convertVariablesToValues(parts);
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
	
	                            // No matter how many + signs, its only a plus
	                            // Positive number of - signs means treat as + sign
	                            if (value.contains("+") || (value.contains("-") && value.length() % 2 == 0)) {
	                                stack.push(BigIntegerCalculator.add(firstValue, secondValue).toString());
	                            } else if (value.contains("-")) {
	                                stack.push(BigIntegerCalculator.subtract(firstValue, secondValue).toString());
	                            } else if (value.contains("*")) {
	                                stack.push(BigIntegerCalculator.multiply(firstValue, secondValue).toString());
	                            } else if (value.contains("/")) {
	                                stack.push(BigIntegerCalculator.divide(firstValue, secondValue).toString());
	                            } else if (value.contains("^")) { // Exponent must be int
	                                stack.push(BigIntegerCalculator.power(firstValue, secondValue.intValue()).toString());
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
	        System.out.println("Goodbye! Thank you for using SMART CALCULATOR!");
        }
	}

    // Return true if program should resume
    private static boolean manageCommand(String command) {
        switch (command) {
            case "/help":
                System.out.println("Smart Calculator is a command-line tool to perform simple arithmetic operations." +
                        "\nTo use: Write the expression you wish to calculate, e.g. '5 * 2'. Note that the calculator uses BODMAS " +
                        "order of operations, so enclose part of an expression in parenthesis to perform that part " +
                        "first." +
                        "\nTo store variables, use the format [variable name] = [value]" +
                        "\nTo check the value of a variable, simply type its name." +
                        "\nVariables can be used in expressions, for example:" +
                        "\n\tn = 5" +
                        "\n\tn * 3" +
                        "\n\tResult: 15" +
                        "\nValid operators:" +
                        "\n+ - * / ^" +
                        "\nCompatible with very large numbers." +
                        "\nTo exit the application, type /exit");
                return true;
            case "/exit":
                return false;
            default:
                System.out.println("Unknown command");
                return true;
        }
    }
}
