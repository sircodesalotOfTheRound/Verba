package com.verba.language.emit.header;

import com.verba.language.emit.rendering.images.ImageRenderer;
import com.verba.language.emit.rendering.images.ImageType;
import com.verba.language.emit.rendering.images.ObjectImage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/11/22.
 */
public class StringTable implements ObjectImage {
  private int index;
  private final Map<String, Integer> stringTable = new HashMap<>();

  public void add(String string) {
    if (!this.stringTable.containsKey(string)) {
      this.stringTable.put(string, index++);
    }
  }

  public boolean containsString(String string) {
    return stringTable.containsKey(string);
  }

  public int getIndex(String string) {
    if (!stringTable.containsKey(string)) {
      stringTable.put(string, index++);
    }

    return stringTable.get(string);
  }


  @Override
  public void accept(ImageRenderer renderer) {

  }

  @Override
  public ImageType imageType() {
    return ImageType.STRING_TABLE;
  }

  @Override
  public byte[] asArray() {
    return new byte[0];
  }

}
