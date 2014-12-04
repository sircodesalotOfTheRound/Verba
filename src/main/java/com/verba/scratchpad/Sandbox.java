package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableStack;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "class MyClass fn function : MyClass { val item = \"something\" }");
    GlobalSymbolTable symbolTable = build.symbolTable();

    VirtualVariableStack set = new VirtualVariableStack(20);
  }
}
