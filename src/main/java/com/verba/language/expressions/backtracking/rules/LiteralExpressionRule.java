package com.verba.language.expressions.backtracking.rules;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRule;
import com.verba.language.expressions.backtracking.MismatchException;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokens.NumericToken;
import com.verba.language.parsing.tokens.QuoteToken;

/**
 * Created by sircodesalot on 14-2-22.
 */
public class LiteralExpressionRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return (restOfLine.startsWith(NumericToken.class) || restOfLine.startsWith(QuoteToken.class));
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
    LexInfo nextToken = restOfLine.first();
    if (nextToken.is(NumericToken.class)) return NumericExpression.read(parent, lexer);
    else if (nextToken.is(QuoteToken.class)) return QuoteExpression.read(parent, lexer);

    throw MismatchException.getInstance();
  }
}
