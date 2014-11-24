package com.verba.language.graph.analysis.expressions.tools;

import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class BuildAnalysis {
  private final StaticSpaceExpression staticSpace;
  private final GlobalSymbolTable symbolTable;

  public BuildAnalysis(StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {

    this.staticSpace = staticSpace;
    this.symbolTable = symbolTable;
  }

  public StaticSpaceExpression staticSpace() { return staticSpace; }
  public GlobalSymbolTable symbolTable() { return symbolTable; }
}
