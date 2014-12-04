package com.verba.language.graph.expressions.functions.nodes;

import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parse.expressions.categories.LiteralExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;

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
      VirtualVariable variable = context.addVariable(statement.name(), context.nativeTypeSymbols().UTF8);

      context.addOpCode(VerbatimOpCodeBase.loadString(variable, text.innerText()));
    }
  }
}
