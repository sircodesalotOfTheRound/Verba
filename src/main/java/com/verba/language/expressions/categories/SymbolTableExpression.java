package com.verba.language.expressions.categories;

import com.verba.language.symbols.table.tables.ScopedSymbolTable;

/**
 * An expression that can contain a symbol table
 */
public interface SymbolTableExpression {
  void accept(ScopedSymbolTable symbolTable);
}
