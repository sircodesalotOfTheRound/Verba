package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.rvalue.cast.CastedRValueExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class CastedRValueExpressionBacktrackRule extends BacktrackRule {
  @Override
  @Deprecated
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return false;
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return CastedRValueExpression.read(parent, lexer);
  }
}
