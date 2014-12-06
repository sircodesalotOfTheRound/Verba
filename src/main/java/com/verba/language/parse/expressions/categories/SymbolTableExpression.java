package com.verba.language.parse.expressions.categories;

import com.verba.language.graph.symbols.table.tables.Scope;

/**
 * An expression that can contain a symbol table
 */
public interface SymbolTableExpression extends ExpressionCategory {
  void accept(Scope symbolTable);
}
