package com.verba.language.build.managers.interfaces;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifactContainer;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;

/**
 * Created by sircodesalot on 15/3/12.
 */
public abstract class BuildManagerBase implements BuildArtifactContainer {
  private final Build build;

  public BuildManagerBase(Build build) {
    this.build = build;
  }

  @Override
  public <T extends BuildArtifact> boolean containsArtifactOfType(Class<T> type) {
    return build.containsArtifactOfType(type);
  }

  @Override
  public QIterable<Class> artifactTypes() {
    return build.artifactTypes();
  }

  @Override
  public void addArtifact(BuildArtifact value) {
    build.addArtifact(value);
  }

  @Override
  public <T extends BuildArtifact> T getArtifactOfType(Class<T> type) {
    return build.getArtifactOfType(type);
  }

  @Override
  public QIterable<BuildArtifact> getArtifacts() { return build.getArtifacts(); }
}
