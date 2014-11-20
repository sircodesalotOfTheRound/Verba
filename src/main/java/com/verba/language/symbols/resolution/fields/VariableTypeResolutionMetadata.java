package com.verba.language.symbols.resolution.fields;

import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.symbols.meta.interfaces.SymbolTypeMetadata;
import com.verba.language.symbols.resolution.interfaces.SymbolResolutionInfo;
import com.verba.language.symbols.table.entries.SymbolTableEntry;

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
