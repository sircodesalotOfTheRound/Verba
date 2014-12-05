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
      "json");

  private static final QSet<String> accessModifiers = new QSet<String>(
    "public", "private", "protected", "internal", "static"
  );

  private static final QSet<String> typeDeclaration = new QSet<String>(
    "val", "fn", "class", "trait", "sql", "extend", "namespace",
    "meta", "template", "markup", "proxy", "signature", "segment",
    "options", "option"
  );

  private static final QSet<String> declarationModifiers = new QSet<String>(
    "abstract", "mut", "native", "sensitive"
  );

  private static final QSet<String> functionModifiers = new QSet<String>(
    "sync", "async", "operator", "virtual", "override", "abstract", "fork"
  );

  private static final QSet<String> objectInstantiation = new QSet<String>(
    "new", "inject", "resolve"
  );

  private static final QSet<String> operators = new QSet<String>(
    "to", "in", "yield"
  );

  private static final QSet<String> reflection = new QSet<String>(
    "is", "isa", "hasa", "some", "none", "def"
  );

  private static final QSet<String> control = new QSet<String>(
    "if", "else", "assuming", "unless", "for", "while",
    "do", "break", "continue", "match", "case", "return"
  );

  private static final QSet<String> booleans = new QSet<String>(
    "true", "false"
  );

  private static final QSet<String> polymorphishm = new QSet<String>(
    "this", "base"
  );

  private static final QSet<String> exceptions = new QSet<String>(
    "throw", "try", "catch", "finally"
  );

  private static final QSet<String> otherKeywords = new QSet<String>(
      "withns", "threadlocal"
  );

  private static QSet<String> keywords = new Supplier<QSet<String>>() {
    @Override
    public QSet<String> get() {
      QSet<String> allKeywords = new QSet<String>();
      allKeywords.add(nativeTypes);
      allKeywords.add(accessModifiers);
      allKeywords.add(otherKeywords);
      allKeywords.add(typeDeclaration);
      allKeywords.add(declarationModifiers);
      allKeywords.add(functionModifiers);
      allKeywords.add(objectInstantiation);
      allKeywords.add(operators);
      allKeywords.add(reflection);
      allKeywords.add(control);
      allKeywords.add(booleans);
      allKeywords.add(polymorphishm);
      allKeywords.add(exceptions);

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

  public static boolean isAccessModifierKeyword(String fqn) {
    return KeywordToken.accessModifiers.contains(fqn);
  }

  public static boolean isNativeTypeKeyword(String fqn) {
    return KeywordToken.nativeTypes.contains(fqn);
  }
}

