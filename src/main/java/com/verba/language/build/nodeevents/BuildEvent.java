package com.verba.language.build.nodeevents;

import com.verba.language.build.configuration.BuildProfile;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;

/**
 * Created by sircodesalot on 14/12/3.
 */
@Deprecated
public interface BuildEvent {

  // This interface means that the object delegates event subscriptions
  // to a separate handler object.
  public interface ContainsEventSubscriptionObject {
    BuildEventSubscriptionBase buildEventObject();
  }

  public interface NotifyParsingBuildEvent extends BuildEvent {
    void afterParse(BuildProfile analysis, LitFileRootExpression buildAnalysis);
  }

  public interface NotifySymbolTableBuildEvent extends BuildEvent {
    void beforeSymbolsGenerated(BuildProfile profile, LitFileRootExpression staticSpace);
    void afterSymbolsGenerated(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable);
    void onResolveSymbols(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyReadyToCompileEvent extends BuildEvent {
    void validateReadyToCompile(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyCodeOptimizationEvent extends BuildEvent {
    void performCodeOptimization();
  }

  public interface NotifyCodeGenerationEvent extends BuildEvent {
    void beforeCodeGeneration(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyObjectEmitEvent extends BuildEvent {
    ObjectImage onGenerateObjectImage(BuildProfile profile,
                              LitFileRootExpression staticSpace,
                              SymbolTable symbolTable);
  }
}
