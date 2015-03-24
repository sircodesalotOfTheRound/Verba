package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.header.stringtable.StringTableStringEntry;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.platform.PlatformTypeSymbols;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class QuoteNodeProcessor extends NodeProcessor<QuoteExpression> {
  public QuoteNodeProcessor(FunctionContext context) {
    super(context);
  }

  public VirtualVariable process(QuoteExpression expression) {
    String variableName = expression.representation();

    VirtualVariable variable;
    if (variableSet.containsKey(variableName)) {
      variable = variableSet.get(variableName);
    } else {
      variable = variableSet.create(variableName, PlatformTypeSymbols.UTF);
    }

    StringTableStringEntry innerText = stringTable.addString(expression.innerText());
    this.context.opcodes().loadString(variable, innerText);

    return variable;
  }
}
