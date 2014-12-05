package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.parse.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.parse.expressions.containers.markup.MarkupTagItemExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "fn my_function : unit { val item = \"something\" val another = \"another\"}");
    build.saveAssembly("/Users/sircodesalot/Desktop/MyAssembly.vbtm");
  }
}
