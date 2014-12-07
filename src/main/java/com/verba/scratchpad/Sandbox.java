package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.tools.files.FileTools;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, FileTools.readAllText("SomeCodes.v"));

  }
}
