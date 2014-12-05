package com.verba.language.emit.header;

import com.verba.language.emit.images.interfaces.ObjectImage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/11/22.
 */
public class StringTable {
  private int index;
  private final Map<String, StringTableEntry> stringTable = new HashMap<>();

  public StringTableEntry add(String string) {
    if (!this.stringTable.containsKey(string)) {
      StringTableEntry newEntry = new StringTableEntry(string, index++);
      this.stringTable.put(string, newEntry);
      return new StringTableEntry(string, index - 1);
    }

    return this.stringTable.get(string);
  }

  public ObjectImage image() {
    return null;
  }
}
