package com.verba.language.graph.imagegen.function;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.images.types.basic.DebuggingObjectImage;
import com.verba.language.parsing.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parsing.expressions.codepage.VerbaCodePage;
import com.verba.language.emit.opcodes.*;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableSet;
import com.verba.language.graph.analysis.facades.FunctionCallFacade;
import com.verba.language.graph.imagegen.function.nodes.QuoteNodeProcessor;
import com.verba.language.graph.imagegen.function.nodes.ValNodeStatementProcessor;
import com.verba.language.graph.imagegen.function.variables.VariableLifetime;
import com.verba.language.graph.imagegen.function.variables.VariableLifetimeGraph;
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
import com.verba.virtualmachine.VirtualMachineNativeTypes;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class FunctionGraph implements SyntaxGraphVisitor {
  private final VirtualVariableSet variableSet;
  private final FunctionDeclarationExpression function;
  private final VariableLifetimeGraph lifetimeGraph;
  private final StaticSpaceExpression staticSpaceExpression;
  private QList<VerbajOpCodeBase> opcodes = new QList<>();

  private final FunctionContext context;

  // Node processors
  private final ValNodeStatementProcessor valStatementProcessor;
  private final QuoteNodeProcessor quoteNodeProcessor;

  public FunctionGraph(FunctionDeclarationExpression function, StaticSpaceExpression staticSpaceExpression) {
    this.variableSet = new VirtualVariableSet(20);
    this.function = function;
    this.lifetimeGraph = new VariableLifetimeGraph(function);
    this.staticSpaceExpression = staticSpaceExpression;
    this.context = new FunctionContext(staticSpaceExpression, variableSet, lifetimeGraph, opcodes);

    // Statement processors.
    this.valStatementProcessor = new ValNodeStatementProcessor(context);
    this.quoteNodeProcessor = new QuoteNodeProcessor(context);

    System.out.println(function.text());
    System.out.println();

    buildImage(function);

    DebuggingObjectImage renderer = new DebuggingObjectImage(opcodes);
    renderer.display();
  }

  private void buildImage(FunctionDeclarationExpression function) {
    BlockDeclarationExpression block = function.block();
    for (SyntaxGraphVisitable expression : block.expressions().cast(SyntaxGraphVisitable.class)) {
      expression.accept(this);
    }

    closeOutFunction();
  }

  private void closeOutFunction() {
    // If function doesn't end with return, put one there.
    if (opcodes.any() && !(opcodes.last() instanceof RetOpCode)) {
      opcodes.add(VerbajOpCodeBase.ret());
    }

    // Also close out the function.
    opcodes.add(VerbajOpCodeBase.endFunction());
  }

  public FunctionDeclarationExpression function() { return this.function; }

  public void visit(ReturnStatementExpression returnStatementExpression) {
    opcodes.add(VerbajOpCodeBase.ret());
  }

  @Override
  public void visit(SignatureDeclarationExpression signatureDeclarationExpression) {
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
      visitMethodCall(new FunctionCallFacade(namedValueExpression));
    }
  }

  @Override
  public void visit(PolymorphicDeclarationExpression classDeclarationExpression) {
    throw new NotImplementedException();
  }

  private void visitMethodCall(FunctionCallFacade call) {
      QIterable<SyntaxGraphVisitable> parametersAsFunctionElements
        = call.primaryParameters().cast(SyntaxGraphVisitable.class);

      for (SyntaxGraphVisitable declaration : parametersAsFunctionElements) {
        declaration.accept(this);
      }

      for (VerbaExpression expression : call.primaryParameters()) {
        VirtualVariable variable = this.variableSet.variableByExpression(expression);
        opcodes.add(VerbajOpCodeBase.stageArg(variable));

        if (this.lifetimeGraph.isLastOccurance(expression)) {
          // TODO: This is broken.
          //this.variableSet.expireVariable(variable);
        }
      }

      opcodes.add(VerbajOpCodeBase.call(call.functionName()));
  }

  public void visit(AssignmentStatementExpression assignmentStatementExpression) {

  }

  public void visit(NumericExpression expression) {
    VariableLifetime variableLifetime = lifetimeGraph.getVariableLifetime(expression);

    if (variableLifetime.isFirstInstance(expression)) {
      VirtualVariable source = variableSet.add(expression, VirtualMachineNativeTypes.UTF8);
      VirtualVariable destination = variableSet.add(expression, VirtualMachineNativeTypes.BOX_UINT64);

      opcodes.add(VerbajOpCodeBase.loaduint64(source, expression.asLong()));
      opcodes.add(VerbajOpCodeBase.box(source, destination));
    }
  }

  @Override
  public void visit(ValDeclarationStatement statement) {
    valStatementProcessor.process(statement);
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

  public void visit(QuoteExpression expression) {
    quoteNodeProcessor.process(expression);
  }

  public Iterable<VerbajOpCodeBase> opcodes() { return this.opcodes; }

  public String name() { return this.function.name(); }
}
