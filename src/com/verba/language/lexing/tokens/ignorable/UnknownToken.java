package com.verba.language.lexing.tokens.ignorable;

import com.verba.language.lexing.codestream.CodeStream;
import com.verba.language.lexing.tokenization.Token;

/**
 * Created by sircodesalot on 14-4-12.
 */
public class UnknownToken implements Token {
    private String representation;

    private UnknownToken(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return this.representation;
    }

    public static Token read(CodeStream codeStream) {
        return new UnknownToken(codeStream.read().toString());
    }
}
