package com.verba.language.graph.symbols.resolution.polymorphic;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.PolymorphicExpression;
import com.verba.language.graph.symbols.resolution.interfaces.SymbolResolver;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;

/**
 * Created by sircodesalot on 14-5-20.
 */
public class PolymorphicResolver implements SymbolResolver<PolymorphicExpression, PolymorphicResolutionMetadata> {
  private final GlobalSymbolTable symbolTable;

  public PolymorphicResolver(GlobalSymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  public PolymorphicResolutionMetadata resolveBinding(SymbolTableEntry entry) {
    // If the metadata has already been built for this class, then just return it.
    if (entry.metadata().ofType(PolymorphicResolutionMetadata.class).any()) {
      return entry.metadata().ofType(PolymorphicResolutionMetadata.class).single();
    }

    // Add the resolution information as metadata for easy access later.
    PolymorphicResolutionMetadata metadata = new PolymorphicResolutionMetadata(this.symbolTable, entry);
    entry.addMetadata(metadata);

    // Return the member name resolution information
    return metadata;
  }

  @Override
  public PolymorphicResolutionMetadata resolve(PolymorphicExpression declaration) {
    SymbolTableEntry entry = this.symbolTable.getByInstance((VerbaExpression) declaration);
    return this.resolveBinding(entry);
  }
}
