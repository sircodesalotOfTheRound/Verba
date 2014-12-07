package com.verba.language.graph.symbols.meta;

import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;

/**
 * Created by sircodesalot on 14-5-7.
 */
public class ParameterSymbolMetadata implements SymbolTableMetadata {
  public static final ParameterSymbolMetadata INSTANCE = new ParameterSymbolMetadata();

  private ParameterSymbolMetadata() { }
}
