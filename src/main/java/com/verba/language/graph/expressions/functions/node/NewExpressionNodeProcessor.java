package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.rvalue.newexpression.NewExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.platform.PlatformTypeSymbols;

/**
 * Created by sircodesalot on 14/12/9.
 */
public class NewExpressionNodeProcessor extends NodeProcessor<NewExpression> {
  public NewExpressionNodeProcessor(FunctionContext context) {
    super(context);
  }

  @Override
  public VirtualVariable process(NewExpression expression) {
    return null;
  }
}
