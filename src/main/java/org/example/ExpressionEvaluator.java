package org.example;

import java.util.Scanner;

public class ExpressionEvaluator {
    private VariableStore variableStore;

    public ExpressionEvaluator() {
        this.variableStore = new VariableStore();
    }

    public double evaluate(String expression) throws Exception {
        Parser parser = new Parser(variableStore);
        return parser.parse(expression);
    }

    public void requestVariableValues() {
        Scanner scanner = new Scanner(System.in);
        for (String var : variableStore.getVariables()) {
            System.out.print("Введите значение для переменной " + var + ": ");
            double value = scanner.nextDouble();
            variableStore.setVariable(var, value);
        }
    }

    public static void main(String[] args) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        evaluator.requestVariableValues();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение для вычисления: ");
        String expression = scanner.nextLine();

        try {
            double result = evaluator.evaluate(expression);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
