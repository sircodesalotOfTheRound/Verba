package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.expressions.functions.tools.NodeProcessor;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.visitors.ExpressionTreeNode;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;

/**
 * Used to process val declaration statements found during the function graph processing.
 */
public class ValNodeStatementProcessor extends NodeProcessor<ValDeclarationStatement> {
  public ValNodeStatementProcessor(FunctionContext context) {
    super(context);
  }

  public VirtualVariable process(ValDeclarationStatement declaration) {
    // First generate the R-Value.
    VirtualVariable calculatedRValue = this.calculateRValue(declaration);

    // If the rvalue is another named variable, then we need to make a copy.
    // Otherwise the rvalue is a temporary and we just need to rename it.
    if (rvalueIsExistingNamedVariable(declaration, calculatedRValue)) {
      VirtualVariable destination = this.variableScope.addtoScope(declaration.name(), declaration.resolvedType());
      this.opcodes.copy(destination, calculatedRValue);
      return destination;
    } else {
      calculatedRValue.renameVariable(declaration.name());
    }

    return null;
  }

  private VirtualVariable calculateRValue(ValDeclarationStatement statement) {
    return visitAndCaptureResult((ExpressionTreeNode) statement.rvalue());
  }

  public boolean rvalueIsExistingNamedVariable(ValDeclarationStatement declaration, VirtualVariable rvalue) {
    Scope scope = this.symbolTable.findByInstance(declaration).scope();
    return scope.isInScope(rvalue.key());
  }

}
