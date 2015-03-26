package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.UtfExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.literals.UtfToken;
import com.verba.language.parse.tokens.operators.mathop.NumericToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-22.
 */
public class LiteralExpressionRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return (restOfLine.startsWith(NumericToken.class) || restOfLine.startsWith(UtfToken.class));
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    LexInfo nextToken = restOfLine.first();
    if (nextToken.is(NumericToken.class)) return NumericExpression.read(parent, lexer);
    else if (nextToken.is(UtfToken.class)) return UtfExpression.read(parent, lexer);

    throw new NotImplementedException();
  }
}
