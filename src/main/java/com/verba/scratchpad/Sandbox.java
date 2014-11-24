package com.verba.scratchpad;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.buildtools.Build;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parsing.expressions.VerbaExpression;
import sun.jvm.hotspot.memory.SymbolTable;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString("withns vm.nothing");

    for (VerbaExpression expression : build.allExpressions()) {
      System.out.println(expression);
    }
  }
}
