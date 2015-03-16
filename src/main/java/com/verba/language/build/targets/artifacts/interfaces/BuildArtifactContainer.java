package com.verba.language.build.targets.artifacts.interfaces;

import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 15/3/12.
 */
public interface BuildArtifactContainer {
  public <T extends BuildArtifact> boolean containsArtifactOfType(Class<T> type);
  public QIterable<Class> artifactTypes();
  public void addArtifact(BuildArtifact value);
  public <T extends BuildArtifact> T getArtifactOfType(Class<T> type);
  public QIterable<BuildArtifact> getArtifacts();

  default public void addArtifacts(QIterable<BuildArtifact> artifacts) {
    for (BuildArtifact item : artifacts) {
      this.addArtifact(item);
    }
  }


}
