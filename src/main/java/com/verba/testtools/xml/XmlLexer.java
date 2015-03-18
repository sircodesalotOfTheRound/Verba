package com.verba.testtools.xml;

import java.util.Stack;

/**
 * Created by sircodesalot on 14/8/31.
 */
public class XmlLexer {
  private final Stack<Integer> undoStack = new Stack<>();
  private final String text;
  private int index;

  public class XmlLexerException extends RuntimeException {
    public XmlLexerException(String format, Object... args) {
      super(String.format(format, args));
    }
  }

  public XmlLexer(String text) {
    this.text = text;
    this.index = 0;
  }

  public void pushUndoLocation() {
    this.undoStack.push(this.index);
  }

  public void revertToUndoLocation() {
    this.index = this.undoStack.pop();
  }

  public boolean isEof() {
    return this.index >= this.text.length();
  }

  public boolean currentIs(Character letter) {
    return current() == letter;
  }

  public Character current() {
    return text.charAt(index);
  }

  public void advance() {
    this.index++;
  }

  public void skipWhitespaces() {
    while (!isEof()) {
      if (Character.isWhitespace(current())) {
        advance();
      } else {
        break;
      }
    }
  }

  public Character readAndAdvanceSkipWitespaces(Character letter) {
    readAndAdvance(letter);
    skipWhitespaces();

    return letter;
  }

  public Character readAndAdvanceSkipWitespaces() {
    Character letter = readAndAdvance();
    skipWhitespaces();

    return letter;
  }

  public Character readAndAdvance(Character letter) {
    if (!currentIs(letter)) {
      throw new XmlLexerException("Letter at %s was not %s", index, letter);
    }

    advance();
    return letter;
  }

  public Character readAndAdvance() {
    Character current = current();
    advance();

    return current;
  }
}
