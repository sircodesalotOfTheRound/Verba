package com.verba.language.build.configuration;


import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifactSet;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifactContainer;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;

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
  public <T extends BuildArtifact> boolean containsArtifactOfType(Class<T> type) {
    return artifacts.containsArtifactOfType(type);
  }

  @Override
  public QIterable<Class> artifactTypes() {
    return artifacts.artifactTypes();
  }

  @Override
  public void addArtifact(BuildArtifact target) {
    artifacts.addArtifact(target);
    for (Consumer<BuildArtifact> callback : callbacks) {
      callback.accept(target);
    }
  }

  @Override
  public <T extends BuildArtifact> T getArtifactOfType(Class<T> type) {
    return artifacts.getArtifactOfType(type);
  }

  @Override
  public QIterable<BuildArtifact> getArtifacts() {
    return artifacts.getArtifacts();
  }

  public BuildSpecification specification() { return this.specification; }

  public void onArtifactAdded(Consumer<BuildArtifact> callback) {
    if (callback != null) callbacks.add(callback);
  }
}
