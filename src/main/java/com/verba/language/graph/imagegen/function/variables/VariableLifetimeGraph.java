package com.verba.language.graph.imagegen.function.variables;

import com.verba.language.parsing.expressions.blockheader.classes.PolymorphicExpression;
import com.verba.language.parsing.expressions.codepage.VerbaCodePage;
import com.verba.language.graph.analysis.facades.FunctionCallFacade;
import com.verba.language.graph.visitors.SyntaxGraphVisitable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parsing.expressions.containers.json.JsonExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parsing.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parsing.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parsing.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parsing.expressions.withns.WithNsExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/9/21.
 */
public class VariableLifetimeGraph implements SyntaxGraphVisitor {
  private final VariableLifetimeMap lifetimes = new VariableLifetimeMap();
  private final FunctionDeclarationExpression function;

  public VariableLifetimeGraph(FunctionDeclarationExpression function) {
    this.function = function;

    buildGraph(function);
  }

  private void buildGraph(FunctionDeclarationExpression function) {
    BlockDeclarationExpression block = function.block();
    for (SyntaxGraphVisitable expression : block.expressions().cast(SyntaxGraphVisitable.class)) {
      expression.accept(this);
    }
  }

  public FunctionDeclarationExpression function() {
    return this.function;
  }

  public void visit(ReturnStatementExpression returnStatementExpression) {

  }

  @Override
  public void visit(SignatureDeclarationExpression signatureDeclarationExpression) {
    throw new NotImplementedException();
  }

  @Override
  public void visit(AssignmentStatementExpression assignmentStatementExpression) {
    throw new NotImplementedException();
  }

  @Override
  public void visit(QuoteExpression quoteExpression) {
    lifetimes.updateLifetime(quoteExpression);
  }

  @Override
  public void visit(NumericExpression numericExpression) {
    lifetimes.updateLifetime(numericExpression);
  }

  @Override
  public void visit(ValDeclarationStatement valDeclarationStatement) {
    lifetimes.updateLifetime(valDeclarationStatement.nameAsExpression());
  }

  @Override
  public void visit(WithNsExpression withNsExpression) {  }

  @Override
  public void visit(FunctionDeclarationExpression functionDeclarationExpression) {
    // Todo: currently no op.
  }

  @Override
  public void visit(ArrayDeclarationExpression arrayDeclarationExpression) {
    throw new NotImplementedException();

  }

  @Override
  public void visit(JsonExpression jsonExpression) {
    throw new NotImplementedException();
  }

  @Override
  public void visit(TupleDeclarationExpression tupleDeclarationExpression) {
    throw new NotImplementedException();

  }

  @Override
  public void visit(VerbaCodePage verbaCodePage) {
    throw new NotImplementedException();

  }

  @Override
  public void visit(BlockDeclarationExpression verbaExpressions) {
    throw new NotImplementedException();

  }

  @Override
  public void visit(StaticSpaceExpression staticSpaceExpression) {
    throw new NotImplementedException();
  }

  public void visit(NamedValueExpression namedValueExpression) {
    if (FunctionCallFacade.isFunctionCall(namedValueExpression)) {
      FunctionCallFacade call = new FunctionCallFacade(namedValueExpression);
      for (SyntaxGraphVisitable declaration : call.primaryParameters().cast(SyntaxGraphVisitable.class)) {
        declaration.accept(this);
      }
    }
  }

  @Override
  public void visit(PolymorphicExpression classDeclarationExpression) {
    throw new NotImplementedException();
  }

  public boolean containsVariable(VerbaExpression expression) {
    return this.lifetimes.containsLifetime(expression);
  }

  public VariableLifetime getVariableLifetime(VerbaExpression expression) {
    return this.lifetimes.getLifetime(expression);
  }

  public boolean isFirstOccurance(VerbaExpression expression) {
    VariableLifetime lifetime = this.lifetimes.getLifetime(expression);
    return lifetime.isFirstInstance(expression);
  }

  public boolean isLastOccurance(VerbaExpression expression) {
    VariableLifetime lifetime = this.lifetimes.getLifetime(expression);
    return lifetime.isLastOccurance(expression);
  }

}

