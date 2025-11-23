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

    @Test
    void testFloatingPointPrecision() {
        // 双精度运算会产生精度误差，当前实现会返回 0.30000000000000004
        assertEquals("0.3", calculator.calculate("0.1+0.2"));
    }

    @Test
    void testRepeatingDecimal() {
        // 1/3 是无限循环小数，输出保留最多4位小数并四舍五入
        assertEquals("0.3333", calculator.calculate("1/3"));
    }

    @Test
    void testRoundingToFourDecimals() {
        // 1/6 = 0.16666..., 四舍五入到4位小数
        assertEquals("0.1667", calculator.calculate("1/6"));
    }
}
