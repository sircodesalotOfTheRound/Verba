package com.verba.language.parse.tokens.identifiers;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by sircodesalot on 14-2-17.
 */

public class SqlKeywordToken extends IdentifierToken {
  private static final Set<String> sqlkeywords = ((Supplier<Set<String>>) () -> {
    String[] sqlKeywords = new String[]{
      "from", "select", "as", "delete",
      "inner", "left", "right", "join", "on",
      "where", "between", "is",
      "execute",
      "union", "all",
      "insert", "into", "entries",
      "update", "set",
      "and", "or", "not",
      "groupby", "having",
      "orderby", "union", "descending", "ascending",
      "like", "distinct", "null", "top",
      "avg", "count", "first", "last", "min", "max", "sum",
      "upper", "lower", "len", "round",
      "case", "when", "then", "end",
      "use"
    };

    Set<String> keywordSet = new HashSet<String>();
    for (String sqlKeyword : sqlKeywords) keywordSet.add(sqlKeyword);
    return keywordSet;

  }).get();

  public SqlKeywordToken(String representation) {
    super(representation);
  }

  @Override
  public String toString() {
    return this.representation;
  }

  public static boolean isKeyword(String text) {
    return SqlKeywordToken.sqlkeywords.contains(text);
  }
}

