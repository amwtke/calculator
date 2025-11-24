package org.example.calculator.core.parser;

import org.example.calculator.core.CalculatorException;
import org.example.calculator.core.parser.nodes.ASTNode;
import org.example.calculator.core.parser.nodes.NumberASTNode;
import org.example.calculator.core.parser.nodes.OpASTNode;
import org.example.calculator.core.parser.token.Token;
import org.example.calculator.core.parser.token.TokenType;

import static org.example.calculator.core.parser.token.TokenType.END;

/**
 * 按照四则运算的语法规则解析得到语法树
 */
public class ASTParser {
    private final TokenParser tokenParser;
    private Token currentToken;

    public ASTParser(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
        currentToken = tokenParser.getNextToken();
    }

    public ASTNode parse() {
//        ASTNode expression = parseExpression();
        ASTNode expression = parseExpression2(null);
        if (currentToken != null && currentToken.getType() != END && currentToken.getType() != TokenType.EQUALS) {
            throw new CalculatorException("语法错误：错误位置 " + currentToken.getPosition(), currentToken.getPosition());
        }
        return expression;
    }

    /**
     * 从当前位置解析表达式得到最终语法树
     * 最终语法树由多个运算单元term 使用 +/- 组合在一起得到
     *
     * @return 最终的语法树
     */
    private ASTNode parseExpression() {
        // 解析得到第一个运算单元 term
        ASTNode leftNode = parseTerm();

        // 循环扫描所有的运算单元，例如  1+2+3+4
        while (currentToken.getType() == TokenType.PLUS ||
                currentToken.getType() == TokenType.MINUS) {

            // 跳过 +- 运算符
            TokenType opType = currentToken.getType();
            checkAndAdvance(opType);

            // 解析得到后续的运算单元
            ASTNode rightNode = parseTerm();

            // 得到最终的语法树
            leftNode = new OpASTNode(leftNode, opType, rightNode);
        }

        return leftNode;
    }

    private ASTNode parseExpression2(ASTNode leftNode) {
        ASTNode innerLeftNode = leftNode == null ? parseFactorAndAdvance() : leftNode;
        TokenType currentOpType = currentToken.getType();
        ASTNode rightNode = getRecursiveRightNode(currentOpType);
        OpASTNode opASTNode = new OpASTNode(innerLeftNode, currentOpType, rightNode);
        if (currentToken.getType() == END || currentToken.getType() == TokenType.RPAREN) {
            return opASTNode;
        }
        return parseExpression2(opASTNode);
    }

    private ASTNode getRecursiveRightNode(TokenType currentOpType) {
        ASTNode rightNode = getRightNode();
        while (currentToken.getType().getPriority() > currentOpType.getPriority()) {
            //说明还要向后
            rightNode = new OpASTNode(rightNode, currentToken.getType(), getRightNode());
        }
        return rightNode;
    }

    private ASTNode getRightNode() {
        //符号
        TokenType currentOpType = currentToken.getType();
        checkAndAdvance(currentOpType);
        //数字
        ASTNode numberNode = parseFactorAndAdvance();
        //当前符号的下一个符号，包括end。
        TokenType nextOpTokenType = currentToken.getType();
        if (nextOpTokenType.equals(END) || nextOpTokenType.getPriority() <= currentOpType.getPriority()) {
            return numberNode;
        }
        return new OpASTNode(numberNode, nextOpTokenType, getRightNode());
    }

    private ASTNode parseFactorAndAdvance() {
        Token token = currentToken;

        switch (token.getType()) {
            case NUMBER:
                checkAndAdvance(TokenType.NUMBER);
                return new NumberASTNode(Double.parseDouble(token.getValue()));
            case LPAREN:
                checkAndAdvance(TokenType.LPAREN);
                ASTNode node = parseExpression2(null);
                checkAndAdvance(TokenType.RPAREN);
                return node;
            case PLUS:
            case MINUS:
                //走了两步，从一个符号跳到了下一个符号。
                checkAndAdvance(token.getType());
                ASTNode factorNode = parseFactorAndAdvance();
                if (token.getType() == TokenType.MINUS) {
                    return new OpASTNode(new NumberASTNode(0), TokenType.MINUS, factorNode);
                }
                return factorNode;

            default:
                throw new CalculatorException("语法错误: 期望数字或括号", token.getPosition());
        }
    }

    /**
     * 从当前位置解析表达式得到一个运算单元term
     * 多个运算单元term 使用 +/- 组合在一起得到最终语法树
     *
     * @return 运算单元
     */
    private ASTNode parseTerm() {
        // 解析得到第一个运算因子 factor
        ASTNode leftNode = parseFactor();

        // 循环扫描所有的运算因子，例如 1*2*3
        while (currentToken.getType() == TokenType.MULTIPLY ||
                currentToken.getType() == TokenType.DIVIDE) {

            // 跳过 */ 运算符
            TokenType opType = currentToken.getType();
            checkAndAdvance(opType);

            // 解析得到后续的运算因子
            ASTNode rightNode = parseFactor();

            // 得到最终的运算单元
            leftNode = new OpASTNode(leftNode, opType, rightNode);
        }

        return leftNode;
    }

    /**
     * 从当前位置解析表达式得到一个最小粒度的运算因子，包含数字、括号表达式
     *
     * @return 最小粒度的运算因子
     */
    private ASTNode parseFactor() {
        Token token = currentToken;

        switch (token.getType()) {
            case NUMBER:
                checkAndAdvance(TokenType.NUMBER);
                return new NumberASTNode(Double.parseDouble(token.getValue()));
            case LPAREN:
                checkAndAdvance(TokenType.LPAREN);
                ASTNode node = parseExpression();
                checkAndAdvance(TokenType.RPAREN);
                return node;
            case PLUS:
            case MINUS:
                //走了两步，从一个符号跳到了下一个符号。
                checkAndAdvance(token.getType());
                ASTNode factorNode = parseFactor();
                if (token.getType() == TokenType.MINUS) {
                    return new OpASTNode(new NumberASTNode(0), TokenType.MINUS, factorNode);
                }
                return factorNode;

            default:
                throw new CalculatorException("语法错误: 期望数字或括号", token.getPosition());
        }
    }

    private void checkAndAdvance(TokenType expectedType) {
        if (currentToken.getType() == expectedType) {
            currentToken = tokenParser.getNextToken();
        } else {
            throw new CalculatorException(
                    String.format("语法错误: 期望 %s, 实际得到 %s, 错误位置 %d",
                            expectedType, currentToken.getType(), currentToken.getPosition()),
                    currentToken.getPosition()
            );
        }
    }

}
