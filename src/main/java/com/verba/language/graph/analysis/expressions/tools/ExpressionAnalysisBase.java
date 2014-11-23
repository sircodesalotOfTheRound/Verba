package com.verba.language.graph.analysis.expressions.tools;

/**
 * Created by sircodesalot on 14/11/23.
 */
public interface ExpressionAnalysisBase {
  void afterParse(BuildAnalysis buildAnalysis);

  void beforeSymbolTableAssociation(BuildAnalysis buildAnalysis);
  void afterSymbolTableAssociation(BuildAnalysis buildAnalysis);
  void beforeCodeGeneration(BuildAnalysis buildAnalysis);
}
