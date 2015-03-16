package com.verba.language.build.targets;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.SourceCodePathListArtifact;
import com.verba.language.build.targets.artifacts.SourceCodeSyntaxTreeListArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class SyntaxTreeListBuildTarget extends BuildTarget {
  public SyntaxTreeListBuildTarget() {
    super(SourceCodePathListArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (this.allDependenciesAvailableOrUpdated(build, artifact)) {
      SourceCodeSyntaxTreeListArtifact syntaxTrees =
        this.parseSources(build.getArtifactOfType(SourceCodePathListArtifact.class));
      build.addArtifact(syntaxTrees);
    }
  }

  private SourceCodeSyntaxTreeListArtifact parseSources(SourceCodePathListArtifact artifact) {
    QIterable<VerbaCodePage> syntaxTrees = artifact.files()
      .map(file -> VerbaCodePage.fromFile(null, file.getAbsolutePath()));

    return new SourceCodeSyntaxTreeListArtifact(syntaxTrees);
  }
}
