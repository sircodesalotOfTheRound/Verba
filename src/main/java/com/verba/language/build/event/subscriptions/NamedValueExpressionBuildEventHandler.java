package com.verba.language.build.event.subscriptions;

import com.verba.language.build.BuildProfile;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventSubscription;
import com.verba.language.graph.expressions.namedvalue.NamedValueExpressionTypeResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.StaticSpaceExpression;
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
  public void beforeSymbolsGenerated(BuildProfile profile, StaticSpaceExpression staticSpace) {

  }

  @Override
  public void afterSymbolsGenerated(BuildProfile profile, StaticSpaceExpression staticSpace, SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.typeResolver = new NamedValueExpressionTypeResolver(this.expression(), symbolTable);
  }

  @Override
  public void onResolveSymbols(BuildProfile profile, StaticSpaceExpression staticSpace, SymbolTable symbolTable) {

  }

  public Symbol resolvedType() {
    return this.typeResolver.resolvedType();
  }
}
