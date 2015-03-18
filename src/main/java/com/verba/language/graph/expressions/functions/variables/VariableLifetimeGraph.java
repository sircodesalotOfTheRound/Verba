package com.verba.language.graph.expressions.functions.variables;

import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;
import com.verba.language.parse.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpression;
import com.verba.language.parse.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.expressions.rvalue.newexpression.NewExpression;
import com.verba.language.parse.expressions.rvalue.simple.BooleanExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.expressions.withns.WithNsExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/9/21.
 */
public class VariableLifetimeGraph extends ExpressionTreeVisitor {
  private final VariableLifetimeMap lifetimes = new VariableLifetimeMap();
  private final FunctionDeclarationExpression function;

  public VariableLifetimeGraph(FunctionDeclarationExpression function) {
    this.function = function;

    buildGraph(function);
  }

  private void buildGraph(FunctionDeclarationExpression function) {
    BlockDeclarationExpression block = function.block();
    for (ExpressionTreeNode expression : block.expressions().cast(ExpressionTreeNode.class)) {
      expression.accept(this);
    }
  }

  public FunctionDeclarationExpression function() {
    return this.function;
  }

  public void visit(ReturnStatementExpression returnStatementExpression) {

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
  public void visit(MarkupDeclarationExpression markupDeclarationExpression) {

  }

  @Override
  public void visit(DeclarationModifierExrpression declarationModifierExrpression) {

  }

  @Override
  public void visit(NewExpression newExpression) {

  }

  @Override
  public void visit(BooleanExpression expression) {

  }

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
  public void visit(VerbaSourceCodeFile sourceFile) {
    throw new NotImplementedException();

  }

  @Override
  public void visit(BlockDeclarationExpression verbaExpressions) {
    throw new NotImplementedException();

  }

  @Override
  public void visit(LitFileRootExpression litFileRoot) {
    throw new NotImplementedException();
  }

  public void visit(NamedValueExpression namedValueExpression) {
    /*if (FunctionCallFacade.isFunctionCall(namedValueExpression)) {
      FunctionCallFacade call = new FunctionCallFacade(this.context, namedValueExpression);
      for (ExpressionTreeNode declaration : call.primaryParameters().cast(ExpressionTreeNode.class)) {
        declaration.accept(this);
      }
    }*/
  }

  @Override
  public void visit(PolymorphicDeclarationExpression classDeclarationExpression) {
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

