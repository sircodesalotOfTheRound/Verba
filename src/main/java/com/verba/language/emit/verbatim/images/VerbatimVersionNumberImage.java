package com.verba.language.emit.verbatim.images;

import com.verba.language.emit.header.StringTable;
import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ImageType;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.interfaces.ObjectImageBase;
import com.verba.language.emit.images.types.basic.InMemoryObjectImage;

/**
 * Created by sircodesalot on 14/12/5.
 */
public class VerbatimVersionNumberImage extends ObjectImageBase {

  public VerbatimVersionNumberImage(StringTableStringEntry version, StringTable stringTable) {
    super(produceImage(version, stringTable));
  }

  private static ObjectImage produceImage(StringTableStringEntry version, StringTable stringTable) {
    return new InMemoryObjectImage("Verbatim Image Version", ImageType.VERSION)
      .writeString("Image Version", version)
      .asObjectImage();
  }

}
