package org.example.calculator.core.parser.nodes;

import org.example.calculator.core.parser.token.TokenType;

import java.math.BigDecimal;

import static org.example.calculator.core.parser.token.TokenType.NUMBER;

/**
 * 纯数字节点，也可以称为叶子节点
 */
public class NumberASTNode implements ASTNode {

    private final BigDecimal value;

    public NumberASTNode(BigDecimal value) {
        this.value = value;
    }

    @Override
    public BigDecimal evaluate() {
        return value;
    }

    @Override
    public TokenType getTokenType() {
        return NUMBER;
    }
}
