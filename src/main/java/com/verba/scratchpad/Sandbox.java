package com.verba.scratchpad;

import com.verba.language.emit.buildtools.Build;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parsing.expressions.blockheader.classes.ClassDeclarationExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString("class MyClass { class InnerClass { val an_item = 20 class SubClass { } } val item = 1 fn function() {  val item = 2 } } class AnotherClass : MyClass.InnerClass { val local_object = 10 }");

    SymbolTableEntry entry = build.symbolTable().getEntryListByFqn("AnotherClass").first();

    for (SymbolTableEntry expression : entry.instanceAs(ClassDeclarationExpression.class).allMembers()) {
      System.out.println("members: " + expression.fqn());
    }
  }
}
