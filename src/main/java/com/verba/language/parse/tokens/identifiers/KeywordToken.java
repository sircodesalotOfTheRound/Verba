package com.verba.language.parse.tokens.identifiers;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.Symbol;

import java.util.function.Supplier;

public class KeywordToken extends IdentifierToken {

  public static final String BOOLEAN = "boolean";
  public static final String BYTE = "byte";
  public static final String NUMERIC = "numeric";
  public static final String POLY = "poly";
  public static final String MATRIX = "matrix";
  public static final String DYNAMIC = "dynamic";
  public static final String DEFAULT = "default";
  public static final String INT = "int";
  public static final String INT8 = "int8";
  public static final String INT16= "int16";
  public static final String INT32 = "int32";
  public static final String INT64 = "int64";
  public static final String UINT= "uint";
  public static final String UINT8 = "uint8";
  public static final String UINT16= "uint16";
  public static final String UINT32 = "uint32";
  public static final String UINT64 = "uint64";
  public static final String DECIMAL = "decimal";
  public static final String DECIMAL32 = "decimal32";
  public static final String DECIMAL64 = "decimal64";
  public static final String CURRENCY = "currency";
  public static final String CHAR = "char";
  public static final String ASCII = "ascii";
  public static final String UTF = "utf";
  public static final String UTF8 = "utf8";
  public static final String UTF16 = "utf16";
  public static final String UTF32= "utf32";
  public static final String UTF8BE = "utf8be";
  public static final String UTF16BE = "utf16be";
  public static final String UTF32BE= "utf32be";
  public static final String UNIT = "unit";
  public static final String OBJECT = "object";
  public static final String JSON = "json";

  private static final QSet<String> vmTypes = new QSet<String>(
    BOOLEAN, BYTE, NUMERIC, POLY, MATRIX, DEFAULT,
    INT, INT8, INT16, INT32, INT64,
    UINT, UINT8, UINT16, UINT32, UINT64,
    DECIMAL, DECIMAL32, DECIMAL64,
    CURRENCY,
    CHAR,
    ASCII,
    UTF, UTF8, UTF16, UTF32,
    UTF8BE, UTF16BE, UTF32BE,
    UNIT,
    OBJECT,
    DYNAMIC,
    JSON);

  public static final String PUBLIC = "public";
  public static final String PRIVATE = "private";
  public static final String PROTECTED = "protected";
  public static final String INTERNAL = "internal";
  public static final String STATIC = "static";
  public static final String INTEROP = "interop";

  public static final QSet<String> accessModifiers = new QSet<>(
    PUBLIC, PRIVATE, PROTECTED, INTERNAL, STATIC, INTEROP
  );

  public static final String VAL = "val";
  public static final String FN = "fn";
  public static final String ASM = "asm";
  public static final String CLASS = "class";
  public static final String TRAIT = "trait";
  public static final String SQL = "sql";
  public static final String NAMESPACE = "namespace";
  public static final String META = "meta";
  public static final String TEMPLATE = "template";
  public static final String MARKUP = "markup";
  public static final String PROXY = "proxy";
  public static final String SIGNATURE = "signature";
  public static final String SEGMENT = "segment";
  public static final String OPTIONS = "options";
  public static final String OPTION = "option";

  private static final QSet<String> typeDeclaration = new QSet<String>(
    ASM, VAL, FN, CLASS, TRAIT, SQL, NAMESPACE, META, TEMPLATE, MARKUP, PROXY,
    SIGNATURE, SEGMENT, OPTIONS, OPTION
  );

  public static final String INLINE = "inline";
  public static final String ABSTRACT = "abstract";
  public static final String MUT = "mut";
  public static final String NATIVE = "native";
  public static final String SENSITIVE = "sensitive";
  public static final String EXTEND = "extend";
  public static final String INJECT = "inject";

  private static final QSet<String> declarationModifiers = new QSet<String>(
    ABSTRACT, MUT, NATIVE, SENSITIVE, EXTEND, INJECT, INLINE
  );

  public static final String SYNC = "sync";
  public static final String ASYNC = "async";
  public static final String OPERATOR = "operator";
  public static final String VIRTUAL = "virtual";
  public static final String OVERRIDE = "override";
  public static final String FORK = "fork";

  private static final QSet<String> functionModifiers = new QSet<String>(
    ABSTRACT, SYNC, ASYNC, OPERATOR, VIRTUAL, OVERRIDE, FORK
  );

  public static final String NEW = "new";
  public static final String RESOLVE = "resolve";

  private static final QSet<String> objectInstantiation = new QSet<String>(
    NEW, INJECT, RESOLVE
  );

  private static final String TO = "to";
  private static final String IN = "in";
  private static final String YIELD = "yield";

  private static final QSet<String> operators = new QSet<String>(
    TO, IN, YIELD
  );

  public static final String IS = "is";
  public static final String ISA = "isa";
  public static final String HASA = "hasa";
  public static final String SOME = "some";
  public static final String NONE = "none";
  public static final String DEF = "def";

  private static final QSet<String> reflection = new QSet<String>(
    IS, ISA, HASA, SOME, NONE, DEF
  );

  public static final String IF = "if";
  public static final String ELSE = "else";
  public static final String ASSUMING = "assuming";
  public static final String UNLESS = "unless";
  public static final String FOR = "for";
  public static final String WHILE = "while";
  public static final String DO = "do";
  public static final String BREAK = "break";
  public static final String CONTINUE = "continue";
  public static final String MATCH = "match";
  public static final String CASE = "case";
  public static final String RETURN = "return";

  private static final QSet<String> control = new QSet<String>(
    IF, ELSE, ASSUMING, UNLESS, FOR, WHILE, DO, BREAK, CONTINUE,
    MATCH, CASE, RETURN
  );

  public static final String TRUE = "true";
  public static final String FALSE = "false";

  private static final QSet<String> booleans = new QSet<String>(
    TRUE, FALSE
  );

  public static final String THIS = "this";
  public static final String BASE = "base";

  private static final QSet<String> polymorphishm = new QSet<String>(
    THIS, BASE
  );

  public static final String THROW = "throw";
  public static final String TRY = "try";
  public static final String CATCH = "catch";
  public static final String FINALLY = "finally";

  private static final QSet<String> exceptions = new QSet<String>(
    THROW, TRY, CATCH, FINALLY
  );

  public static final String WITHNS = "withns";
  public static final String THREADLOCAL = "threadlocal";
  public static final String VOLATILE = "threadlocal";

  private static final QSet<String> otherKeywords = new QSet<String>(
    WITHNS, THREADLOCAL, VOLATILE
  );

  private static QSet<String> keywords = new Supplier<QSet<String>>() {
    @Override
    public QSet<String> get() {
      QSet<String> allKeywords = new QSet<String>();
      allKeywords.add(vmTypes);
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

  public static QIterable<String> platformTypeKeywords() { return KeywordToken.vmTypes; }
  public KeywordToken(String representation) {
    super(representation);
  }

  @Override
  public String toString() {
    return super.representation;
  }

  public static boolean isKeyword(String representation) {
    return KeywordToken.keywords.contains(representation);
  }

  public static boolean isAccessModifierKeyword(String representation) {
    return KeywordToken.accessModifiers.contains(representation);
  }

  public static boolean isFunctionModifierExpression(String representation) {
    return KeywordToken.functionModifiers.contains(representation);
  }

  public static boolean isNativeTypeKeyword(String representation) {
    return KeywordToken.vmTypes.contains(representation);
  }
}

