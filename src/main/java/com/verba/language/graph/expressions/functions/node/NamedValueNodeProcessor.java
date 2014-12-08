package com.verba.language.graph.expressions.functions.node;

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
  public void process(NamedValueExpression expression) {
    if (FunctionCallFacade.isFunctionCall(expression)) {
      this.functionCallNodeProcessor.process(new FunctionCallFacade(expression));
    }
  }


}
