package org.example.calculator.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatorTest {
    private String calculate(String expression) {
        return new Calculator(expression).calculate();
    }

    @Test
    void testBasicOperations() {
        assertEquals("24", calculate("1*2*3*4"));
        assertEquals("10", calculate("1+2+3+4"));
        assertEquals("3", calculate("1+2"));
        assertEquals("6", calculate("2*3"));
        assertEquals("2", calculate("4/2"));
        assertEquals("6", calculate("1+4/2+3"));
    }

    @Test
    void testParentheses() {
        assertEquals("9", calculate("(1+2)*3"));
        assertEquals("22", calculate("4*(5+6)/2"));
    }

    @Test
    void testErrorCases() {
        assertEquals("Error:不能除0", calculate("1/0"));
        assertTrue(calculate("1+2/(3").startsWith("Error:"));
    }

    @Test
    void testDoublePrecisionHandling() {
        assertEquals("0.75", calculate("0.5+0.25"));
    }

    @Test
    void testDoublePrecisionHandling2() {
        assertEquals("0.3", calculate("0.1+0.2"));
        assertEquals("0.1", calculate("0.3-0.2"));
    }

    @Test
    void testExponentOperator() {
        assertEquals("56", calculate("1+3^3*2+1"));
        assertEquals("29", calculate("1+3^3+1"));
        assertEquals("49", calculate("1+(2+3)^2*2-2"));
        assertEquals("56", calculate("2+3^3*2"));
        assertEquals("8", calculate("2^3"));
        assertEquals("27", calculate("3^3"));
        assertEquals("3", calculate("(3^3+3)/10"));
        assertEquals("16", calculate("2*2^3"));
    }

    @Test
    void testTESTOperator() {
        assertEquals("50", calculate("2@3^2*2"));
        assertEquals("98", calculate("1+2^2@3*3+1"));
        assertEquals("8", calculate("2@3@2@+1"));
        assertEquals("17", calculate("1+2^3@1"));
    }

    @Test
    void testDecimalNumbers() {
        assertEquals("6.28", calculate("3.14*2"));
    }

    @Test   
    void testPiConstantSupport() {
        assertEquals("6.28", calculate("2*pi"));
        assertEquals("4.14", calculate("pi+1"));
    }

    @Test
    void testFactorialOperator() {
        assertEquals("120", calculate("5!"));
        assertEquals("60", calculate("5!/2"));
        assertEquals("122", calculate("5!/2+2"));
        assertEquals("122", calculate("5!+2"));
        assertEquals("722", calculate("(1+5)!+2"));
    }
}
