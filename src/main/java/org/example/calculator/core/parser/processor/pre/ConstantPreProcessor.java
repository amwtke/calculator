package org.example.calculator.core.parser.processor.pre;

import org.example.calculator.core.parser.token.Constant;
import org.example.calculator.core.parser.processor.PreProcessor;

import java.util.regex.Pattern;

public class ConstantPreProcessor implements PreProcessor {
    @Override
    public String preProcess(String expression) {
        if (expression == null || expression.isEmpty()) {
            return expression;
        }
        String result = expression;
        for (Constant constant : Constant.values()) {
            String symbol = constant.getSymbol();
            String value = constant.getValue();
            String regex = "(?i)\\b" + Pattern.quote(symbol) + "\\b";
            result = result.replaceAll(regex, value);
        }
        return result;
    }
}
