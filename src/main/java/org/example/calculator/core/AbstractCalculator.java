package org.example.calculator.core;

import org.example.calculator.core.parser.processor.PostProcessor;
import org.example.calculator.core.parser.processor.PreProcessor;

import java.util.LinkedList;

public abstract class AbstractCalculator {
    protected String expression;
    private Object result;

    private LinkedList<PreProcessor> preProcessors = new LinkedList<>();
    private LinkedList<PostProcessor> postProcessors = new LinkedList<>();

    public AbstractCalculator(String expression) {
        this.expression = expression;
        initPreProcessors();
        initPostProcessors();
    }

    private void initPostProcessors() {
    }

    private void initPreProcessors() {

    }

    void preProcess() {
        for (PreProcessor preProcessor : preProcessors) {
            this.expression = preProcessor.preProcess(this.expression);
        }
    }

    void postProcess() {
        for (PostProcessor postProcessor : postProcessors) {
            this.result = postProcessor.process(this.result);
        }
    }
}
