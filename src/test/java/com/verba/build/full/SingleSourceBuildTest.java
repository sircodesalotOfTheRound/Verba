package com.verba.build.full;

import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.managers.LitFileBuildManager;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class SingleSourceBuildTest {
  public static LitFileBuildManager build = new BuildSpecification()
    .addSourceFolder("verba_sources/single_source_build_test")
    .isDebugBuild(true)
    .outputPath("verba_builds/single-source-build.vlit")
    .createLitFileBuild();

  @Test
  public void testSourceLoading() {
    SymbolTableArtifact symbolTableArtifact = build.getArtifactOfType(SymbolTableArtifact.class);
    Symbol twoFunctionSymbol = symbolTableArtifact.findSingleSymbolByFqn("two");
    FunctionDeclarationExpression twoFunction = twoFunctionSymbol.expressionAs(FunctionDeclarationExpression.class);

    assert(twoFunction.hasTypeConstraint());
    assert(!twoFunction.hasGenericParameters());
    assert(!twoFunction.hasParameters());
  }

}
