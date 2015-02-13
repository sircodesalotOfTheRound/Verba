package com.verba.language.parse.expressions.blockheader.sql;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class SqlExpression extends VerbaExpression {
  private FullyQualifiedNameExpression identifier;

  private SqlExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void onParse(VerbaExpression parent, Lexer lexer) {
    lexer.readNext(KeywordToken.class, KeywordToken.SQL);
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
  }

  public static VerbaExpression read(VerbaExpression parent, Lexer lexer) {
    return new SqlExpression(parent, lexer);
  }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
