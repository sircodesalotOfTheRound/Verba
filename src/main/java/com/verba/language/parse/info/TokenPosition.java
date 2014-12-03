package com.verba.language.parse.info;

import com.verba.language.parse.codestream.CodeStream;

/**
 * Created by sircodesalot on 14-4-12.
 */
public class TokenPosition {
  private final String filename;
  private final int absolutePosition;
  private final int line;
  private final int column;

  public TokenPosition(CodeStream stream) {
    this.absolutePosition = stream.absolutePosition();
    this.line = stream.line();
    this.column = stream.column();
    this.filename = stream.filename();
  }

  public int absolutePosition() {
    return absolutePosition;
  }

  public int line() {
    return line;
  }

  public int column() {
    return column;
  }

  public String filename() {
    return this.filename;
  }
}
