package org.example.calculator.core.parser.nodes;

/**
 * 纯数字节点，也可以称为叶子节点
 */
public class NumberASTNode implements ASTNode {

    private final double value;

    public NumberASTNode(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }
}
