package com.verba.language.build.nodeevents.subscriptions;

import com.verba.language.build.configuration.BuildProfile;
import com.verba.language.build.nodeevents.BuildEvent;
import com.verba.language.build.nodeevents.BuildEventSubscription;
import com.verba.language.graph.symbols.resolution.NamedValueExpressionTypeResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class NamedValueExpressionBuildEventHandler extends BuildEventSubscription<NamedValueExpression>
  implements BuildEvent.NotifySymbolTableBuildEvent
{
  private SymbolTable symbolTable;
  private NamedValueExpressionTypeResolver typeResolver;

  public NamedValueExpressionBuildEventHandler(NamedValueExpression expression) {
    super(expression);
  }

  @Override
  public void beforeSymbolsGenerated(BuildProfile profile, LitFileRootExpression staticSpace) {

  }

  @Override
  public void afterSymbolsGenerated(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.typeResolver = new NamedValueExpressionTypeResolver(this.expression(), symbolTable);
  }

  @Override
  public void onResolveSymbols(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }

  public Symbol resolvedType() {
    return this.typeResolver.resolvedType();
  }
}
