package com.verba.language.graph.statictyping;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.categories.ResolvableTypeExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.graph.symbols.resolution.fields.VariableTypeResolver;
import com.verba.language.graph.symbols.resolution.function.FunctionReturnTypeResolver;
import com.verba.language.graph.symbols.resolution.polymorphic.PolymorphicResolver;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;

/**
 * Resolves symbol table information about application symbols.
 */
public class SymbolTypeResolver {
  private final GlobalSymbolTable symbolTable;
  private final PolymorphicResolver polymorphicResolver;
  private final VariableTypeResolver variableResolver;
  private final FunctionReturnTypeResolver functionResolver;


  public SymbolTypeResolver(GlobalSymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.polymorphicResolver = new PolymorphicResolver(symbolTable);
    this.variableResolver = new VariableTypeResolver(symbolTable);
    this.functionResolver = new FunctionReturnTypeResolver(symbolTable);
  }

  public void resolve(ResolvableTypeExpression expression) {
    expression.accept(this);
  }

  public void resolveAll() {
    QIterable<ResolvableTypeExpression> resolvableExpressions = this.symbolTable
      .entries()
      .map(SymbolTableEntry::instance)
      .ofType(ResolvableTypeExpression.class);

    for (ResolvableTypeExpression expression : resolvableExpressions) {
      expression.accept(this);
    }
  }

  public void visit(ValDeclarationStatement valDeclarationStatement) {
    variableResolver.resolve(valDeclarationStatement);
  }

  public void visit(FunctionDeclarationExpression function) {
    functionResolver.resolve(function);
  }

  public static void resolveNames(GlobalSymbolTable globalSymbolTable) {
    new SymbolTypeResolver(globalSymbolTable).resolveAll();
  }

}
