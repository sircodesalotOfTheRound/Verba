package com.verba.language.graph.symbols.resolution.fields;

import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTypeMetadata;
import com.verba.language.graph.symbols.resolution.interfaces.SymbolResolutionInfo;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;

/**
 * Created by sircodesalot on 14-5-22.
 */
public class VariableTypeResolutionMetadata implements SymbolTableMetadata, SymbolTypeMetadata, SymbolResolutionInfo {
  private final SymbolTableEntry entry;
  private final TypeDeclarationExpression type;
  private final boolean isClosedOver;

  public VariableTypeResolutionMetadata(SymbolTableEntry entry, boolean isClosedOver, TypeDeclarationExpression typeDeclarationExpression) {
    this.entry = entry;
    this.isClosedOver = isClosedOver;
    this.type = typeDeclarationExpression;
  }

  public SymbolTableEntry symbolTableEntry() {
    return this.entry;
  }

  public boolean isClosedOver() { return this.isClosedOver; }

  @Override
  public TypeDeclarationExpression symbolType() {
    return this.type;
  }
}
