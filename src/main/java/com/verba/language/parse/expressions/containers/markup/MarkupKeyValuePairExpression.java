package com.verba.language.parse.expressions.containers.markup;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.MarkupRvalueExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-5-21.
 */
public class MarkupKeyValuePairExpression extends VerbaExpression {
  private final VerbaExpression key;
  private final MarkupRvalueExpression value;

  private MarkupKeyValuePairExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.key = VerbaExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(OperatorToken.class, "=");
    this.value = MarkupRvalueExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void parse(VerbaExpression parent, Lexer lexer) {

  }

  public static MarkupKeyValuePairExpression read(VerbaExpression parent, Lexer lexer) {
    return new MarkupKeyValuePairExpression(parent, lexer);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
