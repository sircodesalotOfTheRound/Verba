package com.verba.language.parsing.tokens;

import com.verba.language.parsing.tokens.identifiers.IdentifierToken;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by sircodesalot on 14-2-16.
 */

public class IterationKeywordToken extends IdentifierToken {
  private static final Set<String> keywords = ((Supplier<Set<String>>) () -> {
    String[] iterationKeywords = new String[]{
      "map", "reduce", "partition",
      "all", "any",
      "distinct",
      "oftype",
      "reverse",
      "flatten", "take", "takewhile", "skip", "skipwhile",
      "zip", "when",
      "sum", "average", "cast", "max", "min",
      "count", "exlcuding", "first", "last",
      "single", "groupby", "orderby", "inner", "leftOuter",

    };

    Set<String> keywordSet = new HashSet<String>();
    for (String keyword : iterationKeywords) keywordSet.add(keyword);

    return keywordSet;
  }).get();

  public IterationKeywordToken(String representation) {
    super(representation);
  }

  @Override
  public String toString() {
    return super.representation;
  }

  public static boolean isKeyword(String text) {
    return IterationKeywordToken.keywords.contains(text);
  }
}

