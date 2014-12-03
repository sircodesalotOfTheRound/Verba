package com.verba.language.graph.expressions.functions;

import com.javalinq.implementations.QList;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableSet;
import com.verba.language.graph.expressions.functions.variables.VariableLifetime;
import com.verba.language.graph.expressions.functions.variables.VariableLifetimeGraph;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class FunctionContext {
  private final StaticSpaceExpression staticSpaceExpression;
  private final VirtualVariableSet variableSet;
  private final VariableLifetimeGraph lifetimeGraph;
  private final QList<VerbatimOpCodeBase> opcodes;

  public FunctionContext(StaticSpaceExpression staticSpaceExpression, VirtualVariableSet variableSet, VariableLifetimeGraph lifetimeGraph, QList<VerbatimOpCodeBase> opcodes) {
    this.staticSpaceExpression = staticSpaceExpression;
    this.variableSet = variableSet;
    this.lifetimeGraph = lifetimeGraph;
    this.opcodes = opcodes;
  }

  public StaticSpaceExpression staticSpaceExpression() { return this.staticSpaceExpression; }
  public VirtualVariableSet variableSet() { return this.variableSet; }
  public VariableLifetimeGraph lifetimeGraph() { return this.lifetimeGraph; }
  public QList<VerbatimOpCodeBase> opcodes() { return this.opcodes; }

  // Todo: make this take more than just val declaration statements.
  public TypeDeclarationExpression getObjectType(ValDeclarationStatement instance) { return null; }

  public VirtualVariable addVariable(VerbaExpression expression, TypeDeclarationExpression type) { return variableSet.add(expression, type); }

  public void addOpCode(VerbatimOpCodeBase opcode) { opcodes.add(opcode); }

  public VariableLifetime getVariableLifetime(VerbaExpression expression) { return lifetimeGraph.getVariableLifetime(expression); }
}
