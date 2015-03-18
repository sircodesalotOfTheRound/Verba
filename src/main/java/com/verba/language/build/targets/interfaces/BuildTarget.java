package com.verba.language.build.targets.interfaces;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
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

  protected boolean allTargetDependenciesAvailable(Build build) {
    return targetDepdendencies.targetDependenciesAvailable(build);
  }

  protected boolean isRequiredDependency(BuildArtifact artifact) {
    return targetDepdendencies.isRequiredDependency(artifact);
  }

  @Deprecated
  protected boolean allDependenciesAvailableOrUpdated(Build build, BuildArtifact artifact) {
    if (artifact == null) {
      return allTargetDependenciesAvailable(build);
    } else {
      return (isRequiredDependency(artifact) && allTargetDependenciesAvailable(build));
    }
  }

  public QSet<Class> dependencies() { return targetDepdendencies.dependencies(); }

  public abstract void onBuildUpdated(Build build, BuildArtifact artifact);
}
