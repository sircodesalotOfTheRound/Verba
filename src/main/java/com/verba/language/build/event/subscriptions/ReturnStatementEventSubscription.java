package com.verba.language.build.event.subscriptions;

import com.verba.language.build.BuildProfile;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.ExpressionBuildEventSubscription;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/12/6.
 */
public class ReturnStatementEventSubscription extends ExpressionBuildEventSubscription<ReturnStatementExpression>
  implements BuildEvent.NotifySymbolTableBuildEvent
{
  private Symbol returnType;
  private SymbolTable symbolTable;

  public ReturnStatementEventSubscription(ReturnStatementExpression statement) {
    super(statement);
  }

  @Override
  public void beforeSymbolsGenerated(BuildProfile analysis, StaticSpaceExpression buildAnalysis) {

  }

  @Override
  public void afterSymbolsGenerated(BuildProfile buildProfile, StaticSpaceExpression staticSpace, SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  @Override
  public void onResolveSymbols(BuildProfile profile, StaticSpaceExpression staticSpace, SymbolTable symbolTable) {
    this.returnType = this.determineReturnValue(symbolTable);
  }

  public Symbol determineReturnValue(SymbolTable symbolTable) {
    if (!this.expression().hasValue()) {
      return symbolTable.findSymbolForType(KeywordToken.UNIT);
    }

    throw new NotImplementedException();
  }

  public Symbol returnType() {
    if (this.returnType == null) {
      this.returnType = determineReturnValue(symbolTable);
    }

    return this.returnType;
  }

}
