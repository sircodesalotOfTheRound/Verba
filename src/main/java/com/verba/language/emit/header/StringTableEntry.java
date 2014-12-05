package com.verba.language.emit.header;

/**
 * Created by sircodesalot on 14/12/5.
 */
public class StringTableEntry {
  private final String text;
  private final int index;

  public StringTableEntry(String text, int index) {
    this.text = text;
    this.index = index;
  }

  public String text() { return this.text; }
  public int index() { return this.index; }
}
