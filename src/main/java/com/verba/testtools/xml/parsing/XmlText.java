package com.verba.testtools.xml.parsing;

import com.verba.testtools.xml.XmlLexer;

/**
 * Created by sircodesalot on 14/9/1.
 */
public class XmlText {
  private final String text;

  public XmlText(String text) {
    this.text = text;
  }

  public boolean isEmpty() {
    return text.isEmpty();
  }

  public static XmlText parse(XmlLexer lexer) {
    StringBuilder text = new StringBuilder();

    while (!lexer.isEof() && !lexer.currentIs('<')) {
      text.append(lexer.readAndAdvance());
    }

    return new XmlText(text.toString());
  }

  @Override
  public String toString() {
    return this.text;
  }
}
