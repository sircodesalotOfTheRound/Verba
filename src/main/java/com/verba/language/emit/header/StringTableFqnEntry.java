package com.verba.language.emit.header;

import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 14/12/6.
 */
public class StringTableFqnEntry {
  private final int length;
  private final String text;
  private final QIterable<StringTableStringEntry> fqn;

  public StringTableFqnEntry(String text, QIterable<StringTableStringEntry> fqn) {
    this.length = (int)fqn.count();
    this.text = text;
    this.fqn = fqn;
  }

  public int length() { return this.length; }
  public String text() { return this.text; }
  public QIterable<StringTableStringEntry> entries() { return this.fqn; }
}
