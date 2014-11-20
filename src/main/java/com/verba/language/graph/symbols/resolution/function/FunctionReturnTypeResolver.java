package com.verba.language.graph.symbols.resolution.function;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.InvokableExpression;
import com.verba.language.graph.symbols.resolution.interfaces.SymbolResolver;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;

/**
 * Created by sircodesalot on 14-5-25.
 */
public class FunctionReturnTypeResolver implements SymbolResolver<InvokableExpression, FunctionReturnTypeResolutionMetadata> {
  private final GlobalSymbolTable symbolTable;

  public FunctionReturnTypeResolver(GlobalSymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  @Override
  public FunctionReturnTypeResolutionMetadata resolve(InvokableExpression expression) {
    FunctionReturnTypeResolutionMetadata metadata
      = new FunctionReturnTypeResolutionMetadata(symbolTable, expression);

    SymbolTableEntry entry = symbolTable.getByInstance((VerbaExpression) expression);

    // Cache the value so we don't need to calculate it later
    entry.addMetadata(metadata);

    return metadata;
  }
}
