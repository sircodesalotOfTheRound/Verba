package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
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

    // TODO: This is really not very thorough
    // If there is a specific type constraint, return that.
    if (this.declaration.hasTypeConstraint()) {
      QIterable<SymbolResolutionMatch> symbolsInScope =
        resolver.findSymbolsInScope(this.declaration.typeConstraint().representation());

      this.hasConsistentReturnType = true;
      return symbolsInScope.single().symbol();
    }

    // If there isn't, check the return statements.
    // If there are none, then return unit.
    QIterable<ReturnStatementExpression> returnStatements = scanForReturnStatements();
    if (!returnStatements.any()) {
      return symbolTable.findSymbolForType(KeywordToken.UNIT);
    }

    // Otherwise return the first entry:
    return returnStatements.single().returnType();
  }

  // Not very thorough, but it will work for now.
  public QIterable<ReturnStatementExpression> scanForReturnStatements() {
    return this.declaration.block()
      .expressions()
      .ofType(ReturnStatementExpression.class);
  }

  private boolean isNativeType(VerbaExpression constraint) {
    return false;
  }

  public boolean hasConsistentReturnType() { return this.hasConsistentReturnType; }
}
