package com.verba.language.emit.vbaj;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.emit.images.ObjectImageSet;
import com.verba.language.emit.images.interfaces.ObjectImage;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class VerbatimFileWriter {
  public ObjectImageSet images;

  public VerbatimFileWriter(ObjectImageSet images) {
    this.images = images;
  }

  public boolean save(String path) {
   return true;
  }

}
