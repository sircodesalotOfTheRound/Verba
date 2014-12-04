package com.verba.language.graph.expressions.functions.node;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.visitors.SyntaxGraphNode;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;

/**
 * Used to process val declaration statements found during the function graph processing.
 */
public class ValNodeStatementProcessor {
  private final FunctionContext context;

  public ValNodeStatementProcessor(FunctionContext context) {
    this.context = context;
  }

  public void process(ValDeclarationStatement declaration) {
    // First generate the R-Value. Then rename it.
    VirtualVariable calculatedRValue = this.calculateRValue(declaration);
    calculatedRValue.renameVariable(declaration.name());
  }

  public VirtualVariable calculateRValue(ValDeclarationStatement statement) {
    return context.visitWithNewStackFrame((SyntaxGraphNode) statement.rvalue());
  }
}
