package com.verba.language.graph.expressions.events.interfaces;

import com.verba.language.build.configuration.Build;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;

/**
 * Created by sircodesalot on 14/12/3.
 */
public interface VerbaExpressionBuildEvent {

  // This interface means that the object delegates event expressions
  // to a separate handler object.
  public interface ContainsEventSubscriptionObject {
    VerbaExpressionBuildEventSubscriptionBase buildEventObject();
  }

  public interface NotifyParsingVerbaExpressionBuildEvent extends VerbaExpressionBuildEvent {
    void afterParse(Build build, LitFileRootExpression buildAnalysis);
  }

  public interface NotifySymbolTableVerbaExpressionBuildEvent extends VerbaExpressionBuildEvent {
    void beforeSymbolsGenerated(Build build, LitFileRootExpression staticSpace);
    void afterSymbolsGenerated(Build build, LitFileRootExpression staticSpace, SymbolTable symbolTable);
    void onResolveSymbols(Build build, LitFileRootExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyReadyToCompileEventVerbaExpression extends VerbaExpressionBuildEvent {
    void validateReadyToCompile(Build build, LitFileRootExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyCodeOptimizationEventVerbaExpression extends VerbaExpressionBuildEvent {
    void performCodeOptimization();
  }

  public interface NotifyCodeGenerationEventVerbaExpression extends VerbaExpressionBuildEvent {
    void beforeCodeGeneration(Build build, LitFileRootExpression staticSpace, SymbolTable symbolTable);
  }

  public interface NotifyObjectEmitEventVerbaExpression extends VerbaExpressionBuildEvent {
    ObjectImage onGenerateObjectImage(Build build,
                              LitFileRootExpression staticSpace,
                              SymbolTable symbolTable);
  }
}
