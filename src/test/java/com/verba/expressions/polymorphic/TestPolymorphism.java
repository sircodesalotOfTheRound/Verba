package com.verba.expressions.polymorphic;

import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.managers.LitFileBuildManager;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.platform.PlatformTypeSymbols;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/30.
 */
public class TestPolymorphism {
  private static final LitFileBuildManager build = new BuildSpecification()
    .addSourceFolder("verba_sources/polymorphic_tests")
    .isDebugBuild(false)
    .shouldCreateSymbolTable(true)
    .createLitFileBuild();

  @Test
  public void testImmediateMemberPolymorphism() {
    SymbolTableArtifact symbolTableArtifact = build.getArtifactOfType(SymbolTableArtifact.class);

    assert (symbolTableArtifact != null);
    Symbol rectangleSymbol = symbolTableArtifact.findSingleSymbolByFqn("Rectangle");
    PolymorphicDeclarationExpression rectangle = rectangleSymbol.expressionAs(PolymorphicDeclarationExpression.class);

    assert (rectangle.immediateMembers().single(member -> member.name().equals("width")) != null);
    assert (rectangle.immediateMembers().single(member -> member.name().equals("height")) != null);

    assert (rectangle.isImmediateMember("width"));
    assert (rectangle.isImmediateMember("height"));

    Symbol width = rectangle.findImmediateMembersByName("width").single();
    Symbol height = rectangle.findImmediateMembersByName("height").single();

    assert (width != null);
    assert (height != null);

    ValDeclarationStatement widthStatement = width.expressionAs(ValDeclarationStatement.class);
    ValDeclarationStatement heightStatement = height.expressionAs(ValDeclarationStatement.class);

    assert (widthStatement.resolvedType() == PlatformTypeSymbols.INT);
    assert (heightStatement.resolvedType() == PlatformTypeSymbols.INT);
  }

  @Test
  public void testClassPolymorphism() {
    SymbolTableArtifact symbolTableArtifact = build.getArtifactOfType(SymbolTableArtifact.class);

    Symbol baseSymbol = symbolTableArtifact.findSingleSymbolByFqn("Base");
    Symbol derivedSymbol = symbolTableArtifact.findSingleSymbolByFqn("Derived");
    PolymorphicDeclarationExpression base = baseSymbol.expressionAs(PolymorphicDeclarationExpression.class);
    PolymorphicDeclarationExpression derived = derivedSymbol.expressionAs(PolymorphicDeclarationExpression.class);

    assert(derived.traits().single().expression() == base);
  }
}
