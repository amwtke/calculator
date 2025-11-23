package org.example.calculator.core.parser.nodes;

import java.math.BigDecimal;

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
}
