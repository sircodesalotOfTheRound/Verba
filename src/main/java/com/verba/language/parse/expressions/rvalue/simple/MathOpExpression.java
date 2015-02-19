package com.verba.language.parse.expressions.rvalue.simple;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */

public class MathOpExpression extends VerbaExpression implements RValueExpression {
  private VerbaExpression lhs;
  private VerbaExpression rhs;
  private final LexInfo operationToken;

  public MathOpExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    // This checks for unary expressions (vs. binary expressions)
    if (!lexer.currentIs(MathOpToken.class)) {
      this.lhs = (VerbaExpression)RValueExpression.readNonMathExpression(parent, lexer);
    }

    this.operationToken = lexer.readCurrentAndAdvance(MathOpToken.class);
    this.rhs = (VerbaExpression)RValueExpression.readNonMathExpression(parent, lexer);
    this.closeLexingRegion();
  }

  public boolean hasLhs() { return this.lhs == null; }
  public VerbaExpression lhs() { return this.lhs; }
  public VerbaExpression rhs() { return this.rhs; }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  public LexInfo operator() {
    return this.operationToken;
  }

  public static MathOpExpression read(VerbaExpression parent, Lexer lexer) {
    return new MathOpExpression(parent, lexer);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }
}
