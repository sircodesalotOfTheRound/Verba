package com.verba.language.platform;

import com.verba.language.graph.symbols.table.tables.Scope;
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
}
