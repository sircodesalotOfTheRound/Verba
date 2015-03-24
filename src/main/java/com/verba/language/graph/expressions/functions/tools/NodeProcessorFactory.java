package com.verba.language.graph.expressions.functions.tools;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.node.*;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.rvalue.newexpression.NewExpression;
import com.verba.language.parse.expressions.rvalue.simple.BooleanExpression;
import com.verba.language.parse.expressions.rvalue.simple.InfixExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class NodeProcessorFactory {
  private final ValNodeStatementProcessor valStatementProcessor;
  private final QuoteNodeProcessor quoteNodeProcessor;
  private final NumericNodeProcessor numericNodeProcessor;
  private final NamedValueNodeProcessor namedValueNodeProcessor;
  private final NewExpressionNodeProcessor newExpressionNodeProcessor;
  private final ReturnStatementNodeProcessor returnStatementNodeProcessor;
  private final BooleanExpressionNodeProcessor booleanExpressionProcessor;
  private final InfixExpressionNodeProcessor infixNodeProcessor;

  public NodeProcessorFactory(FunctionContext context) {
    this.valStatementProcessor = new ValNodeStatementProcessor(context);
    this.quoteNodeProcessor = new QuoteNodeProcessor(context);
    this.numericNodeProcessor = new NumericNodeProcessor(context);
    this.namedValueNodeProcessor = new NamedValueNodeProcessor(context);
    this.newExpressionNodeProcessor = new NewExpressionNodeProcessor(context);
    this.returnStatementNodeProcessor = new ReturnStatementNodeProcessor(context);
    this.booleanExpressionProcessor = new BooleanExpressionNodeProcessor(context);
    this.infixNodeProcessor = new InfixExpressionNodeProcessor(context);
  }

  public VirtualVariable process(ValDeclarationStatement declaration) {
    return this.valStatementProcessor.process(declaration);
  }

  public VirtualVariable process(QuoteExpression quote) {
    return this.quoteNodeProcessor.process(quote);
  }

  public VirtualVariable process(NumericExpression expression) {
    return this.numericNodeProcessor.process(expression);
  }

  public VirtualVariable process(NamedValueExpression expression) {
    return this.namedValueNodeProcessor.process(expression);
  }

  public VirtualVariable process(NewExpression expression) {
    return this.newExpressionNodeProcessor.process(expression);
  }

  public VirtualVariable process(ReturnStatementExpression expression) {
    return this.returnStatementNodeProcessor.process(expression);
  }

  public VirtualVariable process(BooleanExpression expression) {
    return this.booleanExpressionProcessor.process(expression);
  }

  public VirtualVariable process(InfixExpression expression) {
    return this.infixNodeProcessor.process(expression);
  }
}
