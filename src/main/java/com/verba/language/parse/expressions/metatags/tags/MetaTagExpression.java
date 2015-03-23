package com.verba.language.parse.expressions.metatags.tags;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;
import com.verba.language.parse.tokens.operators.tags.MetaTagToken;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class MetaTagExpression extends VerbaExpression implements com.verba.language.parse.expressions.categories.MetaTagExpression {
  public FullyQualifiedNameExpression identifier;

  private MetaTagExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(MetaTagToken.class);
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

  public static MetaTagExpression read(VerbaExpression parent, Lexer lexer) {
    return new MetaTagExpression(parent, lexer);
  }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }
}
