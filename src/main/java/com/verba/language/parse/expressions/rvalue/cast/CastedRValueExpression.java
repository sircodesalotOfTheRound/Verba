package com.verba.language.parse.expressions.rvalue.cast;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class CastedRValueExpression extends VerbaExpression implements RValueExpression {
  private TypeConstraintExpression toType;
  private RValueExpression rvalue;

  private CastedRValueExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
    this.toType = TypeConstraintExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

    this.rvalue = RValueExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  public static CastedRValueExpression read(VerbaExpression parent, Lexer lexer) {
    return new CastedRValueExpression(parent, lexer);
  }

  public TypeConstraintExpression toType() {
    return this.toType;
  }

  public RValueExpression rvalue() {
    return this.rvalue;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
