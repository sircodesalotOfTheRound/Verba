package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.backtracking.MismatchException;
import com.verba.language.parsing.expressions.categories.MathOperandExpression;
import com.verba.language.parsing.expressions.rvalue.math.RpnExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class MathExpressionBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    boolean followingIsMathExpression = false;
    lexer.setUndoPoint();
    if (lexer.notEOF()) {
      MathOperandExpression.read(null, lexer);
      followingIsMathExpression = lexer.currentIs(MathOpToken.class);
    }
    lexer.rollbackToUndoPoint();

    return followingIsMathExpression;
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
    return tryWithRollback(lexer, () -> RpnExpression.read(parent, lexer));
  }
}
