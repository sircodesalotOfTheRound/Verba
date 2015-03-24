package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class NumericNodeProcessor extends NodeProcessor<NumericExpression> {
  private final Symbol INT = this.symbolTable.findSymbolForType(KeywordToken.INT);

  public NumericNodeProcessor(FunctionContext context) {
    super(context);
  }

  @Override
  public VirtualVariable process(NumericExpression expression) {
    return this.loadValue(expression.asLong());
  }

  public VirtualVariable loadValue(Long value) {
    String valueAsString = value.toString();
    if (!this.variableSet.containsKey(valueAsString)) {
      VirtualVariable variable = this.variableSet.create(value.toString(), INT);
      this.opcodes.loaduint64(variable, value);

      return variable;
    } else {
      return this.variableSet.get(valueAsString);
    }
  }
}
