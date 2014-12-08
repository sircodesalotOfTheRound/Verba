package com.verba.language.graph.expressions.functions.tools;

import com.verba.language.emit.header.StringTable;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableScopeTree;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.FunctionOpCodeSet;
import com.verba.language.graph.expressions.functions.variables.VariableLifetimeGraph;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.parse.expressions.categories.TypedExpression;

/**
 * Created by sircodesalot on 14/12/7.
 */
public abstract class NodeProcessor<T> {
  protected final FunctionContext context;
  protected final VirtualVariableScopeTree variableScopeTree;
  protected final StringTable stringTable;
  protected final SymbolTable symbolTable;
  protected final FunctionOpCodeSet opcodes;
  protected final VariableLifetimeGraph lifetimeGraph;

  public NodeProcessor(FunctionContext context) {
    this.context = context;
    this.symbolTable = context.symbolTable();
    this.variableScopeTree = context.variableScopeTree();
    this.stringTable = context.stringTable();
    this.opcodes = context.opcodes();
    this.lifetimeGraph = context.lifetimeGraph();
  }

  protected void visit(ExpressionTreeNode node) { context.visit(node); }
  protected VirtualVariable visitWithNewVarScope(ExpressionTreeNode node) { return context.visitWithNewVarScope(node); }
  protected Symbol getTypeForSymbol(TypedExpression expression) { return expression.resolvedType(); }

  public abstract void process(T expression);
}
