package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableStack;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class QuoteNodeProcessor {
  private final FunctionContext context;
  private final VirtualVariableStack variableStack;

  private final Symbol UTF;

  public QuoteNodeProcessor(FunctionContext context) {
    this.context = context;
    this.variableStack = context.variableStack();
    this.UTF = context.symbolTable().findSymbolForType(KeywordToken.UTF);
  }

  public void process(QuoteExpression expression) {
    String variableName = expression.representation();

    VirtualVariable variable;
    if (variableStack.containsVariableMatching(variableName, UTF)) {
      variable = variableStack.variableByName(variableName);
    } else {
      variable = variableStack.addToFrame(variableName, UTF);
    }

    variableStack.setFrameReturnValue(variable);
    context.opcodes().loadString(variable, expression.innerText());
  }
}
