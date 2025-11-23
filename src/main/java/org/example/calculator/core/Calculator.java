package org.example.calculator.core;

import org.example.calculator.core.parser.nodes.ASTNode;
import org.example.calculator.core.parser.ASTParser;
import org.example.calculator.core.parser.TokenParser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private static final int OUTPUT_SCALE = 4;
    private static final RoundingMode OUTPUT_ROUNDING = RoundingMode.HALF_UP;

    public String calculate(String expression) {
        try {
            if (expression == null || expression.trim().isEmpty()) {
                throw new CalculatorException("空表达式");
            }

            TokenParser tokenParser = new TokenParser(expression);
            ASTParser astParser = new ASTParser(tokenParser);
            ASTNode ast = astParser.parse();
            BigDecimal result = ast.evaluate();
            // 格式化输出
            return formatResult(result);

        } catch (CalculatorException ex) {
            return "Error:" + ex.getMessage();
        } catch (Exception ex) {
            return "Error:计算错误 - " + ex.getMessage();
        }
    }

    private String formatResult(BigDecimal result) {
        if (result == null) {
            throw new CalculatorException("计算结果无效");
        }

        BigDecimal rounded = result.setScale(OUTPUT_SCALE, OUTPUT_ROUNDING);
        BigDecimal normalized = rounded.stripTrailingZeros();
        return normalized.toPlainString();
    }

    public List<String> batchCalculate(List<String> expressions) {
        if (expressions == null || expressions.isEmpty()) {
            return new ArrayList();
        }
        List<String> result = new ArrayList();
        for (String expression : expressions) {
            String data = calculate(expression);
            result.add(data);
        }
        return result;
    }
}
