package com.verba.language.graph.symbols.meta;

import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * This is for items in a symbol table that themselves are a symbol table block.
 */
public class NestedSymbolTableMetadata implements SymbolTableMetadata {
  private final ScopedSymbolTable nestedTable;

  public NestedSymbolTableMetadata(ScopedSymbolTable nestedTable) {
    this.nestedTable = nestedTable;
  }

  public ScopedSymbolTable symbolTable() {
    return this.nestedTable;
  }

}
