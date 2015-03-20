package com.verba.language.parse.expressions.tags.hashtag;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.MetaTagExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parse.tokens.operators.tags.HashTagToken;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class HashTagExpression extends VerbaExpression implements MetaTagExpression {
  public FullyQualifiedNameExpression identifier;

  private HashTagExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(HashTagToken.class);
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, "]");
    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void afterContentsParsed(Build build) {

  }

  @Override
  public void afterSymbolsGenerated(Build build, SymbolTable table) {

  }

  @Override
  public void onResolveSymbols(Build build, SymbolTable table) {

  }

  @Override
  public void onValidate(Build build, SymbolTable table) {

  }

  public static HashTagExpression read(VerbaExpression parent, Lexer lexer) {
    return new HashTagExpression(parent, lexer);
  }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
