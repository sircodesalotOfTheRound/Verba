package com.verba.scratchpad;

import com.verba.language.build.buildtools.Build;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString("class MyClass { fn function() { val item  = 10 class SubClass { val inner_item = 10 } } fn function2() { val second = 5 } }");

    for (SymbolTableEntry entry : build.symbolTable().entries()) {
      System.out.println(entry.fqn());
    }
  }
}
