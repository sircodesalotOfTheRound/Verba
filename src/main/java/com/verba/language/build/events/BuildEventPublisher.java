package com.verba.language.build.events;

import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.interfaces.BuildTarget;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class BuildEventPublisher {
  private final Build build;
  private final BuildTargetDependencyGraph dependencyGraph = new BuildTargetDependencyGraph();

  public BuildEventPublisher(Build build) {
    this.build = build;
    this.build.onArtifactAdded(this::publishBuildUpdated);
  }

  public BuildEventPublisher addTarget(BuildTarget target) {
    dependencyGraph.addTarget(target);
    target.onBuildUpdated(build, null);
    return this;
  }

  public void publishBuildUpdated(BuildArtifact artifact) {
    if (dependencyGraph.containsTargetsForDependency(artifact)) {
      for (BuildTarget target : dependencyGraph.getTargetsForDependency(artifact)) {
        target.onBuildUpdated(build, artifact);
      }
    }
  }
}
