package com.verba.language.parse.expressions.containers.array;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.DataContainerExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parse.tokens.operators.mathop.NumericToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class ArrayDeclarationExpression extends VerbaExpression implements RValueExpression,
  DataContainerExpression<VerbaExpression> {

  QList<VerbaExpression> items = new QList<>();

  public ArrayDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(EnclosureToken.class, "[");
    while (lexer.notEOF() && !lexer.currentIs(EnclosureToken.class, "]")) {
      items.add(VerbaExpression.read(parent, lexer));
      if (lexer.currentIs(OperatorToken.class, ",")) lexer.readCurrentAndAdvance();
    }
    lexer.readCurrentAndAdvance(EnclosureToken.class, "]");
    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  private LexInfo readContents(Lexer lexer) {
    return lexer.readCurrentAndAdvance(NumericToken.class);
  }

  @Override
  public QIterable<VerbaExpression> items() {
    return this.items;
  }

  public static ArrayDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new ArrayDeclarationExpression(parent, lexer);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }
}

