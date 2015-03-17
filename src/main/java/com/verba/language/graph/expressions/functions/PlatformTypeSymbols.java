package com.verba.language.graph.expressions.functions;

import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class PlatformTypeSymbols {
  public final Symbol UNIT;
  public final Symbol ASCII;
  public final Symbol UTF;
  public final Symbol INT;
  public final Symbol DYNAMIC;
  public final Symbol BOOLEAN;

  private final SymbolTable symbolTable;
  private final Map<String, Symbol> entriesByName = new HashMap<>();

  public PlatformTypeSymbols(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.UNIT = captureEntryByKey(KeywordToken.UNIT);

    this.ASCII = captureEntryByKey(KeywordToken.ASCII);
    this.UTF = captureEntryByKey(KeywordToken.UTF);
    this.INT = captureEntryByKey(KeywordToken.INT);
    this.DYNAMIC = captureEntryByKey(KeywordToken.DYNAMIC);

    this.BOOLEAN = captureEntryByKey(KeywordToken.BOOLEAN);
  }

  private Symbol captureEntryByKey(String key) {
    Symbol entry = symbolTable.findAllMatchingFqn(key).single();
    this.entriesByName.put(key, entry);

    return entry;
  }

  public Symbol findNativeTypeSymbolByName(String name) {
    return this.entriesByName.get(name);
  }

  public boolean isNativeTypeSymbol(String name) {
    return this.entriesByName.containsKey(name);
  }
}
