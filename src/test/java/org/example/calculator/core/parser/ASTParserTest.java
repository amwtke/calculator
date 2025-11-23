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
        assertEquals(new BigDecimal("3"), ast.evaluate());
    }

    @Test
    void testMultiplicationPrecedence() {
        TokenParser tokenParser = new TokenParser("2+3*4");
        ASTParser parser = new ASTParser(tokenParser);
        ASTNode ast = parser.parse();
        assertEquals(new BigDecimal("14"), ast.evaluate());
    }

    @Test
    void testParenthesesPrecedence() {
        TokenParser tokenParser = new TokenParser("(2+3)*4");
        ASTParser parser = new ASTParser(tokenParser);
        ASTNode ast = parser.parse();
        assertEquals(new BigDecimal("20"), ast.evaluate());
    }
}
