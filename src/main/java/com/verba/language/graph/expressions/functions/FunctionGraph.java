package com.verba.language.graph.expressions.functions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.images.types.basic.DebuggingObjectImage;
import com.verba.language.emit.opcodes.RetOpCode;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableStack;
import com.verba.language.graph.expressions.functions.node.QuoteNodeProcessor;
import com.verba.language.graph.expressions.functions.node.ValNodeStatementProcessor;
import com.verba.language.graph.expressions.functions.variables.VariableLifetime;
import com.verba.language.graph.expressions.functions.variables.VariableLifetimeGraph;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.visitors.SyntaxGraphVisitable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.language.parse.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.facades.FunctionCallFacade;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.expressions.withns.WithNsExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class FunctionGraph implements SyntaxGraphVisitor {
  private final VirtualVariableStack variableSet;
  private final FunctionDeclarationExpression function;
  private final VariableLifetimeGraph lifetimeGraph;
  private final StaticSpaceExpression staticSpaceExpression;
  private final GlobalSymbolTable symbolTable;

  private final FunctionContext context;
  private final FunctionOpCodeSet opcodes;

  // Node processors
  private final ValNodeStatementProcessor valStatementProcessor;
  private final QuoteNodeProcessor quoteNodeProcessor;

  public FunctionGraph(FunctionDeclarationExpression function, GlobalSymbolTable symbolTable, StaticSpaceExpression staticSpaceExpression) {
    this.variableSet = new VirtualVariableStack(20);
    this.function = function;
    this.lifetimeGraph = new VariableLifetimeGraph(function);
    this.staticSpaceExpression = staticSpaceExpression;
    this.symbolTable = symbolTable;
    this.opcodes = new FunctionOpCodeSet();
    this.context = new FunctionContext(staticSpaceExpression, symbolTable, variableSet, lifetimeGraph, opcodes);

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
      opcodes.ret();
    }

    // Also close out the function.
    opcodes.endFunction();
  }

  public FunctionDeclarationExpression function() { return this.function; }

  public void visit(ReturnStatementExpression returnStatementExpression) {
    opcodes.ret();
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
        VirtualVariable variable = this.variableSet.variableByName(expression.text());
        opcodes.stageArg(variable);

        if (this.lifetimeGraph.isLastOccurance(expression)) {
          // TODO: This is broken.
          //this.variableSet.expireVariable(variable);
        }
      }

      opcodes.call(call.functionName());
  }

  public void visit(AssignmentStatementExpression assignmentStatementExpression) {

  }

  public void visit(NumericExpression expression) {
    VariableLifetime variableLifetime = lifetimeGraph.getVariableLifetime(expression);

    /*if (variableLifetime.isFirstInstance(expression)) {
      this.symbolTable.getByFqn()

      VirtualVariable source = variableSet.add(expression, this.staticSpaceExpression);
      VirtualVariable destination = variableSet.add(expression, VirtualMachineNativeTypes.BOX_UINT64);

      opcodes.add(VerbatimOpCodeBase.loaduint64(source, expression.asLong()));
      opcodes.add(VerbatimOpCodeBase.box(source, destination));
    }*/
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

  public Iterable<VerbatimOpCodeBase> opcodes() { return this.opcodes; }

  public String name() { return this.function.name(); }
}
