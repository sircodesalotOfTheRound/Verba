package com.verba.language.graph.symbols.meta.types;

import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class UserDefinedTypeMetadata implements TypeDefinitionMetadata {
  public static UserDefinedTypeMetadata INSTANCE = new UserDefinedTypeMetadata();

  private UserDefinedTypeMetadata() { }
}
