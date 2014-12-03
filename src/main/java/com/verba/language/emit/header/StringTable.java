package com.verba.language.emit.header;

import com.verba.language.emit.images.interfaces.ObjectImage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/11/22.
 */
public class StringTable {
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

  public ObjectImage image() {
    return null;
  }
}
