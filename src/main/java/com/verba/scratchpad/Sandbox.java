package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "fn second_function(parameter : utf) { return parameter }");

    FunctionDeclarationExpression my_function = build.symbolTable()
      .findAllMatchingFqn("second_function")
      .single()
      .expressionAs(FunctionDeclarationExpression.class);

    System.out.println("This function returns: " + my_function.resolvedType().fqn());
  }
}
