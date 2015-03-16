package com.verba.language.graph.events;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.events.interfaces.VerbaExpressionBuildEvent;
import com.verba.language.graph.events.interfaces.VerbaExpressionBuildEventSubscription;
import com.verba.language.graph.symbols.resolution.ValDeclarationTypeResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class ValDeclarationEventSubscriptionVerbaExpression extends VerbaExpressionBuildEventSubscription<ValDeclarationStatement>
  implements VerbaExpressionBuildEvent.NotifySymbolTableVerbaExpressionBuildEvent
{
  private ValDeclarationTypeResolver typeResolver;

  public ValDeclarationEventSubscriptionVerbaExpression(ValDeclarationStatement expression) {
    super(expression);
  }
/*
  @Override
  public void beforeSymbolsGenerated(BuildProfile profile, LitFileRootExpression staticSpace) {

  }

  @Override
  public void afterSymbolsGenerated(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.typeResolver = new ValDeclarationTypeResolver(this.expression(), symbolTable);
  }

  @Override
  public void onResolveSymbols(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }
*/
  public Symbol resolvedType() { return typeResolver.resolvedType(); }

  @Override
  public void beforeSymbolsGenerated(Build build, LitFileRootExpression staticSpace) {

  }

  @Override
  public void afterSymbolsGenerated(Build build, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }

  @Override
  public void onResolveSymbols(Build build, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }
}
