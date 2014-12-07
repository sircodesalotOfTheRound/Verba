package com.verba.language.parse.expressions.containers.markup;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.IdentifierToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-5-21.
 */
public class MarkupTagItemExpression extends VerbaExpression {

  private VerbaExpression identifier;
  private final QIterable<MarkupKeyValuePairExpression> items;
  private boolean isClosingTag = false;
  private boolean isSelfClosing = false;

  private MarkupTagItemExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.readOpening(lexer);
    this.identifier = this.readIdentifier(lexer);
    this.items = this.readItems(lexer);
    this.readClosing(lexer);

    this.closeLexingRegion();
  }

  private void readOpening(Lexer lexer) {
    if (lexer.currentIs(OperatorToken.class, "<")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, "<");
    }

    if (lexer.currentIs(OperatorToken.class, "/")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, "/");
      this.isClosingTag = true;
    }
  }

  private VerbaExpression readIdentifier(Lexer lexer) {
    return VerbaExpression.read(this, lexer);
  }

  private QIterable<MarkupKeyValuePairExpression> readItems(Lexer lexer) {
    QList<MarkupKeyValuePairExpression> values = new QList<>();
    while (lexer.notEOF() && lexer.currentIs(IdentifierToken.class)) {
      MarkupKeyValuePairExpression pairExpression = MarkupKeyValuePairExpression.read(this, lexer);
      values.add(pairExpression);
    }

    return values;
  }


  private void readClosing(Lexer lexer) {
    if (lexer.currentIs(OperatorToken.class, "/")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, "/");
      this.isSelfClosing = true;
    }

    if (lexer.currentIs(OperatorToken.class, ">")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ">");
    }
  }

  public QIterable<VerbaExpression> items() {
    return this.items.cast(VerbaExpression.class);
  }

  public VerbaExpression identifier() {
    return this.identifier;
  }

  public boolean isSelfClosing() {
    return this.isSelfClosing;
  }

  public boolean isClosingTag() {
    return this.isClosingTag;
  }

  public static MarkupTagItemExpression read(VerbaExpression parent, Lexer lexer) {
    return new MarkupTagItemExpression(parent, lexer);
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
