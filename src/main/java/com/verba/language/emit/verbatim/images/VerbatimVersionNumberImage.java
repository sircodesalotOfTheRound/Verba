package com.verba.language.emit.verbatim.images;

import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.emit.header.stringtable.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ImageType;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.interfaces.ObjectImageBase;
import com.verba.language.emit.images.types.common.InMemoryObjectImage;

/**
 * Created by sircodesalot on 14/12/5.
 */
public class VerbatimVersionNumberImage extends ObjectImageBase {

  public VerbatimVersionNumberImage(StringTableStringEntry version, StringTableArtifact stringTable) {
    super(produceImage(version, stringTable));
  }

  private static ObjectImage produceImage(StringTableStringEntry version, StringTableArtifact stringTable) {
    return new InMemoryObjectImage("Verbatim Image Version", ImageType.VERSION)
      .writeString("Image Version", version)
      .asObjectImage();
  }

}
