package org.example.calculator.core.parser.processor.pre;

import org.example.calculator.core.parser.processor.PreProcessor;

public class FactorTokenPreProcessor implements PreProcessor {
    @Override
    public String preProcess(String expression) {
        return expression;
    }
}
