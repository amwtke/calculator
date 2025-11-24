package org.example.calculator.core.parser.processor.pre;

import org.example.calculator.core.parser.processor.PreProcessor;

public class IfNoEqualPreprocessor implements PreProcessor {
    @Override
    public String preProcess(String expression) {
        if (expression == null || expression.isEmpty()) {
            return expression;
        }
        if (expression.contains("=")) {
            return expression;
        }
        return expression + "=";
    }
}
