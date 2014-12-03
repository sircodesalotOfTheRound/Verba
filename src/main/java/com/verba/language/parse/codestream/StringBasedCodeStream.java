package com.verba.language.parse.codestream;

import java.util.Stack;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class StringBasedCodeStream implements CodeStream {
  private final int NEWLINE_CHARACTER = 10;
  private final String filename;
  private final String text;
  private int index = 0;
  private int lineNumber = 1;
  private int currentPosition = 1;
  private Stack<Integer> undoStack = new Stack<>();

  public StringBasedCodeStream(String filename, String text) {
    this.text = text;
    this.filename = filename;
  }

  public StringBasedCodeStream(CharSequence text) {
    this("", text);
  }

  public StringBasedCodeStream(String filename, CharSequence text) {
    this(filename, text.toString());
  }

  @Override
  public Character peek() {
    return this.text.charAt(index);
  }

  @Override
  public Character read() {
    if (this.text.charAt(index) == NEWLINE_CHARACTER) {
      this.lineNumber += 1;
      this.currentPosition = 0;
    }

    Character next = this.text.charAt(index);
    this.currentPosition += 1;
    this.index += 1;

    return next;
  }

  @Override
  public int peekLineForNextChar() {
    if (this.text.charAt(index) == NEWLINE_CHARACTER) {
      return this.lineNumber + 1;
    } else return this.lineNumber;
  }

  @Override
  public int getFileLength() {
    return this.text.length();
  }

  @Override
  public String filename() {
    return this.filename;
  }

  @Override
  public void setUndoPosition() {
    this.undoStack.push(this.index);
  }

  @Override
  public void rollbackToUndoPosition() {
    this.index = this.undoStack.pop();
  }

  @Override
  public String text() { return this.text; }

  @Override
  public boolean hasNext() {
    return this.index < text.length();
  }

  @Override
  public int absolutePosition() {
    return this.index;
  }

  public int line() {
    return this.lineNumber;
  }

  public int column() {
    return this.currentPosition;
  }

}
