package com.verba.scratchpad;

import com.verba.language.build.Build;
import com.verba.language.build.BuildConfiguration;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class Sandbox {
  public static void main(String[] args) throws Exception {
    BuildConfiguration configuration = new BuildConfiguration()
      .isDebugBuild(true)
      .shouldCreateSymbolTable(true)
      .shouldEmitCode(false);

    Build build = Build.fromSingleFile("GraphingTests.v", configuration);
    PolymorphicDeclarationExpression derivedClass = build.symbolTable().findSymbolForType("Derived")
      .expressionAs(PolymorphicDeclarationExpression.class);

    System.out.println("Is this derived from Base? " + derivedClass.isDerivedFrom("Base"));
    System.out.println("Is this derived from BaseTrait? " + derivedClass.isDerivedFrom("BaseTrait"));
    System.out.println("Is this derived from NonTrait? " + derivedClass.isDerivedFrom("NonTrait"));
    System.out.println();

    for (Symbol expression : derivedClass.allMembers()) {
      System.out.println("Derived class has member: " + expression.fqn() + " of type " +
        expression.expression().getClass().getSimpleName());
    }
  }
}
