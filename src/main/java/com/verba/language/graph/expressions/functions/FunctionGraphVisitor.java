package com.verba.language.graph.expressions.functions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.BuildProfile;
import com.verba.language.emit.header.StringTable;
import com.verba.language.emit.images.types.basic.DebuggingObjectImage;
import com.verba.language.emit.opcodes.RetOpCode;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.variables.VirtualVariableScopeTree;
import com.verba.language.graph.expressions.functions.tools.NodeProcessorFactory;
import com.verba.language.graph.expressions.functions.variables.VariableLifetimeGraph;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.language.parse.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpression;
import com.verba.language.parse.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.expressions.rvalue.newexpression.NewExpression;
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
public class FunctionGraphVisitor implements ExpressionTreeVisitor {
  private final VirtualVariableScopeTree variableSet;
  private final FunctionDeclarationExpression function;
  private final VariableLifetimeGraph lifetimeGraph;
  private final StaticSpaceExpression staticSpaceExpression;
  private final SymbolTable symbolTable;
  private final StringTable stringTable;

  private final FunctionContext context;
  private final FunctionOpCodeSet opcodes;
  private final NodeProcessorFactory nodeProcessors;

  // Node processors

  public FunctionGraphVisitor(BuildProfile buildProfile, FunctionDeclarationExpression function, SymbolTable symbolTable, StaticSpaceExpression staticSpaceExpression) {
    this.variableSet = new VirtualVariableScopeTree(20);
    this.function = function;
    this.lifetimeGraph = new VariableLifetimeGraph(function);
    this.staticSpaceExpression = staticSpaceExpression;
    this.symbolTable = symbolTable;
    this.stringTable = buildProfile.stringTable();
    this.opcodes = new FunctionOpCodeSet();
    this.context = new FunctionContext(this, buildProfile, staticSpaceExpression, symbolTable, variableSet, lifetimeGraph, opcodes);
    this.nodeProcessors = new NodeProcessorFactory(context);

    System.out.println(function.text());
    System.out.println();

    this.buildImage(function);

    DebuggingObjectImage renderer = new DebuggingObjectImage(opcodes);
    renderer.display();
  }

  public QIterable<VerbatimOpCodeBase> opcodes() { return this.opcodes; }

  private void buildImage(FunctionDeclarationExpression function) {
    BlockDeclarationExpression block = function.block();
    for (ExpressionTreeNode expression : block.expressions().cast(ExpressionTreeNode.class)) {
      expression.accept(this);
    }

    closeFunction();
  }

  private void closeFunction() {
    // If function doesn't end with return, put one there.
    if (opcodes.any() && !(opcodes.last() instanceof RetOpCode)) {
      opcodes.ret();
    }

    // Also close out the function.
    opcodes.endFunction();
  }

  public void visit(ReturnStatementExpression expression) {
    this.nodeProcessors.process(expression);
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

  public void visit(NamedValueExpression expression) {
    this.nodeProcessors.process(expression);
  }

  @Override
  public void visit(PolymorphicDeclarationExpression classDeclarationExpression) {
    throw new NotImplementedException();
  }


  public void visit(AssignmentStatementExpression assignmentStatementExpression) {

  }

  public void visit(NumericExpression expression) {
    this.nodeProcessors.process(expression);
  }

  @Override
  public void visit(ValDeclarationStatement statement) {
    this.nodeProcessors.process(statement);
  }

  @Override
  public void visit(WithNsExpression withNsExpression) {
  }

  @Override
  public void visit(MarkupDeclarationExpression markupDeclarationExpression) {

  }

  @Override
  public void visit(DeclarationModifierExrpression declarationModifierExrpression) {

  }

  @Override
  public void visit(NewExpression expression) {
    this.nodeProcessors.process(expression);
  }

  @Override
  public void visit(FunctionDeclarationExpression functionDeclarationExpression) {

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
    this.nodeProcessors.process(expression);
  }

}
