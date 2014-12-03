package com.verba.language.emit.images;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.types.specialized.FunctionObjectImage;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class ObjectImageSet {
  private final QIterable<ObjectImage> images;
  private final Partition<Class, ObjectImage> imagesByType;

  public ObjectImageSet(QIterable<ObjectImage> images) {
    this.images = images;
    this.imagesByType = images.parition(ObjectImage::getClass);
  }

  public QIterable<ObjectImage> images() { return this.images; }

  public <T extends ObjectImage> QIterable<T> ofType(Class<T> type) {
    if (this.imagesByType.containsKey(type)) {
      return this.imagesByType.get(type).cast(type);
    }

    return new QList<>();
  }
}
