package org.example.calculator.core.parser.nodes;

import org.example.calculator.core.CalculatorException;
import org.example.calculator.core.parser.token.TokenType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 表达式节点，也称为非叶子节点
 */
public class OpASTNode implements ASTNode {

    private static final MathContext MATH_CONTEXT = new MathContext(34, RoundingMode.HALF_UP);

    private final ASTNode left;
    private final TokenType operator;
    private final ASTNode right;

    public OpASTNode(ASTNode left, TokenType operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public BigDecimal evaluate() {
        BigDecimal leftVal = left.evaluate();
        BigDecimal rightVal = right.evaluate();

        switch (operator) {
            case PLUS:
                return leftVal.add(rightVal);
            case MINUS:
                return leftVal.subtract(rightVal);
            case MULTIPLY:
                return leftVal.multiply(rightVal);
            case DIVIDE:
                if (rightVal.compareTo(BigDecimal.ZERO) == 0) {
                    throw new CalculatorException("不能除0");
                }
                return leftVal.divide(rightVal, MATH_CONTEXT);
            case EXP:
                return pow(leftVal, rightVal);
            default:
                throw new CalculatorException("不支持的运算符: " + operator);
        }
    }

    private BigDecimal pow(BigDecimal base, BigDecimal exponent) {
        BigDecimal normalizedExponent = exponent.stripTrailingZeros();
        int exponentInt;
        try {
            exponentInt = normalizedExponent.intValueExact();
        } catch (ArithmeticException ex) {
            throw new CalculatorException("指数必须是整数");
        }

        if (exponentInt == 0) {
            return BigDecimal.ONE;
        }
        if (exponentInt < 0) {
            if (base.compareTo(BigDecimal.ZERO) == 0) {
                throw new CalculatorException("0不能作为负指数的底数");
            }
            BigDecimal positive = base.pow(Math.abs(exponentInt), MATH_CONTEXT);
            return BigDecimal.ONE.divide(positive, MATH_CONTEXT);
        }
        return base.pow(exponentInt, MATH_CONTEXT);
    }

    @Override
    public TokenType getTokenType() {
        return this.operator;
    }
}
