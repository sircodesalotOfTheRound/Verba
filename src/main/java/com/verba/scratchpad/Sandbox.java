package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "markup MyMarkup { <something /> }");

    for (VerbaExpression expression : build.allExpressions()) {
      System.out.println(expression);
    }
  }
}
