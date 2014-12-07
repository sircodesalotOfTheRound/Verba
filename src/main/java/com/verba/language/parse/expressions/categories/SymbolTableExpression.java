package com.verba.language.parse.expressions.categories;

import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.visitors.ExpressionTreeNode;

/**
 * An expression that can contain a symbol table
 */
public interface SymbolTableExpression extends ExpressionTreeNode, ExpressionCategory {
  void accept(Scope symbolTable);
}
