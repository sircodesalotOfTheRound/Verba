package com.verba.language.platform.expressions;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class PlatformExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return PlatformTypeExpression.isPlatformTypeName(lexer);
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return PlatformTypeExpression.read(parent, lexer);
  }
}
