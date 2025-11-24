package org.example.calculator.core.parser;

import org.example.calculator.core.parser.nodes.ASTNode;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ASTParserTest {

    @Test
    void testSimpleExpression() {
        TokenParser tokenParser = new TokenParser("1+2");
        ASTParser parser = new ASTParser(tokenParser);
        ASTNode ast = parser.parse();
        assertNotNull(ast);
        assertEquals(0, ast.evaluate().compareTo(new BigDecimal("3")));
    }

    @Test
    void testMultiplicationPrecedence() {
        TokenParser tokenParser = new TokenParser("2+3*4");
        ASTParser parser = new ASTParser(tokenParser);
        ASTNode ast = parser.parse();
        assertEquals(0, ast.evaluate().compareTo(new BigDecimal("14")));
    }

    @Test
    void testParenthesesPrecedence() {
        TokenParser tokenParser = new TokenParser("(2+3)*4");
        ASTParser parser = new ASTParser(tokenParser);
        ASTNode ast = parser.parse();
        assertEquals(0, ast.evaluate().compareTo(new BigDecimal("20")));
    }
}
