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

public class MathExpression extends VerbaExpression implements RValueExpression {
  private VerbaExpression lhs;
  private VerbaExpression rhs;
  private final LexInfo operationToken;

  public MathExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    // This checks for unary expressions (vs. binary expressions)
    if (!lexer.currentIs(MathOpToken.class)) {
      this.lhs = (VerbaExpression)RValueExpression.readIgnoreMathExpressions(parent, lexer);
    }

    this.operationToken = lexer.readCurrentAndAdvance(MathOpToken.class);
    this.rhs = readRhs(parent, lexer);
    this.closeLexingRegion();
  }

  // In some situations, RHS could be a mathematical sub tree.
  // For example: 4 + 5 + 6 needs to be parsed as:
  //    +
  //   / \
  //  4  +
  //    / \
  //   5  6
  // So we need to process when that happens.
  public VerbaExpression readRhs(VerbaExpression parent, Lexer lexer) {
    lexer.setUndoPoint();

    RValueExpression expression = RValueExpression.readIgnoreMathExpressions(parent, lexer);

    // If reading the last expression ended in a math operatr, then
    if (lexer.currentIs(MathOpToken.class)) {
      lexer.rollbackToUndoPoint();
      expression = MathExpression.read(this, lexer);
    } else {
      lexer.clearUndoPoint();
    }

    return (VerbaExpression) expression;
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

  public static MathExpression read(VerbaExpression parent, Lexer lexer) {
    return new MathExpression(parent, lexer);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }
}
