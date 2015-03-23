package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.parse.expressions.rvalue.simple.InfixExpression;
import com.verba.language.parse.tokens.operators.mathop.AddOpToken;

/**
 * Created by sircodesalot on 15/3/23.
 */
public class InfixExpressionNodeProcessor extends NodeProcessor<InfixExpression> {
  public InfixExpressionNodeProcessor(FunctionContext context) {
    super(context);
  }

  @Override
  public VirtualVariable process(InfixExpression expression) {
    if (expression.operator().is(AddOpToken.class)) {
      VirtualVariable lhs = this.visitAndCaptureResult(expression.lhs());
      VirtualVariable rhs = this.visitAndCaptureResult(expression.lhs());
      this.opcodes.add(lhs, rhs);

      this.variableScope.setScopeValue(lhs);
    }

    return null;
  }
}
