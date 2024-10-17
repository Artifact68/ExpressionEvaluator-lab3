package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExpressionEvaluatorTest {
    @Test
    public void testEvaluateSimpleExpression() throws Exception {
        VariableStore variableStore = new VariableStore();
        variableStore.setVariable("x", 5);
        Parser parser = new Parser(variableStore);
        assertEquals(7.0, parser.parse("2 + 5"), 0.001);
    }

    @Test
    public void testEvaluateWithVariable() throws Exception {
        VariableStore variableStore = new VariableStore();
        variableStore.setVariable("y", 3);
        Parser parser = new Parser(variableStore);
        assertEquals(8.0, parser.parse("5 + y"), 0.001);
    }

    @Test(expected = Exception.class)
    public void testUndefinedVariable() throws Exception {
        VariableStore variableStore = new VariableStore();
        Parser parser = new Parser(variableStore);
        parser.parse("5 + z"); // z не определена
    }

    @Test
    public void testEvaluateExpressionWithParentheses() throws Exception {
        VariableStore variableStore = new VariableStore();
        variableStore.setVariable("y", 2);
        Parser parser = new Parser(variableStore);
        assertEquals(10.0, parser.parse("(3 + 2) * y"), 0.001);
    }

    @Test
    public void testEvaluateDivisionByZero() {
        VariableStore variableStore = new VariableStore();
        Parser parser = new Parser(variableStore);
        try {
            parser.parse("5 / 0");
            fail("Ожидалось исключение деления на ноль.");
        } catch (Exception e) {
            assertEquals("Деление на ноль.", e.getMessage());
        }
    }
}
