package com.verba.language.build.artifacts;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.artifacts.containers.BuildArtifact;

import java.io.File;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class SourcesBuildArtifact implements BuildArtifact {
  private final QIterable<File> files;
  public SourcesBuildArtifact(QIterable<File> files) {
    this.files = files;
  }

  public QIterable<File> files() { return this.files; }
}
