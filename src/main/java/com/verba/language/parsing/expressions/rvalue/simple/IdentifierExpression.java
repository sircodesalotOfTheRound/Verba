package com.verba.language.parsing.expressions.rvalue.simple;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.RValueExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.tokens.identifiers.IdentifierToken;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class IdentifierExpression extends VerbaExpression implements RValueExpression {
  private LexInfo identifier;

  public IdentifierExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.identifier = lexer.readCurrentAndAdvance(IdentifierToken.class);
    this.closeLexingRegion();
  }

  public static IdentifierExpression read(VerbaExpression parent, Lexer lexer) {
    return new IdentifierExpression(parent, lexer);
  }

  @Override
  public QIterable<ExpressionValidator> validators() {
    return null;
  }

  public LexInfo identifier() {
    return this.identifier;
  }

  public String representation() {
    return this.identifier.representation();
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}

