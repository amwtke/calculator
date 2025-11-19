package org.example.calculator.core.parser;

import org.example.calculator.core.parser.token.Token;
import org.example.calculator.core.parser.token.TokenType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenParserTest {

    @Test
    void testNumberToken() {
        TokenParser parser = new TokenParser("123");
        Token token = parser.getNextToken();
        assertEquals(TokenType.NUMBER, token.getType());
        assertEquals("123", token.getValue());
    }

    @Test
    void testOperatorTokens() {
        TokenParser parser = new TokenParser("+-*/");
        assertEquals(TokenType.PLUS, parser.getNextToken().getType());
        assertEquals(TokenType.MINUS, parser.getNextToken().getType());
        assertEquals(TokenType.MULTIPLY, parser.getNextToken().getType());
        assertEquals(TokenType.DIVIDE, parser.getNextToken().getType());
    }

    @Test
    void testParenthesesTokens() {
        TokenParser parser = new TokenParser("()");
        assertEquals(TokenType.LPAREN, parser.getNextToken().getType());
        assertEquals(TokenType.RPAREN, parser.getNextToken().getType());
    }

    @Test
    void testDecimalNumber() {
        TokenParser parser = new TokenParser("3.14");
        Token token = parser.getNextToken();
        assertEquals(TokenType.NUMBER, token.getType());
        assertEquals("3.14", token.getValue());
    }
}