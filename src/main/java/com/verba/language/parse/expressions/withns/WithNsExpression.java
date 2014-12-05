package com.verba.language.parse.expressions.withns;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class WithNsExpression extends VerbaExpression {
  private final FullyQualifiedNameExpression namespace;

  public WithNsExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.WITHNS);
    this.namespace = FullyQualifiedNameExpression.read(this, lexer);
  }

  public FullyQualifiedNameExpression namespace() { return this.namespace; }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  public static WithNsExpression read(VerbaExpression parent, Lexer lexer) {
    return new WithNsExpression(parent, lexer);
  }
}
