package com.verba.language.parse.expressions.containers.tuple;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.DataContainerExpression;
import com.verba.language.parse.expressions.categories.TupleItemExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class TupleDeclarationExpression extends VerbaExpression implements TypeConstraintExpression,
  DataContainerExpression<TupleItemExpression> {

  QList<TupleItemExpression> tokens = new QList<>();

  public TupleDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);
    this.tokens = this.readContents(lexer);
    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  private QList<TupleItemExpression> readContents(Lexer lexer) {
    QList<TupleItemExpression> contents = new QList<>();

    lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
    while (lexer.notEOF() && !lexer.currentIs(EnclosureToken.class, ")")) {
      contents.add(TupleItemExpression.read(this, lexer));

      // If a comma is seen, consume it.
      if (lexer.currentIs(OperatorToken.class, ",")) lexer.readCurrentAndAdvance();
    }
    lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

    return contents;
  }

  public static boolean isTupleTypeDeclaration(Lexer lexer) {
    return lexer.currentIs(EnclosureToken.class, "(");
  }

  public static TupleDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new TupleDeclarationExpression(parent, lexer);
  }

  public String representation() {
    throw new NotImplementedException();
  }

  public boolean hasItems() {
    return this.tokens.any();
  }

  public long count() {
    return this.tokens.count();
  }

  public TupleItemExpression get(int index) {
    return this.tokens.get(index);
  }

  public QIterable<TupleItemExpression> items() {
    return this.tokens;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }
}
