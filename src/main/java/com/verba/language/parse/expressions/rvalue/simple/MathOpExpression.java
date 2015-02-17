package com.verba.language.parse.expressions.rvalue.simple;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */

public class MathOpExpression extends VerbaExpression {
  private final LexInfo operationToken;

  public MathOpExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.operationToken = lexer.readCurrentAndAdvance(MathOpToken.class);
    this.closeLexingRegion();
  }

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

  }
}
