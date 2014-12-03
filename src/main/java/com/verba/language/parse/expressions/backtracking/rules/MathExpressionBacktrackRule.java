package com.verba.language.parse.expressions.backtracking.rules;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRule;
import com.verba.language.parse.expressions.categories.MathOperandExpression;
import com.verba.language.parse.expressions.rvalue.math.RpnExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.MathOpToken;

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
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return RpnExpression.read(parent, lexer);
  }
}
