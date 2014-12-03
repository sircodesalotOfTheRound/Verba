package com.verba.language.build.event;

import com.verba.language.build.BuildAnalysis;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parse.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/12/3.
 */
public interface BuildEvent {

  public interface NotifyParsingBuildEvent extends BuildEvent {
    void afterParse(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis);
  }

  public interface NotifySymbolTableBuildEvent extends BuildEvent {
    void beforeSymbolTableAssociation(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis);
    void afterSymbolTableAssociation(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable);
  }

  public interface NotifyCodeOptimizationEvent extends BuildEvent {
    void performCodeOptimization();
  }

  public interface NotifyCodeGenerationEvent extends BuildEvent {
    void beforeCodeGeneration(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable);
  }

  public interface NotifyObjectEmitEvent extends BuildEvent {
    ObjectImage onGenerateObjectImage(BuildAnalysis buildAnalysis,
                              StaticSpaceExpression staticSpace,
                              GlobalSymbolTable symbolTable);
  }
}
