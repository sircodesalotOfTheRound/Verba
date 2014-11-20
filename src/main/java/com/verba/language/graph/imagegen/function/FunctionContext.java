package com.verba.language.graph.imagegen.function;

import com.javalinq.implementations.QList;
import com.verba.language.codegen.opcodes.LdStrOpCode;
import com.verba.language.codegen.registers.VirtualVariable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.graph.imagegen.function.variables.VariableLifetime;
import com.verba.language.graph.imagegen.function.variables.VariableLifetimeGraph;
import com.verba.language.codegen.opcodes.VerbajOpCode;
import com.verba.language.codegen.registers.VirtualVariableSet;
import com.verba.language.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class FunctionContext {
  private final StaticSpaceExpression staticSpaceExpression;
  private final VirtualVariableSet variableSet;
  private final VariableLifetimeGraph lifetimeGraph;
  private final QList<VerbajOpCode> opcodes;

  public FunctionContext(StaticSpaceExpression staticSpaceExpression, VirtualVariableSet variableSet, VariableLifetimeGraph lifetimeGraph, QList<VerbajOpCode> opcodes) {
    this.staticSpaceExpression = staticSpaceExpression;
    this.variableSet = variableSet;
    this.lifetimeGraph = lifetimeGraph;
    this.opcodes = opcodes;
  }

  public StaticSpaceExpression staticSpaceExpression() { return this.staticSpaceExpression; }
  public VirtualVariableSet variableSet() { return this.variableSet; }
  public VariableLifetimeGraph lifetimeGraph() { return this.lifetimeGraph; }
  public QList<VerbajOpCode> opcodes() { return this.opcodes; }

  // Todo: make this take more than just val declaration statements.
  public TypeDeclarationExpression getObjectType(ValDeclarationStatement instance) { return staticSpaceExpression.getObjectType(instance); }

  public VirtualVariable addVariable(VerbaExpression expression, TypeDeclarationExpression type) { return variableSet.add(expression, type); }

  public void addOpCode(VerbajOpCode opcode) { opcodes.add(opcode); }

  public VariableLifetime getVariableLifetime(VerbaExpression expression) { return lifetimeGraph.getVariableLifetime(expression); }
}
