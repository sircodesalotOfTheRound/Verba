package com.verba.language.parse.info;

/**
 * Created by sircodesalot on 14/9/18.
 */
public class LexedRange {
  private final LexInfo start;
  private final LexInfo end;

  public LexedRange(LexInfo start, LexInfo end) {
    this.start = start;
    this.end = end;
  }

  public LexInfo start() { return this.start; }
  public LexInfo end() { return this.end; }
}
