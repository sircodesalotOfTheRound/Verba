package com.verba.language.graph.expressions.events;

import com.verba.language.build.configuration.Build;
import com.verba.language.graph.symbols.resolution.ReturnStatementTypeResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;

/**
 * Created by sircodesalot on 14/12/6.
 */
public class ReturnStatementEventSubscriptionVerbaExpression
{

  private SymbolTable symbolTable;
  private ReturnStatementTypeResolver typeResolver;

  public void beforeSymbolsGenerated(Build analysis, LitFileRootExpression buildAnalysis) {

  }


  public void afterSymbolsGenerated(Build buildProfile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    //this.typeResolver = new ReturnStatementTypeResolver(null, symbolTable);
  }


  public void onResolveSymbols(Build profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }

  public Symbol returnType() {
    return this.typeResolver.returnType();
  }

}
