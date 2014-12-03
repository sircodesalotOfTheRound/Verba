package com.verba.language.parse.tokens.ignorable;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.tokenization.Token;

/**
 * Created by sircodesalot on 14-3-1.
 */
public class WhitespaceToken implements Token {
  private final static char NEWLINE_CHARACTER = '\n';
  private String representation;

  @Override
  public String toString() {
    return this.representation;
  }

  private WhitespaceToken(CodeStream stream) {
    this.representation = consumeSpaces(stream);
  }

  public static boolean isWhitespaceToken(Character next) {
    return (Character.isSpaceChar(next) || next == NEWLINE_CHARACTER);
  }

  public static WhitespaceToken read(CodeStream stream) {
    return new WhitespaceToken(stream);
  }

  private String consumeSpaces(CodeStream codeStream) {
    StringBuilder representation = new StringBuilder();

    // If the next item is a newline, read it as a separate token.
    // Else add the whitespace to the representation.
    if (codeStream.peek() == NEWLINE_CHARACTER) return codeStream.read().toString();
    else representation.append(codeStream.read());

    // else, read whitespaces until we reach something that is either not a whitespace
    // or we reach a newline (which is technically a whitespace, but shouldn't be included
    // in a regular whitespace set).
    while (codeStream.hasNext() && WhitespaceToken.isWhitespaceToken(codeStream.peek())) {
      char next = codeStream.peek();

      if (next == NEWLINE_CHARACTER) break;
      else representation.append(codeStream.read());
    }

    return representation.toString();
  }
}
