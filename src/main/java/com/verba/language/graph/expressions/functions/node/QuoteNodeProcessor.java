package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.variables.VariableLifetime;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;

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
      VirtualVariable variable = context.variableStack().add(expression.innerText(), context.nativeTypeSymbols().UTF8);
      context.opcodes().loadString(variable, expression.innerText());
    }
  }
}
