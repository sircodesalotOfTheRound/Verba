package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.header.stringtable.StringTableStringEntry;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.parse.expressions.rvalue.simple.UtfExpression;
import com.verba.language.platform.PlatformTypeSymbols;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class UtfNodeProcessor extends NodeProcessor<UtfExpression> {
  public UtfNodeProcessor(FunctionContext context) {
    super(context);
  }

  public VirtualVariable process(UtfExpression expression) {
    String variableName = expression.representation();
    StringTableStringEntry innerText = stringTable.addString(expression.innerText());

    if (variableSet.containsKey(variableName)) {
      return variableSet.get(variableName);
    } else {
      VirtualVariable variable = variableSet.create(variableName, PlatformTypeSymbols.UTF);
      this.context.opcodes().ldutf(variable, innerText);
      return variable;
    }
  }
}
