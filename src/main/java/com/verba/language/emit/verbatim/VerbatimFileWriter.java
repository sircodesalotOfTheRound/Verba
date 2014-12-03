package com.verba.language.emit.verbatim;

import com.verba.language.emit.images.ObjectImageSet;
import com.verba.language.emit.images.types.specialized.FunctionObjectImage;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class VerbatimFileWriter {
  public ObjectImageSet images;

  public VerbatimFileWriter(ObjectImageSet images) {
    this.images = images;
  }

  public boolean save(String path) {
    boolean returnValue = true;
    try (FileOutputStream stream = new FileOutputStream(path)) {
      this.emitFunctions(stream);
    }
    catch (IOException ex) { returnValue = false; }

    return returnValue;
  }

  private void emitFunctions(FileOutputStream stream) {
    for (FunctionObjectImage image : this.images.ofType(FunctionObjectImage.class)) {
      try { stream.write(image.data()); }
      catch (IOException ex) { }
    }
  }

}
