package com.verba.language.build.targets.artifacts.interfaces;

import com.javalinq.interfaces.QIterable;
import com.javalinq.implementations.QMap;
import com.javalinq.tools.KeyValuePair;

/**
 * Created by sircodesalot on 15/3/12.
 */
public class BuildArtifactSet implements BuildArtifactContainer {
  private final QMap<Class, BuildArtifact> artifacts = new QMap<>();

  public <T extends BuildArtifact> boolean containsArtifactOfType(Class<T> type) {
    return artifacts.containsKey(type);
  }

  public QIterable<Class> artifactTypes() { return artifacts.map(KeyValuePair::key); }

  public void addArtifact(BuildArtifact value) {
    artifacts.add(value.getClass(), value);
  }

  public <T extends BuildArtifact> T getArtifactOfType(Class<T> type) { return (T)this.artifacts.get(type); }

  @Override
  public QIterable<BuildArtifact> getArtifacts() {
    return artifacts.map(KeyValuePair::value);
  }
}
