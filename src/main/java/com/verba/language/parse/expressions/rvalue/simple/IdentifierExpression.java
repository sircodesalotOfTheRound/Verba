package com.verba.language.parse.expressions.rvalue.simple;

import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.IdentifierToken;

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

  public static IdentifierExpression read(VerbaExpression parent, Lexer lexer) {
    return new IdentifierExpression(parent, lexer);
  }

  public LexInfo identifier() {
    return this.identifier;
  }

  public String representation() {
    return this.identifier.representation();
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }

  @Override
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return null;
  }
}

