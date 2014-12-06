package com.verba.scratchpad;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.Build;
import com.verba.language.emit.header.StringTable;
import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.parse.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.parse.expressions.containers.markup.MarkupTagItemExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "fn my_function : unit { val item = \"something\" val another = \"another\"}");

    StringTable stringTable = new StringTable();

    stringTable.addString("something");
    StringTableFqnEntry stringTableFqnEntry = stringTable.addFqn("first.second.third");

    QIterable<String> fqnUnits = stringTableFqnEntry.entries()
      .map(entry -> String.format("[%s : %s]", entry.index(), stringTable.findByIndex(entry.index()).text()));

    String joinedFqn = String.join(".", fqnUnits);

    System.out.println(String.format("%s %s", stringTableFqnEntry.length(), joinedFqn));
  }
}
