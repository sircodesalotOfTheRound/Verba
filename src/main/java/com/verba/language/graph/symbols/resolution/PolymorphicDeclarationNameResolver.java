package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parsing.expressions.categories.NamedExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class PolymorphicDeclarationNameResolver {
  private final GlobalSymbolTable symbolTable;
  private final PolymorphicDeclarationExpression declaration;
  private final QIterable<SymbolTableEntry> scopedEntries;

  public PolymorphicDeclarationNameResolver(GlobalSymbolTable symbolTable, PolymorphicDeclarationExpression declaration) {
    this.symbolTable = symbolTable;
    this.declaration = declaration;
    this.scopedEntries = determineNamesInScope(declaration);
  }

  public QIterable<SymbolTableEntry> determineNamesInScope(PolymorphicDeclarationExpression declaration) {
    return declaration.block().expressions()
      .ofType(NamedExpression.class)
      .map(expression -> this.symbolTable.getByInstance((VerbaExpression) expression));
  }

  public PolymorphicDeclarationExpression declaration() { return declaration; }
  public QIterable<SymbolTableEntry> immediateMembers() { return this.scopedEntries; }
}
