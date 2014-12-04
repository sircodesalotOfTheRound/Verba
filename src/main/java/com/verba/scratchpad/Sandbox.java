package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "class MyClass class AnotherClass { class InnerClass } ");
    GlobalSymbolTable symbolTable = build.symbolTable();

    System.out.println(symbolTable.getEntryForSymbolType("AnotherClass.InnerClass").fqn());
  }
}
