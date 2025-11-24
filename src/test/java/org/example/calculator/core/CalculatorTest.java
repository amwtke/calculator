package org.example.calculator.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void testBasicOperations() {
        assertEquals("24", calculator.calculate("1*2*3*4"));
        assertEquals("10", calculator.calculate("1+2+3+4"));
        assertEquals("3", calculator.calculate("1+2"));
        assertEquals("6", calculator.calculate("2*3"));
        assertEquals("2", calculator.calculate("4/2"));
        assertEquals("6", calculator.calculate("1+4/2+3"));
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
    void testDoublePrecisionHandling() {
        assertEquals("0.75", calculator.calculate("0.5+0.25"));
    }

    @Test
    void testDoublePrecisionHandling2() {
        assertEquals("0.3", calculator.calculate("0.1+0.2"));
        assertEquals("0.1", calculator.calculate("0.3-0.2"));
    }

    @Test
    void testExponentOperator() {
        assertEquals("56", calculator.calculate("1+3^3*2+1"));
        assertEquals("29", calculator.calculate("1+3^3+1"));
        assertEquals("49", calculator.calculate("1+(2+3)^2*2-2"));
        assertEquals("56", calculator.calculate("2+3^3*2"));
        assertEquals("8", calculator.calculate("2^3"));
        assertEquals("27", calculator.calculate("3^3"));
        assertEquals("3", calculator.calculate("(3^3+3)/10"));
        assertEquals("16", calculator.calculate("2*2^3"));
    }

    @Test
    void testDecimalNumbers() {
        assertEquals("6.28", calculator.calculate("3.14*2"));
    }

    @Test
    void testPiConstantSupport() {
        assertEquals("3.14", calculator.calculate("pi"));
        assertEquals("6.28", calculator.calculate("2*pi"));
    }

    @Test
    void testFactorialOperator() {
        assertEquals("120", calculator.calculate("5!"));
        assertEquals("60", calculator.calculate("5!/2"));
        assertEquals("122", calculator.calculate("5!/2+2"));
        assertEquals("122", calculator.calculate("5!+2"));
        assertEquals("722", calculator.calculate("(1+5)!+2"));
    }
}
