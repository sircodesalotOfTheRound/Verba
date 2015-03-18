package com.verba.language.build.targets.depdendencies;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
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
    if (targetDependencies.any()) {
      return this.targetDependencies.all(build::containsArtifactOfType);
    } else {
      return true;
    }
  }

  public boolean isRequiredDependency(BuildArtifact artifact) {
    if (artifact == null) {
      return false;
    } else {
      return this.targetDependencies.contains(artifact.getClass());
    }
  }

  public QSet<Class> dependencies() {
    return targetDependencies;
  }
}
