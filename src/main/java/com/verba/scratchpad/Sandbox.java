package com.verba.scratchpad;

import com.verba.language.emit.buildtools.Build;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.graph.symbols.resolution.SymbolNameResolver;
import com.verba.language.graph.symbols.resolution.SymbolResolutionMatch;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parsing.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString("withns vm.nothing fn function() { val item = 10 }");

    SymbolTableEntry entry = build.symbolTable().getEntryListByFqn("function.item").first();
    SymbolNameResolver resolver = new SymbolNameResolver(build.symbolTable(), entry.table());

    for (SymbolResolutionMatch match : resolver.findSymbolsInScope("item")) {
      System.out.println("found " + match.entry().fqn());
    }
  }
}
