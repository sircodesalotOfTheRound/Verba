package com.verba.language.emit.images;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.emit.images.interfaces.ImageType;
import com.verba.language.emit.images.interfaces.ObjectImage;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class ObjectImageSet {
  private final QIterable<ObjectImage> images;
  private final Partition<ImageType, ObjectImage> imagesByType;

  public ObjectImageSet(QIterable<ObjectImage> images) {
    this.images = images;
    this.imagesByType = images.parition(ObjectImage::imageType);
  }

  public QIterable<ObjectImage> images() { return this.images; }

  public QIterable<ObjectImage> ofType(ImageType imageType) {
    if (this.imagesByType.containsKey(imageType)) {
      return this.imagesByType.get(imageType);
    }

    return new QList<>();
  }
}
