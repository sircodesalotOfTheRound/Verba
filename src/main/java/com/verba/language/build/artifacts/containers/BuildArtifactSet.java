package com.verba.language.build.artifacts.containers;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/3/12.
 */
public class BuildArtifactSet implements BuildArtifactContainer {
  private final Map<Class, Object> buildInfo = new HashMap<>();

  public <T extends BuildArtifact> boolean containsBuildInfoOfType(Class<T> type) {
    return buildInfo.containsKey(type);
  }

  public QIterable<Class> buildInfoKeys() { return new QSet<>(this.buildInfo.keySet()); }

  public void addArtifact(BuildArtifact value) {
    buildInfo.put(value.getClass(), value);
  }

  public <T extends BuildArtifact> T getBuildInfo(Class<T> type) { return (T)this.buildInfo.get(type); }
}
