package com.verba.language.graph.expressions.events;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.expressions.events.interfaces.VerbaExpressionBuildEvent;
import com.verba.language.graph.expressions.events.interfaces.VerbaExpressionBuildEventSubscription;
import com.verba.language.graph.symbols.resolution.NamedValueExpressionTypeResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class NamedValueExpressionVerbaExpressionBuildEventHandler extends VerbaExpressionBuildEventSubscription<NamedValueExpression>
  implements VerbaExpressionBuildEvent.NotifySymbolTableVerbaExpressionBuildEvent
{
  private SymbolTable symbolTable;
  private NamedValueExpressionTypeResolver typeResolver;

  public NamedValueExpressionVerbaExpressionBuildEventHandler(NamedValueExpression expression) {
    super(expression);
  }

  @Override
  public void beforeSymbolsGenerated(Build profile, LitFileRootExpression staticSpace) {

  }

  @Override
  public void afterSymbolsGenerated(Build profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.typeResolver = new NamedValueExpressionTypeResolver(this.expression(), symbolTable);
  }

  @Override
  public void onResolveSymbols(Build profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }

  public Symbol resolvedType() {
    return this.typeResolver.resolvedType();
  }
}
