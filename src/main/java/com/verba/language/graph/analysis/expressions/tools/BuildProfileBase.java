package com.verba.language.graph.analysis.expressions.tools;

import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public interface BuildProfileBase {
  void afterParse(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis);

  void beforeSymbolTableAssociation(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis);
  void afterSymbolTableAssociation(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable);
  void beforeCodeGeneration(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable);
}
