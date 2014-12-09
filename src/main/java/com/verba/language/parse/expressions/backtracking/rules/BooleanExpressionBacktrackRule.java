package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.rvalue.simple.BooleanExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/9.
 */
public class BooleanExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return lexer.currentIs(KeywordToken.class, KeywordToken.TRUE)
      || lexer.currentIs(KeywordToken.class, KeywordToken.FALSE);
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return BooleanExpression.read(parent, lexer);
  }
}
