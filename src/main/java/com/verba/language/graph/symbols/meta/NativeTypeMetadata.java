package com.verba.language.graph.symbols.meta;

import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class NativeTypeMetadata implements SymbolTableMetadata {
  public static NativeTypeMetadata INSTANCE = new NativeTypeMetadata();

  private NativeTypeMetadata() { }
}
