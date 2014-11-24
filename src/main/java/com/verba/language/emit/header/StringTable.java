package com.verba.language.emit.header;

import com.verba.language.emit.images.ImageSegment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/11/22.
 */
public class StringTable extends ImageSegment {
  private int index;
  private final Map<String, Integer> stringTable = new HashMap<>();

  public StringTable(ImageSegmentType type, Iterable<Byte> data) {
    super(ImageSegmentType.STRING_TABLE);
  }

  public void add(String string) {
    if (!this.stringTable.containsKey(string)) {
      this.stringTable.put(string, index++);
    }
  }

  public boolean containsString(String string) {
    return stringTable.containsKey(string);
  }

  public int getIndex(String string) {
    return stringTable.get(string);
  }

  @Override
  public Iterable<Byte> data() { return null; }
}
