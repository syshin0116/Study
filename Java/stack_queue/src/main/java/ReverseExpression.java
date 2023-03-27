import java.util.Stack;

public class ReverseExpression {
    public static String reverse(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                output.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                if (!stack.isEmpty() )
                    if (stack.peek() != '(') {
                        return "Invalid expression";
                    } else {
                        stack.pop();
                    }
            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    output.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }
        return output.toString();
    }

    public static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    public static void main(String[] args) {
        String expression1 = "a + b * (c - d) / e";
        String expression2 = "(a * (b + c) / d) * e + f";
        System.out.println("Input: " + expression1);
        System.out.println("Output: " + reverse(expression1));
        System.out.println("Input: " + expression2);
        System.out.println("Output: " + reverse(expression2));
    }
}