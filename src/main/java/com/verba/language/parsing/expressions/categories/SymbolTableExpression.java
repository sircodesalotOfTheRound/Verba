package com.verba.language.parsing.expressions.categories;

import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * An expression that can contain a symbol table
 */
public interface SymbolTableExpression {
  void accept(ScopedSymbolTable symbolTable);
}
