package org.example.calculator.core;

import org.example.calculator.core.parser.ASTParser;
import org.example.calculator.core.parser.TokenParser;
import org.example.calculator.core.parser.nodes.ASTNode;

import java.math.BigDecimal;
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

            TokenParser tokenParser = new TokenParser(expression);
            ASTParser astParser = new ASTParser(tokenParser);
            ASTNode ast = astParser.parse();
            double result = ast.evaluate();
            postProcess();
            // 格式化输出
            return formatResult(result);

        } catch (CalculatorException ex) {
            return "Error:" + ex.getMessage();
        } catch (Exception ex) {
            return "Error:计算错误 - " + ex.getMessage();
        }
    }

    private String formatResult(double result) {
        if (Double.isInfinite(result)) {
            throw new CalculatorException("计算结果超出范围");
        }
        if (Double.isNaN(result)) {
            throw new CalculatorException("计算结果非数字");
        }

        if (result == (long) result) {
            return String.valueOf((long) result);
        }

        String stringValue = String.valueOf(result);
        if (stringValue.contains("E") || stringValue.contains("e")) {
            // 使用BigDecimal避免科学计数法
            BigDecimal bd = new BigDecimal(stringValue);
            return bd.stripTrailingZeros().toPlainString();
        }

        return stringValue;
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
