package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.identifiers.IdentifierToken;

/**
 * Created by sircodesalot on 14-4-26.
 */
public class RValueExpressionBacktrackRule extends BacktrackRule {

  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    // Todo: This is not very specific.
    return (lexer.currentIs(IdentifierToken.class));
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return (VerbaExpression) RValueExpression.read(parent, lexer);
  }
}
