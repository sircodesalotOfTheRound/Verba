package com.verba.language.graph.analysis.expressions.analyzers;

import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.analysis.expressions.tools.BuildProfile;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class FunctionExpressionAnalyzer extends BuildProfile<FunctionDeclarationExpression> {
  public FunctionExpressionAnalyzer(FunctionDeclarationExpression expression) {
    super(expression);
  }

  @Override
  public void afterParse(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) {

  }

  @Override
  public void beforeSymbolTableAssociation(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) {

  }

  @Override
  public void afterSymbolTableAssociation(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {

  }

  @Override
  public void beforeCodeGeneration(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {

  }
}
