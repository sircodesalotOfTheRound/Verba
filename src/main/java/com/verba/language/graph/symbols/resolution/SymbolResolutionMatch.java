package com.verba.language.graph.symbols.resolution;

import com.verba.language.graph.symbols.table.entries.Symbol;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class SymbolResolutionMatch {
  private final Symbol entry;

  public SymbolResolutionMatch(Symbol entry) {
    this.entry = entry;
  }

  public Symbol entry() { return this.entry; }
}
