package org.example.calculator.core.parser.token;

public enum TokenType {
    PLUS("+", 1),
    MINUS("-", 1),
    MULTIPLY("*", 2),
    DIVIDE("/", 2),
    NUMBER("number", -1),
    LPAREN("(", -1),
    RPAREN(")", -1),
    EQUALS("=", -1),
    END("", -1),
    INVALID("#", -1),
    EXP("^", 3);

    private final String symbol;
    private final int priority;


    TokenType(String symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    /**
     * 根据 symbol 获取对应的 TokenType 枚举
     *
     * @param symbol 符号字符串（如 "+", "-" 等）
     * @return 匹配的 TokenType
     */
    public static TokenType fromSymbol(String symbol) {
        for (TokenType type : values()) {
            if (type.symbol.equals(symbol)) {
                return type;
            }
        }
        return INVALID;
    }

    /**
     * 获取该枚举对应的优先级
     *
     * @return 优先级，-1 表示不参与排序
     */
    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
