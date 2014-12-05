package com.verba.language.graph.expressions.functions;

import com.verba.language.build.BuildProfile;
import com.verba.language.emit.header.StringTable;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableStack;
import com.verba.language.graph.expressions.functions.variables.VariableLifetime;
import com.verba.language.graph.expressions.functions.variables.VariableLifetimeGraph;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.SyntaxGraphNode;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class FunctionContext {
  private final FunctionGraph functionGraph;
  private final StaticSpaceExpression staticSpaceExpression;
  private final VirtualVariableStack variableStack;
  private final VariableLifetimeGraph lifetimeGraph;
  private final FunctionOpCodeSet opcodes;
  private final SymbolTable symbolTable;
  private final StringTable stringTable;

  public FunctionContext(FunctionGraph functionGraph,
                         BuildProfile buildProfile,
                         StaticSpaceExpression staticSpaceExpression,
                         SymbolTable symbolTable,
                         VirtualVariableStack variableStack,
                         VariableLifetimeGraph lifetimeGraph,
                         FunctionOpCodeSet opcodes) {

    this.stringTable = buildProfile.stringTable();
    this.functionGraph = functionGraph;
    this.symbolTable = symbolTable;
    this.staticSpaceExpression = staticSpaceExpression;
    this.variableStack = variableStack;
    this.lifetimeGraph = lifetimeGraph;
    this.opcodes = opcodes;
  }

  public StaticSpaceExpression staticSpaceExpression() { return this.staticSpaceExpression; }
  public VirtualVariableStack variableStack() { return this.variableStack; }
  public VariableLifetimeGraph lifetimeGraph() { return this.lifetimeGraph; }
  public FunctionOpCodeSet opcodes() { return this.opcodes; }
  public SymbolTable symbolTable() { return this.symbolTable; }
  public StringTable stringTable() { return this.stringTable; }

  public void visit(SyntaxGraphNode node) { node.accept(functionGraph); }
  public VirtualVariable visitWithNewStackFrame(SyntaxGraphNode node) {
    return variableStack.withNewStackFrame(x -> {
      node.accept(functionGraph);
    });
  }

  // Todo: make this take more than just val declaration statements.
  public TypeDeclarationExpression getObjectType(ValDeclarationStatement instance) { return null; }

  public VariableLifetime getVariableLifetime(VerbaExpression expression) { return lifetimeGraph.getVariableLifetime(expression); }
}
