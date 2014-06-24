package com.verba.language.lexing.tokens.identifiers;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class KeywordToken extends IdentifierToken {
    private static final Set<String> keywords = ((Supplier<Set<String>>) () -> {
        String[] basicKeywords = new String[]{
            "public", "private", "protect", "internal",
            "static", "operator", "override", "segment", "virtual", "injected",
            "to", "in", "until", "friendly", "grab", "hashcode", "vnum", "trait",
            "options", "option", "enum", "get", "set",
            "namespace", "class", "abstract", "extend", "sql", "environment", "native",
            "signature", "fn", "task", "meta", "service", "event", "proxy",
            "val", "muta", "yield",
            "new", "inject", "injected", "async",
            "ret",
            "if", "then", "else", "for", "while", "do", "break", "continue", "parallel",
            "isa", "hasa",
            "try", "catch", "finally", "throw",
            "this", "base",
            "none", "some", "is", "isnt",
            "def", "json",
            "match", "case",
            "true", "false",
            "sync", "sensitive"
        };

        Set<String> keywordSet = new HashSet<String>();
        for (String keyword : basicKeywords) keywordSet.add(keyword);

        return keywordSet;
    }).get();

    public KeywordToken(String representation) {
        super(representation);
    }

    @Override
    public String toString() {
        return super.representation;
    }

    public static boolean isKeyword(String text) {
        return KeywordToken.keywords.contains(text);
    }
}

