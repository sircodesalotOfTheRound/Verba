package com.verba.language.build.targets;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.LitFileSyntaxTreeArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolTableBuildTarget extends BuildTarget {
  public SymbolTableBuildTarget() {
    super(LitFileSyntaxTreeArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (allDependenciesAvailableOrUpdated(build, artifact)) {
      LitFileSyntaxTreeArtifact syntaxTree = build.getArtifactOfType(LitFileSyntaxTreeArtifact.class);
      build.addArtifact(new SymbolTableArtifact(syntaxTree));
    }
  }
}
