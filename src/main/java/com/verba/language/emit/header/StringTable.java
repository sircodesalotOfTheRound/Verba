package com.verba.language.emit.header;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.images.interfaces.ObjectImage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/11/22.
 */
public class StringTable {
  private int index;
  private final QList<StringTableStringEntry> entriesByIndex = new QList<>();
  private final Map<String, StringTableStringEntry> stringTable = new HashMap<>();

  public StringTableStringEntry addString(String string) {
    if (!this.stringTable.containsKey(string)) {
      StringTableStringEntry newEntry = new StringTableStringEntry(string, index++);

      this.stringTable.put(string, newEntry);
      this.entriesByIndex.add(newEntry);
      return new StringTableStringEntry(string, index - 1);
    }

    return this.stringTable.get(string);
  }

  public StringTableFqnEntry addFqn(String fqn) {
    QIterable<StringTableStringEntry> fqnList = new QList<>(fqn.split("\\."))
      .map(this::addString);

    return new StringTableFqnEntry(fqn, fqnList);
  }

  public StringTableStringEntry findByIndex(int index) {
    return entriesByIndex.get(index);
  }

  public ObjectImage image() {
    return null;
  }
}
