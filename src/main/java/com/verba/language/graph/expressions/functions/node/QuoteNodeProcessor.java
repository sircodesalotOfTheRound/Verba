package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableStack;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.NativeTypeSymbols;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class QuoteNodeProcessor {
  private final FunctionContext context;
  private final VirtualVariableStack variableStack;
  private final NativeTypeSymbols nativeTypes;

  public QuoteNodeProcessor(FunctionContext context) {
    this.context = context;
    this.variableStack = context.variableStack();
    this.nativeTypes = context.nativeTypeSymbols();
  }

  public void process(QuoteExpression expression) {
    String variableName = expression.representation();

    VirtualVariable variable;
    if (variableStack.containsVariableMatching(variableName, nativeTypes.UTF8)) {
      variable = variableStack.variableByName(variableName);
    } else {
      variable = variableStack.addToFrame(variableName, nativeTypes.UTF8);
    }

    variableStack.setFrameReturnValue(variable);
    context.opcodes().loadString(variable, expression.innerText());
  }
}
