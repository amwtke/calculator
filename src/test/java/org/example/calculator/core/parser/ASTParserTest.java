package org.example.calculator.core.parser;

import org.example.calculator.core.parser.nodes.ASTNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ASTParserTest {

    @Test
    void testSimpleExpression() {
        TokenParser tokenParser = new TokenParser("1+2");
        ASTParser parser = new ASTParser(tokenParser);
        ASTNode ast = parser.parse();
        assertNotNull(ast);
        assertEquals(3.0, ast.evaluate(), 0.001);
    }

    @Test
    void testMultiplicationPrecedence() {
        TokenParser tokenParser = new TokenParser("2+3*4");
        ASTParser parser = new ASTParser(tokenParser);
        ASTNode ast = parser.parse();
        assertEquals(14.0, ast.evaluate(), 0.001);
    }

    @Test
    void testParenthesesPrecedence() {
        TokenParser tokenParser = new TokenParser("(2+3)*4");
        ASTParser parser = new ASTParser(tokenParser);
        ASTNode ast = parser.parse();
        assertEquals(20.0, ast.evaluate(), 0.001);
    }
}