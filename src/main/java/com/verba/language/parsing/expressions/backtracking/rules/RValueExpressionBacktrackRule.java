package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.backtracking.MismatchException;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexList;

/**
 * Created by sircodesalot on 14-4-26.
 */
public class RValueExpressionBacktrackRule extends BacktrackRule {

  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return super.verifyThenRollback(lexer, lex -> {
      RValueExpression.read(parent, lexer);
    });
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
    return (VerbaExpression) RValueExpression.read(parent, lexer);
  }
}
