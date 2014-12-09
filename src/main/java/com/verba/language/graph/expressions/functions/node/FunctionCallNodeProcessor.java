package com.verba.language.graph.expressions.functions.node;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.symbols.table.entries.Symbol;
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
    StringTableFqnEntry functionName = this.stringTable.addFqn(call.functionName());
    QIterable<VirtualVariable> arguments = this.loadArguments(call);
    VirtualVariable returnValue = this.createReturnValueStorage(call);

    this.opcodes.call(functionName, returnValue, arguments);
    this.variableScopeTree.setScopeValue(returnValue);
  }

  private QIterable<VirtualVariable> loadArguments(FunctionCallFacade call) {
    return call.primaryParameters()
      .cast(ExpressionTreeNode.class)
      .map(parameter -> this.visitWithNewVarScope(parameter));
  }

  private VirtualVariable createReturnValueStorage(FunctionCallFacade call) {
    Symbol returnType = call.resolvedType();
    return this.variableScopeTree.addtoScope("temporary-return-value", returnType);
  }
}
