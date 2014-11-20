package com.verba.language.graph.symbols.resolution.metatags;

import com.verba.language.graph.symbols.resolution.interfaces.SymbolResolutionInfo;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;

/**
 * Created by sircodesalot on 14-5-16.
 */
public class MetaTagResolutionInfo implements SymbolResolutionInfo {
  public SymbolTableEntry entry;

  public MetaTagResolutionInfo(SymbolTableEntry entry) {
    this.entry = entry;
  }

  public SymbolTableEntry targetEntry() {
    return this.entry;
  }
}
