package com.verba.language.parse.expressions.rvalue.lambda;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.lambda.LambdaToken;

/**
 * Created by sircodesalot on 14-2-28.
 */
public class LambdaExpression extends VerbaExpression implements RValueExpression {
  private TypeConstraintExpression lvalue;
  private RValueExpression rvalue;

  public LambdaExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.lvalue = TypeConstraintExpression.read(this, lexer);

    lexer.readCurrentAndAdvance(LambdaToken.class);

    // Attempt to read RValueExpression
    this.rvalue = RValueExpression.read(this, lexer);
    if (this.rvalue == null) {
      FullyQualifiedNameExpression.read(this, lexer);
    }

    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  public static LambdaExpression read(VerbaExpression parent, Lexer lexer) {
    return new LambdaExpression(parent, lexer);
  }

  public TypeConstraintExpression lvalue() {
    return this.lvalue;
  }

  public RValueExpression rvalue() {
    return this.rvalue;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
