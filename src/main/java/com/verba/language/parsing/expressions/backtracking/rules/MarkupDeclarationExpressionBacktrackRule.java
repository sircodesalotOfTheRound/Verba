package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.backtracking.MismatchException;
import com.verba.language.parsing.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-5-22.
 */
public class MarkupDeclarationExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return lexer.currentIs(OperatorToken.class, "<");
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
    return MarkupDeclarationExpression.read(parent, lexer);
  }
}
