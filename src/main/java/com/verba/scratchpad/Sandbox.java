package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "class MyClass fn my_function() : MyClass { } ");

    FunctionDeclarationExpression myFunction = build.symbolTable()
      .getByFqn("my_function")
      .single()
      .instanceAs(FunctionDeclarationExpression.class);

    System.out.println(myFunction.returnType().fqn());
  }
}
