package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.build.BuildConfiguration;
import com.verba.language.graph.expressions.modifiers.ExpressionModifierInfo;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.tools.files.FileTools;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    BuildConfiguration configuration = new BuildConfiguration()
      .isDebugBuild(true)
      .shouldCreateSymbolTable(false)
      .shouldEmitCode(false);

    Build build = Build.fromSingleFile("ParseOnly.v", configuration);

    for (VerbaExpression expression : build.allExpressions()) {
      System.out.println(expression);
    }
  }
}
