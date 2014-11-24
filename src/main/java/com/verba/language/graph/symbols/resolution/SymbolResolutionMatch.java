package com.verba.language.graph.symbols.resolution;

import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import sun.jvm.hotspot.memory.SymbolTable;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class SymbolResolutionMatch {
  private final SymbolTableEntry entry;

  public SymbolResolutionMatch(SymbolTableEntry entry) {
    this.entry = entry;
  }

  public SymbolTableEntry entry() { return this.entry; }
}
