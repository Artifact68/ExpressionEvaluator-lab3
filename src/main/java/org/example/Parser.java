package org.example;

import java.util.Stack;

public class Parser {
    private VariableStore variableStore;

    public Parser(VariableStore variableStore) {
        this.variableStore = variableStore;
    }

    public double parse(String expression) throws Exception {
        expression = expression.replace(" ", ""); // Удалить пробелы
        return evaluateExpression(expression);
    }

    private double evaluateExpression(String expression) throws Exception {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // Если текущий символ - число или переменная
            if (Character.isDigit(ch) || Character.isAlphabetic(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || Character.isAlphabetic(expression.charAt(i)))) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                if (Character.isDigit(sb.charAt(0))) {
                    values.push(Double.parseDouble(sb.toString()));
                } else {
                    values.push(variableStore.getVariable(sb.toString()));
                }
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (isOperator(ch)) {
                while (!operators.empty() && precedence(ch) <= precedence(operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(ch);
            }
        }

        while (!operators.empty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private double applyOperation(char operator, double b, double a) throws Exception {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new Exception("Деление на ноль.");
                return a / b;
            default:
                throw new Exception("Неизвестный оператор: " + operator);
        }
    }
}
