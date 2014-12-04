package com.verba.language.graph.expressions.functions.nodes;

import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.variables.VariableLifetime;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.virtualmachine.VirtualMachineNativeTypes;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class QuoteNodeProcessor {
  private final FunctionContext context;

  public QuoteNodeProcessor(FunctionContext context) {
    this.context = context;
  }

  public void process(QuoteExpression expression) {
    VariableLifetime variableLifetime = context.getVariableLifetime(expression);

    // If this is the first time seeing this variable, add it.
    if (variableLifetime.isFirstInstance(expression)) {
      VirtualVariable variable = context.addVariable(expression.innerText(), context.nativeTypeSymbols().UTF8);
      context.addOpCode(VerbatimOpCodeBase.loadString(variable, expression.innerText()));
    }
  }
}
