package com.verba.language;

import com.javalinq.implementations.QSet;
import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.managers.LitFileBuildManager;
import com.verba.language.build.targets.artifacts.LitFileSyntaxTreeArtifact;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class TestNamespaceImports {
  private static final LitFileBuildManager build = new BuildSpecification()
    .isDebugBuild(false)
    .addSourceFolder("verba_sources/language_feature_tests")
    .shouldPersist(false)
    .createLitFileBuild();

  @Test
  public void testNamespaces() {
    assert(build.containsArtifactOfType(LitFileSyntaxTreeArtifact.class));
    LitFileSyntaxTreeArtifact syntaxTreeArtifact = build.getArtifactOfType(LitFileSyntaxTreeArtifact.class);

    VerbaSourceCodeFile source = syntaxTreeArtifact.rootExpression()
      .allExpressions()
      .ofType(VerbaSourceCodeFile.class)
      .single();

    QSet<String> namespaces = source.importedNamespaces().toSet();

    assert(namespaces.contains("platform"));
    assert(namespaces.contains("platform.io"));
    assert(namespaces.contains("platform.data"));
    assert(namespaces.contains("platform.data.big"));
    assert(namespaces.contains("platform.security"));
    assert(namespaces.contains("platform.networking"));
  }
}
