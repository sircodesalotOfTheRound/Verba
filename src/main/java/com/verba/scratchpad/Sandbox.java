package com.verba.scratchpad;

import com.verba.language.emit.buildtools.Build;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parsing.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString("withns vm.nothing fn function() { val item = 10 }");

    for (SymbolTableEntry entry : build.symbolTable().entries()) {
      VerbaCodePage page = entry.page();
      System.out.println(page.path());
    }
  }
}
