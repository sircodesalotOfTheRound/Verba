package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.graph.visitors.FunctionGraphNode;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;

/**
 * Created by sircodesalot on 14/12/9.
 */
public class ReturnStatementNodeProcessor extends NodeProcessor<ReturnStatementExpression> {
  public ReturnStatementNodeProcessor(FunctionContext context) {
    super(context);
  }

  @Override
  public VirtualVariable process(ReturnStatementExpression expression) {
    if (expression.hasValue()) {
      VirtualVariable variable = captureReturnValue(expression);
      this.opcodes.ret(variable);
    } else {
      this.opcodes.ret();
    }

    return null;
  }

  private VirtualVariable captureReturnValue(ReturnStatementExpression expression) {
    FunctionGraphNode value = (FunctionGraphNode)expression.value();
    return this.visitAndCaptureResult(value);
  }

}
