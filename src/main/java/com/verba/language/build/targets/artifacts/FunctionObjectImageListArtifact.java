package com.verba.language.build.targets.artifacts;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.emit.images.types.common.FilePersistenceWriter;
import com.verba.language.emit.images.types.specialized.FunctionObjectImage;
import com.verba.language.emit.litfile.LitFileItem;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class FunctionObjectImageListArtifact implements BuildArtifact, LitFileItem {
  private final QIterable<FunctionObjectImage> images;

  public FunctionObjectImageListArtifact(QIterable<FunctionObjectImage> images) {
    this.images = images;
  }

  public FunctionObjectImageListArtifact() {
    this.images = new QList<>();
  }

  public QIterable<FunctionObjectImage> images() { return this.images; }

  @Override
  public void emit(FilePersistenceWriter writer) {
    for (FunctionObjectImage image : images) {
      writer.writeImage(image.name(), image);
    }
  }
}
