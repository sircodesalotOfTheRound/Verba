package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.categories.SymbolTableExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class FunctionReturnTypeResolver {
  private final GlobalSymbolTable symbolTable;
  private final FunctionDeclarationExpression declaration;
  private final ScopedSymbolTable scope;

  public FunctionReturnTypeResolver(GlobalSymbolTable symbolTable, FunctionDeclarationExpression declaration) {
    this.symbolTable = symbolTable;
    this.declaration = declaration;
    this.scope = symbolTable.getByInstance(declaration).table();
  }

  public SymbolTableEntry resolve() {
    SymbolNameResolver resolver = new SymbolNameResolver(symbolTable, scope);

    if (this.declaration.hasTypeConstraint()) {
      QIterable<SymbolResolutionMatch> matches = resolver
        .findSymbolsInScope(this.declaration.typeDeclaration().representation());

      return matches.single().entry();
    }

    throw new NotImplementedException();
  }


}
