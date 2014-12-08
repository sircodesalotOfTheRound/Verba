package com.verba.language.graph.expressions.functions.node;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.parse.expressions.facades.FunctionCallFacade;

/**
 * Created by sircodesalot on 14/12/8.
 */
public class FunctionCallNodeProcessor extends NodeProcessor<FunctionCallFacade> {
  public FunctionCallNodeProcessor(FunctionContext context) {
    super(context);
  }

  @Override
  public void process(FunctionCallFacade call) {
    this.loadArguments(call);
    this.writeFunctionCall(call);
  }

  private void loadArguments(FunctionCallFacade call) {
    QIterable<VirtualVariable> argumentToLocalVariableProjection
      = call.primaryParameters()
        .cast(ExpressionTreeNode.class)
        .map(parameter -> this.visitWithNewVarScope(parameter));

    for (VirtualVariable variable : argumentToLocalVariableProjection){
      opcodes.stageArg(variable);
    }

  }

  private void writeFunctionCall(FunctionCallFacade call) {
    StringTableFqnEntry calledFunctionName = this.stringTable.addFqn(call.functionName());
    opcodes.call(calledFunctionName);
  }
}
