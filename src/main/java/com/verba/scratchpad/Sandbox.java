package com.verba.scratchpad;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.buildtools.Build;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import sun.jvm.hotspot.memory.SymbolTable;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString("fn function<T>(parameter) { val item = 10 fn second() { val subitem = 20 } } class MyClass<T, U, V>(first, second, third) { }");

    QIterable<SymbolTableEntry> entries = build.symbolTable().getByFqn("function.second.subitem");

    for (SymbolTableEntry entry : entries) {
      System.out.println(entry.name());
      System.out.println(entry.table().fqn());
    }
  }
}
