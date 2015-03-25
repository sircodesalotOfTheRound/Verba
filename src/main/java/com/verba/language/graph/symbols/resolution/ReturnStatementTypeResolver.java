package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.meta.ParameterSymbolMetadata;
import com.verba.language.graph.symbols.resolution.SymbolNameResolver;
import com.verba.language.graph.symbols.resolution.SymbolResolutionMatch;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.*;
import com.verba.language.parse.expressions.rvalue.simple.InfixExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.platform.PlatformTypeSymbols;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/12/6.
 */
public class ReturnStatementTypeResolver {
  private final ReturnStatementExpression statement;
  private Symbol returnType;

  public ReturnStatementTypeResolver(ReturnStatementExpression statement) {
    this.statement = statement;
  }

  private Symbol determineReturnValue(SymbolTable symbolTable) {
    if (!statement.hasValue()) {
      return PlatformTypeSymbols.UNIT;
    } else {
      return determineTypeForValue(statement.value(), symbolTable);
    }
  }

  private Symbol determineTypeForValue(RValueExpression value, SymbolTable symbolTable) {
    // TODO
    if (value instanceof InfixExpression) {
      return resolveInfixExpressionReturnType((InfixExpression)value, symbolTable);
    }

    if (value instanceof LiteralExpression) {
      if (value instanceof QuoteExpression) return PlatformTypeSymbols.UTF;
      if (value instanceof NumericExpression) return resolveIntegertype((NumericExpression)value, symbolTable);
    }

    if (value instanceof NamedExpression) {
      NamedExpression namedExpression = (NamedExpression)value;
      Symbol matchingName = determineMatchingSymbolForName(namedExpression.name(), symbolTable);
      return determineTypeForMatchingName(symbolTable, matchingName);
    }

    throw new NotImplementedException();
  }


  private Symbol determineMatchingSymbolForName(String name, SymbolTable symbolTable) {
    Scope scope = symbolTable.resolveScope(statement);
    SymbolNameResolver nameResolver = new SymbolNameResolver(symbolTable, scope);
    QIterable<SymbolResolutionMatch> symbolsInScope = nameResolver.findSymbolsInScope(name);

    // TODO: Not by any means correct, but for now this will work.
    return symbolsInScope.first().symbol();
  }

  private Symbol determineTypeForMatchingName(SymbolTable symbolTable, Symbol symbol) {
    if (symbol.is(TypedExpression.class)) {
      return symbol.expressionAs(TypedExpression.class).resolvedType();

    } else if (symbol.expression() instanceof ValDeclarationStatement) {
      ValDeclarationStatement declaration = symbol.expressionAs(ValDeclarationStatement.class);
      TypeConstraintExpression typeConstraint = declaration.typeConstraint();

      return symbolTable.findSymbolForType(typeConstraint.representation());
    }

    throw new NotImplementedException();
  }

  public Symbol resolveInfixExpressionReturnType(InfixExpression value, SymbolTable symbolTable) {
    VerbaExpression lhs = value.lhs();
    if (lhs instanceof QuoteExpression) return PlatformTypeSymbols.UTF;
    if (lhs instanceof NumericExpression) return resolveIntegertype((NumericExpression) lhs, symbolTable);

    throw new NotImplementedException();
  }

  private Symbol resolveIntegertype(NumericExpression value, SymbolTable symbolTable) {
    // TODO: More processing:
    return PlatformTypeSymbols.INT;
  }

  public Symbol returnType() { return this.returnType; }

  public Symbol resolveReturnType(SymbolTable table) {
    if (returnType == null) {
      this.returnType = determineReturnValue(table);
    }

    return this.returnType;
  }
}
