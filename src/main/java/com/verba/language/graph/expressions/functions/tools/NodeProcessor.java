package com.verba.language.graph.expressions.functions.tools;

import com.verba.language.emit.header.StringTable;
import com.verba.language.emit.variables.VirtualVariableStack;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.FunctionOpCodeSet;
import com.verba.language.graph.symbols.table.tables.SymbolTable;

/**
 * Created by sircodesalot on 14/12/7.
 */
public abstract class NodeProcessor<T> {
  protected final FunctionContext context;
  protected final VirtualVariableStack variableStack;
  protected final StringTable stringTable;
  protected final SymbolTable symbolTable;
  protected final FunctionOpCodeSet opcodes;

  public NodeProcessor(FunctionContext context) {
    this.context = context;
    this.symbolTable = context.symbolTable();
    this.variableStack = context.variableStack();
    this.stringTable = context.stringTable();
    this.opcodes = context.opcodes();
  }

  public abstract void process(T expression);
}
