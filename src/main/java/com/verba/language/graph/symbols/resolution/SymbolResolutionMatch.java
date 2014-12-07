package com.verba.language.graph.symbols.resolution;

import com.verba.language.graph.symbols.meta.ParameterSymbolMetadata;
import com.verba.language.graph.symbols.table.entries.Symbol;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class SymbolResolutionMatch {
  private final Symbol entry;

  public SymbolResolutionMatch(Symbol entry) {
    this.entry = entry;
  }

  public Symbol symbol() { return this.entry; }
}
