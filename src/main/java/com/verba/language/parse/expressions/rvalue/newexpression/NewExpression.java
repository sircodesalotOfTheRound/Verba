package com.verba.language.parse.expressions.rvalue.newexpression;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.MathOperandExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.IdentifierToken;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class NewExpression extends VerbaExpression
  implements RValueExpression, MathOperandExpression
{
  private TypeConstraintExpression expression;

  public NewExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.NEW);
    this.expression = this.parseExpression(lexer);

    if (lexer.currentIs(EnclosureToken.class, "(")) {
      lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
      lexer.readCurrentAndAdvance(EnclosureToken.class, ")");
    }
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void onParse(VerbaExpression parent, Lexer lexer) {

  }

  private TypeConstraintExpression parseExpression(Lexer lexer) {
    if (lexer.currentIs(IdentifierToken.class)) return TypeConstraintExpression.read(this, lexer);
    else throw new NotImplementedException();
  }

  public static NewExpression read(VerbaExpression parent, Lexer lexer) {
    return new NewExpression(parent, lexer);
  }

  public TypeConstraintExpression expression() {
    return this.expression;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }
}
