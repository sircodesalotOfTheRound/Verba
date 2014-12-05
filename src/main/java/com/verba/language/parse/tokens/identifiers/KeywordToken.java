package com.verba.language.parse.tokens.identifiers;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;

import java.util.function.Supplier;

public class KeywordToken extends IdentifierToken {

  private static final QSet<String> nativeTypes = new QSet<String>(
      // System types
      "byte", "numeric", "dynamic", "default",
      "int", "int8", "int16", "int32", "int64",
      "uint", "uint8", "uint16", "uint32", "uint64", "ascii",
      "float32", "float64",
      "char", "utc", "currency",
      "utf", "utf8", "utf16", "utf32", "utf8be", "utf16be", "utf32be",
      "unit", "object",
      "json", "sensitive");

  private static final QSet<String> otherKeywords = new QSet<String>(
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
      "sync", "async", "threadlocal");

  private static QSet<String> keywords = new Supplier<QSet<String>>() {
    @Override
    public QSet<String> get() {
      QSet<String> allKeywords = new QSet<String>();
      allKeywords.add(nativeTypes);
      allKeywords.add(otherKeywords);

      return allKeywords;
    }
  }.get();

  public static QIterable<String> nativeTypeKeywords() { return KeywordToken.nativeTypes; }
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

  public static boolean isNativeTypeKeyword(String fqn) {
    return KeywordToken.nativeTypes.contains(fqn);
  }
}

