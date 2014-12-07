package com.verba.language.graph.symbols.resolution;

import com.verba.language.graph.symbols.resolution.SymbolNameResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class NamedValueExpressionTypeResolver {
  private final NamedValueExpression expression;
  private final SymbolTable symbolTable;
  private Symbol symbol;
  private Symbol resolvedType;

  public NamedValueExpressionTypeResolver(NamedValueExpression expression, SymbolTable symbolTable) {
    this.expression = expression;
    this.symbolTable = symbolTable;
    this.symbol = symbolTable.findByInstance(expression);
  }

  public Symbol resolvedType() {
    if (this.resolvedType == null) {
      this.resolvedType = determineType();
    }

    return this.resolvedType;
  }

  private Symbol determineType() {
    if (this.symbol.isParameter()) {
      return this.determineParameterType();
    }

    return symbolTable.findSymbolForType(KeywordToken.DYNAMIC);
  }

  private Symbol determineParameterType() {
    if (this.expression.hasTypeConstraint()) {
      // TODO: This is likely incorrect, and will probably need fixing later.
      return this.symbolTable.findSymbolForType(this.expression.typeConstraint().representation());
    }

    return symbolTable.findSymbolForType(KeywordToken.DYNAMIC);
  }
}
