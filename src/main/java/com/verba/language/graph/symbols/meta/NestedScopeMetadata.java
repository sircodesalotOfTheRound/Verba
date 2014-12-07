package com.verba.language.graph.symbols.meta;

import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.table.tables.Scope;

/**
 * This is for items in a symbol table that themselves are a symbol table block.
 */
public class NestedScopeMetadata implements SymbolTableMetadata {
  private final Scope nestedTable;

  public NestedScopeMetadata(Scope nestedTable) {
    this.nestedTable = nestedTable;
  }

  public Scope nestedScope() {
    return this.nestedTable;
  }

}
