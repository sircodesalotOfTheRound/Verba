package com.verba.language.graph.expressions.functions.node;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.parse.expressions.VerbaExpression;
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
    QIterable<ExpressionTreeNode> parametersAsFunctionElements
      = call.primaryParameters().cast(ExpressionTreeNode.class);

    for (ExpressionTreeNode declaration : parametersAsFunctionElements) {
      this.visit(declaration);
    }

    for (VerbaExpression expression : call.primaryParameters()) {
      VirtualVariable variable = this.variableScopeTree.variableByName(expression.text());
      opcodes.stageArg(variable);

      if (this.lifetimeGraph.isLastOccurance(expression)) {
        // TODO: This is broken.
        //this.variableSet.expireVariable(variable);
      }
    }

    StringTableFqnEntry calledFunctionName = this.stringTable.addFqn(call.functionName());
    opcodes.call(calledFunctionName);
  }
}
