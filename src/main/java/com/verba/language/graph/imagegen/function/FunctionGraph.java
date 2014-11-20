package com.verba.language.graph.imagegen.function;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.imagegen.function.nodes.QuoteNodeProcessor;
import com.verba.language.graph.imagegen.function.nodes.ValNodeStatementProcessor;
import com.verba.language.graph.visitors.SyntaxGraphVisitable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.graph.imagegen.function.variables.VariableLifetime;
import com.verba.language.graph.imagegen.function.variables.VariableLifetimeGraph;
import com.verba.language.codegen.opcodes.*;
import com.verba.language.codegen.registers.VirtualVariable;
import com.verba.language.codegen.registers.VirtualVariableSet;
import com.verba.language.codegen.rendering.functions.DebugOpCodeRenderer;
import com.verba.language.codegen.rendering.functions.MemoryStreamFunctionRenderer;
import com.verba.language.codegen.rendering.images.ObjectImage;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.classes.TraitDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.TaskDeclarationExpression;
import com.verba.language.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.expressions.containers.json.JsonExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.facades.FunctionCallFacade;
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
  private QList<VerbajOpCode> opcodes = new QList<>();

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

    DebugOpCodeRenderer renderer = new DebugOpCodeRenderer(opcodes);
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
      opcodes.add(new RetOpCode());
    }

    // Also close out the function.
    opcodes.add(new EndFunctionOpCode());
  }

  public ObjectImage createImage() {
    return new MemoryStreamFunctionRenderer(this);
  }

  public FunctionDeclarationExpression function() { return this.function; }

  public void visit(ReturnStatementExpression returnStatementExpression) {
    opcodes.add(new RetOpCode());
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
  public void visit(TraitDeclarationExpression traitDeclarationExpression) {
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
  public void visit(ClassDeclarationExpression classDeclarationExpression) {
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
        opcodes.add(new StageArgOpCode(variable));

        if (this.lifetimeGraph.isLastOccurance(expression)) {
          // TODO: This is broken.
          //this.variableSet.expireVariable(variable);
        }
      }

      opcodes.add(new CallOpCode(call.functionName()));
  }

  public void visit(AssignmentStatementExpression assignmentStatementExpression) {

  }

  public void visit(NumericExpression expression) {
    VariableLifetime variableLifetime = lifetimeGraph.getVariableLifetime(expression);

    if (variableLifetime.isFirstInstance(expression)) {
      VirtualVariable source = variableSet.add(expression, VirtualMachineNativeTypes.UTF8);
      VirtualVariable destination = variableSet.add(expression, VirtualMachineNativeTypes.BOX_UINT64);

      opcodes.add(new LdUint64OpCode(source, expression.asLong()));
      opcodes.add(new BoxOpCode(source, destination));
    }
  }

  @Override
  public void visit(ValDeclarationStatement statement) {
    valStatementProcessor.process(statement);
  }

  @Override
  public void visit(FunctionDeclarationExpression functionDeclarationExpression) {
    // Todo: currently no op.
  }

  @Override
  public void visit(TaskDeclarationExpression taskDeclarationExpression) {
    throw new NotImplementedException();

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

  public Iterable<VerbajOpCode> opcodes() { return this.opcodes; }

  public String name() { return this.function.name(); }
}
