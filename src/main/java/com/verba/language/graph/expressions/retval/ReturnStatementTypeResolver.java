package com.verba.language.graph.expressions.retval;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.meta.ParameterSymbolMetadata;
import com.verba.language.graph.symbols.resolution.SymbolNameResolver;
import com.verba.language.graph.symbols.resolution.SymbolResolutionMatch;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.LiteralExpression;
import com.verba.language.parse.expressions.categories.NamedExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/12/6.
 */
public class ReturnStatementTypeResolver {
  private final ReturnStatementExpression statement;
  private final SymbolTable symbolTable;
  private Symbol returnType;
  private Scope scope;

  public ReturnStatementTypeResolver(ReturnStatementExpression statement, SymbolTable symbolTable) {
    this.statement = statement;
    this.symbolTable = symbolTable;
    this.scope = symbolTable.resolveScope(statement);
  }

  private Symbol determineReturnValue() {
    if (!statement.hasValue()) {
      return symbolTable.findSymbolForType(KeywordToken.UNIT);
    }

    return determineTypeForValue(statement.value());
  }

  private Symbol determineTypeForValue(RValueExpression value) {
    if (value instanceof LiteralExpression) {
      if (value instanceof QuoteExpression) return symbolTable.findSymbolForType(KeywordToken.UTF);
      if (value instanceof NumericExpression) return symbolTable.findSymbolForType(KeywordToken.INT);
    }

    if (value instanceof NamedExpression) {
      NamedExpression namedExpression = (NamedExpression)value;
      Symbol matchingName = determineMatchingSymbolForName(namedExpression.name());
      return determineTypeForMatchingName(matchingName);
    }

    throw new NotImplementedException();
  }

  private Symbol determineMatchingSymbolForName(String name) {
    SymbolNameResolver nameResolver = new SymbolNameResolver(symbolTable, scope);
    QIterable<SymbolResolutionMatch> symbolsInScope = nameResolver.findSymbolsInScope(name);

    // TODO: Not by any means correct, but for now this will work.
    return symbolsInScope.first().symbol();
  }

  private Symbol determineTypeForMatchingName(Symbol symbol) {
    if (symbol.isParameter()) {
      NamedValueExpression parameter = symbol.expressionAs(NamedValueExpression.class);

      // TODO: Make this work.
      //return parameter.resolvedType();

      if (parameter.hasTypeConstraint()) {
        return symbolTable.findSymbolForType(parameter.typeConstraint().representation());
      }

      return symbolTable.findSymbolForType(KeywordToken.DYNAMIC);

    } else if (symbol.expression() instanceof ValDeclarationStatement) {
      ValDeclarationStatement declaration = symbol.expressionAs(ValDeclarationStatement.class);
      TypeConstraintExpression typeConstraint = declaration.typeConstraint();

      return symbolTable.findSymbolForType(typeConstraint.representation());
    }

    throw new NotImplementedException();
  }

  public Symbol returnType() {
    if (this.returnType == null) {
      this.returnType = determineReturnValue();
    }

    return this.returnType;
  }
}
