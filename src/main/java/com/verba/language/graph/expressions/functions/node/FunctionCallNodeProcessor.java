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
    QIterable<VirtualVariable> arguments = this.loadArguments(call);
    StringTableFqnEntry functionName = this.stringTable.addFqn(call.functionName());

    this.opcodes.call(functionName, arguments);
  }

  private QIterable<VirtualVariable> loadArguments(FunctionCallFacade call) {
    return call.primaryParameters()
      .cast(ExpressionTreeNode.class)
      .map(parameter -> this.visitWithNewVarScope(parameter));
  }
}
