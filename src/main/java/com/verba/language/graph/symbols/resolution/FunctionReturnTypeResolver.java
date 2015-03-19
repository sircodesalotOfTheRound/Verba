package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class FunctionReturnTypeResolver {
  private final FunctionDeclarationExpression declaration;
  private boolean hasConsistentReturnType = false;
  private Symbol resolvedType;

  public FunctionReturnTypeResolver(FunctionDeclarationExpression declaration) {
    this.declaration = declaration;
  }

  public Symbol resolve(SymbolTable symbolTable) {
    Scope scope = symbolTable.findByInstance(declaration).scope();
    SymbolNameResolver resolver = new SymbolNameResolver(symbolTable, scope);

    // TODO: This is really not very thorough
    // If there is a specific type constraint, return that.
    if (this.declaration.hasTypeConstraint()) {
      QIterable<SymbolResolutionMatch> symbolsInScope =
        resolver.findSymbolsInScope(this.declaration.typeConstraint().representation());

      if (symbolsInScope.any()) {
        this.hasConsistentReturnType = true;
        this.resolvedType = symbolsInScope.single().symbol();
        return this.resolvedType;
      } else {
        this.hasConsistentReturnType = false;
        this.resolvedType = null;
      }
    }

    // If there isn't, check the return statements.
    // If there are none, then return unit.
    QIterable<ReturnStatementExpression> returnStatements = scanForReturnStatements();
    if (!returnStatements.any()) {
      return symbolTable.findSymbolForType(KeywordToken.UNIT);
    }

    // Otherwise return the first entry:
    this.resolvedType = returnStatements.single().returnType();
    return this.resolvedType;
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
  public Symbol resolvedType() { return this.resolvedType; }
}
