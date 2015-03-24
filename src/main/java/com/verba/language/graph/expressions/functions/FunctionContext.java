package com.verba.language.graph.expressions.functions;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.emit.variables.VirtualVariableScopeTree;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.variables.VariableLifetimeGraph;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.graph.visitors.FunctionGraphNode;
import com.verba.language.parse.expressions.LitFileRootExpression;

import java.util.function.Consumer;

/**
 * Created by sircodesalot on 14/10/3.
 */
public class FunctionContext {
  private final FunctionGraphVisitor functionGraphVisitor;
  private final VirtualVariableScopeTree variableScopeTree;
  private final VariableLifetimeGraph lifetimeGraph;
  private final FunctionOpCodeSet opcodes;
  private final SymbolTable symbolTable;
  private final StringTableArtifact stringTable;

  public FunctionContext(FunctionGraphVisitor functionGraphVisitor,
                         Build build,
                         SymbolTable symbolTable,
                         VirtualVariableScopeTree variableScopeTree,
                         VariableLifetimeGraph lifetimeGraph,
                         FunctionOpCodeSet opcodes) {

    this.stringTable = build.getArtifactOfType(StringTableArtifact.class);
    this.functionGraphVisitor = functionGraphVisitor;
    this.symbolTable = symbolTable;
    this.variableScopeTree = variableScopeTree;
    this.lifetimeGraph = lifetimeGraph;
    this.opcodes = opcodes;
  }

  public VirtualVariableScopeTree variableScopeTree() { return this.variableScopeTree; }
  public VariableLifetimeGraph lifetimeGraph() { return this.lifetimeGraph; }
  public FunctionOpCodeSet opcodes() { return this.opcodes; }
  public SymbolTable symbolTable() { return this.symbolTable; }
  public StringTableArtifact stringTable() { return this.stringTable; }

  public VirtualVariable visit(FunctionGraphNode node) {
    return node.accept(functionGraphVisitor);
  }

  @Deprecated
  public VirtualVariable visitWithNewVarScope(FunctionGraphNode node) {
    return variableScopeTree.withNewVariableScope(new Consumer<Object>() {
      @Override
      public void accept(Object o) {
        node.accept(functionGraphVisitor);
      }
    });
  }

}
