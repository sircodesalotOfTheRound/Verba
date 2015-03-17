package com.verba.language.build.targets;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.LitFileSyntaxTreeArtifact;
import com.verba.language.build.targets.artifacts.SourceCodeSyntaxTreeListArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class LitFileSyntaxTreeBuildTarget extends BuildTarget {
  public LitFileSyntaxTreeBuildTarget() {
    super(SourceCodeSyntaxTreeListArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (this.allDependenciesAvailableOrUpdated(build, artifact)) {
      SourceCodeSyntaxTreeListArtifact syntaxTrees =
        build.getArtifactOfType(SourceCodeSyntaxTreeListArtifact.class);

      build.addArtifact(new LitFileSyntaxTreeArtifact(syntaxTrees.syntaxTrees()));
    }
  }
}
