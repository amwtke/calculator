package org.example.calculator.core.parser;

import org.example.calculator.core.CalculatorException;
import org.example.calculator.core.parser.token.Token;
import org.example.calculator.core.parser.token.TokenType;

import java.util.function.IntPredicate;

/**
 * 从当前的字符串表达式expression中解析出来一个个独立的token，类型见 @TokenType
 */
public class TokenParser {
    private final String expression;
    private int position;
    private Character currentChar;

    public TokenParser(String expression) {
        this.expression = expression.replaceAll("\\s+", ""); // 移除所有空白字符
        this.position = 0;
        this.currentChar = !this.expression.isEmpty() ? this.expression.charAt(0) : null;
    }

    public Token getNextToken() {
        if (currentChar != null) {
            if (Character.isDigit(currentChar) || currentChar == '.') {
                return number();
            }
            TokenType tokenType = TokenType.fromSymbol(currentChar.toString());
            if (tokenType == TokenType.INVALID) {
                throw new CalculatorException("无法识别的字符: " + currentChar, position);
            }
            if (tokenType == TokenType.EQUALS && !reachEnd()) {
                throw new CalculatorException("字符=只能在最后 ", position);
            }
            Token token = new Token(tokenType, currentChar.toString(), position);
            advance();
            return token;
        }

        return new Token(TokenType.END, position);
    }

    private boolean reachEnd() {
        return position == expression.length() - 1;
    }

    private void advance() {
        position++;
        if (position >= expression.length()) {
            currentChar = null;
        } else {
            currentChar = expression.charAt(position);
        }
    }

    private Token number() {
        StringBuilder stringBuilder = new StringBuilder();
        int startPos = position;

        while (currentChar != null &&
                (Character.isDigit(currentChar) || currentChar == '.')) {
            stringBuilder.append(currentChar);
            advance();
        }

        String numString = stringBuilder.toString();
        // 验证数字格式
        if (numString.chars().filter(value -> value == '.').count() > 1) {
            throw new CalculatorException("无效的数字格式: " + numString, startPos);
        }
        // 检查数字格式
        if (numString.startsWith(".")) {
            throw new CalculatorException("数字不能以小数点开头: " + numString, startPos);
        }
        if (numString.endsWith(".")) {
            throw new CalculatorException("数字不能以小数点结尾: " + numString, startPos);
        }

        return new Token(TokenType.NUMBER, numString, startPos);
    }
}
