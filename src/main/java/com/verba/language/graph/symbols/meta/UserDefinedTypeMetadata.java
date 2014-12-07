package com.verba.language.graph.symbols.meta;

import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class UserDefinedTypeMetadata implements SymbolTableMetadata {
  public static UserDefinedTypeMetadata INSTANCE = new UserDefinedTypeMetadata();

  private UserDefinedTypeMetadata() { }
}
