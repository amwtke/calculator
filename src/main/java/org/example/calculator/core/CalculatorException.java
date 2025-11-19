package org.example.calculator.core;

public class CalculatorException extends RuntimeException {
    private final int position;

    public CalculatorException(String message) {
        this(message, -1);
    }

    public CalculatorException(String message, int position) {
        super(message);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
