package com.verba.language.graph.symbols.resolution;

import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.categories.LiteralExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class ValDeclarationTypeResolver {
  private final ValDeclarationStatement declaration;
  private final SymbolTable symbolTable;
  private Symbol resolvedType;

  public ValDeclarationTypeResolver(ValDeclarationStatement declaration, SymbolTable symbolTable) {
    this.declaration = declaration;
    this.symbolTable = symbolTable;
  }

  public Symbol resolvedType() {
    if (this.resolvedType == null) {
      this.resolvedType = determineType();
    }

    return this.resolvedType;
  }

  private Symbol determineType() {
    if (this.declaration.hasTypeConstraint()) {
      return symbolTable.findSymbolForType(this.declaration.typeConstraint().representation());
    }

    return determineTypeFromRhs();
  }

  private Symbol determineTypeFromRhs() {
    if (this.declaration.hasRValue()) {
      RValueExpression rvalue = this.declaration.rvalue();
      if (rvalue instanceof LiteralExpression) return determineTypeFromLiteral(rvalue);
    }

    // TODO: No resolution. There should be a 'No-Type' symbol.
    return null;
  }

  private Symbol determineTypeFromLiteral(RValueExpression rvalue) {
    if (rvalue instanceof QuoteExpression) return symbolTable.findSymbolForType(KeywordToken.UTF);
    if (rvalue instanceof NumericExpression) return symbolTable.findSymbolForType(KeywordToken.INT);

    return null;
  }
}
