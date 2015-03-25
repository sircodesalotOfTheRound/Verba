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
    .createLitFileBuild();

  @Test
  public void testExplicitFunctionResolution() {
    SymbolTableArtifact symbolTableArtifact = build.getArtifactOfType(SymbolTableArtifact.class);
    SymbolTable symbolTable = symbolTableArtifact.symbolTable();
    Symbol returnsUnitSymbol = symbolTable.findAllMatchingFqn("returns_unit").single();
    Symbol returnsAsciiSymbol = symbolTable.findAllMatchingFqn("returns_ascii").single();

    FunctionDeclarationExpression returnsUnit = returnsUnitSymbol.expressionAs(FunctionDeclarationExpression.class);
    FunctionDeclarationExpression returnsAscii = returnsAsciiSymbol.expressionAs(FunctionDeclarationExpression.class);


    assert(returnsUnit.resolvedType() == PlatformTypeSymbols.UNIT);
    assert(returnsAscii.resolvedType() == PlatformTypeSymbols.ASCII);
  }

  @Test
  public void testInferredResolution() {
    SymbolTableArtifact symbolTableArtifact = build.getArtifactOfType(SymbolTableArtifact.class);
    SymbolTable symbolTable = symbolTableArtifact.symbolTable();

    Symbol alsoReturnsUnitSymbol = symbolTable.findAllMatchingFqn("also_returns_unit").single();
    Symbol returnsIntSymbol = symbolTable.findAllMatchingFqn("returns_int").single();
    Symbol returnsUtfSymbol = symbolTable.findAllMatchingFqn("returns_utf").single();

    FunctionDeclarationExpression alsoReturnsUnit = alsoReturnsUnitSymbol.expressionAs(FunctionDeclarationExpression.class);
    FunctionDeclarationExpression returnsInt = returnsIntSymbol.expressionAs(FunctionDeclarationExpression.class);
    FunctionDeclarationExpression returnsUtf = returnsUtfSymbol.expressionAs(FunctionDeclarationExpression.class);

    assert(alsoReturnsUnit.resolvedType() == PlatformTypeSymbols.UNIT);
    assert(returnsInt.resolvedType() == PlatformTypeSymbols.INT);
    assert(returnsUtf.resolvedType() == PlatformTypeSymbols.UTF);
  }
}
