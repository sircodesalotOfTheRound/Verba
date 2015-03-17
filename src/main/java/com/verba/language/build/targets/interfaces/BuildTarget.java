package com.verba.language.build.targets.interfaces;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.depdendencies.BuildTargetDependencySet;

/**
 * Created by sircodesalot on 15/3/3.
 */
public abstract class BuildTarget {
  private final BuildTargetDependencySet targetDepdendencies;

  public BuildTarget(Class ... targetDependencies) {
    this.targetDepdendencies = new BuildTargetDependencySet(targetDependencies);
  }

  public boolean allTargetDependenciesAvailable(Build build) {
    return targetDepdendencies.targetDependenciesAvailable(build);
  }

  public boolean isRequiredDependency(BuildArtifact artifact) {
    return targetDepdendencies.isRequiredDependency(artifact);
  }

  public boolean allDependenciesAvailableOrUpdated(Build build, BuildArtifact artifact) {
    if (artifact == null) {
      return allTargetDependenciesAvailable(build);
    } else {
      return (isRequiredDependency(artifact) && allTargetDependenciesAvailable(build));
    }
  }

  public abstract void onBuildUpdated(Build build, BuildArtifact artifact);
}
