package com.verba.language.graph.expressions.functions;

import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class NativeTypeSymbols {
  public final Symbol UNIT;
  public final Symbol ASCII;
  public final Symbol UTF8;
  public final Symbol UTF16;
  public final Symbol UTF32;

  private final SymbolTable symbolTable;
  private final Map<String, Symbol> entriesByName = new HashMap<>();

  public NativeTypeSymbols(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.UNIT = captureEntryByKey("unit");

    this.ASCII = captureEntryByKey("ascii");
    this.UTF8 = captureEntryByKey("utf8");
    this.UTF16 = captureEntryByKey("utf16");
    this.UTF32 = captureEntryByKey("utf32");
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
