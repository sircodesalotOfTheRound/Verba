package com.verba.language.build.events;

import com.javalinq.implementations.QList;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.testtools.exceptions.CompilerException;

import java.util.function.Consumer;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class BuildEventPublisher {
  private final Build build;
  private final QList<BuildTarget> targets = new QList<>();

  public BuildEventPublisher(Build build) {
    this.build = build;
    this.build.onTargetAdded(new Consumer<BuildArtifact>() {
      @Override
      public void accept(BuildArtifact buildArtifact) {
        publishBuildUpdated(buildArtifact);
      }
    });
  }

  public BuildEventPublisher addTarget(BuildTarget target) {
    if (target == null) {
      throw new CompilerException("Build Target cannot be null");
    }

    this.targets.add(target);
    target.onBuildUpdated(build, null);
    for (BuildArtifact artifact : build.getArtifacts()) {
      target.onBuildUpdated(build, artifact);
    }
    return this;
  }

  public void publishBuildUpdated(BuildArtifact artifact) {
    for (BuildTarget target : targets) {
      target.onBuildUpdated(build, artifact);
    }
  }
}
