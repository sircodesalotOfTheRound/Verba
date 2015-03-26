package com.verba.language.graph.expressions.functions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.BuildSpecificationArtifact;
import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.emit.images.types.common.DebuggingObjectImage;
import com.verba.language.emit.opcodes.RetOpCode;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableSet;
import com.verba.language.graph.expressions.functions.tools.NodeProcessorFactory;
import com.verba.language.graph.expressions.functions.variables.VariableLifetimeGraph;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.FunctionGraphNode;
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
import com.verba.language.parse.expressions.rvalue.simple.InfixExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.UtfExpression;
import com.verba.language.parse.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.expressions.withns.WithNsExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class FunctionGraphVisitor {
  private final Build build;
  //private final VirtualVariableScopeTree variableSet;
  private final VirtualVariableSet variableSet;
  private final FunctionDeclarationExpression function;
  private final VariableLifetimeGraph lifetimeGraph;
  private final SymbolTable symbolTable;
  private final StringTableArtifact stringTable;

  private final FunctionContext context;
  private final FunctionOpCodeSet opcodes;
  private final NodeProcessorFactory nodeProcessors;

  public FunctionGraphVisitor(Build build, FunctionDeclarationExpression function, SymbolTable symbolTable) {
    this.build = build;
    this.variableSet = new VirtualVariableSet();
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

        this.showFunctionHeader();
        System.out.println();

        DebuggingObjectImage renderer = new DebuggingObjectImage(opcodes);
        renderer.display();
      }
    }
  }

  private void showFunctionHeader() {
    String message = String.format("%s : %s", function.name(), function.resolvedType().fqn());
    System.out.println(message);
    System.out.println();
  }

  public FunctionContext context() { return this.context; }
  public QIterable<VerbatimOpCodeBase> opcodes() { return this.opcodes; }

  private void buildImage(FunctionDeclarationExpression function) {
    BlockDeclarationExpression block = function.block();
    for (FunctionGraphNode expression : block.expressions().cast(FunctionGraphNode.class)) {
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

  public VirtualVariable visit(ReturnStatementExpression expression) {
    return this.nodeProcessors.process(expression);
  }


  public VirtualVariable visit(BlockDeclarationExpression verbaExpressions) {
    throw new NotImplementedException();
  }


  public VirtualVariable visit(LitFileRootExpression litFileRoot) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(NamedValueExpression expression) {
    return this.nodeProcessors.process(expression);
  }


  public VirtualVariable visit(PolymorphicDeclarationExpression classDeclarationExpression) {
    throw new NotImplementedException();
  }


  public VirtualVariable visit(AssignmentStatementExpression assignmentStatementExpression) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(NumericExpression expression) {
    return this.nodeProcessors.process(expression);
  }


  public VirtualVariable visit(ValDeclarationStatement statement) {
    return this.nodeProcessors.process(statement);
  }

  public VirtualVariable visit(WithNsExpression withNsExpression) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(MarkupDeclarationExpression markupDeclarationExpression) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(DeclarationModifierExrpression declarationModifierExrpression) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(NewExpression expression) {
    return this.nodeProcessors.process(expression);
  }

  public VirtualVariable visit(BooleanExpression expression) {
    return this.nodeProcessors.process(expression);
  }

  public VirtualVariable visit(FunctionDeclarationExpression functionDeclarationExpression) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(ArrayDeclarationExpression arrayDeclarationExpression) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(JsonExpression jsonExpression) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(TupleDeclarationExpression tupleDeclarationExpression) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(VerbaSourceCodeFile sourceFile) {
    throw new NotImplementedException();
  }

  public VirtualVariable visit(UtfExpression expression) {
    return this.nodeProcessors.process(expression);
  }

  public VirtualVariable visit(InfixExpression expression) {
    return this.nodeProcessors.process(expression);
  }
}
