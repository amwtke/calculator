package org.example.calculator.core.parser;

import org.example.calculator.core.CalculatorException;
import org.example.calculator.core.parser.nodes.ASTNode;
import org.example.calculator.core.parser.nodes.NumberASTNode;
import org.example.calculator.core.parser.nodes.OpASTNode;
import org.example.calculator.core.parser.token.Token;
import org.example.calculator.core.parser.token.TokenType;

import static org.example.calculator.core.parser.token.TokenType.END;
import static org.example.calculator.core.parser.token.TokenType.EQUALS;

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
        ASTNode expression = parseExpression(null);
        if (currentToken != null && currentToken.getType() != END && currentToken.getType() != TokenType.EQUALS) {
            throw new CalculatorException("语法错误：错误位置 " + currentToken.getPosition(), currentToken.getPosition());
        }
        return expression;
    }

    private ASTNode parseExpression(ASTNode leftNode) {
        ASTNode innerLeftNode = leftNode == null ? parseFactorAndAdvance() : leftNode;
        TokenType currentOpType = currentToken.getType();
        ASTNode rightNode = getRecursiveRightNode(currentOpType);
        OpASTNode opASTNode = new OpASTNode(innerLeftNode, currentOpType, rightNode);
        if (currentToken.getType() == END || currentToken.getType().equals(EQUALS) || currentToken.getType() == TokenType.RPAREN) {
            return opASTNode;
        }
        return parseExpression(opASTNode);
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
        if (nextOpTokenType.equals(END) || nextOpTokenType.equals(EQUALS) || nextOpTokenType.getPriority() <= currentOpType.getPriority()) {
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
                ASTNode node = parseExpression(null);
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
