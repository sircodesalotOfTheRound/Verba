package com.verba.language.parse.expressions.rvalue.math;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class RpnExpression extends VerbaExpression implements RValueExpression {
  // Should probabaly be a tree rather than a list.
  private final RpnMap expressions;

  private RpnExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.expressions = new RpnMap(parent, lexer);
    this.closeLexingRegion();
  }

  private boolean isNextMathToken(Lexer lexer) {
    boolean isIt = false;

    lexer.setUndoPoint();
    lexer.advance();
    if (lexer.notEOF() && lexer.currentIs(MathOpToken.class)) {
      isIt = true;
    }
    lexer.rollbackToUndoPoint();

    return isIt;
  }

  public static RpnExpression read(VerbaExpression parent, Lexer lexer) {
    return new RpnExpression(parent, lexer);
  }

  public RpnMap expressions() {
    return this.expressions;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
