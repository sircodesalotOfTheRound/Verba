package com.verba.language.build.targets;

import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.targets.artifacts.BuildSpecificationArtifact;
import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.interfaces.BuildTarget;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class AddStringTableBuildTarget extends BuildTarget {
  public AddStringTableBuildTarget() {
    super(BuildSpecificationArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (allDependenciesAvailableOrUpdated(build, artifact)) {
      build.addArtifact(new StringTableArtifact());
    }
  }
}
