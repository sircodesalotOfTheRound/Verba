package com.verba.language.lexing.tokens;

import com.verba.language.lexing.codestream.CodeStream;
import com.verba.language.lexing.tokenization.Token;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by sircodesalot on 14-2-17.
 */

public class EnclosureToken implements Token {
    public static final String OPEN_BRACE = "{";
    public static final String CLOSE_BRACE = "}";
    public static final String OPEN_PARENS = "(";
    public static final String CLOSE_PARENS = ")";

    private static final Set<Character> enclosureTokens = ((Supplier<Set<Character>>) () -> {
        Character[] tokens = new Character[]{'{', '}', '(', ')', '[', ']', '"', '\''};

        Set<Character> tokenSet = new HashSet<Character>();
        for (Character token : tokens) tokenSet.add(token);

        return tokenSet;
    }).get();

    private final String representation;

    public EnclosureToken(String representation) {
        this.representation = representation;
    }

    public EnclosureToken(Character representation) {
        this.representation = representation.toString();
    }

    @Override
    public String toString() {
        return this.representation;
    }

    public static boolean isEnclosureToken(Character text) {
        return EnclosureToken.enclosureTokens.contains(text);
    }

    public static Token read(CodeStream codeStream) {
        return new EnclosureToken(codeStream.read());
    }

}

