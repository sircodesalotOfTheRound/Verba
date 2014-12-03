package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.parsing.expressions.blockheader.classes.PolymorphicDeclarationExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    Build build = Build.fromString(true, "class MyClass { } class AnotherClass : MyClass { }");

    PolymorphicDeclarationExpression myClass = build.symbolTable().getByFqn("AnotherClass").single().instanceAs(PolymorphicDeclarationExpression.class);
    System.out.println(myClass.isDerivedFrom("MyClass"));
  }
}
