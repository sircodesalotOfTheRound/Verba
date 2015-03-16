package com.verba.language.build.configuration;


import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.artifacts.containers.BuildArtifactSet;
import com.verba.language.build.artifacts.containers.BuildArtifactContainer;
import com.verba.language.build.artifacts.containers.BuildArtifact;

import java.util.function.Consumer;

/**
 * The Build class holds all of the build artifacts.
 */
public class Build implements BuildArtifactContainer {
  private final BuildSpecification specification;
  private final BuildArtifactSet artifacts = new BuildArtifactSet();
  private final QList<Consumer<BuildArtifact>> callbacks = new QList<>();

  public Build(BuildSpecification specification) {
    this.specification = specification;
  }

  @Override
  public <T extends BuildArtifact> boolean containsBuildInfoOfType(Class<T> type) {
    return artifacts.containsBuildInfoOfType(type);
  }

  @Override
  public QIterable<Class> buildInfoKeys() {
    return artifacts.buildInfoKeys();
  }

  @Override
  public void addArtifact(BuildArtifact target) {
    artifacts.addArtifact(target);
    for (Consumer<BuildArtifact> callback : callbacks) {
      callback.accept(target);
    }
  }

  @Override
  public <T extends BuildArtifact> T getBuildInfo(Class<T> type) {
    return artifacts.getBuildInfo(type);
  }

  public BuildSpecification specification() { return this.specification; }

  public void onTargetAdded(Consumer<BuildArtifact> callback) {
    if (callback != null) callbacks.add(callback);
  }
}
