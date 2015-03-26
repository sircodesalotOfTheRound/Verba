package com.verba.language.graph.symbols.resolution;

import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.LiteralExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.UtfExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.platform.PlatformTypeSymbols;

/**
 * Created by sircodesalot on 14/12/7.
 */
public class ValDeclarationTypeResolver {
  private final ValDeclarationStatement declaration;
  private Symbol resolvedType;

  public ValDeclarationTypeResolver(ValDeclarationStatement declaration) {
    this.declaration = declaration;
  }

  public Symbol resolvedType(SymbolTable symbolTable) {
    if (this.resolvedType == null) {
      this.resolvedType = determineType(symbolTable);
    }

    return this.resolvedType;
  }

  private Symbol determineType(SymbolTable symbolTable) {
    if (this.declaration.hasTypeConstraint()) {
      return symbolTable.findSymbolForType(this.declaration.typeConstraint().representation());
    }

    return determineTypeFromRhs(symbolTable);
  }

  private Symbol determineTypeFromRhs(SymbolTable symbolTable) {
    if (this.declaration.hasRValue()) {
      RValueExpression rvalue = this.declaration.rvalue();
      if (rvalue instanceof LiteralExpression) {
        return determineTypeFromLiteral(rvalue, symbolTable);
      } else if (rvalue instanceof NamedValueExpression) {
        return resolveByName(((NamedValueExpression) rvalue).name(), symbolTable);
      }
    }

    // TODO: No resolution. There should be a 'No-Type' symbol.
    return null;
  }

  private Symbol determineTypeFromLiteral(RValueExpression rvalue, SymbolTable symbolTable) {
    if (rvalue instanceof UtfExpression) return PlatformTypeSymbols.UTF;
    if (rvalue instanceof NumericExpression) return PlatformTypeSymbols.INT;

    return null;
  }

  private Symbol resolveByName(String name, SymbolTable symbolTable) {
    Scope scope = symbolTable.resolveScope(this.declaration);
    SymbolNameResolver resolver = new SymbolNameResolver(symbolTable, scope);
    SymbolResolutionMatch match = resolver.findSymbolsInScope(name).single();

    if (match.symbol().is(ValDeclarationStatement.class)) {
      return match
        .symbol()
        .expressionAs(ValDeclarationStatement.class)
        .resolvedType();
    }

    return match.symbol();
  }
}
