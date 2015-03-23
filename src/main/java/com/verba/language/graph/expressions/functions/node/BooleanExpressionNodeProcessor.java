package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.rvalue.simple.BooleanExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/9.
 */
public class BooleanExpressionNodeProcessor extends NodeProcessor<BooleanExpression> {
  private final Symbol BOOLEAN = symbolTable.findSymbolForType(KeywordToken.BOOLEAN);

  public BooleanExpressionNodeProcessor(FunctionContext context) {
    super(context);
  }

  @Override
  public VirtualVariable process(BooleanExpression expression) {
    VirtualVariable variable = createVariable(expression);
    this.variableScope.setScopeValue(variable);

    return null;
  }

  private VirtualVariable createVariable(BooleanExpression expression) {
    String keyword = captureKeywordForExpression(expression);

    // No need to create the value if it already exists.
    if (this.variableScope.containsKey(keyword)) {
      return this.variableScope.variableByName(keyword);
    } else {
      VirtualVariable variable = this.variableScope.addtoScope(keyword, BOOLEAN);
      this.opcodes.loadBoolean(variable, expression.value());
      return variable;
    }
  }

  private String captureKeywordForExpression(BooleanExpression expression) {
    if (expression.value()) {
      return KeywordToken.TRUE;
    } else {
      return KeywordToken.FALSE;
    }
  }
}
