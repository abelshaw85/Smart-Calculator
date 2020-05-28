package calculator;

import java.util.ArrayDeque;
import java.util.Deque;

public class PostfixConverter {

    public static String[] infixToPostfix(String[] inputsArray) {
        String[] inputs = new String[inputsArray.length + 1]; //this will make an array + 1 in size
        System.arraycopy(inputsArray, 0, inputs, 0, inputsArray.length); //copy values to larger array
        inputs[inputs.length - 1] = ")";

        Deque<String> stack = new ArrayDeque();
        StringBuilder postfixExpression = new StringBuilder();
        stack.push("(");

        for (String input : inputs) {
            if (StringValidator.isNumber(input)) {
                postfixExpression.append(input + " ");
            }
            if (input.equals("(")) {
                stack.push("(");
            }
            if (StringValidator.isOperator(input)) {
                while (!stack.isEmpty()) {
                    //if operator on stack has higher or equal precedence to "this" operator
                    if (getPrecedence(stack.peek()) >= getPrecedence(input)) {
                        postfixExpression.append(stack.pop() + " ");
                    } else {
                        break;
                    }
                }
                stack.push(input);
            }
            if (input.equals(")")) {
                while (!stack.isEmpty()) {
                    if (!stack.peek().equals("(")) {
                        postfixExpression.append(stack.pop() + " ");
                    } else {
                        break;
                    }
                }
                stack.pop();
            }
        }
        return postfixExpression.toString().split(" ");
    }

    private static int getPrecedence(String str) {
        if (str.equals("^")) {
            return 2;
        }
        if (str.equals("*") || str.equals("/")) {
            return 1;
        }
        if (str.contains("+") || str.contains("-")) {
            return 0;
        }
        return -1;
    }
}
