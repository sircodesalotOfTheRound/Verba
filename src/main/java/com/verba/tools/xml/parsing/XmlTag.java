package com.verba.tools.xml.parsing;

import com.verba.tools.xml.XmlLexer;

/**
 * Created by sircodesalot on 14/9/1.
 */
public class XmlTag {

  private final String name;
  private final XmlTagType type;

  public XmlTag(String text, XmlTagType type) {
    this.name = text;
    this.type = type;
  }

  public String name() {
    return name;
  }

  public XmlTagType type() {
    return type;
  }

  public static XmlTag peekNextTag(XmlLexer lexer) {
    lexer.pushUndoLocation();
    XmlTag tag = parse(lexer);
    lexer.revertToUndoLocation();

    return tag;
  }

  public static XmlTag parse(XmlLexer lexer) {
    lexer.skipWhitespaces();
    lexer.readAndAdvanceSkipWitespaces('<');

    if (lexer.currentIs('/')) {
      lexer.readAndAdvanceSkipWitespaces('/');
      return readClosingTag(lexer);
    } else if (isSelfClosedTag(lexer)) {
      return readSelfClosedTag(lexer);
    } else {
      return readOpeningTag(lexer);
    }
  }

  public static boolean isSelfClosedTag(XmlLexer lexer) {
    boolean sawClosingSlash = false;

    lexer.pushUndoLocation();

    while (!lexer.currentIs('>')) {
      if (lexer.currentIs('/')) {
        sawClosingSlash = true;
      }

      lexer.advance();
    }

    lexer.revertToUndoLocation();

    return sawClosingSlash;
  }

  private static XmlTag readOpeningTag(XmlLexer lexer) {
    StringBuilder content = new StringBuilder();

    while (!lexer.currentIs('>')) {
      content.append(lexer.readAndAdvance());
    }

    lexer.readAndAdvanceSkipWitespaces('>');

    return new XmlTag(content.toString().trim(), XmlTagType.OPENING);
  }

  private static XmlTag readClosingTag(XmlLexer lexer) {
    StringBuilder content = new StringBuilder();

    while (!lexer.currentIs('>')) {
      content.append(lexer.readAndAdvance());
    }

    lexer.readAndAdvanceSkipWitespaces('>');

    return new XmlTag(content.toString().trim(), XmlTagType.CLOSING);
  }

  private static XmlTag readSelfClosedTag(XmlLexer lexer) {
    StringBuilder content = new StringBuilder();

    while (!lexer.currentIs('/')) {
      content.append(lexer.readAndAdvance());
    }

    lexer.readAndAdvanceSkipWitespaces('/');
    lexer.readAndAdvanceSkipWitespaces('>');

    return new XmlTag(content.toString().trim(), XmlTagType.SELF_CLOSED);
  }
}
