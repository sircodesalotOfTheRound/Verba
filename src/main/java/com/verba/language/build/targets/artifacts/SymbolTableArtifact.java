package com.verba.language.build.targets.artifacts;

import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.graph.symbols.table.tables.SymbolTable;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolTableArtifact implements BuildArtifact {
  private final SymbolTable symbolTable;

  public SymbolTableArtifact(LitFileSyntaxTreeArtifact syntaxTree) {
    this.symbolTable = new SymbolTable(syntaxTree.expression());
  }

  public SymbolTable symbolTable() { return this.symbolTable; }
}
