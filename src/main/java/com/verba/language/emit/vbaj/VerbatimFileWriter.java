package com.verba.language.emit.vbaj;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.emit.images.interfaces.ObjectImage;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class VerbatimFileWriter {
  public QIterable<ObjectImage> images;
  public Partition<Class, ObjectImage> imagesByType;

  public VerbatimFileWriter(QIterable<ObjectImage> images) {
    this.images = images;
    this.imagesByType = images.parition(ObjectImage::getClass);
  }

  public void save(String path) {

  }

}
