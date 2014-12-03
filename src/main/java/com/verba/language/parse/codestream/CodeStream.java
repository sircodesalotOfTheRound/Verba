package com.verba.language.parse.codestream;

/**
 * Created by sircodesalot on 14-2-16
 */
public interface CodeStream {
  public Character peek();

  public Character read();

  public boolean hasNext();

  public int column();

  public int line();

  public int peekLineForNextChar();

  public int absolutePosition();

  public int getFileLength();

  public String filename();

  public void setUndoPosition();

  public void rollbackToUndoPosition();

  public String text();
}
