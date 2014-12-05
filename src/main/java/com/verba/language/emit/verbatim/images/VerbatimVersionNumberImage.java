package com.verba.language.emit.verbatim.images;

import com.verba.language.emit.images.interfaces.ImageType;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.interfaces.ObjectImageBase;
import com.verba.language.emit.images.types.basic.InMemoryObjectImage;

import java.util.InputMismatchException;

/**
 * Created by sircodesalot on 14/12/5.
 */
public class VerbatimVersionNumberImage extends ObjectImageBase {

  public VerbatimVersionNumberImage(String version) {
    super(produceImage(version));
  }

  private static ObjectImage produceImage(String version) {
    return new InMemoryObjectImage("Verbatim Image Version", ImageType.VERSION)
      .writeString("Image Version", version)
      .asObjectImage();
  }

}
