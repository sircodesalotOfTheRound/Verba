package com.verba.language.emit.images;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.images.interfaces.ObjectImage;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class ObjectImageSet {
  private final QIterable<ObjectImage> images;

  public ObjectImageSet(QIterable<ObjectImage> images) {
    this.images = images;
  }

  public QIterable<ObjectImage> images() { return this.images; }
}
