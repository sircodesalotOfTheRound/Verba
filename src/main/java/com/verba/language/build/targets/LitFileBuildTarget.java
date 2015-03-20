package com.verba.language.build.targets;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.BuildSpecificationArtifact;
import com.verba.language.build.targets.artifacts.FunctionObjectImageListArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.language.emit.litfile.LitFileContent;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class LitFileBuildTarget extends BuildTarget {
  public LitFileBuildTarget() {
    super(BuildSpecificationArtifact.class,
      FunctionObjectImageListArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (allDependenciesAvailableOrUpdated(build, artifact)
      && build.containsArtifactOfType(BuildSpecificationArtifact.class)) {
      if (LitFileContent.isBuildInValidStateToGenerateLitFile(build)) {
        persistLitFile(build);
      }
    }
  }

  private void persistLitFile(Build build) {
    LitFileContent content = new LitFileContent(build);
    content.save();
  }
}
