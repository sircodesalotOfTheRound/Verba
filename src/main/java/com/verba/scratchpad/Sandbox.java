package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "class MyClass");
    GlobalSymbolTable symbolTable = build.symbolTable();
    Symbol symbol = symbolTable.findSymbolForType("unit");

    System.out.println(symbol.fqn());
  }
}
