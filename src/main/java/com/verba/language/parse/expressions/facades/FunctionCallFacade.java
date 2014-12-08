package com.verba.language.parse.expressions.facades;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.expressions.functions.FunctionContext;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;

/**
 * Created by sircodesalot on 14/9/21.
 */
public class FunctionCallFacade {
  private final NamedValueExpression expression;
  private final FunctionDeclarationExpression function;
  private final FunctionContext context;
  private final SymbolTable symbolTable;

  public FunctionCallFacade(FunctionContext context, NamedValueExpression expression) {
    this.context = context;
    this.symbolTable = context.symbolTable();
    this.expression = expression;

    this.function = determineFunction(expression);
  }

  private FunctionDeclarationExpression determineFunction(NamedValueExpression expression) {
    return symbolTable.findAllMatchingFqn(expression.name())
      .single(entry -> entry.is(FunctionDeclarationExpression.class))
      .expressionAs(FunctionDeclarationExpression.class);
  }

  // Continuation means that the return value of the function call
  // is also called. For example if a function returns another function,
  // and that function is called, like:
  //
  //  fn outer(first, second) { return fn inner(lhs, rhs) }
  //
  // is called using:
  //
  // outer(1, 2)(3, 4) # This calls outer, which returns inner which is called.
  public boolean hasCallContinuation() {
    return expression.parameters().count() > 1;
  }

  public String functionName() { return expression.name(); }

  public QIterable<VerbaExpression> primaryParameters() {
    return expression.identifier().members().first().parameterLists().first().items();
  }

  public static boolean isFunctionCall(NamedValueExpression expression) {
    return expression.hasParameters();
  }

  private Symbol determineType() { return null; }

  public Symbol resolvedType() { return this.function.resolvedType(); }
}
