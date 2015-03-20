package com.verba.language.platform.expressions;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.ExpressionSource;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;

/**
 * All platform specific symbols are extracted from this expression.
 */
public class PlatformSourceExpression extends VerbaExpression implements ExpressionSource, SymbolTableExpression {
  public static final PlatformSourceExpression INSTANCE = new PlatformSourceExpression();

  private PlatformSourceExpression() {
    super(null, null);
  }

  @Override
  public void accept(Scope symbolTable) {

  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

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

}
