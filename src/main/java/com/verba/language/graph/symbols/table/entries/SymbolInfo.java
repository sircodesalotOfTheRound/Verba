package com.verba.language.graph.symbols.table.entries;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.meta.ParameterSymbolMetadata;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.meta.types.SystemTypeMetadata;
import com.verba.language.graph.symbols.meta.types.TypeDefinitionMetadata;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class SymbolInfo {
  private final boolean isParameter;
  private final boolean isUserDefinedType;
  private final boolean isSystemType;

  public SymbolInfo(QIterable<SymbolTableMetadata> metadata) {
    this.isParameter = metadata.ofType(ParameterSymbolMetadata.class).any();
    this.isUserDefinedType = metadata.ofType(TypeDefinitionMetadata.class).any();
    this.isSystemType = metadata.ofType(SystemTypeMetadata.class).any();
  }

  public boolean isParameter() { return this.isParameter; }
  public boolean isType() { return this.isSystemType || this.isUserDefinedType; }
  public boolean isUserDefinedType() { return this.isUserDefinedType; }
  public boolean isSystemType() { return this.isSystemType; }
}

