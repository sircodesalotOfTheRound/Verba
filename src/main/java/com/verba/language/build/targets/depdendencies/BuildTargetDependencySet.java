package com.verba.language.build.targets.depdendencies;

import com.javalinq.implementations.QSet;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class BuildTargetDependencySet {
  private final QSet<Class> targetDependencies;

  public BuildTargetDependencySet(Class ... targetDependencies) {
    this.targetDependencies = new QSet<>(targetDependencies);
  }

  public boolean targetDependenciesAvailable(Build build) {
    if (!targetDependencies.any()) {
      return false;
    } else {
      return this.targetDependencies.all(targetDependencies::contains);
    }
  }

  public boolean isRequiredDependency(BuildArtifact artifact) {
    if (artifact == null) {
      return false;
    } else {
      return this.targetDependencies.contains(artifact.getClass());
    }
  }
}
