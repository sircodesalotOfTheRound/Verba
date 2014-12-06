package com.verba.language.build.event;

import com.verba.language.build.BuildProfile;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/12/3.
 */
public interface BuildEvent {

  // This interface means that the object delegates event subscriptions
  // to a separate handler object.
  public interface ContainsEventSubscriptionObject {
    BuildEventSubscriptionBase buildEventObject();
  }

  public interface NotifyParsingBuildEvent extends BuildEvent {
    void afterParse(BuildProfile analysis, StaticSpaceExpression buildAnalysis);
  }

  public interface NotifySymbolTableBuildEvent extends BuildEvent {
    void beforeSymbolsGenerated(BuildProfile profile, StaticSpaceExpression staticSpace);
    void afterSymbolsGenerated(BuildProfile profile, StaticSpaceExpression staticSpace, SymbolTable symbolTable);
    void onResolveSymbols(BuildProfile profile, StaticSpaceExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyReadyToCompileEvent extends BuildEvent {
    void validateReadyToCompile(BuildProfile profile, StaticSpaceExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyCodeOptimizationEvent extends BuildEvent {
    void performCodeOptimization();
  }

  public interface NotifyCodeGenerationEvent extends BuildEvent {
    void beforeCodeGeneration(BuildProfile profile, StaticSpaceExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyObjectEmitEvent extends BuildEvent {
    ObjectImage onGenerateObjectImage(BuildProfile profile,
                              StaticSpaceExpression staticSpace,
                              SymbolTable symbolTable);
  }
}
