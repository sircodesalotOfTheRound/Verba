package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-23.
 */
public class PolymorphicExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return restOfLine.startsWith(KeywordToken.class, KeywordToken.CLASS)
      || restOfLine.startsWith(KeywordToken.class, KeywordToken.TRAIT);
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return PolymorphicDeclarationExpression.read(parent, lexer);
  }
}
