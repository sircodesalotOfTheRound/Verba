package com.verba.language.graph.expressions.functions;

import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class NativeTypeSymbols {
  public final SymbolTableEntry UNIT;

  public final SymbolTableEntry ASCII;
  public final SymbolTableEntry UTF8;
  public final SymbolTableEntry UTF16;
  public final SymbolTableEntry UTF32;

  public NativeTypeSymbols(GlobalSymbolTable symbolTable) {
    this.UNIT = symbolTable.getByFqn("unit").single();

    this.ASCII = symbolTable.getByFqn("ascii").single();
    this.UTF8 = symbolTable.getByFqn("utf8").single();
    this.UTF16 = symbolTable.getByFqn("utf16").single();
    this.UTF32 = symbolTable.getByFqn("utf32").single();
  }
}
