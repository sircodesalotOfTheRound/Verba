package com.verba.language.graph.imagegen.function.nodes;

import com.javalinq.implementations.QList;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.graph.imagegen.function.FunctionContext;
import com.verba.language.codegen.opcodes.LdStrOpCode;
import com.verba.language.codegen.opcodes.VerbajOpCode;
import com.verba.language.codegen.registers.VirtualVariable;
import com.verba.language.codegen.registers.VirtualVariableSet;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.categories.LiteralExpression;
import com.verba.language.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.symbols.meta.interfaces.SymbolTypeMetadata;
import com.verba.language.symbols.table.entries.SymbolTableEntry;

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
