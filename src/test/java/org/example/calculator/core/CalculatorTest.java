package org.example.calculator.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void testBasicOperations() {
        assertEquals("3", calculator.calculate("1+2"));
        assertEquals("6", calculator.calculate("2*3"));
        assertEquals("2", calculator.calculate("4/2"));
    }

    @Test
    void testParentheses() {
        assertEquals("9", calculator.calculate("(1+2)*3"));
        assertEquals("22", calculator.calculate("4*(5+6)/2"));
    }

    @Test
    void testErrorCases() {
        assertEquals("Error:不能除0", calculator.calculate("1/0"));
        assertTrue(calculator.calculate("1+2/(3").startsWith("Error:"));
    }

    @Test
    void testDecimalNumbers() {
        assertEquals("6.28", calculator.calculate("3.14*2"));
    }
}