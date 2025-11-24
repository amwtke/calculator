package org.example.calculator.core.parser.token;

public enum Constant {
    PI("pi", "3.14"),
    E("e", "2.72");

    private final String symbol;
    private final String value;

    Constant(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }

    public static String getValueBySymbol(String symbol) {
        for (Constant constant : values()) {
            if (constant.getSymbol().equalsIgnoreCase(symbol)) {
                return constant.getValue();
            }
        }
        throw new RuntimeException("未找到常量: " + symbol);
    }
}
