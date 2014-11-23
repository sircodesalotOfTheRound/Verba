package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parsing.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokens.operators.mathop.NumericToken;
import com.verba.language.parsing.tokens.literals.QuoteToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-22.
 */
public class LiteralExpressionRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return (restOfLine.startsWith(NumericToken.class) || restOfLine.startsWith(QuoteToken.class));
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    LexInfo nextToken = restOfLine.first();
    if (nextToken.is(NumericToken.class)) return NumericExpression.read(parent, lexer);
    else if (nextToken.is(QuoteToken.class)) return QuoteExpression.read(parent, lexer);

    throw new NotImplementedException();
  }
}
