package com.verba.language.graph.expressions.retval;

import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.categories.LiteralExpression;
import com.verba.language.parse.expressions.categories.RValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
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

  public ReturnStatementTypeResolver(ReturnStatementExpression statement, SymbolTable symbolTable) {
    this.statement = statement;
    this.symbolTable = symbolTable;
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

    throw new NotImplementedException();
  }

  public Symbol returnType() {
    if (this.returnType == null) {
      this.returnType = determineReturnValue();
    }

    return this.returnType;
  }
}
