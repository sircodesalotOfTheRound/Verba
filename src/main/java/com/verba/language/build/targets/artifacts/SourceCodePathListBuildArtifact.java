package com.verba.language.build.targets.artifacts;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;

import java.io.File;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class SourceCodePathListBuildArtifact implements BuildArtifact {
  private final QIterable<File> files;
  public SourceCodePathListBuildArtifact(QIterable<File> files) {
    this.files = files;
  }

  public QIterable<File> files() { return this.files; }
}
