package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class FunctionReturnTypeResolver {
  private final SymbolTable symbolTable;
  private final FunctionDeclarationExpression declaration;
  private final Scope scope;
  private boolean hasConsistentReturnType = false;

  public FunctionReturnTypeResolver(SymbolTable symbolTable, FunctionDeclarationExpression declaration) {
    this.symbolTable = symbolTable;
    this.declaration = declaration;
    this.scope = symbolTable.getByInstance(declaration).table();
  }

  public Symbol resolve() {
    SymbolNameResolver resolver = new SymbolNameResolver(symbolTable, scope);

    if (this.declaration.hasTypeConstraint()) {

      // TODO: Native types should be added to symbol table.
      /*if (isNativeType(this.declaration.typeConstraint())) {
        return
      }*/

      QIterable<SymbolResolutionMatch> matches = resolver
        .findSymbolsInScope(this.declaration.typeConstraint().representation());

      this.hasConsistentReturnType = true;
      return matches.single().entry();
    }

    throw new NotImplementedException();
  }

  private boolean isNativeType(VerbaExpression constraint) {
    return false;
  }

  public boolean hasConsistentReturnType() { return this.hasConsistentReturnType; }
}
