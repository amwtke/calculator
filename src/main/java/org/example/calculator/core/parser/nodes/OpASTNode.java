package org.example.calculator.core.parser.nodes;

import org.example.calculator.core.CalculatorException;
import org.example.calculator.core.parser.token.TokenType;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 表达式节点，也称为非叶子节点
 */
public class OpASTNode implements ASTNode {

    private final ASTNode left;
    private final TokenType operator;
    private final ASTNode right;
    private final MathContext mathContext;

    public OpASTNode(ASTNode left, TokenType operator, ASTNode right, MathContext mathContext) {
        this.left = left;
        this.operator = operator;
        this.right = right;
        this.mathContext = mathContext;
    }

    @Override
    public BigDecimal evaluate() {
        BigDecimal leftVal = left.evaluate();
        BigDecimal rightVal = right.evaluate();

        switch (operator) {
            case PLUS:
                return leftVal.add(rightVal, mathContext);
            case MINUS:
                return leftVal.subtract(rightVal, mathContext);
            case MULTIPLY:
                return leftVal.multiply(rightVal, mathContext);
            case DIVIDE:
                if (rightVal.compareTo(BigDecimal.ZERO) == 0) {
                    throw new CalculatorException("不能除0");
                }
                return leftVal.divide(rightVal, mathContext);
            default:
                throw new CalculatorException("不支持的运算符: " + operator);
        }
    }
}
