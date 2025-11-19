package org.example.calculator.core.parser.token;

public enum TokenType {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    NUMBER("number"),
    LPAREN("("),
    RPAREN(")"),
    EQUALS("="),
    END(""),
    INVALID("#");

    private final String symbol;


    TokenType(String symbol) {
        this.symbol = symbol;
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

    @Override
    public String toString() {
        return symbol;
    }
}
