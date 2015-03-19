package com.verba.language.graph.expressions.events;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.resolution.NamedValueExpressionTypeResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class NamedValueExpressionVerbaExpressionBuildEventHandler
{
  private SymbolTable symbolTable;
  private NamedValueExpressionTypeResolver typeResolver;

  public void afterSymbolsGenerated(Build profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.typeResolver = new NamedValueExpressionTypeResolver(null, symbolTable);
  }

  public void onResolveSymbols(Build profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }

  public Symbol resolvedType() {
    return this.typeResolver.resolvedType();
  }
}
