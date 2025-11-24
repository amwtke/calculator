package org.example.calculator.core.parser.nodes;

import org.example.calculator.core.CalculatorException;
import org.example.calculator.core.parser.token.TokenType;

/**
 * 表达式节点，也称为非叶子节点
 */
public class OpASTNode implements ASTNode {

    private final ASTNode left;
    private final TokenType operator;
    private final ASTNode right;

    public OpASTNode(ASTNode left, TokenType operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public double evaluate() {
        double leftVal = left.evaluate();
        double rightVal = right.evaluate();

        switch (operator) {
            case PLUS:
                return leftVal + rightVal;
            case MINUS:
                return leftVal - rightVal;
            case MULTIPLY:
                return leftVal * rightVal;
            case DIVIDE:
                if (rightVal == 0) {
                    throw new CalculatorException("不能除0");
                }
                return leftVal / rightVal;
            case EXP:
                return Math.pow(leftVal, rightVal);
            default:
                throw new CalculatorException("不支持的运算符: " + operator);
        }
    }

    @Override
    public TokenType getTokenType() {
        return this.operator;
    }
}
