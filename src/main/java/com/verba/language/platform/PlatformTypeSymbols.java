package com.verba.language.platform;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.platform.expressions.PlatformTypeExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class PlatformTypeSymbols {
  public static final Symbol UNIT = new Symbol(KeywordToken.UNIT, PlatformSourceSymbolTable.INSTANCE, PlatformTypeExpression.UNIT);
  public static final Symbol UTF = new Symbol(KeywordToken.UTF, PlatformSourceSymbolTable.INSTANCE, PlatformTypeExpression.UTF);
  public static final Symbol ASCII = new Symbol(KeywordToken.ASCII, PlatformSourceSymbolTable.INSTANCE, PlatformTypeExpression.ASCII);
  public static final Symbol INT = new Symbol(KeywordToken.INT, PlatformSourceSymbolTable.INSTANCE, PlatformTypeExpression.INT);
  public static final Symbol DYNAMIC = new Symbol(KeywordToken.DYNAMIC, PlatformSourceSymbolTable.INSTANCE, PlatformTypeExpression.DYNAMIC);
  public static final Symbol BOOLEAN = new Symbol(KeywordToken.BOOLEAN, PlatformSourceSymbolTable.INSTANCE, PlatformTypeExpression.BOOLEAN);

  private static final QSet<Symbol> symbols = new QSet<>(
    UNIT, UTF, ASCII, INT, DYNAMIC, BOOLEAN
  );

  private static final Partition<String, Symbol> symbolsByName = symbols.parition(Symbol::name);

  private PlatformTypeSymbols() { }

  public static QIterable<Symbol> platformSymbols() { return PlatformTypeSymbols.symbols; }
  public static Symbol findNativeTypeSymbolByName(String name) { return PlatformTypeSymbols.symbolsByName.get(name).single(); }
  public static boolean isNativeTypeSymbol(String name) { return PlatformTypeSymbols.symbolsByName.containsKey(name); }
}
