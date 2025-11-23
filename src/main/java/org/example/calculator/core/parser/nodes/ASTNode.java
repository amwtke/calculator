package org.example.calculator.core.parser.nodes;

import java.math.BigDecimal;

/**
 * 语法树节点接口
 */
public interface ASTNode {
    /**
     * 计算节点的值
     *
     * @return 返回节点的计算值
     */
    BigDecimal evaluate();
}
