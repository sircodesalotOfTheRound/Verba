package com.verba.language.graph.expressions.functions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.targets.artifacts.BuildSpecificationArtifact;
import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.emit.images.types.basic.DebuggingObjectImage;
import com.verba.language.emit.opcodes.RetOpCode;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.variables.VirtualVariableScopeTree;
import com.verba.language.graph.expressions.functions.tools.NodeProcessorFactory;
import com.verba.language.graph.expressions.functions.variables.VariableLifetimeGraph;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.LitFileRootExpression;
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
 * Created by sircodesalot on 14/9/19.
 */
public class FunctionGraphVisitor extends ExpressionTreeVisitor {
  private final Build build;
  private final VirtualVariableScopeTree variableSet;
  private final FunctionDeclarationExpression function;
  private final VariableLifetimeGraph lifetimeGraph;
  private final SymbolTable symbolTable;
  private final StringTableArtifact stringTable;

  private final FunctionContext context;
  private final FunctionOpCodeSet opcodes;
  private final NodeProcessorFactory nodeProcessors;

  // Node processors

  public FunctionGraphVisitor(Build build, FunctionDeclarationExpression function, SymbolTable symbolTable) {
    this.build = build;
    this.variableSet = new VirtualVariableScopeTree(20);
    this.function = function;
    this.lifetimeGraph = new VariableLifetimeGraph(function);
    this.symbolTable = symbolTable;
    this.stringTable = build.getArtifactOfType(StringTableArtifact.class);
    this.opcodes = new FunctionOpCodeSet();
    this.context = new FunctionContext(this, build, symbolTable, variableSet, lifetimeGraph, opcodes);
    this.nodeProcessors = new NodeProcessorFactory(context);


    this.buildImage(function);
    this.performDebugOutput();
  }

  private void performDebugOutput() {
    if (this.build.containsArtifactOfType(BuildSpecificationArtifact.class)) {
      BuildSpecificationArtifact specification = build.getArtifactOfType(BuildSpecificationArtifact.class);
      if (specification.isDebugBuild()) {
        System.out.println(function.text());
        System.out.println();

        DebuggingObjectImage renderer = new DebuggingObjectImage(opcodes);
        renderer.display();
      }
    }
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
  public void visit(BlockDeclarationExpression verbaExpressions) {
    throw new NotImplementedException();
  }

  @Override
  public void visit(LitFileRootExpression litFileRoot) {
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
  public void visit(BooleanExpression expression) {
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
  public void visit(VerbaSourceCodeFile sourceFile) {
    throw new NotImplementedException();
  }

  public void visit(QuoteExpression expression) {
    this.nodeProcessors.process(expression);
  }

}
