package com.verba.language.lexing.info;

import com.verba.language.lexing.tokenization.Token;

/**
 * Created by sircodesalot on 14-2-20.
 */
public class LexInfo {
    private Token token;
    private final String filename;
    private final int absolutePosition;
    private final Class type;
    private final int line;
    private final int column;

    public LexInfo(Token token, TokenPosition position) {
        this(token, position.filename(), position.absolutePosition(), position.line(), position.column());
    }

    public LexInfo(Token token, String filename, int absolutePosition, int line, int column) {
        this.token = token;
        this.line = line;
        this.absolutePosition = absolutePosition;
        this.column = column;
        this.filename = filename;
        this.type = token.getClass();
    }

    public Token getToken() {
        return token;
    }

    public int line() {
        return line;
    }

    public int column() {
        return column;
    }

    public Class type() {
        return this.type;
    }

    public int getLength() {
        return this.representation().length();
    }

    public String representation() {
        return this.token.toString();
    }

    public int absolutePosition() {
        return this.absolutePosition;
    }

    public String filename() {
        return this.filename;
    }

    public <T> boolean is(Class<T> type) {
        return type.isAssignableFrom(this.type);
    }

    public <T> boolean is(String representation) {
        return this.token.toString().equals(representation);
    }

    public <T> boolean is(Class<T> type, String representation) {
        return type.isAssignableFrom(this.type) && this.token.toString().equals(representation);
    }

    public String toString() {
        return this.representation();
    }

    public String toVerboseString() {
        return String.format("%s (%s %s:%s)", this.token, this.type, this.line, this.column);
    }
}
