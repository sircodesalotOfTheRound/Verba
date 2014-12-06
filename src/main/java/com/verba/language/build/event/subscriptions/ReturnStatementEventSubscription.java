package com.verba.language.build.event.subscriptions;

import com.verba.language.build.BuildProfile;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/12/6.
 */
public class ReturnStatementEventSubscription
  implements BuildEvent.NotifySymbolTableBuildEvent
{
  @Override
  public void beforeSymbolsGenerated(BuildProfile analysis, StaticSpaceExpression buildAnalysis) {

  }

  @Override
  public void afterSymbolsGenerated(BuildProfile buildProfile, StaticSpaceExpression staticSpace, SymbolTable symbolTable) {

  }
}
