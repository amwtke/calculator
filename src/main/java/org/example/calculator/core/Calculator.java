package org.example.calculator.core;

import org.example.calculator.core.parser.ASTParser;
import org.example.calculator.core.parser.TokenParser;
import org.example.calculator.core.parser.nodes.ASTNode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Calculator extends AbstractCalculator {
    public Calculator(String expression) {
        super(expression);
    }

    public String calculate() {
        try {
            preProcess();
            if (this.expression == null || this.expression.trim().isEmpty()) {
                throw new CalculatorException("空表达式");
            }

            TokenParser tokenParser = new TokenParser(this.expression);
            ASTParser astParser = new ASTParser(tokenParser);
            ASTNode ast = astParser.parse();
            BigDecimal result = ast.evaluate();
            postProcess();
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
            throw new CalculatorException("计算结果为空");
        }
        BigDecimal normalized = result.stripTrailingZeros();
        if (normalized.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        if (normalized.scale() < 0) {
            normalized = normalized.setScale(0, RoundingMode.UNNECESSARY);
        }
        return normalized.toPlainString();
    }

    public static List<String> batchCalculate(List<String> expressions) {
        if (expressions == null || expressions.isEmpty()) {
            return new ArrayList();
        }
        List<String> result = new ArrayList();
        for (String expression : expressions) {
            Calculator calculator = new Calculator(expression);
            String data = calculator.calculate();
            result.add(data);
        }
        return result;
    }
}
