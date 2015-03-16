package com.verba.language.build.artifacts.containers;

import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 15/3/12.
 */
public interface BuildArtifactContainer {
  public <T extends BuildArtifact> boolean containsBuildInfoOfType(Class<T> type);
  public QIterable<Class> buildInfoKeys();
  public void addArtifact(BuildArtifact value);
  public <T extends BuildArtifact> T getBuildInfo(Class<T> type);

  default public void addArtifacts(QIterable<BuildArtifact> artifacts) {
    for (BuildArtifact item : artifacts) {
      this.addArtifact(item);
    }
  }
}
