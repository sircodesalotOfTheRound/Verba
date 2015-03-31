package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;

/**
 * Created by sircodesalot on 15/3/31.
 */
public class PolymorphicDeclarationTraitResolver {
  private final PolymorphicDeclarationExpression declaration;
  private QIterable<Symbol> bases;
  private Partition<String, Symbol> basesByName;

  public PolymorphicDeclarationTraitResolver(PolymorphicDeclarationExpression declaration) {
    this.declaration = declaration;
  }

  public QIterable<Symbol> determineBases(SymbolTable symbolTable) {
    Scope scope = symbolTable.resolveScope(declaration);
    SymbolNameResolver resolver = new SymbolNameResolver(symbolTable, scope);

    return declaration.traitNames()
      .map(expression -> {
        SymbolNameResolver.SymbolNameQueryBuilder query =
          new SymbolNameResolver.SymbolNameQueryBuilder(expression.representation());

        return resolver.findBestMatchForName(query);
      }).toList();
  }

  public PolymorphicDeclarationExpression declaration() { return declaration; }
  public QIterable<Symbol> traits() { return this.bases; }

  public void resolve(SymbolTable table) {
    this.bases = determineBases(table);
    this.basesByName = this.bases.parition(Symbol::name);
  }
}
