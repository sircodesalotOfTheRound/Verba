package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class QuoteNodeProcessor extends NodeProcessor<QuoteExpression> {
  private final Symbol UTF;

  public QuoteNodeProcessor(FunctionContext context) {
    super(context);
    this.UTF = context.symbolTable().findSymbolForType(KeywordToken.UTF);
  }

  public void process(QuoteExpression expression) {
    String variableName = expression.representation();

    VirtualVariable variable;
    if (variableScopeTree.containsVariableMatching(variableName, UTF)) {
      variable = variableScopeTree.variableByName(variableName);
    } else {
      variable = variableScopeTree.addtoScope(variableName, UTF);
    }

    variableScopeTree.setScopeValue(variable);

    StringTableStringEntry innerText = stringTable.addString(expression.innerText());
    context.opcodes().loadString(variable, innerText);
  }
}
