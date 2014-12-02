package com.verba.scratchpad;

import com.verba.language.emit.buildtools.Build;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parsing.expressions.blockheader.classes.ClassDeclarationExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString("fn main() { val string = \"Something is amiss\" }");
  }
}
