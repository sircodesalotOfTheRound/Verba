package com.verba.expressions.functions;

import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.managers.LitFileBuildManager;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.platform.PlatformTypeSymbols;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/19.
 */
public class TestFunctionTypeResolution {
  private static final LitFileBuildManager build = new BuildSpecification()
    .isDebugBuild(false)
    .addSourceFolder("verba_sources/function_tests")
    .shouldPersist(false)
    .createLitFileBuild();

  @Test
  public void testFunctionResolution() {
    SymbolTableArtifact symbolTableArtifact = build.getArtifactOfType(SymbolTableArtifact.class);
    SymbolTable symbolTable = symbolTableArtifact.symbolTable();
    Symbol returnsUnitSymbol = symbolTable.findAllMatchingFqn("returns_unit").single();
    Symbol returnsAsciiSymbol = symbolTable.findAllMatchingFqn("returns_ascii").single();

    FunctionDeclarationExpression returnsUnit = returnsUnitSymbol.expressionAs(FunctionDeclarationExpression.class);
    FunctionDeclarationExpression returnsAscii = returnsAsciiSymbol.expressionAs(FunctionDeclarationExpression.class);

    assert(returnsUnit.resolvedType() == PlatformTypeSymbols.UNIT);
    assert(returnsAscii.resolvedType() == PlatformTypeSymbols.ASCII);
  }
}
