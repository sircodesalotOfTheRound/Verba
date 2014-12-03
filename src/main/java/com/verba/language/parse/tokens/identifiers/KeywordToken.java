package com.verba.language.parse.tokens.identifiers;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class KeywordToken extends IdentifierToken {
  private static final Set<String> keywords = ((Supplier<Set<String>>) () -> {
    String[] basicKeywords = new String[]{
      "public", "private", "protected", "internal",
      "static", "operator", "override", "segment", "virtual", "injected",
      "to", "in", "withns", "trait", "interface",
      "options", "option", "get", "set", "inline",
      "namespace", "class", "abstract", "extend", "sql", "native",
      "signature", "fn", "meta", "template", "fork", "proxy",
      "val", "mut", "yield",
      "new", "inject",
      "return",
      "if", "then", "else", "unless", "assuming",
      "until", "for", "while", "do", "break", "continue",
      "isa", "hasa",
      "throw", "try", "catch", "finally",
      "this", "base",
      "none", "some", "is",
      "def", "resolve",
      "match", "case",
      "true", "false",
      "sync", "async", "threadlocal",

      // System types
      "byte", "numeric", "dynamic", "default",
      "int8", "int16", "int32", "int64",
      "uint8", "uint16", "uint32", "uint64", "ascii",
      "float32", "float64",
      "char", "utc", "currency",
      "utf8", "utf16", "utf32", "utf8be", "utf16be", "utf32be",
      "unit", "object",
      "json", "sensitive"
    };

    Set<String> keywordSet = new HashSet<String>();
    for (String keyword : basicKeywords) {
      keywordSet.add(keyword);
    }

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

