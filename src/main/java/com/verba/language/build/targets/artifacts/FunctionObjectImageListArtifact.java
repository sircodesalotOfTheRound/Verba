package com.verba.language.build.targets.artifacts;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.emit.images.types.specialized.FunctionObjectImage;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class FunctionObjectImageListArtifact implements BuildArtifact {
  private final QIterable<FunctionObjectImage> images;

  public FunctionObjectImageListArtifact(QIterable<FunctionObjectImage> images) {
    this.images = images;
  }

  public FunctionObjectImageListArtifact() {
    this.images = new QList<>();
  }

  public QIterable<FunctionObjectImage> images() { return this.images; }
}
