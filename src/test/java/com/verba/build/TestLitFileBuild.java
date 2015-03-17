package com.verba.build;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.managers.LitFileBuildManager;
import com.verba.language.build.targets.artifacts.LitFileSyntaxTreeArtifact;
import com.verba.language.build.targets.artifacts.SourceCodePathListArtifact;
import com.verba.language.build.targets.artifacts.SourceCodeSyntaxTreeListArtifact;
import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import org.junit.Test;

import java.io.File;

/**
 * Created by sircodesalot on 15/3/10.
 */
public class TestLitFileBuild {
  final QSet<String> allowedFunctionNames = new QSet<>("file_one", "file_two", "file_three", "file_four", "file_five");
  private LitFileBuildManager generateBuild() {
    return new BuildSpecification()
      .addSourceFolder("verba_sources/glob_test")
      .createLitFileBuild();
  }


  @Test
  public void testGlobbing() {
    LitFileBuildManager build = generateBuild();
    assert(build.containsArtifactOfType(SourceCodePathListArtifact.class));

    QSet<String> filesAsSet = generateBuild()
      .getArtifactOfType(SourceCodePathListArtifact.class)
      .files()
      .map(File::toString)
      .toSet();

    assert(filesAsSet.count() == 5);
    assert(filesAsSet.contains("verba_sources/glob_test/File1.v"));
    assert(filesAsSet.contains("verba_sources/glob_test/File2.v"));
    assert(filesAsSet.contains("verba_sources/glob_test/File3.v"));

    assert(filesAsSet.contains("verba_sources/glob_test/subfolder/File4.v"));
    assert(filesAsSet.contains("verba_sources/glob_test/subfolder/File5.v"));
  }

  @Test
  public void testContainsStringTable() {
    LitFileBuildManager build = generateBuild();
    assert(generateBuild().containsArtifactOfType(StringTableArtifact.class));
  }

  @Test
  public void testSyntaxTreeBuilding() {
    LitFileBuildManager build = generateBuild();
    assert (build.containsArtifactOfType(SourceCodeSyntaxTreeListArtifact.class));
    SourceCodeSyntaxTreeListArtifact syntaxTrees = build.getArtifactOfType(SourceCodeSyntaxTreeListArtifact.class);

    // Make sure that all of the functions are represented.
    assert (syntaxTrees.syntaxTrees().count() == 5);
    assert (syntaxTrees.syntaxTrees().all(tree -> {
      FunctionDeclarationExpression function = tree.childExpressions()
        .ofType(FunctionDeclarationExpression.class).single();

      // Make sure each name is only represented once.
      String name = function.name();
      boolean containsFunction = allowedFunctionNames.contains(function.name());
      allowedFunctionNames.remove(name);
      return containsFunction;
    }));
  }

  @Test
  public void testSingleSyntaxTreeGeneration() {
    LitFileBuildManager build = generateBuild();
    assert (build.containsArtifactOfType(LitFileSyntaxTreeArtifact.class));
    LitFileSyntaxTreeArtifact syntaxTree = build.getArtifactOfType(LitFileSyntaxTreeArtifact.class);
    LitFileRootExpression root = syntaxTree.expression();

    QIterable<String> functionNames = root.allExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .map(FunctionDeclarationExpression::name);

    assert (functionNames.all(allowedFunctionNames::contains));
  }
}
