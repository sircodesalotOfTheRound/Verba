package com.verba.language.build.types.interfaces;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.artifacts.containers.BuildArtifactContainer;
import com.verba.language.build.artifacts.containers.BuildArtifact;

/**
 * Created by sircodesalot on 15/3/12.
 */
public abstract class BuildManagerBase implements BuildArtifactContainer {
  private final Build build;

  public BuildManagerBase(Build build) {
    this.build = build;
  }

  @Override
  public <T extends BuildArtifact> boolean containsBuildInfoOfType(Class<T> type) {
    return build.containsBuildInfoOfType(type);
  }

  @Override
  public QIterable<Class> buildInfoKeys() {
    return build.buildInfoKeys();
  }

  @Override
  public void addArtifact(BuildArtifact value) {
    build.addArtifact(value);
  }

  @Override
  public <T extends BuildArtifact> T getBuildInfo(Class<T> type) {
    return build.getBuildInfo(type);
  }
}
