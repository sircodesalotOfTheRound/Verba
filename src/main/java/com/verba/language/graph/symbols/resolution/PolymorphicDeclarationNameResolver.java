package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.categories.NamedExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class PolymorphicDeclarationNameResolver {
  private final GlobalSymbolTable symbolTable;
  private final PolymorphicDeclarationExpression declaration;
  private final QIterable<Symbol> scopedEntries;

  public PolymorphicDeclarationNameResolver(GlobalSymbolTable symbolTable, PolymorphicDeclarationExpression declaration) {
    this.symbolTable = symbolTable;
    this.declaration = declaration;
    this.scopedEntries = determineNamesInScope(declaration);
  }

  public QIterable<Symbol> determineNamesInScope(PolymorphicDeclarationExpression declaration) {
    return declaration.block().expressions()
      .ofType(NamedExpression.class)
      .map(expression -> this.symbolTable.getByInstance((VerbaExpression) expression));
  }

  public PolymorphicDeclarationExpression declaration() { return declaration; }
  public QIterable<Symbol> immediateMembers() { return this.scopedEntries; }
}
