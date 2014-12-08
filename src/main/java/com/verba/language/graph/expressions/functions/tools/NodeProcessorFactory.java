package com.verba.language.graph.expressions.functions.tools;

import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.node.NamedValueNodeProcessor;
import com.verba.language.graph.expressions.functions.node.NumericNodeProcessor;
import com.verba.language.graph.expressions.functions.node.QuoteNodeProcessor;
import com.verba.language.graph.expressions.functions.node.ValNodeStatementProcessor;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class NodeProcessorFactory {
  private final ValNodeStatementProcessor valStatementProcessor;
  private final QuoteNodeProcessor quoteNodeProcessor;
  private final NumericNodeProcessor numericNodeProcessor;
  private final NamedValueNodeProcessor namedValueNodeProcessor;

  public NodeProcessorFactory(FunctionContext context) {
    this.valStatementProcessor = new ValNodeStatementProcessor(context);
    this.quoteNodeProcessor = new QuoteNodeProcessor(context);
    this.numericNodeProcessor = new NumericNodeProcessor(context);
    this.namedValueNodeProcessor = new NamedValueNodeProcessor(context);
  }

  public void process(ValDeclarationStatement declaration) {
    this.valStatementProcessor.process(declaration);
  }

  public void process(QuoteExpression quote) {
    this.quoteNodeProcessor.process(quote);
  }

  public void process(NumericExpression expression) {
    this.numericNodeProcessor.process(expression);
  }

  public void process(NamedValueExpression expression) {
    this.namedValueNodeProcessor.process(expression);
  }
}
