package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.facades.FunctionCallFacade;

/**
 * Created by sircodesalot on 14/12/8.
 */
public class NamedValueNodeProcessor extends NodeProcessor<NamedValueExpression> {
  private final FunctionCallNodeProcessor functionCallNodeProcessor;

  public NamedValueNodeProcessor(FunctionContext context) {
    super(context);

    this.functionCallNodeProcessor = new FunctionCallNodeProcessor(context);
  }

  @Override
  public VirtualVariable process(NamedValueExpression expression) {
    if (FunctionCallFacade.isFunctionCall(expression)) {
      this.functionCallNodeProcessor.process(new FunctionCallFacade(context, expression));
    } else {
      this.captureValue(expression);
    }

    return null;
  }

  private void captureValue(NamedValueExpression expression) {
    // Make this variable the scope value.
    VirtualVariable variable = this.variableScope.variableByName(expression.name());
    this.variableScope.setScopeValue(variable);
  }


}
