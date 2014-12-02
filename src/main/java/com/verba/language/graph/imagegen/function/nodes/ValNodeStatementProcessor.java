package com.verba.language.graph.imagegen.function.nodes;

import com.verba.language.emit.opcodes.LdStrOpCode;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.imagegen.function.FunctionContext;
import com.verba.language.parsing.expressions.categories.LiteralExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;

/**
 * Used to process val declaration statements found during the function graph processing.
 */
public class ValNodeStatementProcessor {
  private final FunctionContext context;

  public ValNodeStatementProcessor(FunctionContext context) {
    this.context = context;
  }

  public void process(ValDeclarationStatement statement) {
    TypeDeclarationExpression objectType = context.getObjectType(statement);

    if (statement.rvalue() instanceof LiteralExpression) {
      QuoteExpression text = (QuoteExpression) statement.rvalue();
      VirtualVariable variable = context.addVariable(statement.nameAsExpression(), objectType);

      context.addOpCode(new LdStrOpCode(variable, text.innerText()));
    }
  }
}
